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
    private final RuangRepository ruangRepository;
    private final RuangDetailRepository ruangDetailRepository;
    private final RecordHousekeepingRepository recordHousekeepingRepository;


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
        saveAduanHousekeepingAll();
        saveRuangAll();
        saveRuangDetailAll();
        saveRecordHousekeepingAll();
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

    private void saveAduanHousekeepingAll() {
        saveAduanHousekeeping("111", "Mess Joyfull", "Dinding kotor", "CLEANING_PROGRESS");
        saveAduanHousekeeping("111", "Mess Joyfull", "Lantai kotor", "CLEANING_PROGRESS");
        saveAduanHousekeeping("112", "Mess Joyfull", "Jendela kotor", "DONE");
    }

    private void saveAduanHousekeeping(String nrp, String lokasi, String deskripsi, String status) {
        User user = userRepository.findByNrp(nrp);
        Housekeeping housekeeping = new Housekeeping();
        housekeeping.setUser(user);
        housekeeping.setLokasi(lokasi);
        housekeeping.setDeskripsi(deskripsi);
        housekeeping.setStatus(status);
        housekeepingRepository.save(housekeeping);
    }

    private void saveRuangAll() {
        saveRuang("Ruang Tamu");
        saveRuang("Ruang Kamar");
        saveRuang("Lorong");
    }

    private void saveRuang(String name) {
        Ruang ruang = new Ruang();
        ruang.setName(name);
        ruangRepository.save(ruang);
    }

    private void saveRuangDetailAll() {
        saveRuangDetail(1L, "Lantai");
        saveRuangDetail(1L, "Dinding");
        saveRuangDetail(1L, "Jendela");
        saveRuangDetail(2L, "Lantai");
        saveRuangDetail(2L, "Dinding");
        saveRuangDetail(3L, "Jendela");
    }

    private void saveRuangDetail(Long id, String detail){
        Ruang ruang = ruangRepository.findRuangByIdNative(id);
        RuangDetail ruangDetail = new RuangDetail();
        ruangDetail.setDetail(detail);
        ruangDetail.setRuang(ruang);
        ruangDetailRepository.save(ruangDetail);
    }

    private void saveRecordHousekeepingAll() {
        saveRecordHousekeeping("111", 1L, true);
        saveRecordHousekeeping("111", 2L, true);
        saveRecordHousekeeping("111", 3L, false);
        saveRecordHousekeeping("111", 4L, false);
        saveRecordHousekeeping("112", 1L, true);
        saveRecordHousekeeping("112", 2L, true);
        saveRecordHousekeeping("112", 3L, false);
    }

    private void saveRecordHousekeeping(String nrp, Long idRuangDetail, Boolean ceklis) {
        RecordHousekeeping recordHousekeeping = new RecordHousekeeping();
        User user = userRepository.findByNrp(nrp);
        RuangDetail ruangDetail = ruangDetailRepository.findRuangDetailByIdNative(idRuangDetail);
        recordHousekeeping.setUser(user);
        recordHousekeeping.setRuangDetail(ruangDetail);
        recordHousekeeping.setCeklis(ceklis);
        recordHousekeepingRepository.save(recordHousekeeping);
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
