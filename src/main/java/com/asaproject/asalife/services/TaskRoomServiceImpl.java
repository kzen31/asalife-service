package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.TaskRoom;
import com.asaproject.asalife.domains.models.reqres.SetTaskRoom;
import com.asaproject.asalife.repositories.TaskRoomRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskRoomServiceImpl implements TaskRoomService{
    private final TaskRoomRepository taskRoomRepository;

    public void setTaskRoomToEntity(TaskRoom taskRoom, SetTaskRoom setTaskRoom){
        taskRoom.setLantaiKamar(setTaskRoom.getLantaiKamar());
        taskRoom.setLantaiToilet(setTaskRoom.getLantaiToilet());
        taskRoom.setLantaiLangitKamar(setTaskRoom.getLantaiLangitKamar());
        taskRoom.setLantaiLangitKamarMandi(setTaskRoom.getLantaiLangitKamarMandi());
        taskRoom.setWc(setTaskRoom.getWc());
        taskRoom.setWastafel(setTaskRoom.getWastafel());
        taskRoom.setTempatTidur(setTaskRoom.getTempatTidur());
        taskRoom.setSprei(setTaskRoom.getSprei());
        taskRoom.setSelimut(setTaskRoom.getSelimut());
        taskRoom.setAc(setTaskRoom.getAc());
        taskRoom.setMeja(setTaskRoom.getMeja());
        taskRoom.setCermin(setTaskRoom.getCermin());
        taskRoom.setKeran(setTaskRoom.getKeran());
        taskRoom.setShower(setTaskRoom.getShower());
        taskRoom.setTempatSampah(setTaskRoom.getTempatSampah());
        taskRoom.setJendela(setTaskRoom.getJendela());
        taskRoom.setGorden(setTaskRoom.getGorden());
        taskRoom.setLemari(setTaskRoom.getLemari());
        taskRoomRepository.save(taskRoom);
    }

    @Override
    public void addTaskRoom(SetTaskRoom setTaskRoom) {
        TaskRoom taskRoom = new TaskRoom();
        setTaskRoomToEntity(taskRoom, setTaskRoom);
    }

    @Override
    public void updateTaskRoom(Long id, SetTaskRoom setTaskRoom) throws Exception {
        try {
            TaskRoom taskRoom = findById(id);
            setTaskRoomToEntity(taskRoom, setTaskRoom);
        } catch (Exception e){
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    public TaskRoom findById(Long id) throws Exception {
        return taskRoomRepository.findTaskRoomByIdNative(id);
    }

    @Override
    public List<TaskRoom> finByUser(Principal principal) {
        return null;
    }

    @Override
    public List<TaskRoom> findAll() {
        return null;
    }
}
