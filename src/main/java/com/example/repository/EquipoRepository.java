package com.example.repository;

import com.example.domain.Equipo;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Equipo entity.
 */
public interface EquipoRepository extends JpaRepository<Equipo,Long> {

    @Query("select distinct equipo from Equipo equipo left join fetch equipo.socios")
    List<Equipo> findAllWithEagerRelationships();

    @Query("select equipo from Equipo equipo left join fetch equipo.socios where equipo.id =:id")
    Equipo findOneWithEagerRelationships(@Param("id") Long id);

}
