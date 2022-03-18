package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.Ruang;
import com.asaproject.asalife.repositories.RuangRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RuangServiceImpl implements RuangService{
    private final RuangRepository ruangRepository;

    @Override
    public List<Ruang> getAllRuang() {
        return ruangRepository.findAll();
    }
}
