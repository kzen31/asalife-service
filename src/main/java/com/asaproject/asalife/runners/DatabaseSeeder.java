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
    private final MessRepository messRepository;
    private final MaintenanceRepository maintenanceRepository;
    private final TaskMaintenanceRepository taskMaintenanceRepository;


    @Override
    public void run(ApplicationArguments args) {
        log.info("Seeding DB");
        saveRoles();
        saveUsers();
        saveMaintenanceAll();
        saveTaskMaintenanceAll();
        saveAduanHousekeepingAll();
        saveRuangAll();
        saveRuangDetailAll();
        saveRecordHousekeepingAll();
        saveAduanLaundryAll();
        saveMessAll();
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

    private void saveAduanLaundryAll() {
        saveAduanLaundry("111", "Mess Joyfull", "1", "Pakaian Dinas", "Tertukar");
        saveAduanLaundry("111", "Mess Joyfull", "1", "Pakaian Kaos", "Hilang");
        saveAduanLaundry("112", "Mess Joyfull", "2", "Pakaian Kerja", "Tertukar");
    }

    private void saveAduanLaundry(String nrp, String mess, String noKamar, String jenisPakaian, String deviasi) {
        User user = userRepository.findByNrp(nrp);
        Laundry laundry = new Laundry();
        laundry.setUser(user);
        laundry.setMess(mess);
        laundry.setNo_kamar(noKamar);
        laundry.setJenis_pakaian(jenisPakaian);
        laundry.setJenis_deviasi(deviasi);
        laundry.setTanggal_laundry(new Date());

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

    private void saveMessAll() {
        saveMess("Mess Funny");
        saveMess("Mess Enjoy");
        saveMess("Mess Security");
        saveMess("Mess Good");
    }

    private void saveMess(String name) {
        Mess mess = new Mess();
        mess.setName(name);
        messRepository.save(mess);
    }

    private void saveMaintenanceAll() {
        saveMaintenance("111", "Lampu Mati", "Mess Enjoy");
        saveMaintenance("112", "Colokan Mati", "Mess Enjoy");
        saveMaintenance("111", "Kulkas rusak", "Mess Security");
        saveMaintenance("112", "Lampu Mati", "Mess Security");
        saveMaintenance("001", "Lampu Mati", "Mess Enjoy");
        saveMaintenance("002", "Lampu Mati", "Mess Funny");
    }

    private void saveMaintenance(String nrp, String jenisAduan, String lokasi) {
        User user = userRepository.findByNrp(nrp);

        Maintenance maintenance = new Maintenance();
        maintenance.setUser(user);
        maintenance.setJenisAduan(jenisAduan);
        maintenance.setLokasi(lokasi);
        maintenanceRepository.save(maintenance);
    }

    private void saveTaskMaintenanceAll() {
        saveTaskMaintenance("111", "Mesin Cuci", "Mess Enjoy", "Masik layak", "BAGUS");
        saveTaskMaintenance("111", "Televisi", "Mess Enjoy", "Masik layak pakai", "BAGUS");
        saveTaskMaintenance("111", "Pompa Air", "Mess Enjoy", "sudah diperbaiki", "BAGUS");
        saveTaskMaintenance("112", "Pompa Air", "Mess Security", "sudah diperbaiki", "BAGUS");
        saveTaskMaintenance("001", "Kulkas", "Mess Funny", "sudah diperbaiki", "BAGUS");
        saveTaskMaintenance("002", "Televisi", "Mess Funny", "sudah diperbaiki", "BAGUS");
    }


    private void saveTaskMaintenance(String nrp, String jenisAset, String lokasiAset, String keterangan, String status) {
        User user = userRepository.findByNrp(nrp);

        TaskMaintenance taskMaintenance = new TaskMaintenance();
        taskMaintenance.setJenisAset(jenisAset);
        taskMaintenance.setLokasiAset(lokasiAset);
        taskMaintenance.setKeterangan(keterangan);
        taskMaintenance.setStatus(status);
        taskMaintenance.setUser(user);
        taskMaintenanceRepository.save(taskMaintenance);
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
