package com.camla.global.erp.resource.domain;

import com.camlait.global.erp.domain.helper.SerializerHelper;

public abstract class AbstractModel {

    public String toJson() {
        return SerializerHelper.toJson(this);
    }
}
