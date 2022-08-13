package io.github.zam0k.HolyHealth.rest.dto;

import io.github.zam0k.HolyHealth.domain.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateClientDTO {
    private String name;
    private LocalDate birthdate;
    private Gender gender;
}
