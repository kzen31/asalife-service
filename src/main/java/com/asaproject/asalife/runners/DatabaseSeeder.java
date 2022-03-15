package com.asaproject.asalife.runners;

import com.asaproject.asalife.domains.ERole;
import com.asaproject.asalife.domains.entities.Catering;
import com.asaproject.asalife.domains.entities.Role;
import com.asaproject.asalife.domains.entities.User;
import com.asaproject.asalife.repositories.CateringRepository;
import com.asaproject.asalife.repositories.RoleRepository;
import com.asaproject.asalife.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;

@Slf4j
@Transactional
@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements ApplicationRunner {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CateringRepository cateringRepository;


    @Override
    public void run(ApplicationArguments args) {
        log.info("Seeding DB");
        saveRoles();
        saveUsers();
        saveCatering();
    }

    private void saveRoles() {
        saveRoleIfNotExists(new Role(null, ERole.ROLE_ADMIN));
        saveRoleIfNotExists(new Role(null, ERole.ROLE_USER));
        saveRoleIfNotExists(new Role(null, ERole.ROLE_CUSTOMER));
        saveRoleIfNotExists(new Role(null, ERole.ROLE_WORKER));
        saveRoleIfNotExists(new Role(null, ERole.ROLE_SUPERUSER));
        saveRoleIfNotExists(new Role(null, ERole.ROLE_MEGAUSER));
    }

    private void saveUsers() {
        User customer = new User();
        customer.setNrp("111");
        customer.setName("budi");
        customer.setPassword(passwordEncoder.encode("123"));
        customer.setRoles(Arrays.asList(roleRepository.findByName(ERole.ROLE_CUSTOMER),
                roleRepository.findByName(ERole.ROLE_USER)));
        registerUserAdminIfNotExists(customer);

        User worker = new User();
        worker.setNrp("112");
        worker.setName("binomo");
        worker.setPassword(passwordEncoder.encode("123"));
        worker.setRoles(Arrays.asList(roleRepository.findByName(ERole.ROLE_WORKER),
                roleRepository.findByName(ERole.ROLE_USER)));
        registerUserAdminIfNotExists(worker);

        User megauser = new User();
        megauser.setNrp("001");
        megauser.setName("asa");
        megauser.setPassword(passwordEncoder.encode("123"));
        megauser.setRoles(Arrays.asList(roleRepository.findByName(ERole.ROLE_MEGAUSER),
                roleRepository.findByName(ERole.ROLE_ADMIN)));
        registerUserAdminIfNotExists(megauser);

        User superuser = new User();
        superuser.setNrp("002");
        superuser.setName("loj");
        superuser.setPassword(passwordEncoder.encode("123"));
        superuser.setRoles(Arrays.asList(roleRepository.findByName(ERole.ROLE_SUPERUSER),
                roleRepository.findByName(ERole.ROLE_ADMIN)));
        registerUserAdminIfNotExists(superuser);
    }

    private void saveCatering() {
        Catering catering = new Catering();
        catering.setUser(userRepository.findByNrp("111"));
        catering.setDeskripsi("Gosong");
        catering.setLokasi("Mess Good");
        catering.setKritik_saran("Jangan Gosong");

        cateringRepository.save(catering);
    }

    private void saveRoleIfNotExists(Role role) {
        if (roleRepository.findByName(role.getName()) == null) {
            roleRepository.save(role);
        }
    }

    private void registerUserAdminIfNotExists(User user) {
        if (userRepository.findByNrp(user.getNrp()) == null) {
            try {
                userRepository.save(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
