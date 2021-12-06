package com.youngeun.myhome.controller;

import com.youngeun.myhome.model.Contract;
import com.youngeun.myhome.model.Request;
import com.youngeun.myhome.model.RequestButton;
import com.youngeun.myhome.model.UserList;
import com.youngeun.myhome.repository.ContractRepository;
import com.youngeun.myhome.repository.RequestRepository;
import com.youngeun.myhome.repository.UserListRepository;
import com.youngeun.myhome.repository.UserRepository;
import com.youngeun.myhome.validator.ContractValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/dataset")
public class DatasetController {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ContractValidator contractValidator;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserListRepository getUserRepository;

    @Autowired
    private RequestRepository requestRepository;

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 20) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String searchText) {
        Page<UserList> datasets = getUserRepository.findByUsernameContaining(searchText, pageable);
        int startPage = Math.max(1, datasets.getPageable().getPageNumber() - 4);
        int endPage = Math.min(datasets.getTotalPages(), datasets.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("getUsers", datasets);

        System.out.println("datasets = " + datasets.getTotalPages());


        String username = null;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            //anonymousUser
            username = principal.toString();
        }

        List<UserList> userLists = datasets.getContent();

        for (int i = 0; i < userLists.size(); i++) {
            System.out.println("user " + userLists.get(i));
        }

        List<Request> requests = requestRepository.findAll();
        List<Contract> contracts = contractRepository.findAll();

        List<RequestButton> requestButtons = new ArrayList<>();

        for (int i = 0; i < userLists.size(); i++) {
            if (!userLists.get(i).getUsername().equals(username)) {
                RequestButton requestButton = new RequestButton();
                requestButton.setId(userLists.get(i).getId().toString());
                requestButton.setClient(userLists.get(i).getUsername());
                requestButton.setInstitution("0");
                requestButton.setButton("0");

                for (int j = 0; j < requests.size(); j ++) {
                    if (userLists.get(i).getUsername().equals(requests.get(j).getClient())) {
                        requestButton.setButton("1");
                    }
                }

                for (int j = 0; j < contracts.size(); j ++) {
                    if (userLists.get(i).getUsername().equals(contracts.get(j).getClient())
                            && username.equals(contracts.get(j).getInstitution())
                    ) {
                        requestButton.setButton("2");
                    }
                }

                requestButtons.add(requestButton);
            }
        }

        System.out.println("rest" + requestButtons);

        model.addAttribute("searchList", requestButtons);

        model.addAttribute("requests", requests);
        return "dataset/list";//추후 datalist 파일 만들기
    }

    @PostMapping("/form")
    public String saveData(@Valid Contract contract, BindingResult bindingResult) {
        contractRepository.save(contract);
        return "redirect:/dataset/list";
    }
}
