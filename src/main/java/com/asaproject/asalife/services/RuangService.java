package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.Ruang;
import com.asaproject.asalife.domains.models.responses.RuangDto;

import java.util.List;

public interface RuangService {
    List<RuangDto> getAllRuang();
}
