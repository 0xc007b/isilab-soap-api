package com.flrxnt.soap.mappers;

import com.flrxnt.soap.entities.SchoolClass;
import com.flrxnt.soap.generated.CreateClassRequest;
import com.flrxnt.soap.generated.UpdateClassRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper MapStruct pour la conversion entre les entités SchoolClass et les DTOs SOAP générés.
 */
@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SchoolClassMapper {

    /**
     * Convertit une entité SchoolClass en DTO SOAP SchoolClass.
     *
     * @param schoolClass l'entité à convertir
     * @return le DTO SOAP correspondant, ou null si l'entité est null
     */
    @Mapping(source = "sector.id", target = "sectorId")
    @Mapping(source = "sector.name", target = "sectorName")
    com.flrxnt.soap.generated.SchoolClass toSoapDto(SchoolClass schoolClass);

    /**
     * Convertit une liste d'entités SchoolClass en liste de DTOs SOAP SchoolClass.
     *
     * @param schoolClasses la liste d'entités à convertir
     * @return la liste de DTOs SOAP correspondante
     */
    List<com.flrxnt.soap.generated.SchoolClass> toSoapDtoList(List<SchoolClass> schoolClasses);

    /**
     * Convertit un DTO CreateClassRequest en entité SchoolClass.
     * Note: Le secteur n'est pas mappé automatiquement et doit être défini séparément.
     *
     * @param request le DTO de création
     * @return l'entité SchoolClass correspondante, ou null si le DTO est null
     */
    @Mapping(target = "sector", ignore = true)
    @Mapping(target = "id", ignore = true)
    SchoolClass toEntity(CreateClassRequest request);

    /**
     * Met à jour une entité SchoolClass existante avec les données d'un DTO UpdateClassRequest.
     * Note: Le secteur et l'ID ne sont pas mis à jour par cette méthode.
     *
     * @param request le DTO contenant les nouvelles données
     * @param schoolClass l'entité existante à mettre à jour (passée en paramètre de sortie)
     */
    @Mapping(target = "sector", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateEntity(UpdateClassRequest request, @MappingTarget SchoolClass schoolClass);
}
