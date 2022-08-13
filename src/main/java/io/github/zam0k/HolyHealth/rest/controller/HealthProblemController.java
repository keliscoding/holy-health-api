package io.github.zam0k.HolyHealth.rest.controller;

import io.github.zam0k.HolyHealth.domain.entities.HealthProblem;
import io.github.zam0k.HolyHealth.service.HealthProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/health-problems")
public class HealthProblemController {

    @Autowired
    private HealthProblemService service;

    @PostMapping
    @ResponseStatus(CREATED)
    public HealthProblem save(@Valid @RequestBody HealthProblem healthProblem) {
        HealthProblem hp = service.save(healthProblem);
        return hp;
    }
}
