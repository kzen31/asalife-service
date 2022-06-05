package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.TaskRoom;
import com.asaproject.asalife.domains.entities.User;
import com.asaproject.asalife.domains.models.reqres.SetTaskRoom;
import com.asaproject.asalife.domains.models.requests.EditTaskRoom;
import com.asaproject.asalife.repositories.TaskRoomRepository;
import com.asaproject.asalife.utils.mappers.TaskRoomMapper;
import com.asaproject.asalife.utils.mappers.UserAdminMapper;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskRoomServiceImpl implements TaskRoomService{
    private final TaskRoomRepository taskRoomRepository;
    private final TaskRoomMapper taskRoomMapper;

    @Override
    public void setTaskRoomToEntity(User user, TaskRoom taskRoom, SetTaskRoom setTaskRoom){
        saveSetTaskRoom(taskRoom, setTaskRoom);
        taskRoom.setUser(user);

        taskRoomRepository.save(taskRoom);
    }

    @Override
    public void saveSetTaskRoom(TaskRoom taskRoom, SetTaskRoom setTaskRoom) {
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
    }

    @Override
    public void addTaskRoom(Principal principal, SetTaskRoom setTaskRoom) {
        User user = UserAdminMapper.principalToUser(principal);

        TaskRoom taskRoom = new TaskRoom();
        setTaskRoomToEntity(user, taskRoom, setTaskRoom);
    }

    @Override
    public void updateTaskRoom(Long id, EditTaskRoom editTaskRoom) throws Exception {
        try {
            TaskRoom taskRoom = findById(id);
            taskRoom.setLantaiKamar(editTaskRoom.getLantaikamar());
            taskRoom.setLantaiToilet(editTaskRoom.getLantaitoilet());
            taskRoom.setLantaiLangitKamar(editTaskRoom.getLantailangitkamar());
            taskRoom.setLantaiLangitKamarMandi(editTaskRoom.getLantailangitkamarmandi());
            taskRoom.setWc(editTaskRoom.getWc());
            taskRoom.setWastafel(editTaskRoom.getWastafel());
            taskRoom.setTempatTidur(editTaskRoom.getTempattidur());
            taskRoom.setSprei(editTaskRoom.getSprei());
            taskRoom.setSelimut(editTaskRoom.getSelimut());
            taskRoom.setAc(editTaskRoom.getAc());
            taskRoom.setMeja(editTaskRoom.getMeja());
            taskRoom.setCermin(editTaskRoom.getCermin());
            taskRoom.setKeran(editTaskRoom.getKeran());
            taskRoom.setShower(editTaskRoom.getShower());
            taskRoom.setTempatSampah(editTaskRoom.getTempatsampah());
            taskRoom.setJendela(editTaskRoom.getJendela());
            taskRoom.setGorden(editTaskRoom.getGorden());
            taskRoom.setLemari(editTaskRoom.getLemari());
        } catch (Exception e){
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    public TaskRoom findById(Long id) throws Exception {
        TaskRoom taskRoom = taskRoomRepository.findTaskRoomByIdNative(id);
        if (ObjectUtils.isEmpty(taskRoom)) {
            throw new NotFoundException("Task Room id not valid");
        }
        return taskRoomRepository.findTaskRoomByIdNative(id);
    }

    @Override
    public List<SetTaskRoom> finByUser(Principal principal) {
        User user = UserAdminMapper.principalToUser(principal);

        return taskRoomMapper.mapToSetTaskRoomList(taskRoomRepository.findByUserOrderByCreatedAtAsc(user));
    }

    @Override
    public List<TaskRoom> findAll() {
        return taskRoomRepository.findAllByOrderByCreatedAtAsc();
    }

    @Override
    public void deleteTaskRoom(Long id) throws Exception{
        TaskRoom taskRoom = taskRoomRepository.findTaskRoomByIdNative(id);
        if (ObjectUtils.isEmpty(taskRoom)) {
            throw new NotFoundException("Task Room id not valid");
        }

        if (!ObjectUtils.isEmpty(taskRoom.getDeletedAt())) {
            throw new NotFoundException("Task Room id not valid");
        }

        taskRoom.setDeletedAt(new Date());
        taskRoomRepository.save(taskRoom);
    }
}
