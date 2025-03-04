package com.halo.eventer.domain.manager.repository;

import com.halo.eventer.domain.manager.Manager;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    @Query("select m from Manager m where m.festival.id = :id ")
    public List<Manager> searchManagerByFestivalId(Long id);
}
