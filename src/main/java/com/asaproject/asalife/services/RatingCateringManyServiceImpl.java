package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.RatingCateringMany;
import com.asaproject.asalife.domains.entities.User;
import com.asaproject.asalife.domains.models.requests.RatingManyRequest;

import com.asaproject.asalife.repositories.RatingCateringManyRepository;
import com.asaproject.asalife.utils.mappers.UserAdminMapper;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RatingCateringManyServiceImpl implements RatingCateringManyService {
    private final RatingCateringManyRepository ratingCateringManyRepo;

    @Override
    public void addRatingCateringMany(Principal principal, RatingManyRequest ratingManyRequest) throws Exception {
        User user = UserAdminMapper.principalToUser(principal);
        if (ObjectUtils.isEmpty(user)) {
            throw new NotFoundException("USER NOT FOUND");
        }

        RatingCateringMany ratingCateringMany = new RatingCateringMany();
        ratingCateringMany.setNilai1(ratingManyRequest.getNilai1());
        ratingCateringMany.setNilai2(ratingManyRequest.getNilai2());
        ratingCateringMany.setNilai3(ratingManyRequest.getNilai3());
        ratingCateringMany.setNilai4(ratingManyRequest.getNilai4());
        ratingCateringMany.setNilai5(ratingManyRequest.getNilai5());
        ratingCateringMany.setNilai6(ratingManyRequest.getNilai6());
        ratingCateringMany.setNilai7(ratingManyRequest.getNilai7());
        ratingCateringMany.setNilai8(ratingManyRequest.getNilai8());
        ratingCateringMany.setSaran(ratingManyRequest.getSaran());

        ratingCateringMany.setUser(user);

        ratingCateringManyRepo.save(ratingCateringMany);
    }

    @Override
    public List<RatingCateringMany> getRatingCateringMany() {
        return ratingCateringManyRepo.findAll();
    }
}
