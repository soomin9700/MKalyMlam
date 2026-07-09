package com.mkalymlam.controller;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mkalymlam.dto.ApprovisionnementForm;
import com.mkalymlam.entity.Approvisionnement;
import com.mkalymlam.entity.DetailApprovisionnement;
import com.mkalymlam.service.ApprovisionnementService;

@Controller
@RequestMapping("/approvisionnements")
public class ApprovisionnementController {

    private static final int PDF_LINES_PER_PAGE = 34;

    private final ApprovisionnementService approvisionnementService;

    public ApprovisionnementController(ApprovisionnementService approvisionnementService) {
        this.approvisionnementService = approvisionnementService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("besoins", approvisionnementService.calculerBesoins());
        model.addAttribute("form", new ApprovisionnementForm());
        model.addAttribute("historiques", approvisionnementService.historique());
        return "approvisionnement/index";
    }

    @PostMapping("/enregistrer")
    public String enregistrer(@ModelAttribute("form") ApprovisionnementForm form,
                              RedirectAttributes redirectAttributes) {
        Approvisionnement approvisionnement = approvisionnementService.enregistrer(form);
        redirectAttributes.addFlashAttribute("successMessage",
                "Approvisionnement enregistre avec un cout estime de "
                        + approvisionnement.getCoutTotalEstime());
        return "redirect:/approvisionnements";
    }

    @GetMapping("/{idApprovisionnement}/export/csv")
    public ResponseEntity<byte[]> exportCsv(@PathVariable Long idApprovisionnement) {
        List<DetailApprovisionnement> details = approvisionnementService.details(idApprovisionnement);
        Approvisionnement approvisionnement = approvisionnementService.findById(idApprovisionnement);
        byte[] content = buildDetailCsv(details, approvisionnement).getBytes(StandardCharsets.UTF_8);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"approvisionnement-" + idApprovisionnement + ".csv\"")
                .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
                .body(content);
    }

    @GetMapping("/{idApprovisionnement}/export/pdf")
    public ResponseEntity<byte[]> exportPdf(@PathVariable Long idApprovisionnement) {
        List<DetailApprovisionnement> details = approvisionnementService.details(idApprovisionnement);
        Approvisionnement approvisionnement = approvisionnementService.findById(idApprovisionnement);
        byte[] content = buildDetailPdf(details, approvisionnement);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"approvisionnement-" + idApprovisionnement + ".pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(content);
    }

    @GetMapping("/{idApprovisionnement}")
    public String detail(@PathVariable Long idApprovisionnement, Model model) {
        model.addAttribute("approvisionnement", approvisionnementService.findById(idApprovisionnement));
        model.addAttribute("details", approvisionnementService.details(idApprovisionnement));
        return "approvisionnement/detail";
    }

    private String buildDetailCsv(List<DetailApprovisionnement> details, Approvisionnement approvisionnement) {
        StringBuilder csv = new StringBuilder();
        csv.append('\uFEFF');
        csv.append("Ingredient,Stock actuel,Prix estime unitaire,Quantite a acheter,Cout estime\n");

        for (DetailApprovisionnement d : details) {
            csv.append(csvValue(d.getIngredient() != null ? d.getIngredient().getNomIngredient() : ""))
                    .append(',')
                    .append(csvValue(d.getStockActuel()))
                    .append(',')
                    .append(csvValue(d.getPrixEstimeUnitaire()))
                    .append(',')
                    .append(csvValue(d.getQuantiteAAcheter()))
                    .append(',')
                    .append(csvValue(d.getCoutEstime()))
                    .append('\n');
        }

        csv.append("\nTotal estime,").append(csvValue(approvisionnement.getCoutTotalEstime())).append('\n');
        return csv.toString();
    }

    private String csvValue(Object value) {
        String text = value == null ? "" : value.toString();
        return "\"" + text.replace("\"", "\"\"") + "\"";
    }

    private byte[] buildDetailPdf(List<DetailApprovisionnement> details, Approvisionnement approvisionnement) {
        List<String> lines = new ArrayList<>();
        lines.add("Approvisionnement N\u00B0 " + approvisionnement.getIdApprovisionnement()
                + " - " + approvisionnement.getDateApprovisionnement());
        lines.add("");
        lines.add("Ingredient | Stock actuel | Prix estime | Quantite a acheter | Cout estime");
        lines.add("------------------------------------------------------------------------");

        for (DetailApprovisionnement d : details) {
            lines.add(safePdfText(d.getIngredient() != null ? d.getIngredient().getNomIngredient() : "") + " | "
                    + safePdfText(d.getStockActuel()) + " | "
                    + safePdfText(d.getPrixEstimeUnitaire()) + " | "
                    + safePdfText(d.getQuantiteAAcheter()) + " | "
                    + safePdfText(d.getCoutEstime()));
        }

        lines.add("");
        lines.add("Total estime : " + safePdfText(approvisionnement.getCoutTotalEstime()));

        if (details.isEmpty()) {
            lines.add("Aucun detail pour cet approvisionnement.");
        }

        return writeSimplePdf(lines);
    }

    private byte[] writeSimplePdf(List<String> lines) {
        int pageCount = Math.max(1, (int) Math.ceil(lines.size() / (double) PDF_LINES_PER_PAGE));
        int fontObjectNumber = 3 + pageCount * 2;
        List<String> objects = new ArrayList<>();

        objects.add("<< /Type /Catalog /Pages 2 0 R >>");

        StringBuilder kids = new StringBuilder();
        for (int page = 0; page < pageCount; page++) {
            kids.append(3 + page * 2).append(" 0 R ");
        }
        objects.add("<< /Type /Pages /Kids [" + kids + "] /Count " + pageCount + " >>");

        for (int page = 0; page < pageCount; page++) {
            int pageObjectNumber = 3 + page * 2;
            int contentObjectNumber = pageObjectNumber + 1;
            objects.add("<< /Type /Page /Parent 2 0 R /MediaBox [0 0 595 842] "
                    + "/Resources << /Font << /F1 " + fontObjectNumber + " 0 R >> >> "
                    + "/Contents " + contentObjectNumber + " 0 R >>");
            String stream = buildPdfPageStream(lines, page);
            objects.add("<< /Length " + stream.getBytes(StandardCharsets.ISO_8859_1).length + " >>\nstream\n"
                    + stream + "endstream");
        }

        objects.add("<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica >>");

        ByteArrayOutputStream pdf = new ByteArrayOutputStream();
        writePdfPart(pdf, "%PDF-1.4\n");
        List<Integer> offsets = new ArrayList<>();

        for (int index = 0; index < objects.size(); index++) {
            offsets.add(pdf.size());
            writePdfPart(pdf, (index + 1) + " 0 obj\n" + objects.get(index) + "\nendobj\n");
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

    private String buildPdfPageStream(List<String> lines, int page) {
        int start = page * PDF_LINES_PER_PAGE;
        int end = Math.min(start + PDF_LINES_PER_PAGE, lines.size());
        StringBuilder stream = new StringBuilder();
        stream.append("BT\n/F1 10 Tf\n14 TL\n50 795 Td\n");

        for (int i = start; i < end; i++) {
            stream.append('(').append(escapePdf(lines.get(i))).append(") Tj\nT*\n");
        }

        stream.append("ET\n");
        return stream.toString();
    }

    private void writePdfPart(ByteArrayOutputStream pdf, String text) {
        byte[] bytes = text.getBytes(StandardCharsets.ISO_8859_1);
        pdf.write(bytes, 0, bytes.length);
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
}
