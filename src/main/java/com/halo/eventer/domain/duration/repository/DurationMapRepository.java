package com.halo.eventer.domain.duration.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.halo.eventer.domain.duration.DurationMap;

public interface DurationMapRepository extends JpaRepository<DurationMap, Long> {

    @Query("SELECT dm FROM DurationMap dm WHERE dm.duration.id in :durationIds ")
    List<DurationMap> findAllByDurationIds(@Param("durationIds") List<Long> ids);
}
