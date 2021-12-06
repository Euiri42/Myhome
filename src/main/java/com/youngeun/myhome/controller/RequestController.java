package com.youngeun.myhome.controller;

import com.youngeun.myhome.model.Contract;
import com.youngeun.myhome.model.Request;
import com.youngeun.myhome.repository.ContractRepository;
import com.youngeun.myhome.repository.RequestRepository;
import com.youngeun.myhome.repository.UserListRepository;
import com.youngeun.myhome.repository.UserRepository;
import com.youngeun.myhome.validator.ContractValidator;
import com.youngeun.myhome.validator.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/request")
public class RequestController {
    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private RequestValidator requestValidator;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ContractValidator contractValidator;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserListRepository getUserRepository;

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 20) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String searchText) {
        Page<Request> requests = requestRepository.findByClientContainingOrInstitutionContaining(searchText, searchText, pageable);
        int startPage = Math.max(1, requests.getPageable().getPageNumber() - 4);
        int endPage = Math.min(requests.getTotalPages(), requests.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("searchList", requests);

        return "request/list";
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id) {
        if (id == null) {
            model.addAttribute("request", new Request());
        } else {
            Request request = requestRepository.findById(id).orElse(null);
            model.addAttribute("request", request);
        }
        return "request/form";
    }

    @RequestMapping(value="/edit", method= RequestMethod.POST, params="action=save")
    public String accept(@Valid Contract contract, BindingResult bindingResult) {
        contractRepository.save(contract);
        requestRepository.deleteById(contract.getId());
        return "redirect:/request/list";
    }


    @RequestMapping(value="/edit", method=RequestMethod.POST, params="action=cancel")
    public String deny(@Valid Contract contract, BindingResult bindingResult) {
        requestRepository.deleteById(contract.getId());
        return "redirect:/request/list";
    }


    @PostMapping("/form")
    public String greetingSubmit(@Valid Contract contract, BindingResult bindingResult) {
        contractValidator.validate(contract, bindingResult);
        if (bindingResult.hasErrors()) {
            return "request/form";
        }
        contractRepository.save(contract);
        return "redirect:/request/list";
    }

    @PostMapping("/submit")
    public String saveData(@Valid Request request, BindingResult bindingResult) {
        requestRepository.save(request);
        return "redirect:/dataset/list";
    }
}
