package com.asaproject.asalife.services;


import com.asaproject.asalife.domains.entities.TaskMess;
import com.asaproject.asalife.domains.entities.TaskRoom;
import com.asaproject.asalife.domains.models.reqres.SetTaskMess;
import com.asaproject.asalife.domains.models.reqres.SetTaskRoom;

import java.security.Principal;
import java.util.List;

public interface TaskMessService {
    void addTaskMess(Principal principal, SetTaskMess setTaskMess) throws Exception;

    void updateTaskMess(Long id, SetTaskMess setTaskMess) throws Exception;

    TaskMess findById(Long id) throws Exception;

    List<SetTaskMess> finByUser(Principal principal);

    List<TaskMess> findAll();
}