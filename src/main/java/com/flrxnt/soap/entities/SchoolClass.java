package com.flrxnt.soap.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "classes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "class_name", nullable = false, length = 120)
    private String className;

    @Column(length = 255)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sector_id", nullable = false, foreignKey = @ForeignKey(name = "fk_class_sector"))
    private Sector sector;

    /**
     * Keep both sides of the bidirectional association in sync.
     */
    public void setSector(Sector newSector) {
        if (this.sector == newSector) {
            return;
        }

        // Detach from current sector
        if (this.sector != null && this.sector.getClasses() != null) {
            this.sector.getClasses().remove(this);
        }

        this.sector = newSector;

        // Attach to new sector
        if (newSector != null && newSector.getClasses() != null && !newSector.getClasses().contains(this)) {
            newSector.getClasses().add(this);
        }
    }
}
