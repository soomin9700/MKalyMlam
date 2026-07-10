package com.mkalymlam.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImportService {

    private final JdbcTemplate jdbcTemplate;

    public ImportService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void importCsv(String table, MultipartFile fichier) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(fichier.getInputStream()));
        List<String> colonnes = lireColonnesCsv(reader);
        String requeteInsert = construireRequeteInsert(table, colonnes);
        String ligne;
        while ((ligne = reader.readLine()) != null) {
            if (ligne.trim().isEmpty()) {
                continue;
            }
            List<String> valeurs = lireLigneCsv(ligne);
            insererLigne(requeteInsert, valeurs);
        }
        reader.close();
    }

    private List<String> lireColonnesCsv(BufferedReader reader) throws Exception {
        String entete = reader.readLine();
        return Arrays.asList(entete.split(","));
    }

    private List<String> lireLigneCsv(String ligne) {
        return Arrays.asList(ligne.split(","));
    }

    private String construireRequeteInsert(String table, List<String> colonnes) {
        String colonnesSql = colonnes.stream() .map(colonne -> "\"" + colonne.trim() + "\"").collect(Collectors.joining(","));
        String placeholders = genererPlaceholders(colonnes.size());
        return "INSERT INTO \"" + table + "\" (" + colonnesSql + ") VALUES (" + placeholders + ")";
    }

    private String genererPlaceholders(int nombreColonnes) {
        return IntStream.range(0, nombreColonnes)
                .mapToObj(i -> "?")
                .collect(Collectors.joining(","));
    }

    private void insererLigne(String requeteInsert, List<String> valeurs) {
        jdbcTemplate.update(requeteInsert, valeurs.toArray());
    }
}