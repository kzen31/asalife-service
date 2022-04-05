package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.Mess;
import com.asaproject.asalife.domains.models.requests.MessRequest;
import com.asaproject.asalife.domains.models.responses.MessDto;
import com.asaproject.asalife.repositories.MessRepository;
import com.asaproject.asalife.utils.mappers.MessMapper;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MessServiceImpl implements MessService{
    private final MessRepository messRepository;
    private final MessMapper messMapper;

    @Override
    public List<MessDto> getAllMess() {
        return messMapper.mapMessDtoToList(messRepository.findAll());
    }

    @Override
    public void addMess(MessRequest messRequest) throws Exception {
        Boolean available = isMessAvailable(messRequest.getName());
        if (available) {
            throw new Exception("MESS_ALREADY_EXIST");
        }

        Mess mess = new Mess();
        mess.setName(messRequest.getName());
        messRepository.save(mess);
    }

    @Override
    public Boolean isMessAvailable(String name) {
        Mess mess = messRepository.findByNameIgnoreCaseAndDeletedAtIsNull(name);
        return !ObjectUtils.isEmpty(mess);
    }

    @Override
    public void deleteMess(Long id) throws Exception {
        Mess mess = messRepository.findMessByIdNative(id);

        if (ObjectUtils.isEmpty(mess) || !ObjectUtils.isEmpty(mess.getDeletedAt())) {
            throw new NotFoundException("MESS_NOT_FOUND");
        }

        mess.setDeletedAt(new Date());
        messRepository.save(mess);
    }
}
