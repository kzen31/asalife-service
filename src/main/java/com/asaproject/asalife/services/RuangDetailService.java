package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.Ruang;
import com.asaproject.asalife.domains.entities.RuangDetail;
import com.asaproject.asalife.domains.models.responses.RuangDetailDto;

import java.util.List;

public interface RuangDetailService {
    List<RuangDetailDto> getAllRuangDetail (Long id) throws Exception;

    List<RuangDetailDto> getAllRuangDetail ();
}
