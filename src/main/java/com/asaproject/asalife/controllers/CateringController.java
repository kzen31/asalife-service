package com.asaproject.asalife.controllers;

import com.asaproject.asalife.domains.ERole;
import com.asaproject.asalife.domains.entities.Bobot;
import com.asaproject.asalife.domains.entities.Catering;
import com.asaproject.asalife.domains.entities.Pertanyaan;
import com.asaproject.asalife.domains.entities.RatingCatering;
import com.asaproject.asalife.domains.models.requests.*;
import com.asaproject.asalife.domains.models.responses.CateringDto;
import com.asaproject.asalife.domains.models.responses.RatingCateringDto;
import com.asaproject.asalife.services.BobotService;
import com.asaproject.asalife.services.CateringService;
import com.asaproject.asalife.services.PertanyaanService;
import com.asaproject.asalife.services.RatingCateringService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/catering")
public class CateringController {
    private final CateringService cateringService;
    private final PertanyaanService pertanyaanService;
    private final BobotService bobotService;
    private final RatingCateringService ratingCateringService;

    @PostMapping("/add")
    public ResponseEntity<List<CateringDto>> addAduanCatering(Principal principal, @Valid @RequestBody AduanCatering aduanCatering) {
        try {
            List<CateringDto> cateringDtoList = cateringService.addAduanCatering(principal, aduanCatering);
            return ResponseEntity.ok(cateringDtoList);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/my")
    public ResponseEntity<List<CateringDto>> myCaterings(Principal principal) {
        try {
            List<CateringDto> cateringDtoList = cateringService.getUserCaterings(principal);
            return ResponseEntity.ok(cateringDtoList);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Secured({ ERole.Constants.ADMIN })
    @GetMapping("/all")
    public ResponseEntity<List<CateringDto>> getAllCaterings() {
        try {
            List<CateringDto> cateringDtoList = cateringService.getCaterings();
            return ResponseEntity.ok(cateringDtoList);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/all-by-status")
    public ResponseEntity<List<CateringDto>> getAllCateringsByStatus(StatusCatering statusCatering) {
        try {
            List<CateringDto> cateringDtoList = cateringService.getCateringsByStatus(statusCatering);
            return ResponseEntity.ok(cateringDtoList);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Secured({ ERole.Constants.MEGAUSER })
    @PutMapping("/update-status")
    public ResponseEntity<Catering> updateStatusAduanCatering(@RequestParam Long id,
                                                              @RequestBody StatusCatering statusCatering) {
        try {
            Catering caterings = cateringService.updateStatusCatering(id, statusCatering);
            return ResponseEntity.ok(caterings);
        } catch (Exception e) {
            if(e.getMessage().equals("NOT_FOUND")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            } else
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/info")
    public ResponseEntity<Catering> findCatering(@RequestParam Long id) {
        try {
            Catering catering = cateringService.getCateringById(id);
            return ResponseEntity.ok(catering);
        } catch (Exception e) {
            if (e.getMessage().equals("NOT_FOUND")) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
            } else
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/pertanyaan")
    public ResponseEntity<List<Pertanyaan>> getAllPertanyaan() {
        return ResponseEntity.ok(pertanyaanService.getAllPertanyaan());
    }

    @PostMapping("/pertanyaan-add")
    public ResponseEntity<Pertanyaan> addPertanyaan(@RequestBody PertanyaanRequest pertanyaanRequest) {
        try {
            Pertanyaan pertanyaan = pertanyaanService.addPertanyaan(pertanyaanRequest);
            return ResponseEntity.ok(pertanyaan);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/pertanyaan-update")
    public ResponseEntity<Pertanyaan> updatePertanyaan(@RequestParam Long id, @RequestBody PertanyaanRequest pertanyaanRequest) {
        try {
            Pertanyaan pertanyaan = pertanyaanService.updatePertanyaanIfExist(id, pertanyaanRequest);
            return ResponseEntity.ok(pertanyaan);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/pertanyaan-delete")
    public ResponseEntity<String> deletePertanyaan(@RequestParam Long id) {
        try {
            pertanyaanService.deletePertanyaan(id);
            return ResponseEntity.ok("Success");
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/pertanyaan-byid")
    public ResponseEntity<Pertanyaan> getPertanyaanById(@RequestParam Long id) {
        try {
            Pertanyaan pertanyaan = pertanyaanService.getPertanyaanByID(id);
            return ResponseEntity.ok(pertanyaan);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/bobot")
    public ResponseEntity<List<Bobot>> getAllBobotPertanyaan() {
        List<Bobot> bobotList = bobotService.getListBobot();
        return ResponseEntity.ok(bobotList);
    }

    @GetMapping("/bobot-bypertanyaan")
    public ResponseEntity<List<Bobot>> getAllBobotByPertanyaan(@RequestParam Long id) {
        try {
            List<Bobot> bobotList = bobotService.getListBobotByPertanyaan(id);
            return ResponseEntity.ok(bobotList);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/bobot-add")
    public ResponseEntity<List<Bobot>> addBobotToPertanyaan(@RequestBody BobotRequest bobotRequest) {
        try {
            List<Bobot> bobotList = bobotService.addBobot(bobotRequest);
            return ResponseEntity.ok(bobotList);
        } catch (NotFoundException e) {
            throw new  ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/bobot-delete")
    public ResponseEntity<List<Bobot>> deleteBobot(Long id) {
        try {
            List<Bobot> bobotList = bobotService.deleteBobot(id);
            return ResponseEntity.ok(bobotList);
        } catch (NotFoundException e) {
            throw new  ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/rating-catering")
    public ResponseEntity<List<RatingCateringDto>> getAllRatingCatering() {
        return ResponseEntity.ok(ratingCateringService.getAllRatingCatering());
    }

    @PostMapping("/rating-catering-add")
    public ResponseEntity<Boolean> addRatingCatering(Principal principal, @RequestBody RatingRequest ratingRequest) {
        try {
            Boolean ratingCaterings = ratingCateringService.addRatingCatering(principal, ratingRequest);
            return ResponseEntity.ok(ratingCaterings);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/rating-catering-addbulk")
    public ResponseEntity<String> addRatingCateringBulk(Principal principal, @RequestBody List<RatingRequest> ratingRequestList) {
        try {
            ratingCateringService.addRatingCateringBulk(principal, ratingRequestList);
            return ResponseEntity.ok("Success");
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/rating-catering-available")
    public ResponseEntity<Boolean> getRatingCateringLastUser(Principal principal) {
        return ResponseEntity.ok(ratingCateringService.isAddRatingCateringAvailable(principal));
    }
}
