package com.asaproject.asalife.controllers;

import com.asaproject.asalife.domains.ERole;
import com.asaproject.asalife.domains.models.requests.*;
import com.asaproject.asalife.domains.models.responses.ApiResponse;
import com.asaproject.asalife.domains.models.responses.RegisMany;
import com.asaproject.asalife.domains.models.responses.TokenResponse;
import com.asaproject.asalife.services.RefreshTokenService;
import com.asaproject.asalife.services.TokenService;
import com.asaproject.asalife.services.UserService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController extends HandlerController {
    private final UserService userService;
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register-user")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody UserRegister user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/register-user").toUriString());
        try {
            userService.registerUser(user);
            return ResponseEntity.created(uri)
                    .body(ApiResponse.builder().message("Create User Success").build());
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().equals("NRP_UNAVAILABLE")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NRP is taken", e.getCause());
            }
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
        }
    }

    @PostMapping("/register-admin")
    public ResponseEntity<ApiResponse> registerAdmin(@Valid @RequestBody AdminRegister user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/register-admin").toUriString());
        try {
            userService.registerAdmin(user);
            return ResponseEntity.created(uri)
                    .body(ApiResponse.builder().message("Create User Success").build());
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().equals("NRP_UNAVAILABLE")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NRP is taken", e.getCause());
            }
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
        }
    }

    @PostMapping("/regis")
    public ResponseEntity<ApiResponse> registerCommon(@Valid @RequestBody Register user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/regis").toUriString());
        try {
            userService.register(user);
            return ResponseEntity.created(uri)
                    .body(ApiResponse.builder().message("Create User Success").build());
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().equals("NRP_UNAVAILABLE")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NRP is taken", e.getCause());
            }
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
        }
    }

    @PostMapping("/regis-many")
    public ResponseEntity<RegisMany> registerCommonMany(@Valid @RequestBody List<Register> users) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/regis-many").toUriString());
        try {
            RegisMany regisMany = userService.registerCommonMany(users);
            return ResponseEntity.ok(regisMany);
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().equals("NRP_UNAVAILABLE")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NRP is taken", e.getCause());
            }
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
        }
    }


    @PostMapping("/nrp-availability")
    public ResponseEntity<Boolean> checkNrpAvailability(@Valid @RequestBody NrpRequest availability) {
        boolean getIsAvailable = userService.getIsNrpAvailable(availability.getNrp());
        return ResponseEntity.ok(getIsAvailable);
    }

    @PostMapping("/signin")
    public ResponseEntity<TokenResponse> signIn(@Valid @RequestBody SignInRequest signInRequest) {
        try {
            TokenResponse result = userService.signIn(signInRequest);
            if (result == null)
                throw new DisabledException(null);
            return ResponseEntity.ok(result);
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Nrp or password is incorrect", e.getCause());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e.getCause());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Failed to login", e.getCause());
        }
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<TokenResponse> getNewToken(@Valid @RequestBody NewTokenRequest request) {
        try {
            TokenResponse tokenResponse = tokenService.getNewToken(request);
            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().equals("Token Not Found")) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Token Not Found", e.getCause());
            }
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Failed", e.getCause());
        }
    }

    @PutMapping("/signout")
    public ResponseEntity<ApiResponse> signOut(@Valid @RequestBody SignOutRequest signOutRequest) {
        try {
            refreshTokenService.signOut(signOutRequest);
            return ResponseEntity.ok(ApiResponse.builder().message("Successfully signed out").build());
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().equals("Token Not Found")) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Token Not Found", e.getCause());
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found", e.getCause());
        }
    }

    @PostMapping("/admin-signin")
    public ResponseEntity<TokenResponse> signInAdmin(@Valid @RequestBody SignInRequest signInRequest) {
        try {
            TokenResponse result = userService.signInAdmin(signInRequest);
            if (result == null)
                throw new DisabledException(null);
            return ResponseEntity.ok(result);
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Nrp or password is incorrect", e.getCause());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e.getCause());
        } catch (Exception e) {
            if (e.getMessage().equals("FORBIDDEN")) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User do not have access", e.getCause());
            }
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Failed to login", e.getCause());
        }
    }

    @Secured({ ERole.Constants.ADMIN })
    @PutMapping("/edit")
    public ResponseEntity<ApiResponse> editUser(@Valid @RequestBody UpdateDetailUser updateDetailUser) {
        try {
            userService.updateDetailUser(updateDetailUser);
            return ResponseEntity.ok(ApiResponse.builder().message("Successfully Edit User").build());
        }   catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e.getCause());
        }   catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
        }
    }

    @Secured({ ERole.Constants.ADMIN })
    @PutMapping("/reset")
    public ResponseEntity<ApiResponse> resetPassword(@Valid @RequestBody PasswordChangeRequest passwordChangeRequest) {
        try {
            userService.changePassword(passwordChangeRequest);
            return ResponseEntity.ok(ApiResponse.builder().message("Successfully Edit User Password").build());
        }   catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e.getCause());
        }   catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
        }
    }

    @Secured({ ERole.Constants.ADMIN })
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteUser(@Valid @RequestBody NrpRequest nrp) {
        try {
            userService.deleteUser(nrp);
            return ResponseEntity.ok(ApiResponse.builder().message("Successfully Delete User").build());
        }   catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e.getCause());
        }   catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
        }
    }
}
