package com.entreprise.ejb.devises;

import com.entreprise.devises.dto.ConversionResultDTO;
import com.entreprise.devises.dto.DeviseDTO;
import com.entreprise.devises.remote.DeviseConversionRemote;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Stateful;
import jakarta.ejb.Remove;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateful
public class DeviseConversionBean implements DeviseConversionRemote {
    
    private Map<String, DeviseDTO> devises;
    private DateTimeFormatter dateFormatter;
    
    @PostConstruct
    public void init() {
        devises = new HashMap<>();
        dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        chargerDevises();
    }
    
    @Override
    public void chargerDevises() {
        devises.clear();
        
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("devises.csv");
            
            if (inputStream == null) {
                System.err.println("❌ Fichier devises.csv introuvable");
                return;
            }
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            
            String line;
            boolean firstLine = true;
            int lineNumber = 0;
            
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                
                if (line.trim().isEmpty()) continue;
                
                try {
                    String[] parts = line.split(";");
                    if (parts.length != 4) continue;
                    
                    String code = parts[0].trim().toUpperCase();
                    LocalDate dateDebut = LocalDate.parse(parts[1].trim(), dateFormatter);
                    LocalDate dateFin = LocalDate.parse(parts[2].trim(), dateFormatter);
                    BigDecimal cours = new BigDecimal(parts[3].trim());
                    
                    DeviseDTO devise = new DeviseDTO(code, dateDebut, dateFin, cours);
                    devises.put(code, devise);
                    
                    System.out.println("✅ " + code + " = " + cours + " Ar");
                    
                } catch (Exception e) {
                    System.err.println("❌ Ligne " + lineNumber + ": " + e.getMessage());
                }
            }
            
            reader.close();
            System.out.println("✅ " + devises.size() + " devise(s) chargée(s)");
            
        } catch (Exception e) {
            System.err.println("❌ Erreur CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    public List<DeviseDTO> getAllDevises() {
        LocalDate today = LocalDate.now();
        List<DeviseDTO> result = new ArrayList<>();
        
        // Toujours inclure Ariary
        result.add(new DeviseDTO("AR", LocalDate.MIN, LocalDate.MAX, BigDecimal.ONE));
        
        for (DeviseDTO devise : devises.values()) {
            if (!today.isBefore(devise.getDateDebut()) && !today.isAfter(devise.getDateFin())) {
                result.add(devise);
            }
        }
        
        return result;
    }
    
    @Override
    public ConversionResultDTO convertirEnAriary(String codeDevise, BigDecimal montant) {
        try {
            if (codeDevise == null || montant == null || montant.compareTo(BigDecimal.ZERO) <= 0) {
                return ConversionResultDTO.error("Paramètres invalides");
            }
            
            String code = codeDevise.trim().toUpperCase();
            
            if ("AR".equals(code) || "MGA".equals(code)) {
                return new ConversionResultDTO(code, montant, montant, BigDecimal.ONE);
            }
            
            DeviseDTO devise = devises.get(code);
            if (devise == null) {
                return ConversionResultDTO.error("Devise introuvable");
            }
            
            LocalDate today = LocalDate.now();
            if (today.isBefore(devise.getDateDebut()) || today.isAfter(devise.getDateFin())) {
                return ConversionResultDTO.error("Devise non valide");
            }
            
            BigDecimal montantAriary = montant.multiply(devise.getCours()).setScale(2, RoundingMode.HALF_UP);
            
            System.out.println("💱 " + montant + " " + code + " = " + montantAriary + " Ar");
            
            return new ConversionResultDTO(code, montant, montantAriary, devise.getCours());
            
        } catch (Exception e) {
            return ConversionResultDTO.error(e.getMessage());
        }
    }
    
    @Override
    public BigDecimal getCours(String codeDevise) {
        if (codeDevise == null) return null;
        
        String code = codeDevise.trim().toUpperCase();
        if ("AR".equals(code) || "MGA".equals(code)) return BigDecimal.ONE;
        
        DeviseDTO devise = devises.get(code);
        if (devise == null) return null;
        
        LocalDate today = LocalDate.now();
        if (today.isBefore(devise.getDateDebut()) || today.isAfter(devise.getDateFin())) {
            return null;
        }
        
        return devise.getCours();
    }
    
    @Override
    public boolean deviseExiste(String codeDevise) {
        if (codeDevise == null) return false;
        String code = codeDevise.trim().toUpperCase();
        return "AR".equals(code) || "MGA".equals(code) || devises.containsKey(code);
    }
    
    @Remove
    public void endSession() {
        System.out.println("✅ Session DeviseConversion terminée");
        devises.clear();
    }
}