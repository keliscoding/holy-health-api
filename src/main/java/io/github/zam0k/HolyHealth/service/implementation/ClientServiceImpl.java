package io.github.zam0k.HolyHealth.service.implementation;

import io.github.zam0k.HolyHealth.domain.entities.Client;
import io.github.zam0k.HolyHealth.domain.entities.HealthProblem;
import io.github.zam0k.HolyHealth.domain.repository.ClientRepository;
import io.github.zam0k.HolyHealth.domain.repository.HealthProblemRepository;
import io.github.zam0k.HolyHealth.exception.EntityNotFoundException;
import io.github.zam0k.HolyHealth.exception.SearchHealthProblemsException;
import io.github.zam0k.HolyHealth.exception.UpdateEntityException;
import io.github.zam0k.HolyHealth.helper.NullAwareBeanUtilsBean;
import io.github.zam0k.HolyHealth.rest.dto.ClientDTO;
import io.github.zam0k.HolyHealth.rest.dto.RiskierClientDTO;
import io.github.zam0k.HolyHealth.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
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
    @Transactional
    public Client save(ClientDTO dto) {
        Client client = modelMapper.map(dto, Client.class);

        List<UUID> ids = dto.getHealthProblems().stream().map(HealthProblem::getId).toList();

        Set<HealthProblem> multipleHealthProblems = healthRepository.getMultipleHealthProblems(ids);
        if(multipleHealthProblems.size() < ids.size()) throw new SearchHealthProblemsException();
        client.setHealthProblems(multipleHealthProblems);

        Integer typeSum = getTypeSum(multipleHealthProblems);

        Double score = calculateScore(typeSum);
        client.setScore(score);

        clientRepository.save(client);
        return client;

    }

    private Double calculateScore(int typeValueSum) {
        return (1 / ( 1 + Math.exp(-(-2.8 + typeValueSum )))) * 100;
    }

    private Integer getTypeSum(Set<HealthProblem> multipleHealthProblems) {
        return multipleHealthProblems.stream()
                .map(hp -> hp.getType().getTypeValue()).mapToInt(Integer::intValue).sum();
    }

    @Override
    public ClientDTO getById(UUID id) {
        Client client = clientRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(client, ClientDTO.class);
    }

    @Override
    public List<ClientDTO> listAll() {
        List<Client> clientsList = clientRepository.findAll();
        return modelMapper
                .map(clientsList, new TypeToken<List<ClientDTO>>(){}.getType());
    }

    @Override
    public void update(ClientDTO dto, UUID id) {
        Client client = clientRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        try {
            beanUtilsBean.copyProperties(client, dto);
            clientRepository.save(client);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new UpdateEntityException(e.getMessage());
        }

    }

    @Override
    public void remove(UUID id) {
        Client client = clientRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        clientRepository.delete(client);
    }

    @Override
    public Set<RiskierClientDTO> listRiskier() {
        return clientRepository.findTop10ByOrderByScoreDesc();
    }

}
