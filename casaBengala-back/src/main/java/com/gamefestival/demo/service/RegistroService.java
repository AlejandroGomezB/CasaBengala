package com.gamefestival.demo.service;

import com.gamefestival.demo.dto.RegistroDtoRequest;
import com.gamefestival.demo.dto.RegistroDtoResponse;
import com.gamefestival.demo.entity.RegistroEntity;
import com.gamefestival.demo.exception.RegistroException;
import com.gamefestival.demo.mapper.MapToRegistroDto;
import com.gamefestival.demo.repository.RegistroRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RegistroService {

    @Autowired
    private RegistroRepository registroRepository;

    public RegistroDtoResponse save(RegistroDtoRequest registroDtoRequest) {
        RegistroEntity registroEntity = MapToRegistroDto.toEntity(registroDtoRequest);
        if(getByEmail(registroDtoRequest) == null){
            RegistroEntity savedEntity = registroRepository.save(registroEntity);
            return  MapToRegistroDto.toDto(savedEntity);
        }else{
            throw new RegistroException(registroDtoRequest);
        }
    }

    public RegistroEntity getByEmail(RegistroDtoRequest registroDtoRequest){
        RegistroEntity registroEntity = MapToRegistroDto.toEntity(registroDtoRequest);
        Optional<RegistroEntity> getEntity = registroRepository.findByEmail(registroEntity.getEmail());
        return getEntity.orElse(null);
    }

    public List<RegistroDtoResponse> getAll(){
        List<RegistroEntity> registroEntities = registroRepository.findAll();
        List<RegistroDtoResponse> registroDtoResponses = new ArrayList<>();
        for(RegistroEntity registroEntity : registroEntities){
            registroDtoResponses.add(MapToRegistroDto.toDto(registroEntity));
        }
        return registroDtoResponses;
    }
}
