package com.flrxnt.soap.mappers;

import com.flrxnt.soap.entities.Sector;
import com.flrxnt.soap.generated.CreateSectorRequest;
import com.flrxnt.soap.generated.UpdateSectorRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper MapStruct pour la conversion entre les entités Sector et les DTOs SOAP générés.
 */
@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SectorMapper {

    /**
     * Convertit une entité Sector en DTO SOAP Sector.
     *
     * @param sector l'entité à convertir
     * @return le DTO SOAP correspondant, ou null si l'entité est null
     */
    com.flrxnt.soap.generated.Sector toSoapDto(Sector sector);

    /**
     * Convertit une liste d'entités Sector en liste de DTOs SOAP Sector.
     *
     * @param sectors la liste d'entités à convertir
     * @return la liste de DTOs SOAP correspondante
     */
    List<com.flrxnt.soap.generated.Sector> toSoapDtoList(List<Sector> sectors);

    /**
     * Convertit un DTO CreateSectorRequest en entité Sector.
     *
     * @param request le DTO de création
     * @return l'entité Sector correspondante, ou null si le DTO est null
     */
    Sector toEntity(CreateSectorRequest request);

    /**
     * Met à jour une entité Sector existante avec les données d'un DTO UpdateSectorRequest.
     *
     * @param request le DTO contenant les nouvelles données
     * @param sector l'entité existante à mettre à jour (passée en paramètre de sortie)
     */
    void updateEntity(UpdateSectorRequest request, @MappingTarget Sector sector);
}
