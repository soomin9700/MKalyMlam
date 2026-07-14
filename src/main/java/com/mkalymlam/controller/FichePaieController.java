package com.mkalymlam.controller;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mkalymlam.entity.FichePaie;
import com.mkalymlam.entity.Utilisateur;
import com.mkalymlam.service.FichePaieService;

@Controller
@RequestMapping("/fiches-paie")
public class FichePaieController {

    private final FichePaieService fichePaieService;
    private static final int[] COL_X = {40, 80, 235, 320, 410, 500};
    private static final int COL_END = 555;
    private static final String[] COL_HDR = {"ID", "Employe", "Mois", "Brut (Ar)", "Net verse (Ar)", "Date"};
    private static final int ROW_H = 20;
    private static final int HDR_H = 22;
    private static final int FIRST_PAGE_MAX = 31;
    private static final int OTHER_PAGE_MAX = 35;

    public FichePaieController(FichePaieService fichePaieService) {
        this.fichePaieService = fichePaieService;
    }

    @GetMapping
    public String list(Model model,
                       @RequestParam(required = false) Long idUtilisateur,
                       @RequestParam(required = false) String moisAnnee) {
        model.addAttribute("fichesPaie", fichePaieService.findFiltered(idUtilisateur, moisAnnee));
        model.addAttribute("selectedUtilisateur", idUtilisateur);
        model.addAttribute("selectedMoisAnnee", moisAnnee);
        return "fichePaie/list";
    }

