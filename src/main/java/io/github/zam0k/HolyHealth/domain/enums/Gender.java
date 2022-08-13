package io.github.zam0k.HolyHealth.domain.enums;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

public enum Gender {
    MALE,
    FEMALE,
    @JsonEnumDefaultValue
    UNKNOWN
}
