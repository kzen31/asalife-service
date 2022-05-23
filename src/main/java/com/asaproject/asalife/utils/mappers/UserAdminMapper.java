package com.asaproject.asalife.utils.mappers;

import com.asaproject.asalife.domains.entities.User;
import com.asaproject.asalife.domains.models.reqres.UpdateUser;
import com.asaproject.asalife.domains.models.requests.AdminRegister;
import com.asaproject.asalife.domains.models.requests.UserRegister;
import com.asaproject.asalife.domains.models.responses.MyProfile;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.security.Principal;

public final class UserAdminMapper {
    public static User userRegisterToUser(UserRegister userRegister) {
        User user = new User();
        user.setName(userRegister.getName());
        user.setDepartment(userRegister.getDepartment());
        user.setNrp(userRegister.getNrp());
        user.setPassword(userRegister.getPassword());
        return user;
    }

    public static User userRegisterToAdmin(AdminRegister adminRegister) {
        User user = new User();
        user.setName(adminRegister.getName());
        user.setDepartment(adminRegister.getDepartment());
        user.setNrp(adminRegister.getNrp());
        user.setPassword(adminRegister.getPassword());
        return user;
    }

    public static User principalToUser(Principal principal) {
        return (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
    }

    public static UpdateUser principalToUpdateUser(Principal principal) {
        User user = principalToUser(principal);
        UpdateUser updateUser = new UpdateUser();
        updateUser.setName(user.getName());
        updateUser.setNrp(user.getNrp());
        updateUser.setPhoneNumber(user.getPhoneNumber());
        updateUser.setDepartment(user.getDepartment());
        return updateUser;
    }

    public static UpdateUser userToUpdateUser(User user) {
        UpdateUser updateUser = new UpdateUser();
        updateUser.setName(user.getName());
        updateUser.setNrp(user.getNrp());
        updateUser.setPhoneNumber(user.getPhoneNumber());
        updateUser.setDepartment(user.getDepartment());
        return updateUser;
    }

    public static MyProfile principalToMyProfile(Principal principal) {
        User user = principalToUser(principal);
        MyProfile myProfile = new MyProfile();
        myProfile.setId(user.getId());
        myProfile.setName(user.getName());
        myProfile.setNrp(user.getNrp());
        myProfile.setRoles(user.getRoles());
        myProfile.setPhoneNumber(user.getPhoneNumber());
        myProfile.setDepartment(user.getDepartment());
        return myProfile;
    }

}
