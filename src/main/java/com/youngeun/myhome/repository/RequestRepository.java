package com.youngeun.myhome.repository;

import com.youngeun.myhome.model.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request,Long> {

    Page<Request> findByClientContainingOrInstitutionContaining(String client, String institution, Pageable pageable);
}
