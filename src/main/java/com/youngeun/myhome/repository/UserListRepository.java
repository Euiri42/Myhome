package com.youngeun.myhome.repository;

import com.youngeun.myhome.model.UserList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserListRepository extends JpaRepository<UserList,Long> {
    Page<UserList> findByUsernameContaining(String username, Pageable pageable);
}
