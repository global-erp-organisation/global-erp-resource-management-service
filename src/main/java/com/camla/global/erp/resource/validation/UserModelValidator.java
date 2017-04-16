package com.camla.global.erp.resource.validation;

import java.util.List;

import org.springframework.stereotype.Component;

import com.camla.global.erp.resource.domain.UserModel;
import com.camlait.global.erp.validation.Validator;

@Component
public class UserModelValidator implements Validator<UserModel> {

    @Override
    public List<String> validate(UserModel toValidate) {
        // TODO Auto-generated method stub
        return null;
    }

}
