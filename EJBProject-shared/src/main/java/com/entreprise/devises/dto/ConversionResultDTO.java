package com.entreprise.devises.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ConversionResultDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String deviseSource;
    private BigDecimal montantSource;
    private BigDecimal montantAriary;
    private BigDecimal coursUtilise;
    private boolean success;
    private String message;
    
    public ConversionResultDTO() {}
    
    public ConversionResultDTO(String deviseSource, BigDecimal montantSource, 
                               BigDecimal montantAriary, BigDecimal coursUtilise) {
        this.deviseSource = deviseSource;
        this.montantSource = montantSource;
        this.montantAriary = montantAriary;
        this.coursUtilise = coursUtilise;
        this.success = true;
    }
    
    public static ConversionResultDTO error(String message) {
        ConversionResultDTO result = new ConversionResultDTO();
        result.success = false;
        result.message = message;
        return result;
    }
    
    public String getDeviseSource() { return deviseSource; }
    public void setDeviseSource(String deviseSource) { this.deviseSource = deviseSource; }
    
    public BigDecimal getMontantSource() { return montantSource; }
    public void setMontantSource(BigDecimal montantSource) { this.montantSource = montantSource; }
    
    public BigDecimal getMontantAriary() { return montantAriary; }
    public void setMontantAriary(BigDecimal montantAriary) { this.montantAriary = montantAriary; }
    
    public BigDecimal getCoursUtilise() { return coursUtilise; }
    public void setCoursUtilise(BigDecimal coursUtilise) { this.coursUtilise = coursUtilise; }
    
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}