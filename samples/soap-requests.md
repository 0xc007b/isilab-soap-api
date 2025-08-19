# Sample SOAP Requests

This document contains sample SOAP requests for testing the Simple SOAP API.

## Prerequisites

1. Make sure PostgreSQL is running and create the database:
```sql
CREATE DATABASE simple_soap_api;
```

2. Start the application:
```bash
mvn spring-boot:run
```

3. The WSDL will be available at: http://localhost:8080/ws/sectorsClasses.wsdl

## Testing with curl

### 1. Get All Sectors (empty response initially)

```bash
curl -X POST \
  http://localhost:8080/ws \
  -H 'Content-Type: text/xml; charset=utf-8' \
  -H 'SOAPAction: ""' \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:sec="http://soap.flrxnt.com/sectors-classes">
   <soapenv:Header/>
   <soapenv:Body>
      <sec:getAllSectorsRequest/>
   </soapenv:Body>
</soapenv:Envelope>'
```

### 2. Create a Sector

```bash
curl -X POST \
  http://localhost:8080/ws \
  -H 'Content-Type: text/xml; charset=utf-8' \
  -H 'SOAPAction: ""' \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:sec="http://soap.flrxnt.com/sectors-classes">
   <soapenv:Header/>
   <soapenv:Body>
      <sec:createSectorRequest>
         <sec:name>Informatique</sec:name>
      </sec:createSectorRequest>
   </soapenv:Body>
</soapenv:Envelope>'
```

### 3. Create Another Sector

```bash
curl -X POST \
  http://localhost:8080/ws \
  -H 'Content-Type: text/xml; charset=utf-8' \
  -H 'SOAPAction: ""' \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:sec="http://soap.flrxnt.com/sectors-classes">
   <soapenv:Header/>
   <soapenv:Body>
      <sec:createSectorRequest>
         <sec:name>Génie Civil</sec:name>
      </sec:createSectorRequest>
   </soapenv:Body>
</soapenv:Envelope>'
```

### 4. Get Sector By ID

```bash
curl -X POST \
  http://localhost:8080/ws \
  -H 'Content-Type: text/xml; charset=utf-8' \
  -H 'SOAPAction: ""' \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:sec="http://soap.flrxnt.com/sectors-classes">
   <soapenv:Header/>
   <soapenv:Body>
      <sec:getSectorByIdRequest>
         <sec:id>1</sec:id>
      </sec:getSectorByIdRequest>
   </soapenv:Body>
</soapenv:Envelope>'
```

### 5. Update a Sector

```bash
curl -X POST \
  http://localhost:8080/ws \
  -H 'Content-Type: text/xml; charset=utf-8' \
  -H 'SOAPAction: ""' \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:sec="http://soap.flrxnt.com/sectors-classes">
   <soapenv:Header/>
   <soapenv:Body>
      <sec:updateSectorRequest>
         <sec:id>1</sec:id>
         <sec:name>Informatique et Télécommunications</sec:name>
      </sec:updateSectorRequest>
   </soapenv:Body>
</soapenv:Envelope>'
```

### 6. Create a Class

```bash
curl -X POST \
  http://localhost:8080/ws \
  -H 'Content-Type: text/xml; charset=utf-8' \
  -H 'SOAPAction: ""' \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:sec="http://soap.flrxnt.com/sectors-classes">
   <soapenv:Header/>
   <soapenv:Body>
      <sec:createClassRequest>
         <sec:className>L3 GL</sec:className>
         <sec:description>Licence 3 Génie Logiciel</sec:description>
         <sec:sectorId>1</sec:sectorId>
      </sec:createClassRequest>
   </soapenv:Body>
</soapenv:Envelope>'
```

### 7. Create Another Class

```bash
curl -X POST \
  http://localhost:8080/ws \
  -H 'Content-Type: text/xml; charset=utf-8' \
  -H 'SOAPAction: ""' \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:sec="http://soap.flrxnt.com/sectors-classes">
   <soapenv:Header/>
   <soapenv:Body>
      <sec:createClassRequest>
         <sec:className>M1 GL</sec:className>
         <sec:description>Master 1 Génie Logiciel</sec:description>
         <sec:sectorId>1</sec:sectorId>
      </sec:createClassRequest>
   </soapenv:Body>
</soapenv:Envelope>'
```

### 8. Get All Classes

```bash
curl -X POST \
  http://localhost:8080/ws \
  -H 'Content-Type: text/xml; charset=utf-8' \
  -H 'SOAPAction: ""' \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:sec="http://soap.flrxnt.com/sectors-classes">
   <soapenv:Header/>
   <soapenv:Body>
      <sec:getAllClassesRequest/>
   </soapenv:Body>
</soapenv:Envelope>'
```

### 9. Get Classes by Sector ID

```bash
curl -X POST \
  http://localhost:8080/ws \
  -H 'Content-Type: text/xml; charset=utf-8' \
  -H 'SOAPAction: ""' \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:sec="http://soap.flrxnt.com/sectors-classes">
   <soapenv:Header/>
   <soapenv:Body>
      <sec:getAllClassesRequest>
         <sec:sectorId>1</sec:sectorId>
      </sec:getAllClassesRequest>
   </soapenv:Body>
</soapenv:Envelope>'
```

### 10. Get Class By ID

