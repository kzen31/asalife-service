package com.asaproject.asalife.services;


import com.asaproject.asalife.domains.entities.TaskRoom;
import com.asaproject.asalife.domains.entities.User;
import com.asaproject.asalife.domains.models.reqres.SetTaskRoom;
import com.asaproject.asalife.domains.models.requests.EditTaskRoom;
import com.asaproject.asalife.domains.models.responses.RecordDashboard;

import java.security.Principal;
import java.util.List;

public interface TaskRoomService {
    void addTaskRoom(Principal principal, SetTaskRoom setTaskRoom);

    void saveSetTaskRoom(TaskRoom taskRoom, SetTaskRoom setTaskRoom);

    void setTaskRoomToEntity(User user, TaskRoom taskRoom, SetTaskRoom setTaskRoom);

    void updateTaskRoom(Long id, EditTaskRoom editTaskRoom) throws Exception;

    TaskRoom findById(Long id) throws Exception;

    List<SetTaskRoom> finByUser(Principal principal);

    List<TaskRoom> findAll();

    void deleteTaskRoom(Long id) throws Exception;

}
