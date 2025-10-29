package com.entreprise.ejb.devises.rest;

import com.entreprise.devises.dto.ConversionResultDTO;
import com.entreprise.devises.dto.DeviseDTO;
import com.entreprise.devises.remote.DeviseConversionRemote;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;

@Path("/devises")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DeviseResource {
    
    @EJB
    private DeviseConversionRemote deviseBean;
    
    /**
     * GET /api/devises
     * Récupérer toutes les devises
     */
    @GET
    public Response getAllDevises() {
        try {
            List<DeviseDTO> devises = deviseBean.getAllDevises();
            return Response.ok(devises).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }
    
    /**
     * POST /api/devises/convertir
     * Convertir un montant en Ariary
     * Body: {"codeDevise": "USD", "montant": 100}
     */
    @POST
    @Path("/convertir")
    public Response convertir(ConversionRequest request) {
        try {
            if (request == null || request.getCodeDevise() == null || request.getMontant() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Paramètres invalides"))
                        .build();
            }
            
            ConversionResultDTO result = deviseBean.convertirEnAriary(
                    request.getCodeDevise(), 
                    request.getMontant()
            );
            
            if (!result.isSuccess()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse(result.getMessage()))
                        .build();
            }
            
            return Response.ok(result).build();
            
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }
    
    /**
     * GET /api/devises/{code}/cours
     * Récupérer le cours d'une devise
     */
    @GET
    @Path("/{code}/cours")
    public Response getCours(@PathParam("code") String code) {
        try {
            BigDecimal cours = deviseBean.getCours(code);
            if (cours == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Devise introuvable"))
                        .build();
            }
            return Response.ok(new CoursResponse(code, cours)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }
    
    // Classes internes pour les requêtes/réponses
    
    public static class ConversionRequest {
        private String codeDevise;
        private BigDecimal montant;
        
        public ConversionRequest() {}
        
        public String getCodeDevise() { return codeDevise; }
        public void setCodeDevise(String codeDevise) { this.codeDevise = codeDevise; }
        
        public BigDecimal getMontant() { return montant; }
        public void setMontant(BigDecimal montant) { this.montant = montant; }
    }
    
    public static class ErrorResponse {
        private String error;
        
        public ErrorResponse() {}
        public ErrorResponse(String error) { this.error = error; }
        
        public String getError() { return error; }
        public void setError(String error) { this.error = error; }
    }
    
    public static class CoursResponse {
        private String codeDevise;
        private BigDecimal cours;
        
        public CoursResponse() {}
        public CoursResponse(String codeDevise, BigDecimal cours) {
            this.codeDevise = codeDevise;
            this.cours = cours;
        }
        
        public String getCodeDevise() { return codeDevise; }
        public void setCodeDevise(String codeDevise) { this.codeDevise = codeDevise; }
        
        public BigDecimal getCours() { return cours; }
        public void setCours(BigDecimal cours) { this.cours = cours; }
    }
}