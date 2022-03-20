package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.Mess;
import com.asaproject.asalife.domains.models.requests.MessRequest;
import com.asaproject.asalife.repositories.MessRepository;
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

    @Override
    public List<Mess> getAllMess() {
        return messRepository.findAll();
    }

    @Override
    public List<Mess> addMess(MessRequest messRequest) throws Exception {
        Boolean available = isMessAvailable(messRequest.getName());
        if (available) {
            throw new Exception("MESS_ALREADY_EXIST");
        }

        Mess mess = new Mess();
        mess.setName(messRequest.getName());
        messRepository.save(mess);

        return getAllMess();
    }

    @Override
    public Boolean isMessAvailable(String name) {
        Mess mess = messRepository.findByNameIgnoreCaseAndDeletedAtIsNull(name);
        return !ObjectUtils.isEmpty(mess);
    }

    @Override
    public void deleteMess(MessRequest messRequest) throws Exception {
        Boolean messAvailable = isMessAvailable(messRequest.getName());
        if (!messAvailable) {
            throw new NotFoundException("MESS_NOT_FOUND");
        }

        Mess mess = messRepository.findByNameIgnoreCaseAndDeletedAtIsNull(messRequest.getName());
        mess.setDeletedAt(new Date());
        messRepository.save(mess);
    }
}
