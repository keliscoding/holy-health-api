package io.github.zam0k.HolyHealth.service;

import io.github.zam0k.HolyHealth.domain.entities.Client;
import io.github.zam0k.HolyHealth.rest.dto.ClientDTO;
import io.github.zam0k.HolyHealth.rest.dto.RiskierClientDTO;
import io.github.zam0k.HolyHealth.rest.dto.UpdateClientDTO;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ClientService {
    Client save(ClientDTO client);
    ClientDTO getById(UUID id);
    List<ClientDTO> listAll();
    void update(UpdateClientDTO client, UUID id);
    void remove(UUID id);
    Set<RiskierClientDTO> listRiskier();
}
