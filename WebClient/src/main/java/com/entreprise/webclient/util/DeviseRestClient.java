package com.entreprise.webclient.util;

import com.entreprise.devises.dto.ConversionResultDTO;
import com.entreprise.devises.dto.DeviseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class DeviseRestClient {
    
    private static final String BASE_URL = "http://localhost:8081/DevisesEJB-1.0-SNAPSHOT/api/devises";
    private static final ObjectMapper mapper = new ObjectMapper();
    
    static {
        mapper.registerModule(new JavaTimeModule());
    }
    
    public static List<DeviseDTO> getAllDevises() throws Exception {
        URL url = new URL(BASE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        
        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new Exception("Erreur HTTP: " + responseCode);
        }
        
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();
        
        DeviseDTO[] devises = mapper.readValue(response.toString(), DeviseDTO[].class);
        return Arrays.asList(devises);
    }
    
    public static ConversionResultDTO convertirEnAriary(String codeDevise, BigDecimal montant) throws Exception {
        URL url = new URL(BASE_URL + "/convertir");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        
        // Créer le JSON de la requête
        String jsonRequest = String.format(
            "{\"codeDevise\":\"%s\",\"montant\":%s}", 
            codeDevise, 
            montant.toString()
        );
        
        // Envoyer la requête
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonRequest.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        
        int responseCode = conn.getResponseCode();
        
        // Lire la réponse
        BufferedReader in = new BufferedReader(
            new InputStreamReader(
                responseCode == 200 ? conn.getInputStream() : conn.getErrorStream()
            )
        );
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();
        
        if (responseCode != 200) {
            throw new Exception("Erreur conversion: " + response.toString());
        }
        
        return mapper.readValue(response.toString(), ConversionResultDTO.class);
    }
}