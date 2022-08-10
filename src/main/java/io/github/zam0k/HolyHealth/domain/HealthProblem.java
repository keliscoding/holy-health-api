package io.github.zam0k.HolyHealth.domain;

import io.github.zam0k.HolyHealth.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HealthProblem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Type type;
}