    @GetMapping("/export/csv")
    public ResponseEntity<byte[]> exportCsv(@RequestParam(required = false) Long idUtilisateur,
                                            @RequestParam(required = false) String moisAnnee) {
        List<FichePaie> fichesPaie = fichePaieService.findFiltered(idUtilisateur, moisAnnee);
        byte[] content = buildCsv(fichesPaie).getBytes(StandardCharsets.UTF_8);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"fiches-paie.csv\"")
                .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
                .body(content);
    }

    @GetMapping("/export/pdf")
    public ResponseEntity<byte[]> exportPdf(@RequestParam(required = false) Long idUtilisateur,
                                            @RequestParam(required = false) String moisAnnee) {
        List<FichePaie> fichesPaie = fichePaieService.findFiltered(idUtilisateur, moisAnnee);
        byte[] content = buildPdf(fichesPaie, moisAnnee);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"fiches-paie.pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(content);
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("fichePaie", new FichePaie());
        model.addAttribute("utilisateurs", fichePaieService.findEmployes());
        return "fichePaie/form";
    }

    @GetMapping("/exists")
    @ResponseBody
    public Map<String, Object> exists(@RequestParam Long idUtilisateur,
                                      @RequestParam String moisAnnee) {
        Map<String, Object> response = new HashMap<>();
        response.put("exists", fichePaieService.findExisting(idUtilisateur, moisAnnee).isPresent());
        return response;
    }

    @PostMapping("/generate")
    public String generate(@RequestParam(required = false) Long idUtilisateur,
                           @RequestParam String moisAnnee,
                           RedirectAttributes redirectAttributes) {
        if (idUtilisateur == null) {
            List<FichePaie> fichesPaie = fichePaieService.genererTous(moisAnnee);
            redirectAttributes.addFlashAttribute("successMessage",
                    fichesPaie.size() + " fiche(s) de paie generee(s) ou regeneree(s) pour le mois " + moisAnnee);
            return "redirect:/fiches-paie?moisAnnee=" + moisAnnee;
        }

        FichePaie fichePaie = fichePaieService.generer(idUtilisateur, moisAnnee);
        redirectAttributes.addFlashAttribute("successMessage",
                "Fiche de paie generee pour "
                        + fichePaie.getUtilisateur().getNom()
                        + " "
                        + fichePaie.getUtilisateur().getPrenom());
        return "redirect:/fiches-paie";
    }

    @ModelAttribute("employes")
    public List<Utilisateur> employes() {
        return fichePaieService.findEmployes();
    }

    private String buildCsv(List<FichePaie> fichesPaie) {
        StringBuilder csv = new StringBuilder();
        csv.append('\uFEFF');
        csv.append("ID,Employe,Mois,Brut,Net verse,Date paiement\n");

        for (FichePaie fiche : fichesPaie) {
            csv.append(csvValue(fiche.getIdFiche()))
                    .append(',')
                    .append(csvValue(nomEmploye(fiche)))
                    .append(',')
                    .append(csvValue(fiche.getMoisAnnee()))
                    .append(',')
                    .append(csvValue(fiche.getMontantFixeBrut()))
                    .append(',')
                    .append(csvValue(fiche.getMontantNetVerse()))
                    .append(',')
                    .append(csvValue(fiche.getDatePaiement()))
                    .append('\n');
        }

        return csv.toString();
    }

    private String csvValue(Object value) {
        String text = value == null ? "" : value.toString();
        return "\"" + text.replace("\"", "\"\"") + "\"";
    }

    private byte[] buildPdf(List<FichePaie> fichesPaie, String moisAnnee) {
        int totalRows = fichesPaie.size();
        int pageCount;
        if (totalRows <= FIRST_PAGE_MAX) {
            pageCount = 1;
        } else {
            pageCount = 1 + (int) Math.ceil((double) (totalRows - FIRST_PAGE_MAX) / OTHER_PAGE_MAX);
        }

        List<String> streams = new ArrayList<>();
        for (int p = 0; p < pageCount; p++) {
            streams.add(buildPageStream(fichesPaie, moisAnnee, p, pageCount));
        }

        int fontObjNum = 3 + pageCount * 2;
        List<String> objects = new ArrayList<>();

        objects.add("<< /Type /Catalog /Pages 2 0 R >>");

        StringBuilder kids = new StringBuilder();
        for (int p = 0; p < pageCount; p++) {
            kids.append(3 + p * 2).append(" 0 R ");
        }
        objects.add("<< /Type /Pages /Kids [" + kids.toString().trim() + "] /Count " + pageCount + " >>");

        for (int p = 0; p < pageCount; p++) {
            int pageObj = 3 + p * 2;
            int contentObj = pageObj + 1;
            objects.add("<< /Type /Page /Parent 2 0 R /MediaBox [0 0 595 842] "
                    + "/Resources << /Font << /F1 " + fontObjNum + " 0 R /F2 " + (fontObjNum + 1) + " 0 R >> >> "
                    + "/Contents " + contentObj + " 0 R >>");
            String stream = streams.get(p);
            objects.add("<< /Length " + stream.getBytes(StandardCharsets.ISO_8859_1).length
                    + " >>\nstream\n" + stream + "endstream");
        }

        objects.add("<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica >>");
        objects.add("<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica-Bold >>");

        ByteArrayOutputStream pdf = new ByteArrayOutputStream();
        writePdfPart(pdf, "%PDF-1.4\n");
        List<Integer> offsets = new ArrayList<>();

        for (int i = 0; i < objects.size(); i++) {
            offsets.add(pdf.size());
            writePdfPart(pdf, (i + 1) + " 0 obj\n" + objects.get(i) + "\nendobj\n");
        }

        int xrefOffset = pdf.size();
        writePdfPart(pdf, "xref\n0 " + (objects.size() + 1) + "\n");
        writePdfPart(pdf, "0000000000 65535 f \n");
        for (Integer offset : offsets) {
            writePdfPart(pdf, String.format("%010d 00000 n \n", offset));
        }
        writePdfPart(pdf, "trailer\n<< /Size " + (objects.size() + 1) + " /Root 1 0 R >>\n");
        writePdfPart(pdf, "startxref\n" + xrefOffset + "\n%%EOF");

        return pdf.toByteArray();
    }

    private String buildPageStream(List<FichePaie> fichesPaie, String moisAnnee, int page, int totalPages) {
        StringBuilder s = new StringBuilder();
        boolean first = (page == 0);

        int startRow, endRow;
        if (first) {
            startRow = 0;
            endRow = Math.min(fichesPaie.size(), FIRST_PAGE_MAX);
        } else {
            startRow = FIRST_PAGE_MAX + (page - 1) * OTHER_PAGE_MAX;
            endRow = Math.min(fichesPaie.size(), startRow + OTHER_PAGE_MAX);
        }

        int tableLeft = COL_X[0];
        int tableWidth = COL_END - COL_X[0];
        double cursor = 790;

        if (first) {
            s.append("0.17 0.24 0.42 rg\n");
            s.append("0 808 595 34 re f\n");
            s.append("1 1 1 rg\n");
            s.append("BT\n/F2 18 Tf\n248 820 Td\n(MKALY MLAM) Tj\nET\n");

            String subtitle = "Liste des fiches de paie";
            if (moisAnnee != null && !moisAnnee.isBlank()) {
                subtitle += " - " + moisAnnee;
            }
            s.append("0.2 0.2 0.2 rg\n");
            s.append("BT\n/F1 11 Tf\n");
            s.append((297.5 - subtitle.length() * 3) + " " + cursor + " Td\n");
            s.append("(" + escapePdf(safePdfText(subtitle)) + ") Tj\nET\n");
            cursor -= 18;

            s.append("0.82 0.84 0.87 RG\n0.5 w\n");
            s.append(tableLeft + " " + cursor + " m " + COL_END + " " + cursor + " l S\n");
            cursor -= 20;
        } else {
            s.append("0.2 0.2 0.2 rg\n");
            s.append("BT\n/F1 11 Tf\n");
            String contTitle = "Liste des fiches de paie (suite)";
            s.append((297.5 - contTitle.length() * 3) + " " + cursor + " Td\n");
            s.append("(" + contTitle + ") Tj\nET\n");
            cursor -= 18;

            s.append("0.82 0.84 0.87 RG\n0.5 w\n");
            s.append(tableLeft + " " + cursor + " m " + COL_END + " " + cursor + " l S\n");
            cursor -= 20;
        }

        double tableTop = cursor;

        if (fichesPaie.isEmpty()) {
            s.append("0.4 0.4 0.4 rg\n");
            s.append("BT\n/F1 12 Tf\n200 650 Td\n(Aucune fiche de paie trouvee.) Tj\nET\n");
        } else {
            s.append("0.17 0.24 0.42 rg\n");
            s.append(tableLeft + " " + (cursor - HDR_H) + " " + tableWidth + " " + HDR_H + " re f\n");

            s.append("1 1 1 rg\n");
            s.append("BT\n/F2 9 Tf\n" + (COL_X[0] + 5) + " " + (cursor - 14) + " Td\n(ID) Tj\nET\n");
            s.append("BT\n/F2 9 Tf\n" + (COL_X[1] + 5) + " " + (cursor - 14) + " Td\n(Employe) Tj\nET\n");
            s.append("BT\n/F2 9 Tf\n" + (COL_X[2] + 5) + " " + (cursor - 14) + " Td\n(Mois) Tj\nET\n");
            s.append("BT\n/F2 9 Tf\n" + (COL_X[4] - 5 - "Brut (Ar)".length() * 5.5) + " " + (cursor - 14) + " Td\n(Brut (Ar)) Tj\nET\n");
            s.append("BT\n/F2 9 Tf\n" + (COL_X[5] - 5 - "Net verse (Ar)".length() * 5.5) + " " + (cursor - 14) + " Td\n(Net verse (Ar)) Tj\nET\n");
            s.append("BT\n/F2 9 Tf\n" + (COL_X[5] + 5) + " " + (cursor - 14) + " Td\n(Date) Tj\nET\n");

            cursor -= HDR_H;

            for (int i = startRow; i < endRow; i++) {
                FichePaie fiche = fichesPaie.get(i);
                int rowIdx = i - startRow;

                if (rowIdx % 2 == 1) {
                    s.append("0.95 0.96 0.97 rg\n");
                    s.append(tableLeft + " " + (cursor - ROW_H) + " " + tableWidth + " " + ROW_H + " re f\n");
                }

                double textY = cursor - ROW_H + 5;
                String[] values = {
                    safePdfText(fiche.getIdFiche()),
                    safePdfText(nomEmploye(fiche)),
                    safePdfText(fiche.getMoisAnnee()),
                    safePdfText(formatMontant(fiche.getMontantFixeBrut())),
                    safePdfText(formatMontant(fiche.getMontantNetVerse())),
                    safePdfText(fiche.getDatePaiement())
                };

                s.append("0.1 0.1 0.1 rg\n");
                s.append("BT\n/F1 9 Tf\n" + (COL_X[0] + 5) + " " + textY + " Td\n(" + escapePdf(values[0]) + ") Tj\nET\n");
                s.append("BT\n/F1 9 Tf\n" + (COL_X[1] + 5) + " " + textY + " Td\n(" + escapePdf(values[1]) + ") Tj\nET\n");
                s.append("BT\n/F1 9 Tf\n" + (COL_X[2] + 5) + " " + textY + " Td\n(" + escapePdf(values[2]) + ") Tj\nET\n");
                s.append("BT\n/F1 9 Tf\n" + (COL_X[4] - 5 - values[3].length() * 4.5) + " " + textY + " Td\n(" + escapePdf(values[3]) + ") Tj\nET\n");
                s.append("BT\n/F1 9 Tf\n" + (COL_X[5] - 5 - values[4].length() * 4.5) + " " + textY + " Td\n(" + escapePdf(values[4]) + ") Tj\nET\n");
                s.append("BT\n/F1 9 Tf\n" + (COL_X[5] + 5) + " " + textY + " Td\n(" + escapePdf(values[5]) + ") Tj\nET\n");

                cursor -= ROW_H;
            }
        }

        double tableBottom = cursor;

        if (!fichesPaie.isEmpty()) {
            s.append("0.82 0.84 0.87 RG\n0.5 w\n");
            s.append(tableLeft + " " + tableTop + " m " + COL_END + " " + tableTop + " l S\n");
            s.append(tableLeft + " " + tableBottom + " m " + COL_END + " " + tableBottom + " l S\n");
            s.append(tableLeft + " " + (tableTop - HDR_H) + " m " + COL_END + " " + (tableTop - HDR_H) + " l S\n");
            for (int c = 1; c < COL_X.length; c++) {
                s.append(COL_X[c] + " " + tableBottom + " m " + COL_X[c] + " " + tableTop + " l S\n");
            }
            s.append(COL_END + " " + tableBottom + " m " + COL_END + " " + tableTop + " l S\n");
        }

        s.append("0.17 0.24 0.42 rg\n40 42 515 1.5 re f\n");
        s.append("BT\n/F1 8 Tf\n0.4 0.4 0.5 rg\n");
        s.append("275 30 Td\n(Page " + (page + 1) + " / " + totalPages + ") Tj\nET\n");

        return s.toString();
    }

    private void writePdfPart(ByteArrayOutputStream pdf, String text) {
        byte[] bytes = text.getBytes(StandardCharsets.ISO_8859_1);
        pdf.write(bytes, 0, bytes.length);
    }

    private String formatMontant(Object value) {
        if (value == null) return "";
        if (value instanceof Number) {
            return String.format("%,.0f", ((Number) value).doubleValue());
        }
        return value.toString();
    }

    private String safePdfText(Object value) {
        if (value == null) {
            return "";
        }
        String normalized = Normalizer.normalize(value.toString(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        return normalized.replaceAll("[^\\x20-\\x7E]", "");
    }

    private String escapePdf(String value) {
        return value.replace("\\", "\\\\")
                .replace("(", "\\(")
                .replace(")", "\\)");
    }

    private String nomEmploye(FichePaie fiche) {
        if (fiche.getUtilisateur() == null) {
            return "";
        }
        return fiche.getUtilisateur().getNom() + " " + fiche.getUtilisateur().getPrenom();
    }
}
