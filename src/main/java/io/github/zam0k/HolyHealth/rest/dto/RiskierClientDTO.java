package io.github.zam0k.HolyHealth.rest.dto;

import io.github.zam0k.HolyHealth.domain.enums.Gender;

import java.time.LocalDate;
import java.util.UUID;

public interface RiskierClientDTO {
    UUID getId();
    String getName();
    LocalDate getBirthdate();
    Gender getGender();
    Double getScore();
}
