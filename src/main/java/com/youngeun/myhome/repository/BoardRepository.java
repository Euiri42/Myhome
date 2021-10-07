package com.youngeun.myhome.repository;

import com.youngeun.myhome.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {
    List<Board> findByTitle(String title);
    List<Board> findByTitleOrContent(String title,String content);

    Page<Board> findByTitleContainingOrContentContainingOrNameContaining(String title, String content, String name, Pageable pageable);
}
