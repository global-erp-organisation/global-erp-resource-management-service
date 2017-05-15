package com.camla.global.erp.service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class UserModel extends AbstractModel {

    private String email;
    private String password;

    public UserModel() {

    }
}
