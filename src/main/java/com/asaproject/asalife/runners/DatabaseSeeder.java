package com.asaproject.asalife.runners;

import com.asaproject.asalife.domains.ERole;
import com.asaproject.asalife.domains.entities.*;
import com.asaproject.asalife.repositories.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Date;

@Slf4j
@Transactional
@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements ApplicationRunner {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CateringRepository cateringRepository;
    private final PertanyaanRepository pertanyaanRepository;
    private final BobotRepository bobotRepository;
    private final RatingCateringRepository ratingCateringRepository;
    private final LaundryRepository laundryRepository;
    private final HousekeepingRepository housekeepingRepository;


    @Override
    public void run(ApplicationArguments args) {
        log.info("Seeding DB");
        saveRoles();
        saveUsers();
        saveCatering();
        savePertanyaan();
        saveBobot();
        saveRatingCatering();
        saveAduanLaundry();
        saveAduanHousekeeping();
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

    private void savePertanyaan() {
        Pertanyaan pertanyaan1 = new Pertanyaan();
        pertanyaan1.setIsi("Apakah makanannya enak?");
        pertanyaanRepository.save(pertanyaan1);

        Pertanyaan pertanyaan2 = new Pertanyaan();
        pertanyaan2.setIsi("Apakah minumannya enak?");
        pertanyaanRepository.save(pertanyaan2);
    }

    private void saveBobot() {
        Pertanyaan pertanyaan = pertanyaanRepository.findPertanyaanByIdNative(1L);

        Bobot bobot1 = new Bobot();
        bobot1.setPertanyaan(pertanyaan);
        bobot1.setPilihan("Baik");
        bobot1.setNilai(5);
        bobotRepository.save(bobot1);

        Bobot bobot2 = new Bobot();
        bobot2.setPertanyaan(pertanyaan);
        bobot2.setPilihan("Buruk");
        bobot2.setNilai(1);
        bobotRepository.save(bobot2);
    }

    private void saveRatingCatering() {
        Pertanyaan pertanyaan = pertanyaanRepository.findPertanyaanByIdNative(1L);
        User user = userRepository.findByNrp("111");

        RatingCatering ratingCatering = new RatingCatering();
        ratingCatering.setNilai(1);
        ratingCatering.setPertanyaan(pertanyaan);
        ratingCatering.setUser(user);

        ratingCateringRepository.save(ratingCatering);
    }

    private void saveAduanLaundry() {
        User user = userRepository.findByNrp("111");
        Laundry laundry = new Laundry();
        laundry.setUser(user);
        laundry.setMess("Mess Joyfull");
        laundry.setNo_kamar("1");
        laundry.setJenis_pakaian("Pakaian Dinas");
        laundry.setJenis_deviasi("Kotor");
        laundry.setTanggal_loundry(new Date());

        laundryRepository.save(laundry);
    }

    private void saveAduanHousekeeping() {
        User user = userRepository.findByNrp("111");
        Housekeeping housekeeping = new Housekeeping();
        housekeeping.setUser(user);
        housekeeping.setLokasi("Mess Joyfull");
        housekeeping.setDeskripsi("Dinding kotor");
        housekeeping.setStatus("DONE");

        housekeepingRepository.save(housekeeping);

        Housekeeping housekeeping1 = new Housekeeping();
        housekeeping1.setUser(user);
        housekeeping1.setLokasi("Mess Security");
        housekeeping1.setDeskripsi("Lantai kotor");

        housekeepingRepository.save(housekeeping1);
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
