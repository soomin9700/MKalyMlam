package com.mkalymlam.controller;

import java.time.LocalDate;
import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

import java.io.IOException;
import java.io.PrintWriter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mkalymlam.entity.DisponibiliteProduit;
import com.mkalymlam.entity.Produit;
import com.mkalymlam.entity.ProduitAvecDisponibilite;
import com.mkalymlam.repository.ProduitAvecDisponibiliteRepository;
import com.mkalymlam.service.DisponibiliteService;
import com.mkalymlam.service.ProduitAvecDisponibiliteService;
import com.mkalymlam.service.ProduitService;
import com.mkalymlam.service.CsvExcelImportService;

@Controller
@RequestMapping("/produits")
public class ProduitController {

    private final ProduitService service;
    private final CsvExcelImportService csvExcelImportService;
    private final ProduitAvecDisponibiliteRepository produitVueRepository;
    private final DisponibiliteService disponibiliteService;

    public ProduitController(ProduitService service, CsvExcelImportService csvExcelImportService,
            ProduitAvecDisponibiliteRepository produitVueRepository, DisponibiliteService disponibiliteService) {
        this.service = service;
        this.csvExcelImportService = csvExcelImportService;
        this.produitVueRepository = produitVueRepository;
        this.disponibiliteService = disponibiliteService;
    }

    @GetMapping
    public String list(
            @RequestParam(required = false) String nomProduit,
            @RequestParam(required = false) Boolean nouveauProduit,
            @RequestParam(required = false) Boolean estDisponible,
            @RequestParam(required = false) Boolean estIndisponible,
            Model model) {

        // Utiliser directement la vue

        if (estIndisponible != null && estIndisponible) {
            estDisponible = false;
        }

        List<ProduitAvecDisponibilite> produitsVue = produitVueRepository.findByCriteria(
                nomProduit, estDisponible, nouveauProduit);

        // Convertir en Produit pour garder la compatibilité avec la JSP
        List<Produit> produits = produitsVue.stream()
                .map(p -> {
                    Produit produit = new Produit();
                    produit.setIdProduit(p.getIdProduit());
                    produit.setNomProduit(p.getNomProduit());
                    produit.setPrixBase(p.getPrixBase());
                    produit.setEstNouveau(p.getEstNouveau());
                    produit.setDateCreation(p.getDateCreation());
                    produit.setEstDisponible(p.getEstDisponible());
                    return produit;
                })
                .collect(Collectors.toList());

        model.addAttribute("produits", produits);
        model.addAttribute("totalProduits", produits.size());
        return "produit/list";
    }

    @GetMapping("/liste")
    @ResponseBody
    public List<Produit> liste() {
        return service.findAll();
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("produit", new Produit());
        model.addAttribute("isEdit", false);
        model.addAttribute("actionUrl", "/produits");
        return "produit/form";
    }

    @PostMapping
    public String create(
            @RequestParam String nomProduit,
            @RequestParam Double prixBase,
            @RequestParam(required = false) Boolean estNouveau) {

        Produit produit = new Produit();

        produit.setNomProduit(nomProduit);
        produit.setPrixBase(prixBase);
        produit.setEstNouveau(
                estNouveau != null ? estNouveau : false);

        produit.setDateCreation(LocalDate.now());

        service.save(produit);

        return "redirect:/produits";
    }

    @GetMapping("/{id}/edit")
    public String editForm(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "produit",
                service.getById(id));

        model.addAttribute("isEdit", true);

        model.addAttribute(
                "actionUrl",
                "/produits/" + id + "/edit");

        return "produit/form";
    }

    @PostMapping("/{id}/edit")
    public String update(
            @PathVariable Long id,
            @RequestParam String nomProduit,
            @RequestParam Double prixBase,
            @RequestParam(required = false) Boolean estNouveau) {

        Produit produit = service.getById(id);

        produit.setNomProduit(nomProduit);
        produit.setPrixBase(prixBase);
        produit.setEstNouveau(
                estNouveau != null ? estNouveau : false);

        service.save(produit);

        return "redirect:/produits";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {

        service.deleteById(id);

        return "redirect:/produits";
    }

    @GetMapping("/export/csv")
    public void exportCSV(HttpServletResponse response) throws IOException {
        List<Produit> produits = service.findAll(); // ou repository.findAll()
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"produits.csv\"");
        PrintWriter writer = response.getWriter();
        writer.println("ID,Nom,Prix,Est nouveau,Date création");
        for (Produit p : produits) {
            writer.printf("%d,\"%s\",%.2f,%s,%s%n",
                    p.getIdProduit(),
                    p.getNomProduit().replace("\"", "\"\""),
                    p.getPrixBase(),
                    p.getEstNouveau() ? "Oui" : "Non",
                    p.getDateCreation());
        }
        writer.flush();
    }

    @GetMapping("/print")
    public String printPage(Model model) {
        model.addAttribute("produits", service.findAll());
        return "produit/print";
    }

    @GetMapping("/import")
    public String pageImport(Model model) {
        return "produit/import";
    }

    @PostMapping("/import")
    public String importData(@RequestParam("file") MultipartFile file,
                             RedirectAttributes redirectAttributes) {
        try {
            List<String> erreurs = csvExcelImportService.importFile(file, "produit");
            if (erreurs.isEmpty()) {
                redirectAttributes.addFlashAttribute("success",
                    "Produit(s) importe(s) avec succes");
            } else {
                redirectAttributes.addFlashAttribute("warning",
                    "Erreurs : " + String.join("; ", erreurs));
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                "Erreur lors de l'import : " + e.getMessage());
        }
        return "redirect:/produits";
    }

    @PostMapping("/{id}/activate")
    public String activate(@PathVariable Long id) {

        disponibiliteService.activateProduct(id);

        return "redirect:/produits";
    }

    @PostMapping("/{id}/deactivate")
    public String deactivate(@PathVariable Long id) {

        disponibiliteService.deactivateProduct(id);

        return "redirect:/produits";
    }
}