package io.github.zam0k.HolyHealth.domain.entities;

import io.github.zam0k.HolyHealth.domain.enums.Type;
import io.github.zam0k.HolyHealth.validation.EnumPattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;


@Entity
@Table(name = "health_problem")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HealthProblem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @NotNull(message = "Name field cannot be null.")
    @NotEmpty(message = "Name field cannot be empty.")
    private String name;

    @NotNull(message = "Type field cannot be null.")
    @EnumPattern(regexp = "TYPE_1|TYPE_2", message = "Type must be \"TYPE_1\" or \"TYPE_2\"")
    @Enumerated(EnumType.STRING)
    private Type type;
}
