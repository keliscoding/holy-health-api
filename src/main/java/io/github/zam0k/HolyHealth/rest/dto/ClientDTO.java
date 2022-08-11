package io.github.zam0k.HolyHealth.rest.dto;

import io.github.zam0k.HolyHealth.domain.entities.HealthProblem;
import io.github.zam0k.HolyHealth.domain.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDTO {
    private UUID id;
    private String name;
    private LocalDate birthdate;
    private Gender gender;
    private List<HealthProblem> healthProblems;
}
