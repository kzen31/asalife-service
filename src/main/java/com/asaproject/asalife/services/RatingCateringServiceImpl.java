package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.Bobot;
import com.asaproject.asalife.domains.entities.Pertanyaan;
import com.asaproject.asalife.domains.entities.RatingCatering;
import com.asaproject.asalife.domains.entities.User;
import com.asaproject.asalife.domains.models.requests.RatingRequest;
import com.asaproject.asalife.domains.models.responses.RatingCateringDto;
import com.asaproject.asalife.repositories.PertanyaanRepository;
import com.asaproject.asalife.repositories.RatingCateringRepository;
import com.asaproject.asalife.utils.mappers.CateringMapper;
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
public class RatingCateringServiceImpl implements RatingCateringService {
    private final RatingCateringRepository ratingCateringRepository;
    private final PertanyaanRepository pertanyaanRepository;
    private final CateringMapper cateringMapper;

    @Override
    public Boolean addRatingCatering(Principal principal, RatingRequest ratingRequest) throws Exception {
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

        return true;
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
    public List<RatingCateringDto> getAllRatingCatering() {
        return cateringMapper.mapRatingCateringDtoToList();
    }


}
