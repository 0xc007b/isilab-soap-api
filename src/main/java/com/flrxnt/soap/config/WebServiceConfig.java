package com.flrxnt.soap.config;

import com.flrxnt.soap.exceptions.DetailSoapFaultDefinitionExceptionResolver;
import com.flrxnt.soap.services.SectorService;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import java.util.Properties;

/**
 * Configuration pour les services web SOAP.
 */
@EnableWs
@Configuration
public class WebServiceConfig {

    /**
     * Bean pour le servlet de dispatch des messages SOAP.
     */
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    /**
     * Définition WSDL pour les services Sectors et Classes.
     */
    @Bean(name = "sectorsClasses")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema sectorsClassesSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("SectorsClassesPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://soap.flrxnt.com/sectors-classes");
        wsdl11Definition.setSchema(sectorsClassesSchema);
        return wsdl11Definition;
    }

    /**
     * Schéma XSD pour les secteurs et classes.
     */
    @Bean
    public XsdSchema sectorsClassesSchema() {
        return new SimpleXsdSchema(new ClassPathResource("schemas/sectors-classes.xsd"));
    }

    /**
     * Résolveur d'exceptions pour les SOAP Faults.
     */
    @Bean
    public DetailSoapFaultDefinitionExceptionResolver exceptionResolver() {
        DetailSoapFaultDefinitionExceptionResolver exceptionResolver =
            new DetailSoapFaultDefinitionExceptionResolver();

        SoapFaultDefinition faultDefinition = new SoapFaultDefinition();
        faultDefinition.setFaultCode(SoapFaultDefinition.SERVER);
        exceptionResolver.setDefaultFault(faultDefinition);

        Properties errorMappings = new Properties();
        errorMappings.setProperty(Exception.class.getName(), SoapFaultDefinition.SERVER.toString());
        errorMappings.setProperty(SectorService.SoapServiceException.class.getName(),
                                 SoapFaultDefinition.SERVER.toString());
        exceptionResolver.setExceptionMappings(errorMappings);
        exceptionResolver.setOrder(1);
        return exceptionResolver;
    }


}
