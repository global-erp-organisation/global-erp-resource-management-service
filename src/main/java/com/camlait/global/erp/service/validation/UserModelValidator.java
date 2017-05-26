package com.camlait.global.erp.service.validation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.camlait.global.erp.delegate.auth.UserManager;
import com.camlait.global.erp.domain.auth.User;
import com.camlait.global.erp.service.domain.UserModel;
import com.camlait.global.erp.validation.Validator;
import com.camlait.global.erp.validation.ValidatorResult;
import com.google.common.collect.Lists;

@Component
public class UserModelValidator implements Validator<UserModel, User> {

    private final UserManager userManager;

    @Autowired
    public UserModelValidator(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public ValidatorResult<User> validate(UserModel toValidate) {
        final List<String> errors = Lists.newArrayList();
        
        if (errors.isEmpty()) {
            final User user = userManager.retrieveUser(toValidate.getEmail());
            return build(errors, user);
        }
        return build(errors, null);
    }

    @Override
    public ValidatorResult<User> build(List<String> errors, User result) {
        final ValidatorResult<User> vr = new ValidatorResult<User>();
        vr.setErrors(errors);
        vr.setResult(result);
        return vr;
    }

}
