package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.User;
import com.asaproject.asalife.domains.models.reqres.UpdateUser;
import com.asaproject.asalife.domains.models.requests.*;
import com.asaproject.asalife.domains.models.responses.MyProfile;
import com.asaproject.asalife.domains.models.responses.RegisMany;
import com.asaproject.asalife.domains.models.responses.TokenResponse;

import org.springframework.security.authentication.BadCredentialsException;

import java.security.Principal;
import java.util.List;

public interface UserService {
    void register(Register register) throws Exception;

    void updateDetailUser(UpdateDetailUser updateDetailUser) throws Exception;

    RegisMany registerCommonMany(List<Register> registers) throws Exception;

    void registerUser(UserRegister userRegister) throws Exception;

    void registerAdmin(AdminRegister adminRegister) throws Exception;

    User getUser(String nrp);

    List<User> getUsers();

    boolean getIsNrpAvailable(String nrp);

    TokenResponse signIn(SignInRequest signInRequest) throws Exception;

    TokenResponse signInAdmin(SignInRequest signInRequest) throws Exception;

    void changePassword(PasswordChangeRequest passwordChangeRequest) throws Exception;

    UpdateUser getProfileUpdate(Principal principal);

    UpdateUser updateUser(Principal principal, UpdateUser updateUser);

    MyProfile getMyProfile(Principal principal);

    List<User> getMt();

    void deleteUser(NrpRequest nrpRequest) throws Exception;
}
