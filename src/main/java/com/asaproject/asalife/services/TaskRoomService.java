package com.asaproject.asalife.services;


import com.asaproject.asalife.domains.entities.TaskRoom;
import com.asaproject.asalife.domains.models.reqres.SetTaskRoom;
import com.asaproject.asalife.domains.models.responses.RecordDashboard;

import java.security.Principal;
import java.util.List;

public interface TaskRoomService {
    void addTaskRoom(SetTaskRoom setTaskRoom);
    void updateTaskRoom(Long id, SetTaskRoom setTaskRoom) throws Exception;
    TaskRoom findById(Long id) throws Exception;
    List<TaskRoom> finByUser(Principal principal);
    List<TaskRoom> findAll();
}
