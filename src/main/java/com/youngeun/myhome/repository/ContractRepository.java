package com.youngeun.myhome.repository;

import com.youngeun.myhome.model.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract,Long> {
    Page<Contract> findByInstitutionContaining(String institution, Pageable pageable);
    Contract findByClient(String client);
    Contract findByInstitution(String institution);
}
