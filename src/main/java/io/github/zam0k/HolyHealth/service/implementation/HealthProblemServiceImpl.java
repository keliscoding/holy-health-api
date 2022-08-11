package io.github.zam0k.HolyHealth.service.implementation;

import io.github.zam0k.HolyHealth.domain.entities.HealthProblem;
import io.github.zam0k.HolyHealth.domain.repository.HealthProblemRepository;
import io.github.zam0k.HolyHealth.service.HealthProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HealthProblemServiceImpl implements HealthProblemService {

    private final HealthProblemRepository healthRepository;
    @Override
    public HealthProblem save(HealthProblem healthProblem) {
        return healthRepository.save(healthProblem);
    }
}
