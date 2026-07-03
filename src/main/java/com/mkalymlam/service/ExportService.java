package com.mkalymlam.service;

import java.io.File;
import java.io.FileWriter;
import java.sql.ResultSetMetaData;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ExportService {

    private final JdbcTemplate jdbcTemplate;

    public ExportService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public File exportCsv(String table) throws Exception {
        String nomFichier = table + ".csv";
        File fichier = new File(nomFichier);
        FileWriter writer = new FileWriter(fichier);
        List<String> colonnes = recupererColonnes(table);
        ecrireEntete(writer, colonnes);
        List<Map<String, Object>> donnees = recupererDonnees(table);
        for (Map<String, Object> ligne : donnees) {
            ecrireLigne(writer, colonnes, ligne);
        }
        writer.flush();
        writer.close();
        fichier.deleteOnExit();

        return fichier;
    }

    private List<String> recupererColonnes(String table) {
        String sql = "SELECT * FROM \"" + table + "\" LIMIT 1";
        return jdbcTemplate.query(sql, rs -> {
            ResultSetMetaData metaData = rs.getMetaData();
            return java.util.stream.IntStream
                    .rangeClosed(1, metaData.getColumnCount())
                    .mapToObj(i -> {
                        try {
                            return metaData.getColumnName(i);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toList());
        });
    }

    private List<Map<String, Object>> recupererDonnees(String table) {
        String sql = "SELECT * FROM \"" + table + "\"";
        return jdbcTemplate.queryForList(sql);
    }

    private void ecrireEntete(FileWriter writer, List<String> colonnes) throws Exception {
        writer.write(String.join(",", colonnes));
        writer.write("\n");
    }

    private void ecrireLigne(FileWriter writer, List<String> colonnes, Map<String, Object> ligne) throws Exception {
        String ligneCsv = colonnes.stream()
                .map(colonne -> convertirValeur(ligne.get(colonne)))
                .collect(Collectors.joining(","));

        writer.write(ligneCsv);
        writer.write("\n");
    }

    private String convertirValeur(Object valeur) {
        if (valeur == null) {
            return "";
        }
        return valeur.toString();
    }
}