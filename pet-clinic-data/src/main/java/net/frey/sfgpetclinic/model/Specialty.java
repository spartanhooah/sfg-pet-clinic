package net.frey.sfgpetclinic.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "specialties")
public class Specialty extends BaseEntity {
    @Column(name = "description")
    private String description;
}