```bash
curl -X POST \
  http://localhost:8080/ws \
  -H 'Content-Type: text/xml; charset=utf-8' \
  -H 'SOAPAction: ""' \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:sec="http://soap.flrxnt.com/sectors-classes">
   <soapenv:Header/>
   <soapenv:Body>
      <sec:getClassByIdRequest>
         <sec:id>1</sec:id>
      </sec:getClassByIdRequest>
   </soapenv:Body>
</soapenv:Envelope>'
```

### 11. Update a Class

```bash
curl -X POST \
  http://localhost:8080/ws \
  -H 'Content-Type: text/xml; charset=utf-8' \
  -H 'SOAPAction: ""' \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:sec="http://soap.flrxnt.com/sectors-classes">
   <soapenv:Header/>
   <soapenv:Body>
      <sec:updateClassRequest>
         <sec:id>1</sec:id>
         <sec:className>L3 Génie Logiciel</sec:className>
         <sec:description>Licence 3 en Génie Logiciel - Formation complète</sec:description>
         <sec:sectorId>1</sec:sectorId>
      </sec:updateClassRequest>
   </soapenv:Body>
</soapenv:Envelope>'
```

### 12. Delete a Class

```bash
curl -X POST \
  http://localhost:8080/ws \
  -H 'Content-Type: text/xml; charset=utf-8' \
  -H 'SOAPAction: ""' \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:sec="http://soap.flrxnt.com/sectors-classes">
   <soapenv:Header/>
   <soapenv:Body>
      <sec:deleteClassRequest>
         <sec:id>2</sec:id>
      </sec:deleteClassRequest>
   </soapenv:Body>
</soapenv:Envelope>'
```

### 13. Delete a Sector (will also delete associated classes)

```bash
curl -X POST \
  http://localhost:8080/ws \
  -H 'Content-Type: text/xml; charset=utf-8' \
  -H 'SOAPAction: ""' \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:sec="http://soap.flrxnt.com/sectors-classes">
   <soapenv:Header/>
   <soapenv:Body>
      <sec:deleteSectorRequest>
         <sec:id>2</sec:id>
      </sec:deleteSectorRequest>
   </soapenv:Body>
</soapenv:Envelope>'
```

## Error Examples

### 14. Try to create a sector with duplicate name (should return SOAP Fault)

```bash
curl -X POST \
  http://localhost:8080/ws \
  -H 'Content-Type: text/xml; charset=utf-8' \
  -H 'SOAPAction: ""' \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:sec="http://soap.flrxnt.com/sectors-classes">
   <soapenv:Header/>
   <soapenv:Body>
      <sec:createSectorRequest>
         <sec:name>Informatique</sec:name>
      </sec:createSectorRequest>
   </soapenv:Body>
</soapenv:Envelope>'
```

### 15. Try to get a non-existent sector (should return SOAP Fault)

```bash
curl -X POST \
  http://localhost:8080/ws \
  -H 'Content-Type: text/xml; charset=utf-8' \
  -H 'SOAPAction: ""' \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:sec="http://soap.flrxnt.com/sectors-classes">
   <soapenv:Header/>
   <soapenv:Body>
      <sec:getSectorByIdRequest>
         <sec:id>999</sec:id>
      </sec:getSectorByIdRequest>
   </soapenv:Body>
</soapenv:Envelope>'
```

## Expected Response Formats

### Successful Sector Response
```xml
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <ns2:createSectorResponse xmlns:ns2="http://soap.flrxnt.com/sectors-classes">
         <ns2:sector>
            <ns2:id>1</ns2:id>
            <ns2:name>Informatique</ns2:name>
         </ns2:sector>
      </ns2:createSectorResponse>
   </soap:Body>
</soap:Envelope>
```

### Successful Class Response
```xml
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <ns2:createClassResponse xmlns:ns2="http://soap.flrxnt.com/sectors-classes">
         <ns2:schoolClass>
            <ns2:id>1</ns2:id>
            <ns2:className>L3 GL</ns2:className>
            <ns2:description>Licence 3 Génie Logiciel</ns2:description>
            <ns2:sectorId>1</ns2:sectorId>
            <ns2:sectorName>Informatique</ns2:sectorName>
         </ns2:schoolClass>
      </ns2:createClassResponse>
   </soap:Body>
</soap:Envelope>
```

### SOAP Fault Response
```xml
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <soap:Fault>
         <faultcode>soap:Server</faultcode>
         <faultstring>SOAP Fault: [CONFLICT] Sector with the same name already exists</faultstring>
         <detail>
            <faultCode>CONFLICT</faultCode>
            <faultString>Sector with the same name already exists</faultString>
         </detail>
      </soap:Fault>
   </soap:Body>
</soap:Envelope>
```

## Testing with SoapUI

1. Download and install SoapUI
2. Create a new SOAP project
3. Enter WSDL URL: `http://localhost:8080/ws/sectorsClasses.wsdl`
4. SoapUI will automatically generate sample requests for all operations
5. Modify the generated requests with your test data
6. Execute the requests and verify responses

## Testing with Postman

1. Create a new request in Postman
2. Set method to POST
3. Set URL to `http://localhost:8080/ws`
4. In Headers, add:
   - `Content-Type: text/xml; charset=utf-8`
   - `SOAPAction: ""` (empty string)
5. In Body, select "raw" and paste any of the XML requests above
6. Send the request and examine the response