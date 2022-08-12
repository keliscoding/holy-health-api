package io.github.zam0k.HolyHealth.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.zam0k.HolyHealth.domain.enums.Gender;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birthdate;
    @Enumerated(EnumType.STRING)
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
