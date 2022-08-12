package io.github.zam0k.HolyHealth.domain.repository;

import io.github.zam0k.HolyHealth.domain.entities.HealthProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface HealthProblemRepository extends JpaRepository<HealthProblem, UUID> {
    @Query("SELECT hp FROM HealthProblem hp WHERE hp.id IN :ids")
    Set<HealthProblem> getMultipleHealthProblems(@Param("ids") List<UUID> ids);
}
