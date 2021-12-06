package com.youngeun.myhome.controller;

import com.youngeun.myhome.model.Board;
import com.youngeun.myhome.repository.BoardRepository;
import com.youngeun.myhome.service.BoardService;
import com.youngeun.myhome.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardValidator boardValidator;

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 5)Pageable pageable, @RequestParam(required = false,defaultValue = "") String searchText){
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContainingOrNameContaining(searchText,searchText,searchText,pageable);
        int startPage = Math.max(1,boards.getPageable().getPageNumber()-4);
        int endPage = Math.min(boards.getTotalPages(),boards.getPageable().getPageNumber()+4);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("boards",boards);
        return "list";
    }

//    @GetMapping("/consent")
//    public String consent_list(){
//        return "consent";
//    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id){
        if (id == null){
            model.addAttribute("board",new Board());
        }
        else{
            Board board = boardRepository.findById(id).orElse(null);
            model.addAttribute("board",board);
        }
        return "form";
    }

    @PostMapping("/form")
    public String postForm(@Valid Board board, BindingResult bindingResult, Authentication authentication){
        boardValidator.validate(board, bindingResult);
        if(bindingResult.hasErrors()){
            return "form";
        }

        String username = authentication.getName();
        boardService.save(username,board);
        return "redirect:/list";
    }

}
