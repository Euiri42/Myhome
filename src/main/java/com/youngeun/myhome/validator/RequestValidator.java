package com.youngeun.myhome.validator;

import com.youngeun.myhome.model.Contract;
import com.youngeun.myhome.model.Request;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

@Component
public class RequestValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Request.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Request r = (Request) obj;
        if(StringUtils.isEmpty(r.getInstitution())) {
            errors.rejectValue("institution", "key", "내용을 입력하세요");
        }
    }
}
