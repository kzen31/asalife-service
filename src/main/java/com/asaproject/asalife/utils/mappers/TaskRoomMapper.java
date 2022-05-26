package com.asaproject.asalife.utils.mappers;

import com.asaproject.asalife.domains.entities.TaskRoom;
import com.asaproject.asalife.domains.models.reqres.SetTaskRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public final class TaskRoomMapper {
    public SetTaskRoom entityToSetTaskRoom(TaskRoom taskRoom) {
        SetTaskRoom setTaskRoom = new SetTaskRoom();

        setTaskRoom.setCreatedat(taskRoom.getCreatedAt());
        setTaskRoom.setMess(taskRoom.getMess());
        setTaskRoom.setNokamar(taskRoom.getNoKamar());

        setTaskRoom.setLantaikamar(taskRoom.getLantaiKamar());
        setTaskRoom.setLantaitoilet(taskRoom.getLantaiToilet());
        setTaskRoom.setLantailangitkamar(taskRoom.getLantaiLangitKamar());
        setTaskRoom.setLantailangitkamarmandi(taskRoom.getLantaiLangitKamarMandi());
        setTaskRoom.setWc(taskRoom.getWc());
        setTaskRoom.setWastafel(taskRoom.getWastafel());
        setTaskRoom.setTempattidur(taskRoom.getTempatTidur());
        setTaskRoom.setSprei(taskRoom.getSprei());
        setTaskRoom.setSelimut(taskRoom.getSelimut());
        setTaskRoom.setAc(taskRoom.getAc());
        setTaskRoom.setMeja(taskRoom.getMeja());
        setTaskRoom.setCermin(taskRoom.getCermin());
        setTaskRoom.setKeran(taskRoom.getKeran());
        setTaskRoom.setShower(taskRoom.getShower());
        setTaskRoom.setTempatsampah(taskRoom.getTempatSampah());
        setTaskRoom.setJendela(taskRoom.getJendela());
        setTaskRoom.setGorden(taskRoom.getGorden());
        setTaskRoom.setLemari(taskRoom.getLemari());
        setTaskRoom.setCreatedat(taskRoom.getCreatedAt());

        return setTaskRoom;
    }

    public List<SetTaskRoom> mapToSetTaskRoomList (List<TaskRoom> taskRoomList) {
        List<SetTaskRoom> setTaskRoomList = new ArrayList<>();

        for (TaskRoom taskRoom : taskRoomList) { // (int i = 0; i < list.size(); i++)
            SetTaskRoom setTaskRoom = entityToSetTaskRoom(taskRoom);
            setTaskRoomList.add(setTaskRoom);
        }
        return setTaskRoomList;
    }
}
