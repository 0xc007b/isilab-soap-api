package com.flrxnt.soap.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sectors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120, unique = true)
    private String name;

    @OneToMany(
        mappedBy = "sector",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<SchoolClass> classes = new ArrayList<>();

    public Sector(String name) {
        this.name = name;
    }

    public void addClass(SchoolClass schoolClass) {
        if (schoolClass == null) return;
        classes.add(schoolClass);
        schoolClass.setSector(this);
    }

    public void removeClass(SchoolClass schoolClass) {
        if (schoolClass == null) return;
        classes.remove(schoolClass);
        schoolClass.setSector(null);
    }
}
