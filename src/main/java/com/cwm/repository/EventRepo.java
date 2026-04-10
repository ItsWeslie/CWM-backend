package com.cwm.repository;

import com.cwm.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepo extends JpaRepository<Event, Long> {

    @Modifying
    @Query("DELETE FROM Event e where e.id=:id")
    int deleteByIdAndReturnCount(@Param("id") long id);
}
