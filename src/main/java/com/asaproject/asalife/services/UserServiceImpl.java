package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.ERole;
import com.asaproject.asalife.domains.entities.Role;
import com.asaproject.asalife.domains.entities.User;
import com.asaproject.asalife.domains.models.reqres.UpdateUser;
import com.asaproject.asalife.domains.models.requests.AdminRegister;
import com.asaproject.asalife.domains.models.requests.PasswordChangeRequest;
import com.asaproject.asalife.domains.models.requests.SignInRequest;
import com.asaproject.asalife.domains.models.requests.UserRegister;
import com.asaproject.asalife.domains.models.responses.MyProfile;
import com.asaproject.asalife.domains.models.responses.TokenResponse;
import com.asaproject.asalife.repositories.RoleRepository;
import com.asaproject.asalife.repositories.UserRepository;
import com.asaproject.asalife.utils.mappers.RoleAdminMapper;
import com.asaproject.asalife.utils.mappers.RoleUserMapper;
import com.asaproject.asalife.utils.mappers.UserAdminMapper;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Override
    public User loadUserByUsername(String nrp) throws UsernameNotFoundException {
        User user = userRepo.findByNrp(nrp);
        if (user == null) {
            log.error("NRP not found {}", nrp);
            throw new UsernameNotFoundException(String.format("User not found %s", nrp));
        } else {
            log.info("NRP found {}", nrp);
        }
        return user;
    }

    @Override
    public void registerUser(UserRegister userRegister) throws Exception {
        User user = UserAdminMapper.userRegisterToUser(userRegister);
        if (!getIsNrpAvailable(user.getNrp()))
            throw new Exception("NRP_UNAVAILABLE");
        saveUser(user);
        addRoleToUser(user.getNrp(), RoleUserMapper.mapRole(userRegister.getRole()));
        addRoleToUser(user.getNrp(), ERole.ROLE_USER);
    }

    @Override
    public void registerAdmin(AdminRegister adminRegister) throws Exception {
        User user = UserAdminMapper.userRegisterToAdmin(adminRegister);
        if (!getIsNrpAvailable(user.getNrp()))
            throw new Exception("NRP_UNAVAILABLE");
        saveUser(user);
        addRoleToUser(user.getNrp(), RoleAdminMapper.mapRole(adminRegister.getRole()));
        addRoleToUser(user.getNrp(), ERole.ROLE_ADMIN);
    }

    @Override
    public User getUser(String nrp) {
        log.info("Fetching user {}", nrp);
        return userRepo.findByNrp(nrp);
    }

     @Override
     public List<User> getUsers() {
     log.info("Fetching all users");
     return userRepo.findAll();
     }

    @Override
    public boolean getIsNrpAvailable(String nrp) {
        return getUser(nrp) == null;
    }

    @Override
    public TokenResponse signIn(SignInRequest signInRequest) throws Exception {
        String nrp = signInRequest.getNrp();
        String password = signInRequest.getPassword();

        User user = userRepo.findByNrp(nrp);

        if (user == null)
            throw new NotFoundException("USER_NOT_FOUND");
        if (!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword()))
            throw new BadCredentialsException("PASSWORD_WRONG");

        Exception exception = tokenService.authUser(nrp, password);
        if (exception instanceof DisabledException) {
            return null;
        } else if (exception != null) {
            throw exception;
        }

        return tokenService.getToken(user);
    }

    @Override
    public void changePassword(Principal principal, PasswordChangeRequest passwordChangeRequest) throws BadCredentialsException {
        User userPrincipal = UserAdminMapper.principalToUser(principal);
        User user = userRepo.getById(userPrincipal.getId());
        if (!passwordEncoder.matches(passwordChangeRequest.getOldPassword(), user.getPassword()))
            throw new BadCredentialsException("OLD_PASSWORD_WRONG");
        user.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
    }

    @Override
    public UpdateUser getProfileUpdate(Principal principal) {
        return UserAdminMapper.principalToUpdateUser(principal);
    }

    @Override
    public UpdateUser updateUser(Principal principal, UpdateUser updateUser) {
        User userPrincipal = UserAdminMapper.principalToUser(principal);
        User user = userRepo.getById(userPrincipal.getId());
        user.setName(updateUser.getName());
        user.setNrp(updateUser.getNrp());
        user.setDepartment(updateUser.getDepartment());
        return UserAdminMapper.userToUpdateUser(user);
    }

    @Override
    public MyProfile getMyProfile(Principal principal) {
        return UserAdminMapper.principalToMyProfile(principal);
    }


    private void saveUser(User user) {
        log.info("Saving new user {}", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    private void addRoleToUser(String nrp, ERole roleName) {
        log.info("Adding role {} to user {}", roleName, nrp);
        User user = getUser(nrp);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }
}
