package io.github.zam0k.HolyHealth.domain.repository;

import io.github.zam0k.HolyHealth.domain.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
}
