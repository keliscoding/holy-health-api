package io.github.zam0k.HolyHealth.domain.enums;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

public enum Type {
    TYPE_1(1),
    TYPE_2(2),
    @JsonEnumDefaultValue
    UNKNOWN(0);

    private int typeValue;

    Type(int typeValue) {
        this.typeValue = typeValue;
    }

    public int getTypeValue() {
        return typeValue;
    }
}
