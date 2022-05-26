package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.TaskMess;
import com.asaproject.asalife.domains.entities.User;
import com.asaproject.asalife.domains.models.reqres.SetTaskMess;
import com.asaproject.asalife.repositories.TaskMessRepository;
import com.asaproject.asalife.utils.mappers.TaskMessMapper;
import com.asaproject.asalife.utils.mappers.UserAdminMapper;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskMessServiceImpl implements TaskMessService{
    private final TaskMessRepository taskMessRepository;
    private final TaskMessMapper taskMessMapper;

    @Override
    public void addTaskMess(Principal principal, SetTaskMess setTaskMess) throws Exception {
        User user = UserAdminMapper.principalToUser(principal);
        TaskMess taskMess = new TaskMess();

        setTaskMessToEntity(user, taskMess, setTaskMess);
    }

    @Override
    public void updateTaskMess(Long id, SetTaskMess setTaskMess) throws Exception {
        try {
            TaskMess taskMess = findById(id);
            User user = taskMess.getUser();
            setTaskMessToEntity(user, taskMess, setTaskMess);
        } catch (Exception e){
            throw new NotFoundException(e.getMessage());
        }
    }

    public void setTaskMessToEntity(User user, TaskMess taskMess, SetTaskMess setTaskMess){
        saveSetTaskMess(taskMess, setTaskMess);
        taskMess.setUser(user);
        taskMessRepository.save(taskMess);
    }

    public void saveSetTaskMess (TaskMess taskMess, SetTaskMess setTaskMess) {
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
    }

    @Override
    public TaskMess findById(Long id) throws Exception {
        TaskMess taskMess = taskMessRepository.findTaskMessByIdNative(id);
        if (ObjectUtils.isEmpty(taskMess)) {
            throw new NotFoundException("Task Mess id not valid");
        }
        return taskMess;
    }

    @Override
    public List<SetTaskMess> finByUser(Principal principal) {
        User user = UserAdminMapper.principalToUser(principal);

        return taskMessMapper.mapToSetTaskMessList(taskMessRepository.findByUserOrderByCreatedAtAsc(user));
    }

    @Override
    public List<TaskMess> findAll() {
        return taskMessRepository.findAllByOrderByCreatedAtAsc();
    }
}
