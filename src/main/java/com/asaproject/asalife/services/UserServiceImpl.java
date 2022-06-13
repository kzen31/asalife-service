package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.ERole;
import com.asaproject.asalife.domains.ERoleRegister;
import com.asaproject.asalife.domains.entities.Role;
import com.asaproject.asalife.domains.entities.User;
import com.asaproject.asalife.domains.models.reqres.UpdateUser;
import com.asaproject.asalife.domains.models.requests.*;
import com.asaproject.asalife.domains.models.responses.MyProfile;
import com.asaproject.asalife.domains.models.responses.RegisMany;
import com.asaproject.asalife.domains.models.responses.TokenResponse;
import com.asaproject.asalife.repositories.RoleRepository;
import com.asaproject.asalife.repositories.UserRepository;
import com.asaproject.asalife.utils.mappers.*;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
    public void register(Register register) throws Exception {
        User user = UserAdminMapper.userRegisterCommon(register);
        if (!getIsNrpAvailable(user.getNrp()))
            throw new Exception("NRP_UNAVAILABLE");
        saveUser(user);
        mapRoles(user, register);
    }

    @Override
    public void updateDetailUser(UpdateDetailUser updateDetailUser) throws Exception {
        User user = getUser(updateDetailUser.getNrp());

        if (ObjectUtils.isEmpty(user)) {
            throw new NotFoundException("USER_UNAVAILABLE");
        }
        Collection<Role> roles = new ArrayList<>();

        user.setName(updateDetailUser.getName());
        user.setDepartment(updateDetailUser.getDepartment());
        user.setRoles(roles);

        Register register = UserAdminMapper.updateUserToRegister(updateDetailUser);

        mapRoles(user, register);
    }

    public void mapRoles(User user, Register register) {
        addRoleToUser(user.getNrp(), RoleCommonMapper.mapRole(register.getRole()));
        if (ERoleRegister.getWorker().contains(register.getRole())) {
            addRoleToUser(user.getNrp(), ERole.ROLE_USER);
            addRoleToUser(user.getNrp(), ERole.ROLE_WORKER);
        } else if (ERoleRegister.getSuperUser().contains(register.getRole())) {
            addRoleToUser(user.getNrp(), ERole.ROLE_ADMIN);
            addRoleToUser(user.getNrp(), ERole.ROLE_SUPERUSER);
        } else if (ERoleRegister.getMegaUser().contains(register.getRole())) {
            addRoleToUser(user.getNrp(), ERole.ROLE_ADMIN);
            addRoleToUser(user.getNrp(), ERole.ROLE_MEGAUSER);
        } else {
            addRoleToUser(user.getNrp(), ERole.ROLE_USER);
            addRoleToUser(user.getNrp(), ERole.ROLE_CUSTOMER);
        }
    }

    @Override
    public RegisMany registerCommonMany(List<Register> registers) throws Exception {
        long countSuccess = 0L;
        long countFail = 0L;
        long countNrpExist = 0L;
        for (Register register: registers) {
            if (ObjectUtils.isEmpty(register.getNrp())) {
                countFail++;
                continue;
            }
            if (ObjectUtils.isEmpty(register.getName())) {
                countFail++;
                continue;
            }
            if (ObjectUtils.isEmpty(register.getPassword())) {
                countFail++;
                continue;
            }
            if (ObjectUtils.isEmpty(register.getDepartment())) {
                countFail++;
                continue;
            }
            if (ObjectUtils.isEmpty(register.getRole())) {
                countFail++;
                continue;
            }
            if (!getIsNrpAvailable(register.getNrp())) {
                countNrpExist++;
                continue;
            }
            register(register);
            countSuccess++;
        }
        RegisMany regisMany = new RegisMany();
        regisMany.setCountSuccess(countSuccess);
        regisMany.setCountFail(countFail);
        regisMany.setCountNrpExist(countNrpExist);
        return regisMany;
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
        if (!ObjectUtils.isEmpty(user.getDeletedAt()))
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
    public TokenResponse signInAdmin(SignInRequest signInRequest) throws Exception {
        String nrp = signInRequest.getNrp();
        String password = signInRequest.getPassword();

        User user = userRepo.findByNrp(nrp);

        if (user == null)
            throw new NotFoundException("USER_NOT_FOUND");

        if (!isUserAMegaUser(user))
            throw new Exception("FORBIDDEN");

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
    public void changePassword(PasswordChangeRequest passwordChangeRequest) throws Exception {
        User user = getUser(passwordChangeRequest.getNrp());

        if (ObjectUtils.isEmpty(user)) {
            throw new NotFoundException("USER_NOT_FOUND");
        }

        user.setPassword(passwordEncoder.encode(passwordChangeRequest.getPassword()));
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

    @Override
    public List<User> getMt() {
        return userRepo.findByRole();
    }

    @Override
    public void deleteUser(NrpRequest nrpRequest) throws Exception {
        User user = getUser(nrpRequest.getNrp());
        if (ObjectUtils.isEmpty(user)) {
            throw new NotFoundException("USER_NOT_FOUND");
        }

        if (!ObjectUtils.isEmpty(user.getDeletedAt())) {
            throw new NotFoundException("USER_NOT_FOUND");
        }

        if (isUserAMegaUser(user)) {
            throw new Exception("CANNOT_DELETE_MEGAUSER");
        }

        user.setDeletedAt(new Date());
    }

    private boolean isUserAMegaUser(User user) {
        return user.getRoles().stream().anyMatch(role -> role.getName().equals(ERole.ROLE_MEGAUSER));
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
