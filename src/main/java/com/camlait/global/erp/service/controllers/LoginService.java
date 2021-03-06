package com.camlait.global.erp.service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.camlait.global.erp.delegate.util.encryption.PasswordManager;
import com.camlait.global.erp.domain.auth.User;
import com.camlait.global.erp.service.domain.UserModel;
import com.camlait.global.erp.validation.Validator;
import com.camlait.global.erp.validation.ValidatorResult;

@RestController
@RequestMapping(value = "/global/v1/login")
public class LoginService {

    private final PasswordManager passwordManager;
    private final Validator<UserModel, User> userModelValidator;

    @Autowired
    public LoginService(PasswordManager passwordManager, Validator<UserModel, User> userModelValidator) {
        this.passwordManager = passwordManager;
        this.userModelValidator = userModelValidator;
    }

    @RequestMapping(value = "/checking", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> login(@RequestBody UserModel userModel) {
        final ValidatorResult<User> result = userModelValidator.validate(userModel);
        final List<String> errors = result.getErrors();
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(String.join("\n", errors));
        }
        final User user = result.getResult();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with identifier " + userModel.getEmail() + " not found.");
        }
        final Boolean check = passwordManager.check(userModel.getPassword(), user.getEncryptPassword());
        if (!check) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("A wrong password have been provided.");
        }
        return ResponseEntity.ok(user.toJson());
    }
}
