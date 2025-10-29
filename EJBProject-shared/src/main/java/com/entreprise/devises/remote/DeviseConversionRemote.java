package com.entreprise.devises.remote;

import com.entreprise.devises.dto.ConversionResultDTO;
import com.entreprise.devises.dto.DeviseDTO;
import jakarta.ejb.Remote;
import java.math.BigDecimal;
import java.util.List;

@Remote
public interface DeviseConversionRemote {
    
    void chargerDevises();
    List<DeviseDTO> getAllDevises();
    ConversionResultDTO convertirEnAriary(String codeDevise, BigDecimal montant);
    BigDecimal getCours(String codeDevise);
    boolean deviseExiste(String codeDevise);
}