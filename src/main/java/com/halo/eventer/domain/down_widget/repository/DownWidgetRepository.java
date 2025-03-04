package com.halo.eventer.domain.down_widget.repository;

import com.halo.eventer.domain.down_widget.DownWidget;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DownWidgetRepository extends JpaRepository<DownWidget, Long> {

  @Query("select e from DownWidget e WHERE e.festival.id = :id")
  List<DownWidget> findAllByFestivalId(@Param("id") Long id);
}
