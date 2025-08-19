package com.flrxnt.soap.exceptions;

import com.flrxnt.soap.services.SectorService;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;

import javax.xml.namespace.QName;

/**
 * Résolveur d'exceptions personnalisé pour les services SOAP.
 * Convertit les exceptions Java en SOAP Faults avec des détails appropriés.
 */
public class DetailSoapFaultDefinitionExceptionResolver extends SoapFaultMappingExceptionResolver {

    private static final QName CODE = new QName("faultCode");
    private static final QName STRING = new QName("faultString");

    /**
     * Personnalise les SOAP Faults pour les exceptions de service.
     */
    @Override
    protected void customizeFault(Object endpoint, Exception ex, SoapFault fault) {
        if (ex instanceof SectorService.SoapServiceException) {
            SectorService.SoapServiceException serviceEx = (SectorService.SoapServiceException) ex;

            SoapFaultDetail detail = fault.addFaultDetail();
            detail.addFaultDetailElement(CODE).addText(serviceEx.getFaultCode());
            detail.addFaultDetailElement(STRING).addText(serviceEx.getMessage());
        } else if (ex instanceof RuntimeException) {
            // Pour les autres RuntimeExceptions, on extrait le message
            String message = ex.getMessage();
            if (message != null && message.startsWith("SOAP Fault:")) {
                // Extraction du code d'erreur et du message depuis le format "SOAP Fault: [CODE] message"
                int start = message.indexOf("[");
                int end = message.indexOf("]");
                if (start != -1 && end != -1 && end > start) {
                    String faultCode = message.substring(start + 1, end);
                    String faultMessage = message.substring(end + 2); // +2 pour ignorer "] "

                    SoapFaultDetail detail = fault.addFaultDetail();
                    detail.addFaultDetailElement(CODE).addText(faultCode);
                    detail.addFaultDetailElement(STRING).addText(faultMessage);
                } else {
                    SoapFaultDetail detail = fault.addFaultDetail();
                    detail.addFaultDetailElement(CODE).addText("SERVER");
                    detail.addFaultDetailElement(STRING).addText(message);
                }
            } else {
                SoapFaultDetail detail = fault.addFaultDetail();
                detail.addFaultDetailElement(CODE).addText("SERVER");
                detail.addFaultDetailElement(STRING).addText(ex.getMessage());
            }
        } else {
            // Pour toutes les autres exceptions
            SoapFaultDetail detail = fault.addFaultDetail();
            detail.addFaultDetailElement(CODE).addText("SERVER");
            detail.addFaultDetailElement(STRING).addText("Internal server error: " + ex.getMessage());
        }
    }
}
