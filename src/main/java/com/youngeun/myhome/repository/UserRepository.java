package com.youngeun.myhome.repository;

import com.youngeun.myhome.model.User;
import com.youngeun.myhome.model.UserList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Entity;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long>  {
    List<User> findAll();
    User findByUsername(String username);

}
