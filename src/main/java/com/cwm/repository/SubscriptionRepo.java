package com.cwm.repository;

import com.cwm.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepo extends JpaRepository<Subscription, Long> {
    @Modifying
    @Query("DELETE FROM Subscription s WHERE s.id = :id")
    int deleteByIdAndReturnCount(@Param("id") long id);
}
