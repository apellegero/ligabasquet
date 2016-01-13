package com.example.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A Estadisticas.
 */
@Entity
@Table(name = "ESTADISTICAS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Estadisticas implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    @Column(name = "canastes")
    private Integer canastes;
    
    @Column(name = "asistencies")
    private Integer asistencies;
    
    @Column(name = "rebotes")
    private Integer rebotes;
    
    @Column(name = "faltas")
    private Integer faltas;
    
    @Column(name = "puntos")
    private Integer puntos;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "ESTADISTICAS_JUGADOR",
               joinColumns = @JoinColumn(name="estadisticass_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="jugadors_id", referencedColumnName="ID"))
    private Set<Jugador> jugadors = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCanastes() {
        return canastes;
    }

    public void setCanastes(Integer canastes) {
        this.canastes = canastes;
    }

    public Integer getAsistencies() {
        return asistencies;
    }

    public void setAsistencies(Integer asistencies) {
        this.asistencies = asistencies;
    }

    public Integer getRebotes() {
        return rebotes;
    }

    public void setRebotes(Integer rebotes) {
        this.rebotes = rebotes;
    }

    public Integer getFaltas() {
        return faltas;
    }

    public void setFaltas(Integer faltas) {
        this.faltas = faltas;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public Set<Jugador> getJugadors() {
        return jugadors;
    }

    public void setJugadors(Set<Jugador> jugadors) {
        this.jugadors = jugadors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Estadisticas estadisticas = (Estadisticas) o;

        if ( ! Objects.equals(id, estadisticas.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Estadisticas{" +
                "id=" + id +
                ", canastes='" + canastes + "'" +
                ", asistencies='" + asistencies + "'" +
                ", rebotes='" + rebotes + "'" +
                ", faltas='" + faltas + "'" +
                ", puntos='" + puntos + "'" +
                '}';
    }
}
