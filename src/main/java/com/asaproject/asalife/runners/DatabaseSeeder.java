package com.asaproject.asalife.runners;

import com.asaproject.asalife.domains.ERole;
import com.asaproject.asalife.domains.entities.*;
import com.asaproject.asalife.domains.models.reqres.SetTaskMess;
import com.asaproject.asalife.domains.models.reqres.SetTaskRoom;
import com.asaproject.asalife.domains.models.requests.RatingManyRequest;
import com.asaproject.asalife.repositories.*;

import com.asaproject.asalife.utils.mappers.UserAdminMapper;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

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

    private final TaskRoomRepository taskRoomRepository;
    private final TaskMessRepository taskMessRepository;
    private final RatingCateringManyRepository ratingCateringManyRepository;

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
        saveTaskRoomMany ();
        saveTaskMessMany();
        saveManyRatingCatering();
    }

    private void saveRoles() {
        saveRoleIfNotExists(new Role(null, ERole.ROLE_ADMIN));
        saveRoleIfNotExists(new Role(null, ERole.ROLE_USER));
        saveRoleIfNotExists(new Role(null, ERole.ROLE_CUSTOMER));
        saveRoleIfNotExists(new Role(null, ERole.ROLE_WORKER));
        saveRoleIfNotExists(new Role(null, ERole.ROLE_SUPERUSER));
        saveRoleIfNotExists(new Role(null, ERole.ROLE_MEGAUSER));

        saveRoleIfNotExists(new Role(null, ERole.ROLE_CUS));
        saveRoleIfNotExists(new Role(null, ERole.ROLE_MT));
        saveRoleIfNotExists(new Role(null, ERole.ROLE_HK));
        saveRoleIfNotExists(new Role(null, ERole.ROLE_SPV));
        saveRoleIfNotExists(new Role(null, ERole.ROLE_GS));
        saveRoleIfNotExists(new Role(null, ERole.ROLE_HCGS));
        saveRoleIfNotExists(new Role(null, ERole.ROLE_PROG));
    }

    private void saveUsers() {
        User hcgs = new User();
        hcgs.setNrp("001");
        hcgs.setName("Admin");
        hcgs.setPassword(passwordEncoder.encode("123"));
        hcgs.setDepartment("it");
        hcgs.setRoles(Arrays.asList(roleRepository.findByName(ERole.ROLE_HCGS),
                roleRepository.findByName(ERole.ROLE_MEGAUSER),
                roleRepository.findByName(ERole.ROLE_ADMIN)));
        registerUserAdminIfNotExists(hcgs);

        User prog1 = new User();
        prog1.setNrp("002");
        prog1.setName("Dani");
        prog1.setPassword(passwordEncoder.encode("123"));
        prog1.setDepartment("it");
        prog1.setRoles(Arrays.asList(roleRepository.findByName(ERole.ROLE_PROG),
                roleRepository.findByName(ERole.ROLE_MEGAUSER),
                roleRepository.findByName(ERole.ROLE_ADMIN)));
        registerUserAdminIfNotExists(prog1);

        User prog2 = new User();
        prog2.setNrp("003");
        prog2.setName("zain");
        prog2.setPassword(passwordEncoder.encode("123"));
        prog2.setDepartment("it");
        prog2.setRoles(Arrays.asList(roleRepository.findByName(ERole.ROLE_PROG),
                roleRepository.findByName(ERole.ROLE_MEGAUSER),
                roleRepository.findByName(ERole.ROLE_ADMIN)));
        registerUserAdminIfNotExists(prog2);

        User customer = new User();
        customer.setNrp("111");
        customer.setName("Saif");
        customer.setPassword(passwordEncoder.encode("123"));
        customer.setDepartment("it");
        customer.setRoles(Arrays.asList(roleRepository.findByName(ERole.ROLE_CUS),
                roleRepository.findByName(ERole.ROLE_CUSTOMER),
                roleRepository.findByName(ERole.ROLE_USER)));
        registerUserAdminIfNotExists(customer);

        User mt = new User();
        mt.setNrp("112");
        mt.setName("Yusuf");
        mt.setPassword(passwordEncoder.encode("123"));
        mt.setDepartment("it");
        mt.setRoles(Arrays.asList(roleRepository.findByName(ERole.ROLE_MT),
                roleRepository.findByName(ERole.ROLE_WORKER),
                roleRepository.findByName(ERole.ROLE_USER)));
        registerUserAdminIfNotExists(mt);

        User hk = new User();
        hk.setNrp("113");
        hk.setName("Angga");
        hk.setPassword(passwordEncoder.encode("123"));
        hk.setDepartment("it");
        hk.setRoles(Arrays.asList(roleRepository.findByName(ERole.ROLE_HK),
                roleRepository.findByName(ERole.ROLE_WORKER),
                roleRepository.findByName(ERole.ROLE_USER)));
        registerUserAdminIfNotExists(hk);

        User spv = new User();
        spv.setNrp("114");
        spv.setName("Nieko");
        spv.setPassword(passwordEncoder.encode("123"));
        spv.setDepartment("it");
        spv.setRoles(Arrays.asList(roleRepository.findByName(ERole.ROLE_SPV),
                roleRepository.findByName(ERole.ROLE_SUPERUSER),
                roleRepository.findByName(ERole.ROLE_ADMIN)));
        registerUserAdminIfNotExists(spv);

        User gs = new User();
        gs.setNrp("115");
        gs.setName("Rendy");
        gs.setPassword(passwordEncoder.encode("123"));
        gs.setDepartment("it");
        gs.setRoles(Arrays.asList(roleRepository.findByName(ERole.ROLE_GS),
                roleRepository.findByName(ERole.ROLE_SUPERUSER),
                roleRepository.findByName(ERole.ROLE_ADMIN)));
        registerUserAdminIfNotExists(gs);
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

    private void saveTaskRoomMany () {
        saveTaskRoom("111", new SetTaskRoom(null, "MESS JOY","1a", true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true));
        saveTaskRoom("112", new SetTaskRoom(null, "MESS JOY","1b", true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true));
        saveTaskRoom("111", new SetTaskRoom(null, "MESS JOY","1c", true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true));
        saveTaskRoom("112", new SetTaskRoom(null, "MESS JOY","1d", true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true));
    }

    private void saveTaskRoom (String nrp, SetTaskRoom setTaskRoom) {
        User user = userRepository.findByNrp(nrp);

        TaskRoom taskRoom = new TaskRoom();
        taskRoom.setUser(user);

        taskRoom.setMess(setTaskRoom.getMess());
        taskRoom.setNoKamar(setTaskRoom.getNokamar());

        taskRoom.setLantaiKamar(setTaskRoom.getLantaikamar());
        taskRoom.setLantaiToilet(setTaskRoom.getLantaitoilet());
        taskRoom.setLantaiLangitKamar(setTaskRoom.getLantailangitkamar());
        taskRoom.setLantaiLangitKamarMandi(setTaskRoom.getLantailangitkamarmandi());
        taskRoom.setWc(setTaskRoom.getWc());
        taskRoom.setWastafel(setTaskRoom.getWastafel());
        taskRoom.setTempatTidur(setTaskRoom.getTempattidur());
        taskRoom.setSprei(setTaskRoom.getSprei());
        taskRoom.setSelimut(setTaskRoom.getSelimut());
        taskRoom.setAc(setTaskRoom.getAc());
        taskRoom.setMeja(setTaskRoom.getMeja());
        taskRoom.setCermin(setTaskRoom.getCermin());
        taskRoom.setKeran(setTaskRoom.getKeran());
        taskRoom.setShower(setTaskRoom.getShower());
        taskRoom.setTempatSampah(setTaskRoom.getTempatsampah());
        taskRoom.setJendela(setTaskRoom.getJendela());
        taskRoom.setGorden(setTaskRoom.getGorden());
        taskRoom.setLemari(setTaskRoom.getLemari());

        taskRoomRepository.save(taskRoom);
    }

    private void saveTaskMessMany() {
        saveTaskMess("111", new SetTaskMess(null, "MESS HAPPY", true,true,true,true,true,true,true,true,false,false,false,true,true,true,false,true,true,true,true,true,true,true,true,true,true));
        saveTaskMess("111", new SetTaskMess(null, "MESS ENJOY", false,true,true,true,true,true,true,true,false,false,true,true,true,false,true,true,true,true,true,true,false,true,true,true,true));
        saveTaskMess("111", new SetTaskMess(null, "MESS FUN", false,true,true,true,true,true,false,false,true,true,true,true,true,true,false,true,true,true,true,true,false,true,true,true,true));
        saveTaskMess("111", new SetTaskMess(null, "MESS HEALTH", true,true,true,true,true,true,true,true,true,true,false,false,false,true,true,true,true,true,true,true,false,true,true,true,true));
    }

    private void saveTaskMess (String nrp, SetTaskMess setTaskMess) {
        User user = userRepository.findByNrp(nrp);

        TaskMess taskMess = new TaskMess();
        taskMess.setUser(user);

        taskMess.setMess(setTaskMess.getMess());

        taskMess.setRuangTvKacaJendelaKusen(setTaskMess.getRuangtvkacajendelakusen());
        taskMess.setRuangTvCermin(setTaskMess.getRuangtvcermin());
        taskMess.setRuangTvDispenser(setTaskMess.getRuangtvdispenser());
        taskMess.setRuangTvAc(setTaskMess.getRuangtvac());
        taskMess.setRuangTvFurniture(setTaskMess.getRuangtvfurniture());
        taskMess.setRuangTvRakTv(setTaskMess.getRuangtvraktv());
        taskMess.setRuangTvTiraiKarpet(setTaskMess.getRuangtvtiraikarpet());
        taskMess.setRuangTvDinding(setTaskMess.getRuangtvdinding());
        taskMess.setRuangTvLantai(setTaskMess.getRuangtvlantai());

        taskMess.setKoridorTempatSampah(setTaskMess.getKoridortempatsampah());
        taskMess.setKoridorPintu(setTaskMess.getKoridorpintu());
        taskMess.setKoridorLantaiSudutLantai(setTaskMess.getKoridorlantaisudutlantai());
        taskMess.setKoridorKeset(setTaskMess.getKoridorkeset());
        taskMess.setKoridorPantry(setTaskMess.getKoridorpantry());
        taskMess.setKoridorWastafelChromeFixture(setTaskMess.getKoridorwastafelchromefixture());
        taskMess.setKoridorPeralatanMakanRakPiring(setTaskMess.getKoridorperalatanmakanrakpiring());
        taskMess.setKoridorPintuDinding(setTaskMess.getKoridorpintudinding());
        taskMess.setKoridorKancaJendelaKusen(setTaskMess.getKoridorkancajendelakusen());

        taskMess.setToiletPintuDinding(setTaskMess.getToiletpintudinding());
        taskMess.setToiletTempatSampah(setTaskMess.getToilettempatsampah());
        taskMess.setToiletWastafelChromeFixture(setTaskMess.getToiletwastafelchromefixture());
        taskMess.setToiletUrinoirSelangToiletBowl(setTaskMess.getToileturinoirselangtoiletbowl());
        taskMess.setToiletShowerAreaCurtain(setTaskMess.getToiletshowerareacurtain());
        taskMess.setToiletLantaiSudutLantai(setTaskMess.getToiletlantaisudutlantai());
        taskMess.setToiletTeras(setTaskMess.getToiletteras());
        taskMessRepository.save(taskMess);
    }

    void saveManyRatingCatering() {
        saveRatingCatering("001", new RatingManyRequest(1,2,3,1,1,1,3,2,"Makanan lebih bervariasi"));
        saveRatingCatering("002", new RatingManyRequest(3,2,3,1,1,2,1,1,"Minuman lebih bervariasi"));
        saveRatingCatering("002", new RatingManyRequest(3,2,1,2,2,2,3,3,"Makanan Tambah Garam"));
        saveRatingCatering("111", new RatingManyRequest(1,3,1,2,3,5,3,4,"Makanan matang sempurna"));
        saveRatingCatering("112", new RatingManyRequest(2,2,3,4,3,5,3,2,"Makanan jangan terlambat"));
        saveRatingCatering("112", new RatingManyRequest(1,3,1,4,4,5,3,1,"Makanan jangan basi"));
        saveRatingCatering("113", new RatingManyRequest(2,3,1,4,4,5,3,1,"Makanan bersih"));
    }

    void saveRatingCatering(String nrp, RatingManyRequest ratingManyRequest) {
        User user = userRepository.findByNrp(nrp);

        RatingCateringMany ratingCateringMany = new RatingCateringMany();
        ratingCateringMany.setNilai1(ratingManyRequest.getNilai1());
        ratingCateringMany.setNilai2(ratingManyRequest.getNilai2());
        ratingCateringMany.setNilai3(ratingManyRequest.getNilai3());
        ratingCateringMany.setNilai4(ratingManyRequest.getNilai4());
        ratingCateringMany.setNilai5(ratingManyRequest.getNilai5());
        ratingCateringMany.setNilai6(ratingManyRequest.getNilai6());
        ratingCateringMany.setNilai7(ratingManyRequest.getNilai7());
        ratingCateringMany.setNilai8(ratingManyRequest.getNilai8());
        ratingCateringMany.setSaran(ratingManyRequest.getSaran());

        ratingCateringMany.setUser(user);
        ratingCateringManyRepository.save(ratingCateringMany);
    }
}
