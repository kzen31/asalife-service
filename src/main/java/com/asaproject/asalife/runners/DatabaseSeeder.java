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


    @Override
    public void run(ApplicationArguments args) {
        log.info("Seeding DB");
        saveRoles();
        saveUsers();
        saveAduanCateringAll();
        savePertanyaanAll();
        saveBobotAll();
        saveRatingCateringAll();
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

    private void saveAduanCateringAll() {
        saveCatering("111", "Gosong", "Mess Enjoy", "Jangan Gosong");
        saveCatering("111", "Asin", "Mess Enjoy", "Jangan Asin");
        saveCatering("112", "Hambar", "Mess Healthy", "Tambah garam");

    }

    private void saveCatering(String nrp, String deskripsi, String lokasi, String kritikSaran) {
        Catering catering = new Catering();
        catering.setUser(userRepository.findByNrp(nrp));
        catering.setDeskripsi(deskripsi);
        catering.setLokasi(lokasi);
        catering.setKritik_saran(kritikSaran);

        cateringRepository.save(catering);
    }

    private void savePertanyaanAll() {
        savePertanyaan("Apakah makanannya enak?");
        savePertanyaan("Apakah minumannya enak?");
        savePertanyaan("Tingkat Pelayanan?");

    }

    private void savePertanyaan(String isi) {
        Pertanyaan pertanyaan = new Pertanyaan();
        pertanyaan.setIsi(isi);
        pertanyaanRepository.save(pertanyaan);
    }

    private void saveBobotAll() {
        saveBobot(1L, "Nikmat", 5);
        saveBobot(1L, "Lumayan", 3);
        saveBobot(1L, "Buruk", 1);
        saveBobot(2L, "Nikmat", 5);
        saveBobot(2L, "Lumayan", 1);
    }

    private void saveBobot(Long id, String pilihan, int nilai) {
        Pertanyaan pertanyaan = pertanyaanRepository.findPertanyaanByIdNative(id);
        Bobot bobot = new Bobot();
        bobot.setPertanyaan(pertanyaan);
        bobot.setPilihan(pilihan);
        bobot.setNilai(nilai);
        bobotRepository.save(bobot);
    }

    private void saveRatingCateringAll() {
        saveRatingCatering("111", 1L, 5);
        saveRatingCatering("111", 2L, 3);
        saveRatingCatering("112", 3L, 1);

    }

    private void saveRatingCatering(String nrp, Long idPertanyaan, int nilai) {
        Pertanyaan pertanyaan = pertanyaanRepository.findPertanyaanByIdNative(idPertanyaan);
        User user = userRepository.findByNrp(nrp);

        RatingCatering ratingCatering = new RatingCatering();
        ratingCatering.setNilai(nilai);
        ratingCatering.setPertanyaan(pertanyaan);
        ratingCatering.setUser(user);

        ratingCateringRepository.save(ratingCatering);
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
