package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.Laundry;
import com.asaproject.asalife.domains.models.requests.LaundryRequest;
import com.asaproject.asalife.domains.models.requests.StatusLaundry;
import com.asaproject.asalife.domains.models.responses.LaundryDto;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.List;

public interface LaundryService {
    List<LaundryDto> getAllLaundryDto();

    List<LaundryDto> getAllLaundryByUserDto(Principal principal);

    List<Laundry> getAllLaundry();

    List<Laundry> getAllLaundryByUser(Principal principal);

    void addLaundry(Principal principal, LaundryRequest laundryRequest);

    void updateStatusLaundry(Long id, @RequestBody StatusLaundry statusLaundry)throws Exception;

    Boolean isLaundryExist(Long id);

    void deleteLaundry(Long id) throws Exception;
}
