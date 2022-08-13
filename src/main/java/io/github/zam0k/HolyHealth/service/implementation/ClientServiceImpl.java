package io.github.zam0k.HolyHealth.service.implementation;

import io.github.zam0k.HolyHealth.domain.entities.Client;
import io.github.zam0k.HolyHealth.domain.entities.HealthProblem;
import io.github.zam0k.HolyHealth.domain.repository.ClientRepository;
import io.github.zam0k.HolyHealth.domain.repository.HealthProblemRepository;
import io.github.zam0k.HolyHealth.exception.BusinessRuleException;
import io.github.zam0k.HolyHealth.helper.NullAwareBeanUtilsBean;
import io.github.zam0k.HolyHealth.rest.dto.ClientDTO;
import io.github.zam0k.HolyHealth.rest.dto.RiskierClientDTO;
import io.github.zam0k.HolyHealth.rest.dto.UpdateClientDTO;
import io.github.zam0k.HolyHealth.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final HealthProblemRepository healthRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private NullAwareBeanUtilsBean beanUtilsBean;

    @Override
    public Client save(ClientDTO dto) {

        LocalDate birthdate = getLocalDate(dto.getBirthdate());
        Client client = modelMapper.map(dto, Client.class);
        client.setBirthdate(birthdate);

        List<UUID> ids = dto.getHealthProblems().stream().map(HealthProblem::getId).toList();
        Set<HealthProblem> multipleHealthProblems = healthRepository.getMultipleHealthProblems(ids);
        if(multipleHealthProblems.size() < ids.size()) throw new BusinessRuleException("Incorrect Health Problem id was inserted.");
        client.setHealthProblems(multipleHealthProblems);

        Integer typeSum = getTypeSum(multipleHealthProblems);
        Double score = calculateScore(typeSum);
        client.setScore(score);

        clientRepository.save(client);
        return client;

    }
    @Override
    public List<ClientDTO> listAll() {
        List<Client> clientsList = clientRepository.findAll();
        return modelMapper
                .map(clientsList, new TypeToken<List<ClientDTO>>(){}.getType());
    }

    @Override
    public void update(UpdateClientDTO dto, UUID id) {
        Client client = getClient(id);

        try {
            beanUtilsBean.copyProperties(client, dto);
            clientRepository.save(client);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new BusinessRuleException(e.getMessage());
        }

    }

    @Override
    public void remove(UUID id) {
        Client client = getClient(id);
        clientRepository.delete(client);
    }

    @Override
    public Set<RiskierClientDTO> listRiskier() {
        return clientRepository.findTop10ByOrderByScoreDesc();
    }

    @Override
    public ClientDTO getById(UUID id) {
        Client client = getClient(id);
        return modelMapper.map(client, ClientDTO.class);
    }

    private LocalDate getLocalDate(String birthdate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
            return LocalDate.parse(birthdate, formatter);
        } catch (DateTimeParseException e) {
            throw new BusinessRuleException("Incorrect birthdate format: must be MM-dd-yyyy");
        }
    }

    private Double calculateScore(int typeValueSum) {
        return (1 / ( 1 + Math.exp(-(-2.8 + typeValueSum )))) * 100;
    }

    private Integer getTypeSum(Set<HealthProblem> multipleHealthProblems) {
        return multipleHealthProblems.stream()
                .map(hp -> hp.getType().getTypeValue()).mapToInt(Integer::intValue).sum();
    }


    private Client getClient(UUID id) {
        return clientRepository.findById(id).orElseThrow(() -> new BusinessRuleException("Client cannot be found."));
    }


}
