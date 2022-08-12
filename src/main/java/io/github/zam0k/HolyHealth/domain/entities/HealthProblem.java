package io.github.zam0k.HolyHealth.domain.entities;

import io.github.zam0k.HolyHealth.domain.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private String name;
    @Enumerated(EnumType.STRING)
    private Type type;
}
