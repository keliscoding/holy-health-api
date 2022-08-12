package io.github.zam0k.HolyHealth.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import io.github.zam0k.HolyHealth.domain.entities.Client;
import io.github.zam0k.HolyHealth.domain.entities.HealthProblem;
import io.github.zam0k.HolyHealth.domain.repository.ClientRepository;
import io.github.zam0k.HolyHealth.domain.repository.HealthProblemRepository;
import io.github.zam0k.HolyHealth.rest.dto.ClientDTO;
import io.github.zam0k.HolyHealth.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final HealthProblemRepository healthRepository;
    private ModelMapper modelMapper = new ModelMapper();
    private ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

    @Override
    public Client save(ClientDTO dto) {
        Client client = modelMapper.map(dto, Client.class);
        // TO-DO: Custom errors and implements controller adviser

        List<UUID> ids = dto.getHealthProblems().stream().map(hp -> {
            return hp.getId();
        }).collect(Collectors.toList());

        Set<HealthProblem> multipleHealthProblems = healthRepository.getMultipleHealthProblems(ids);
        if(multipleHealthProblems.size() < ids.size()) throw new RuntimeException();
        client.setHealthProblems(multipleHealthProblems);
        clientRepository.save(client);
        return client;

    }

    @Override
    public ClientDTO getById(UUID id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new RuntimeException());
        ClientDTO clientDTO = modelMapper.map(client, ClientDTO.class);
        return clientDTO;
    }

    @Override
    public List<ClientDTO> listAll() {
        List<Client> clientsList = clientRepository.findAll();
        List<ClientDTO> clientsDTO = modelMapper
                .map(clientsList, new TypeToken<List<ClientDTO>>(){}.getType());
        return clientsDTO;
    }

    @Override
    public void update(JsonPatch patch, UUID id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new RuntimeException());

        try {
            Client clientPatched = applyPatchToClient(patch, client);
            clientPatched.setId(client.getId());
            clientRepository.save(clientPatched);
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void remove(UUID id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new RuntimeException());
        clientRepository.delete(client);
    }

    private Client applyPatchToClient(JsonPatch patch, Client client) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(client, JsonNode.class));
        return objectMapper.treeToValue(patched, Client.class);
    }
}