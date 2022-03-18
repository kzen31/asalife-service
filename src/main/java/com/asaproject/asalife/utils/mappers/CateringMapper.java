package com.asaproject.asalife.utils.mappers;

import com.asaproject.asalife.domains.entities.Catering;
import com.asaproject.asalife.domains.entities.Pertanyaan;
import com.asaproject.asalife.domains.entities.RatingCatering;
import com.asaproject.asalife.domains.entities.User;
import com.asaproject.asalife.domains.models.requests.RatingRequest;
import com.asaproject.asalife.domains.models.responses.CateringDto;
import com.asaproject.asalife.domains.models.responses.RatingCateringDto;
import com.asaproject.asalife.repositories.CateringRepository;
import com.asaproject.asalife.repositories.PertanyaanRepository;
import com.asaproject.asalife.repositories.RatingCateringRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public final class CateringMapper {
    private final ModelMapper modelMapper;
    private final RatingCateringRepository ratingCateringRepository;
    private final PertanyaanRepository pertanyaanRepository;
    private final CateringRepository cateringRepository;

    public CateringDto entityToCateringDto(Catering catering) {
        CateringDto cateringDto = modelMapper.map(catering, CateringDto.class);
        cateringDto.setUserName(catering.getUser().getName());
        cateringDto.setUserNrp(catering.getUser().getNrp());
        return cateringDto;
    }

    public List<CateringDto> mapCateringDtoToList (List<Catering> cateringList) {
        List<CateringDto> cateringDtoList = new ArrayList<CateringDto>();

        for (Catering catering : cateringList) { // (int i = 0; i < list.size(); i++)
            CateringDto cateringDto = entityToCateringDto(catering);
            cateringDtoList.add(cateringDto);
        }
        return cateringDtoList;
    }

    public RatingCateringDto entityToRatingCateringDto(RatingCatering ratingCatering) {
        RatingCateringDto ratingCateringDto = modelMapper.map(ratingCatering, RatingCateringDto.class);
        ratingCateringDto.setId_pertanyaan(ratingCatering.getPertanyaan().getId());
        ratingCateringDto.setIsi_pertanyaan(ratingCatering.getPertanyaan().getIsi());
        ratingCateringDto.setUser_nrp(ratingCatering.getUser().getNrp());
        return ratingCateringDto;
    }
    
    public List<RatingCateringDto> mapRatingCateringDtoToList () {
        List<RatingCatering> ratingCateringList = ratingCateringRepository.findAll();
        List<RatingCateringDto> ratingCateringDtoList = new ArrayList<RatingCateringDto>();

        for (RatingCatering ratingCatering : ratingCateringList) { // (int i = 0; i < list.size(); i++)
            RatingCateringDto ratingCateringDto = entityToRatingCateringDto(ratingCatering);
            ratingCateringDtoList.add(ratingCateringDto);
        }
        return ratingCateringDtoList;
    }

    public void addListRatingCatering(User user, List<RatingRequest> ratingRequestList) throws Exception {
        Boolean resultChecker = pertanyaanExistChecker(ratingRequestList);
        if (!resultChecker) {
            throw new NotFoundException("PERTANYAAN_NOT_FOUND");
        }

        for (RatingRequest ratingRequest : ratingRequestList) { // (int i = 0; i < ratingRequestList.size(); i++)
            RatingCatering ratingCatering = new RatingCatering();
            Pertanyaan pertanyaan = pertanyaanRepository.findPertanyaanByIdNative(ratingRequest.getPertanyaan_id());

            ratingCatering.setUser(user);
            ratingCatering.setNilai(ratingRequest.getNilai());
            ratingCatering.setPertanyaan(pertanyaan);

            ratingCateringRepository.save(ratingCatering);
        }
    }

    public Boolean pertanyaanExistChecker (List<RatingRequest> ratingRequestList) {
        for (RatingRequest ratingRequest : ratingRequestList) {
            Pertanyaan pertanyaan = pertanyaanRepository.findPertanyaanByIdNative(ratingRequest.getPertanyaan_id());
            if (ObjectUtils.isEmpty(pertanyaan)) {
                return false;
            }
        }
        return true;
    }

}
