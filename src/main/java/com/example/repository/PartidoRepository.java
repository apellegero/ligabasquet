package com.example.repository;

import com.example.domain.Partido;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Partido entity.
 */
public interface PartidoRepository extends JpaRepository<Partido,Long> {

    @Query("select distinct partido from Partido partido left join fetch partido.estadisticass")
    List<Partido> findAllWithEagerRelationships();

    @Query("select partido from Partido partido left join fetch partido.estadisticass where partido.id =:id")
    Partido findOneWithEagerRelationships(@Param("id") Long id);

}
