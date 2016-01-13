package com.example.repository;

import com.example.domain.Estadisticas;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Estadisticas entity.
 */
public interface EstadisticasRepository extends JpaRepository<Estadisticas,Long> {

    @Query("select distinct estadisticas from Estadisticas estadisticas left join fetch estadisticas.jugadors")
    List<Estadisticas> findAllWithEagerRelationships();

    @Query("select estadisticas from Estadisticas estadisticas left join fetch estadisticas.jugadors where estadisticas.id =:id")
    Estadisticas findOneWithEagerRelationships(@Param("id") Long id);

}
