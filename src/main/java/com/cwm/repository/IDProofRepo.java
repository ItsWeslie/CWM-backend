package com.cwm.repository;

import com.cwm.model.IDProof;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDProofRepo extends JpaRepository<IDProof, Long> {
}
