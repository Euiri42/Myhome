package com.youngeun.myhome.validator;

import com.youngeun.myhome.model.Contract;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

@Component
public class ContractValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Contract.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Contract c = (Contract) obj;
        if(StringUtils.isEmpty(c.getInstitution())) {
            errors.rejectValue("institution", "key", "내용을 입력하세요");
        }
    }
}
