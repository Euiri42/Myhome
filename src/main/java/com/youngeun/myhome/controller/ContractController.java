package com.youngeun.myhome.controller;

import com.youngeun.myhome.model.Contract;
import com.youngeun.myhome.repository.ContractRepository;
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
@RequestMapping("/contract")
public class ContractController {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ContractValidator contractValidator;

    @GetMapping("/list")
    public String consent(Model model, @PageableDefault(size = 20) Pageable pageable, @RequestParam(required = false,defaultValue = "") String searchText) {
        Page<Contract> contracts = contractRepository.findByInstitutionContaining(searchText, pageable);
        int startPage = Math.max(1, contracts.getPageable().getPageNumber() - 4);
        int endPage = Math.min(contracts.getTotalPages(), contracts.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("contracts", contracts);


        System.out.println("contract = " + contracts.getTotalPages());


        String username = null;
        List<Contract> contractList = new ArrayList<>();
        List<Contract> contractArray = contracts.getContent();


        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();

            if (((UserDetails) principal).getAuthorities().toString().equals("[PATIENT]")) {

                for (int i = 0; i < contractArray.size(); i++) {
                    if (contractArray.get(i).getClient().equals(username)) {
                        contractList.add(contractArray.get(i));
                    }
                }

                model.addAttribute("contractUser", contractList);
            }

            if (((UserDetails) principal).getAuthorities().toString().equals("[INSTITUTION]")) {

                for (int i = 0; i < contractArray.size(); i++) {
                    if (contractArray.get(i).getInstitution().equals(username)) {
                        contractList.add(contractArray.get(i));
                    }
                }

                model.addAttribute("contractInstitution", contractList);
            }
        } else {
            //anonymousUser
            username = principal.toString();
        }

        return "contract/list"; //이후 consent 파일 만들기
    }

//        @PostMapping("/requestData")
//        public String requestData(@Valid Contract contract, BindingResult bindingResult) {
//            System.out.println("requestData");
//            return "contract/patient_data";//이후 fhir와 연결해서 환자 data 보여줄 수 있도록 return,"contract/list/patient_data"
//        }

        @PostMapping("/form")
        public String withdrawContract(@Valid Contract contract, BindingResult bindingResult) {
            contractRepository.deleteById(contract.getId());
            return "redirect:/contract/list";//계약 철회시 동의 버튼 사라지게 됨. id값도 사라짐.
        }

    }