package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.Pertanyaan;
import com.asaproject.asalife.domains.entities.RatingCatering;
import com.asaproject.asalife.domains.entities.User;
import com.asaproject.asalife.domains.models.requests.RatingRequest;
import com.asaproject.asalife.domains.models.responses.RatingResponse;
import com.asaproject.asalife.repositories.PertanyaanRepository;
import com.asaproject.asalife.repositories.RatingCateringRepository;
import com.asaproject.asalife.utils.mappers.CateringMapper;
import com.asaproject.asalife.utils.mappers.UserAdminMapper;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.security.Principal;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RatingCateringServiceImpl implements RatingCateringService {
    private final RatingCateringRepository ratingCateringRepository;
    private final PertanyaanRepository pertanyaanRepository;
    private final CateringMapper cateringMapper;

    @Override
    public void addRatingCatering(Principal principal, RatingRequest ratingRequest) throws Exception {
        User user = UserAdminMapper.principalToUser(principal);
        if (ObjectUtils.isEmpty(user)) {
            throw new NotFoundException("USER NOT FOUND");
        }

        Pertanyaan pertanyaan = pertanyaanRepository.findPertanyaanByIdNative(ratingRequest.getPertanyaan_id());
        if (ObjectUtils.isEmpty(pertanyaan)) {
            throw new NotFoundException("PERTANYAAN NOT FOUND");
        }

        RatingCatering ratingCatering = new RatingCatering();
        ratingCatering.setPertanyaan(pertanyaan);
        ratingCatering.setNilai(ratingRequest.getNilai());
        ratingCatering.setUser(user);

        ratingCateringRepository.save(ratingCatering);
    }

    @Override
    public void addRatingCateringBulk(Principal principal, List<RatingRequest> ratingRequestList) throws Exception {
        try {
            User user = UserAdminMapper.principalToUser(principal);
            if (ObjectUtils.isEmpty(user)) {
                throw new NotFoundException("USER_NOT_FOUND");
            }
            cateringMapper.addListRatingCatering(user, ratingRequestList);
        } catch (NotFoundException e) {
            throw new NotFoundException("PERTANYAAN_NOT_FOUND");
        }
    }

    @Override
    public List<RatingResponse> getAllRatingCatering() {
        return cateringMapper.createTemplateRatingList(ratingCateringRepository.findAllAndOrder());
    }

    @Override
    public List<RatingResponse> getUSerRatingCatering(Principal principal) {
        User user = UserAdminMapper.principalToUser(principal);
        return cateringMapper.createTemplateRatingList(ratingCateringRepository.findAllByUserAndOrder(user.getId()));
    }

    @Override
    public Boolean isAddRatingCateringAvailable(Principal principal) {
        User user = UserAdminMapper.principalToUser(principal);
        RatingCatering ratingCatering = ratingCateringRepository.findRatingCateringLastAdByUser(user.getId());
        if (ObjectUtils.isEmpty(ratingCatering)) {
            return true;
        }

        int monthNow = new LocalDate().getMonthOfYear();
        int monthCreated = ratingCatering.getCreatedAt().getMonth().getValue();

        int yearNow = new LocalDate().getYear();
        int yearCreated = ratingCatering.getCreatedAt().getYear();

        return monthNow > monthCreated && yearNow >= yearCreated;
    }
}
