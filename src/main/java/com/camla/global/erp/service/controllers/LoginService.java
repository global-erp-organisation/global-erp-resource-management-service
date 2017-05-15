package com.camla.global.erp.service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.camla.global.erp.service.domain.UserModel;
import com.camlait.global.erp.delegate.auth.UserManager;
import com.camlait.global.erp.delegate.util.encryption.PasswordManager;
import com.camlait.global.erp.domain.auth.User;
import com.camlait.global.erp.validation.Validator;

@RestController
@RequestMapping(value = "/global/v1/login")
public class LoginService {

    private final UserManager userManager;
    private final PasswordManager passwordManager;
    private final Validator<UserModel> userModelValidator;

    @Autowired
    public LoginService(UserManager userManager, PasswordManager passwordManager, Validator<UserModel> userModelValidator) {
        this.passwordManager = passwordManager;
        this.userManager = userManager;
        this.userModelValidator = userModelValidator;
    }

    @RequestMapping(value = "/checking", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> login(@RequestBody UserModel userModel) {
        final List<String> errors = userModelValidator.validate(userModel);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(String.join("\n", errors));
        }
        final User user = userManager.retrieveUserByEmail(userModel.getEmail());
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
