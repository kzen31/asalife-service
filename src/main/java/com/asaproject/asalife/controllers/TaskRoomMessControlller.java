package com.asaproject.asalife.controllers;

import com.asaproject.asalife.domains.entities.TaskMess;
import com.asaproject.asalife.domains.entities.TaskRoom;
import com.asaproject.asalife.domains.models.reqres.SetTaskMess;
import com.asaproject.asalife.domains.models.reqres.SetTaskRoom;
import com.asaproject.asalife.domains.models.requests.EditTaskMess;
import com.asaproject.asalife.domains.models.requests.EditTaskRoom;
import com.asaproject.asalife.domains.models.responses.ApiResponse;
import com.asaproject.asalife.services.TaskMessService;
import com.asaproject.asalife.services.TaskRoomService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task")
public class TaskRoomMessControlller extends HandlerController{
    private final TaskRoomService taskRoomService;
    private final TaskMessService taskMessService;

    @GetMapping("/room")
    public ResponseEntity<List<TaskRoom>> getAllTaskRoom() {
        return ResponseEntity.ok(taskRoomService.findAll());
    }

    @GetMapping("/room-my")
    public ResponseEntity<List<SetTaskRoom>> getMyTaskRoom(Principal principal) {
        return ResponseEntity.ok(taskRoomService.finByUser(principal));
    }

    @PostMapping("/room-add")
    public ResponseEntity<ApiResponse> addATaskRoom(Principal principal, @Valid @RequestBody SetTaskRoom setTaskRoom) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/room-add").toUriString());
        try {
            taskRoomService.addTaskRoom(principal, setTaskRoom);
            return ResponseEntity.created(uri)
                    .body(ApiResponse.builder().message("Added a Task Room").build());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/room-update/{id}")
    public ResponseEntity<ApiResponse> updateATaskRoom(@PathVariable Long id, @Valid @RequestBody EditTaskRoom editTaskRoom) {
        try {
            taskRoomService.updateTaskRoom(id, editTaskRoom);
            return ResponseEntity.ok(ApiResponse.builder().message("Successfully Update A Task Room").build());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room with given id is Not Found");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/room-delete/{id}")
    public ResponseEntity<ApiResponse> deleteATaskRoom(@PathVariable Long id) {
        try {
            taskRoomService.deleteTaskRoom(id);
            return ResponseEntity.ok(ApiResponse.builder().message("Successfully Delete A Task Room").build());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room with given id is Not Found");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/mess")
    public ResponseEntity<List<TaskMess>> getAllTaskMess() {
        return ResponseEntity.ok(taskMessService.findAll());
    }

    @GetMapping("/mess-my")
    public ResponseEntity<List<SetTaskMess>> getMyTaskMess(Principal principal) {
        return ResponseEntity.ok(taskMessService.finByUser(principal));
    }

    @PostMapping("/mess-add")
    public ResponseEntity<ApiResponse> addATaskMess(Principal principal, @Valid @RequestBody SetTaskMess setTaskMess) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/mess-add").toUriString());
        try {
            taskMessService.addTaskMess(principal, setTaskMess);
            return ResponseEntity.created(uri)
                    .body(ApiResponse.builder().message("Added a Task Mess").build());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/mess-update/{id}")
    public ResponseEntity<ApiResponse> updateATaskMess(@PathVariable Long id, @Valid @RequestBody EditTaskMess editTaskMess) {
        try {
            taskMessService.updateTaskMess(id, editTaskMess);
            return ResponseEntity.ok(ApiResponse.builder().message("Successfully Update A Task Mess").build());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mess with given id is Not Found");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/mess-delete/{id}")
    public ResponseEntity<ApiResponse> deleteATaskMess(@PathVariable Long id) {
        try {
            taskMessService.deleteTaskMess(id);
            return ResponseEntity.ok(ApiResponse.builder().message("Successfully Delete A Task Mess").build());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mess with given id is Not Found");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
