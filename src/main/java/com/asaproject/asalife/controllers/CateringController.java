package com.asaproject.asalife.controllers;

import com.asaproject.asalife.domains.ECateringStatus;
import com.asaproject.asalife.domains.ERole;
import com.asaproject.asalife.domains.entities.RatingCateringMany;
import com.asaproject.asalife.domains.models.requests.*;
import com.asaproject.asalife.domains.models.responses.*;
import com.asaproject.asalife.services.*;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/catering")
public class CateringController extends HandlerController {
    private final CateringService cateringService;
    private final PertanyaanService pertanyaanService;
    private final BobotService bobotService;
    private final RatingCateringService ratingCateringService;
    private final RatingCateringManyService ratingCateringManyService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addAduanCatering(Principal principal, @Valid @RequestBody AduanCatering aduanCatering) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/add").toUriString());
        try {
            cateringService.addAduanCatering(principal, aduanCatering);
            return ResponseEntity.created(uri)
                    .body(ApiResponse.builder().message("Created Aduan Catering").build());
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
    public ResponseEntity<List<CateringDto>> getAllCateringsByStatus(@RequestParam(required = false) ECateringStatus status) {
        try {
            List<CateringDto> cateringDtoList = cateringService.getCateringsByStatus(status);
            return ResponseEntity.ok(cateringDtoList);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Secured({ ERole.Constants.MEGAUSER })
    @PutMapping("/update-status/{id}")
    public ResponseEntity<ApiResponse> updateStatusAduanCatering(@PathVariable Long id,
                                                              @Valid @RequestBody StatusCatering statusCatering) {
        try {
            cateringService.updateStatusCatering(id, statusCatering);
            return ResponseEntity.ok(ApiResponse.builder().message("Successfully Update Status Aduan Catering").build());

        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<CateringDto> findCatering(@PathVariable Long id) {
        try {
            CateringDto cateringDto = cateringService.getCateringById(id);
            return ResponseEntity.ok(cateringDto);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/pertanyaan")
    public ResponseEntity<List<PertanyaanDto>> getAllPertanyaan() {
        return ResponseEntity.ok(pertanyaanService.getAllPertanyaan());
    }

    @Secured({ ERole.Constants.ADMIN })
    @PostMapping("/pertanyaan-add")
    public ResponseEntity<ApiResponse> addPertanyaan(@Valid @RequestBody PertanyaanRequest pertanyaanRequest) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/pertanyaan-add").toUriString());
        try {
            pertanyaanService.addPertanyaan(pertanyaanRequest);
            return ResponseEntity.created(uri)
                    .body(ApiResponse.builder().message("Created Pertanyaan").build());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Secured({ ERole.Constants.ADMIN })
    @PutMapping("/pertanyaan-update/{id}")
    public ResponseEntity<PertanyaanDto> updatePertanyaan(@PathVariable Long id, @Valid @RequestBody PertanyaanRequest pertanyaanRequest) {
        try {
            PertanyaanDto pertanyaan = pertanyaanService.updatePertanyaanIfExist(id, pertanyaanRequest);
            return ResponseEntity.ok(pertanyaan);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Secured({ ERole.Constants.ADMIN })
    @PutMapping("/pertanyaan-delete/{id}")
    public ResponseEntity<ApiResponse> deletePertanyaan(@PathVariable Long id) {
        try {
            pertanyaanService.deletePertanyaan(id);
            return ResponseEntity.ok(ApiResponse.builder().message("Successfully Delete Pertanyaan").build());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/pertanyaan-byid/{id}")
    public ResponseEntity<PertanyaanDto> getPertanyaanById(@PathVariable Long id) {
        try {
            PertanyaanDto pertanyaan = pertanyaanService.getPertanyaanByID(id);
            return ResponseEntity.ok(pertanyaan);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/bobot")
    public ResponseEntity<List<BobotDto>> getAllBobotPertanyaan() {
        List<BobotDto> bobotList = bobotService.getListBobot();
        return ResponseEntity.ok(bobotList);
    }

    @GetMapping("/bobot-bypertanyaan/{id}")
    public ResponseEntity<List<BobotDto>> getAllBobotByPertanyaan(@PathVariable Long id) {
        try {
            List<BobotDto> bobotList = bobotService.getListBobotByPertanyaan(id);
            return ResponseEntity.ok(bobotList);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Secured({ ERole.Constants.ADMIN })
    @PostMapping("/bobot-add")
    public ResponseEntity<ApiResponse> addBobotToPertanyaan(@Valid @RequestBody BobotRequest bobotRequest) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/bobot-add").toUriString());
        try {
            bobotService.addBobot(bobotRequest);
            return ResponseEntity.created(uri)
                    .body(ApiResponse.builder().message("Created Bobot").build());
        } catch (NotFoundException e) {
            throw new  ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Secured({ ERole.Constants.ADMIN })
    @PutMapping("/bobot-delete/{id}")
    public ResponseEntity<ApiResponse> deleteBobot(@PathVariable Long id) {
        try {
            bobotService.deleteBobot(id);
            return ResponseEntity.ok(ApiResponse.builder().message("Successfully Delete Bobot").build());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bobot Not Found", e.getCause());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request not valid", e.getCause());
        }
    }

    @Secured({ ERole.Constants.ADMIN })
    @GetMapping("/rating-catering")
    public ResponseEntity<List<RatingResponse>> getAllRatingCatering() {
        return ResponseEntity.ok(ratingCateringService.getAllRatingCatering());
    }

    @GetMapping("/rating-catering-my")
    public ResponseEntity<List<RatingResponse>> getMyRatingCatering(Principal principal) {
        return ResponseEntity.ok(ratingCateringService.getUSerRatingCatering(principal));
    }

    @PostMapping("/rating-catering-add")
    public ResponseEntity<ApiResponse> addRatingCatering(Principal principal, @Valid @RequestBody RatingRequest ratingRequest) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/rating-catering-add").toUriString());
        try {
            ratingCateringService.addRatingCatering(principal, ratingRequest);
            return ResponseEntity.created(uri)
                    .body(ApiResponse.builder().message("Created Rating Catering").build());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/rating-catering-addbulk")
    public ResponseEntity<ApiResponse> addRatingCateringBulk(Principal principal, @Valid @RequestBody List<RatingRequest> ratingRequestList) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/rating-catering-addbulk").toUriString());
        try {
            ratingCateringService.addRatingCateringBulk(principal, ratingRequestList);
            return ResponseEntity.created(uri)
                    .body(ApiResponse.builder().message("Created Rating Catering").build());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // Rating many hardcore colom
    @GetMapping("/rating-catering-available")
    public ResponseEntity<Boolean> getRatingCateringLastUser(Principal principal) {
        return ResponseEntity.ok(ratingCateringService.isAddRatingCateringAvailable(principal));
    }

    @PostMapping("/rating-catering-many")
    public ResponseEntity<ApiResponse> addRatingCateringMany(Principal principal, @Valid @RequestBody RatingManyRequest ratingManyRequests) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/rating-catering-many").toUriString());
        try {
            ratingCateringManyService.addRatingCateringMany(principal, ratingManyRequests);
            return ResponseEntity.created(uri)
                    .body(ApiResponse.builder().message("Created Rating Catering").build());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/rating-catering-many")
    public ResponseEntity<List<RatingCateringMany>> getRatingCateringMany() {
        return ResponseEntity.ok(ratingCateringManyService.getRatingCateringMany());
    }

    @DeleteMapping ("/delete-record/{id}")
    public ResponseEntity<ApiResponse> deleteRecordCatering(@PathVariable Long id) {
        try {
            cateringService.deleteCatering(id);
            return ResponseEntity.ok(ApiResponse.builder().message("Successfully Delete Record Catering").build());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Record Catering Not Found", e.getCause());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request not valid", e.getCause());
        }
    }
}
