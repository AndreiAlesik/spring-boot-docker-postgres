package com.kaluzny.demo.service;

import com.kaluzny.demo.domain.Automobile;
import com.kaluzny.demo.domain.AutomobileRepository;
import com.kaluzny.demo.dto.AutoRequestDto;
import com.kaluzny.demo.dto.AutoResponseDto;
import com.kaluzny.demo.mapper.AutomobileMapper;
import jakarta.jms.Connection;
import jakarta.jms.JMSException;
import jakarta.jms.Topic;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class AutomobileServiceBean implements AutomobileService {

    private final AutomobileRepository automobileRepository;
    private final AutomobileMapper automobileMapper;
    private final JmsTemplate jmsTemplate;

    public AutomobileServiceBean(AutomobileRepository automobileRepository, AutomobileMapper automobileMapper, JmsTemplate jmsTemplate) {
        this.automobileRepository = automobileRepository;
        this.automobileMapper = automobileMapper;
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public ResponseEntity<UUID> saveAutomobileAndPushMessage(AutoRequestDto automobile) {
        try (Connection connection = Objects.requireNonNull(jmsTemplate.getConnectionFactory()).createConnection()) {
            Topic autoTopic = connection.createSession().createTopic("AutoTopic");
            Automobile savedAuto = automobileRepository.save(automobileMapper.mapToEntity(automobile));

            jmsTemplate.convertAndSend(autoTopic, savedAuto);

            return new ResponseEntity<>(savedAuto.getId(), HttpStatus.CREATED);
        } catch (JMSException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public Collection<AutoResponseDto> getAllAutomobiles() {
        return automobileRepository.findAllExists()
                .stream().map(automobileMapper::mapToDto)
                .toList();
    }

    @Override
    public AutoResponseDto getAutomobileById(UUID id) {
        return automobileRepository.findById(id)
                .map(automobileMapper::mapToDto)
                .orElseThrow();
    }

    @Override
    public Collection<AutoResponseDto> findAutomobileByName(String name) {
        return automobileRepository.findByName(name)
                .stream().map(automobileMapper::mapToDto)
                .toList();
    }

    @Override
    public AutoResponseDto refreshAutomobile(UUID id, AutoRequestDto automobile) {
        Automobile updatedAuto = automobileRepository.updateAutomobile(automobile.name(), automobile.color(), automobile.originalColor(), id);
        return automobileMapper.mapToDto(updatedAuto);
    }

    @Override
    public void removeAutomobileById(UUID id) {
        automobileRepository.markRemoved(id);
    }

    @Override
    public void removeAllAutomobiles() {
        automobileRepository.deleteAll();
    }

    @Override
    public ResponseEntity<Collection<AutoResponseDto>> findAutomobileByColor(String color) {
        try (Connection connection = Objects.requireNonNull(jmsTemplate.getConnectionFactory()).createConnection()) {
            Topic autoTopic = connection.createSession().createTopic("AutoTopicList");
            List<AutoResponseDto> automobiles = automobileRepository.findByColor(color)
                    .stream().map(automobileMapper::mapToDto)
                    .toList();

            jmsTemplate.convertAndSend(autoTopic, automobiles);

            return new ResponseEntity<>(automobiles, HttpStatus.OK);
        } catch (JMSException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Collection<AutoResponseDto> findAutomobileByNameAndColor(String name, String color) {
        return automobileRepository.findByNameAndColor(name, color)
                .stream().map(automobileMapper::mapToDto)
                .toList();
    }

    @Override
    public Collection<AutoResponseDto> findAutomobileByColorStartsWith(String colorStartsWith, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return automobileRepository.findByColorStartsWith(colorStartsWith, pageable)
                .stream().map(automobileMapper::mapToDto)
                .toList();
    }
}
