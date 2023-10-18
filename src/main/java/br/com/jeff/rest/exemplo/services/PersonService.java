package br.com.jeff.rest.exemplo.services;

import br.com.jeff.rest.exemplo.data.vo.v1.PersonVO;
import br.com.jeff.rest.exemplo.data.vo.v2.PersonVOV2;
import br.com.jeff.rest.exemplo.exceptions.ResourceNotFoundException;
import br.com.jeff.rest.exemplo.mapper.ModelMapper;
import br.com.jeff.rest.exemplo.mapper.custom.PersonMapper;
import br.com.jeff.rest.exemplo.model.Person;
import br.com.jeff.rest.exemplo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonService {
    private Logger logger = Logger.getLogger(PersonService.class.getName());

    @Autowired
    PersonRepository repository;

    @Autowired
    PersonMapper mapper;

    public List<PersonVO> findAll() {
        logger.info("Finding all people!");

        return ModelMapper.parseListObjects(repository.findAll(), PersonVO.class);
    }

    public PersonVO findById(Long id) {
        logger.info("Finding one people!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        return ModelMapper.parseObject(entity, PersonVO.class);
    }

    public PersonVO create(PersonVO person) {
        logger.info("Creating one people!");

        var entity = ModelMapper.parseObject(person, Person.class);
        var model = ModelMapper.parseObject(repository.save(entity), PersonVO.class);
        return model;
    }

    public PersonVOV2 createV2(PersonVOV2 person) {
        logger.info("Creating one people!");

        var entity = mapper.convertVoToEntity(person);
        var model = mapper.convertEntityToVo(repository.save(entity));
        return model;
    }

    public PersonVO update(PersonVO PersonVO) {
        logger.info("Update one people!");

        var entity = repository.findById(PersonVO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(PersonVO.getFirstName());
        entity.setLastName(PersonVO.getLastName());
        entity.setAddress(PersonVO.getAddress());
        entity.setGender(PersonVO.getGender());

        var model = ModelMapper.parseObject(repository.save(entity), PersonVO.class);
        return model;
    }

    public void delete(Long id) {
        logger.info("Delete one people!");
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        repository.delete(entity);
    }
}
