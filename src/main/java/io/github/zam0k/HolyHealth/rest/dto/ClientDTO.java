package io.github.zam0k.HolyHealth.rest.dto;

import io.github.zam0k.HolyHealth.domain.entities.HealthProblem;
import io.github.zam0k.HolyHealth.domain.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {
    private UUID id;
    private String name;
    private LocalDate birthdate;
    private Gender gender;
    private Double score;
    private Set<HealthProblem> healthProblems;
}
