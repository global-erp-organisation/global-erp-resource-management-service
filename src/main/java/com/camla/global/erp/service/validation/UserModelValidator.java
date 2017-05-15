package com.camla.global.erp.service.validation;

import java.util.List;
import org.springframework.stereotype.Component;

import com.camla.global.erp.service.domain.UserModel;
import com.camlait.global.erp.validation.Validator;
import com.google.common.collect.Lists;

@Component
public class UserModelValidator implements Validator<UserModel> {

    @Override
    public List<String> validate(UserModel toValidate) {
        final List<String> errors = Lists.newArrayList();
        return errors;
    }

}
