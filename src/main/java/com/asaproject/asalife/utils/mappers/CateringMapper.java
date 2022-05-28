package com.asaproject.asalife.utils.mappers;

import com.asaproject.asalife.domains.entities.Catering;
import com.asaproject.asalife.domains.entities.Pertanyaan;
import com.asaproject.asalife.domains.entities.RatingCatering;
import com.asaproject.asalife.domains.entities.User;
import com.asaproject.asalife.domains.models.requests.RatingRequest;
import com.asaproject.asalife.domains.models.responses.CateringDto;
import com.asaproject.asalife.domains.models.responses.RatingCateringDto;
import com.asaproject.asalife.domains.models.responses.RatingResponse;
import com.asaproject.asalife.repositories.PertanyaanRepository;
import com.asaproject.asalife.repositories.RatingCateringRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public final class CateringMapper {
    private final ModelMapper modelMapper;
    private final RatingCateringRepository ratingCateringRepository;
    private final PertanyaanRepository pertanyaanRepository;

    public CateringDto entityToCateringDto(Catering catering) {
        CateringDto cateringDto = modelMapper.map(catering, CateringDto.class);
        cateringDto.setUserName(catering.getUser().getName());
        cateringDto.setUserNrp(catering.getUser().getNrp());
        cateringDto.setTanggalAduan(catering.getCreatedAt());
        return cateringDto;
    }

    public List<CateringDto> mapCateringDtoToList (List<Catering> cateringList) {
        List<CateringDto> cateringDtoList = new ArrayList<>();

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
        return ratingCateringDto;
    }
    
    public List<RatingCateringDto> mapRatingCateringDtoToList () {
        List<RatingCatering> ratingCateringList = ratingCateringRepository.findAll();
        List<RatingCateringDto> ratingCateringDtoList = new ArrayList<>();

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

    public List<RatingResponse> createTemplateRatingList(List<RatingCatering> ratingCateringList) {
        List<RatingResponse> ratingResponseList = new ArrayList<>();
        int i = 0;
        while (i < ratingCateringList.size()) {
            List<RatingCateringDto> ratingDtoList = new ArrayList<>();
            RatingResponse ratingResponse = new RatingResponse();

            Integer lengthRatingDtoList = checkRatingDtoSize (i,  ratingCateringList);
            ratingResponse.setUsername(ratingCateringList.get(i).getUser().getName());
            ratingResponse.setUsernrp(ratingCateringList.get(i).getUser().getNrp());
            int loopingRecord = 0;
            while (loopingRecord < lengthRatingDtoList) {
                ratingDtoList.add(entityToRatingCateringDto(ratingCateringList.get(i)));
                i++;
                loopingRecord++;
            }
            ratingResponse.setRatingCateringDto(ratingDtoList);
            ratingResponseList.add(ratingResponse);
        }
        return ratingResponseList;
    }

    public Integer checkRatingDtoSize (Integer index, List<RatingCatering> ratingCateringList) {
        Integer valueLength = 1;
        while (index < ratingCateringList.size()) {
            if (isNextValueHasSameUserAndDay(index, ratingCateringList)) {
                valueLength++;
            } else {
                break;
            }
            index++;
        }
        return valueLength;
    }

    public Boolean isNextValueHasSameUserAndDay(Integer index, List<RatingCatering> ratingCateringList) {
        if ((index + 1) == ratingCateringList.size()) {
            return false;
        }
        boolean checkName = Objects.equals(ratingCateringList.get(index).getUser().getName(), ratingCateringList.get(index + 1).getUser().getName());
        String timeA = ratingCateringList.get(index).getCreatedAt().toLocalDate().toString();
        String timeB = ratingCateringList.get(index + 1).getCreatedAt().toLocalDate().toString();

        boolean checkDate = Objects.equals(timeA, timeB);
        return checkName == checkDate;
    }

}
