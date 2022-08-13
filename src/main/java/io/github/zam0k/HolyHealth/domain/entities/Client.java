package io.github.zam0k.HolyHealth.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.zam0k.HolyHealth.domain.enums.Gender;
import io.github.zam0k.HolyHealth.validation.EnumPattern;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;


@Entity
@Table
@Data
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @NotEmpty(message = "Name field cannot be empty.")
    @NotNull(message = "Name field cannot be null.")
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    @NotNull(message = "Birthdate field cannot be null.")
    @Past(message = "Birthdate must be in the past.")
    private LocalDate birthdate;
    @Enumerated(EnumType.STRING)
    @EnumPattern(regexp = "FEMALE|MALE", message = "Gender must be \"MALE\" or \"FEMALE\".")
    private Gender gender;
    @Column(name = "created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @Setter(AccessLevel.NONE)
    private LocalDateTime updatedAt;

    private Double score;

    @ManyToMany
    @JoinTable(name = "client_health_problem",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "health_problem_id"))
    private Set<HealthProblem> healthProblems;

    @PrePersist
    private void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
