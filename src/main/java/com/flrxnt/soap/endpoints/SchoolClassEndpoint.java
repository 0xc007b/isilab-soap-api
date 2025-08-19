package com.flrxnt.soap.endpoints;

import com.flrxnt.soap.generated.*;
import com.flrxnt.soap.services.SchoolClassService;
import com.flrxnt.soap.services.SectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

/**
 * SOAP Endpoint pour la gestion des classes.
 */
@Endpoint
@RequiredArgsConstructor
public class SchoolClassEndpoint {

    private static final String NAMESPACE_URI = "http://soap.flrxnt.com/sectors-classes";

    private final SchoolClassService schoolClassService;

    /**
     * Récupère toutes les classes.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllClassesRequest")
    @ResponsePayload
    public GetAllClassesResponse getAllClasses(@RequestPayload GetAllClassesRequest request) {
        try {
            List<SchoolClass> classes;

            if (request.getSectorId() != null) {
                classes = schoolClassService.getAllClassesBySector(request.getSectorId());
            } else {
                classes = schoolClassService.getAllClasses();
            }

            SchoolClassList classList = new SchoolClassList();
            classList.getSchoolClass().addAll(classes);

            GetAllClassesResponse response = new GetAllClassesResponse();
            response.setClasses(classList);
            return response;
        } catch (SectorService.SoapServiceException e) {
            throw createSoapFault(e.getFaultCode(), e.getMessage());
        } catch (Exception e) {
            throw createSoapFault("SERVER", "Internal server error: " + e.getMessage());
        }
    }

    /**
     * Récupère une classe par son ID.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getClassByIdRequest")
    @ResponsePayload
    public GetClassByIdResponse getClassById(@RequestPayload GetClassByIdRequest request) {
        try {
            SchoolClass schoolClass = schoolClassService.getClassById(request.getId());

            GetClassByIdResponse response = new GetClassByIdResponse();
            response.setSchoolClass(schoolClass);
            return response;
        } catch (SectorService.SoapServiceException e) {
            throw createSoapFault(e.getFaultCode(), e.getMessage());
        } catch (Exception e) {
            throw createSoapFault("SERVER", "Internal server error: " + e.getMessage());
        }
    }

    /**
     * Crée une nouvelle classe.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createClassRequest")
    @ResponsePayload
    public CreateClassResponse createClass(@RequestPayload CreateClassRequest request) {
        try {
            // Validation
            if (request.getClassName() == null || request.getClassName().trim().isEmpty()) {
                throw createSoapFault("CLIENT", "Class name is required and cannot be empty");
            }
            if (request.getClassName().length() > 120) {
                throw createSoapFault("CLIENT", "Class name cannot exceed 120 characters");
            }
            if (request.getSectorId() == 0) {
                throw createSoapFault("CLIENT", "Sector ID is required");
            }
            if (request.getDescription() != null && request.getDescription().length() > 255) {
                throw createSoapFault("CLIENT", "Description cannot exceed 255 characters");
            }

            SchoolClass schoolClass = schoolClassService.createClass(request);

            CreateClassResponse response = new CreateClassResponse();
            response.setSchoolClass(schoolClass);
            return response;
        } catch (SectorService.SoapServiceException e) {
            throw createSoapFault(e.getFaultCode(), e.getMessage());
        } catch (Exception e) {
            throw createSoapFault("SERVER", "Internal server error: " + e.getMessage());
        }
    }

    /**
     * Met à jour une classe existante.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateClassRequest")
    @ResponsePayload
    public UpdateClassResponse updateClass(@RequestPayload UpdateClassRequest request) {
        try {
            // Validation
            if (request.getClassName() == null || request.getClassName().trim().isEmpty()) {
                throw createSoapFault("CLIENT", "Class name is required and cannot be empty");
            }
            if (request.getClassName().length() > 120) {
                throw createSoapFault("CLIENT", "Class name cannot exceed 120 characters");
            }
            if (request.getSectorId() == 0) {
                throw createSoapFault("CLIENT", "Sector ID is required");
            }
            if (request.getDescription() != null && request.getDescription().length() > 255) {
                throw createSoapFault("CLIENT", "Description cannot exceed 255 characters");
            }

            SchoolClass schoolClass = schoolClassService.updateClass(request.getId(), request);

            UpdateClassResponse response = new UpdateClassResponse();
            response.setSchoolClass(schoolClass);
            return response;
        } catch (SectorService.SoapServiceException e) {
            throw createSoapFault(e.getFaultCode(), e.getMessage());
        } catch (Exception e) {
            throw createSoapFault("SERVER", "Internal server error: " + e.getMessage());
        }
    }

    /**
     * Supprime une classe.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteClassRequest")
    @ResponsePayload
    public DeleteClassResponse deleteClass(@RequestPayload DeleteClassRequest request) {
        try {
            boolean success = schoolClassService.deleteClass(request.getId());

            DeleteClassResponse response = new DeleteClassResponse();
            response.setSuccess(success);
            response.setMessage("Class deleted successfully");
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
