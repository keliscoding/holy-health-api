package io.github.zam0k.HolyHealth.service;

import io.github.zam0k.HolyHealth.domain.entities.Client;
import io.github.zam0k.HolyHealth.rest.dto.ClientDTO;

import java.util.List;
import java.util.UUID;

public interface ClientService {
    Client save(ClientDTO client);
    ClientDTO getById(UUID id);
    List<ClientDTO> listAll();
    void update(ClientDTO client, UUID id);
    void remove(UUID id);
}
