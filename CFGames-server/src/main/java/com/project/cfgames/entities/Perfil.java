package com.project.cfgames.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "PERFIL")
@Entity(name = "Perfil")

@Getter
@Setter
@NoArgsConstructor

@JsonIdentityInfo(
        scope = Perfil.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Perfil {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name =  "perfil_id")
    private Long id;
    private String authority;

    public Perfil(Long id, String authority) {
        this.id = id;
        this.authority = authority;
    }
}
