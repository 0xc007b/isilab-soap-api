package com.flrxnt.soap.endpoints;

import com.flrxnt.soap.generated.*;
import com.flrxnt.soap.services.SectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

/**
 * SOAP Endpoint pour la gestion des secteurs.
 */
@Endpoint
@RequiredArgsConstructor
public class SectorEndpoint {

    private static final String NAMESPACE_URI = "http://soap.flrxnt.com/sectors-classes";

    private final SectorService sectorService;

    /**
     * Récupère tous les secteurs.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllSectorsRequest")
    @ResponsePayload
    public GetAllSectorsResponse getAllSectors(@RequestPayload GetAllSectorsRequest request) {
        try {
            List<Sector> sectors = sectorService.getAllSectors();

            SectorList sectorList = new SectorList();
            sectorList.getSector().addAll(sectors);

            GetAllSectorsResponse response = new GetAllSectorsResponse();
            response.setSectors(sectorList);
            return response;
        } catch (SectorService.SoapServiceException e) {
            throw createSoapFault(e.getFaultCode(), e.getMessage());
        } catch (Exception e) {
            throw createSoapFault("SERVER", "Internal server error: " + e.getMessage());
        }
    }

    /**
     * Récupère un secteur par son ID.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getSectorByIdRequest")
    @ResponsePayload
    public GetSectorByIdResponse getSectorById(@RequestPayload GetSectorByIdRequest request) {
        try {
            Sector sector = sectorService.getSectorById(request.getId());

            GetSectorByIdResponse response = new GetSectorByIdResponse();
            response.setSector(sector);
            return response;
        } catch (SectorService.SoapServiceException e) {
            throw createSoapFault(e.getFaultCode(), e.getMessage());
        } catch (Exception e) {
            throw createSoapFault("SERVER", "Internal server error: " + e.getMessage());
        }
    }

    /**
     * Crée un nouveau secteur.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createSectorRequest")
    @ResponsePayload
    public CreateSectorResponse createSector(@RequestPayload CreateSectorRequest request) {
        try {
            // Validation
            if (request.getName() == null || request.getName().trim().isEmpty()) {
                throw createSoapFault("CLIENT", "Sector name is required and cannot be empty");
            }
            if (request.getName().length() > 120) {
                throw createSoapFault("CLIENT", "Sector name cannot exceed 120 characters");
            }

            Sector sector = sectorService.createSector(request);

            CreateSectorResponse response = new CreateSectorResponse();
            response.setSector(sector);
            return response;
        } catch (SectorService.SoapServiceException e) {
            throw createSoapFault(e.getFaultCode(), e.getMessage());
        } catch (Exception e) {
            throw createSoapFault("SERVER", "Internal server error: " + e.getMessage());
        }
    }

    /**
     * Met à jour un secteur existant.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateSectorRequest")
    @ResponsePayload
    public UpdateSectorResponse updateSector(@RequestPayload UpdateSectorRequest request) {
        try {
            // Validation
            if (request.getName() == null || request.getName().trim().isEmpty()) {
                throw createSoapFault("CLIENT", "Sector name is required and cannot be empty");
            }
            if (request.getName().length() > 120) {
                throw createSoapFault("CLIENT", "Sector name cannot exceed 120 characters");
            }

            Sector sector = sectorService.updateSector(request.getId(), request);

            UpdateSectorResponse response = new UpdateSectorResponse();
            response.setSector(sector);
            return response;
        } catch (SectorService.SoapServiceException e) {
            throw createSoapFault(e.getFaultCode(), e.getMessage());
        } catch (Exception e) {
            throw createSoapFault("SERVER", "Internal server error: " + e.getMessage());
        }
    }

    /**
     * Supprime un secteur.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteSectorRequest")
    @ResponsePayload
    public DeleteSectorResponse deleteSector(@RequestPayload DeleteSectorRequest request) {
        try {
            boolean success = sectorService.deleteSector(request.getId());

            DeleteSectorResponse response = new DeleteSectorResponse();
            response.setSuccess(success);
            response.setMessage("Sector deleted successfully");
            return response;
        } catch (SectorService.SoapServiceException e) {
            throw createSoapFault(e.getFaultCode(), e.getMessage());
        } catch (Exception e) {
            throw createSoapFault("SERVER", "Internal server error: " + e.getMessage());
        }
    }

    /**
     * Crée une SOAP Fault avec le code d'erreur et le message fournis.
     */
    private RuntimeException createSoapFault(String faultCode, String message) {
        return new RuntimeException("SOAP Fault: [" + faultCode + "] " + message);
    }
}
