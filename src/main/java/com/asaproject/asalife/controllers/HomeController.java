package com.asaproject.asalife.controllers;

import com.asaproject.asalife.domains.models.responses.DeviceInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class HomeController extends HandlerController {
    @GetMapping("")
    public ResponseEntity<DeviceInfo> getDeviceInfo(@RequestHeader("User-Agent") String userAgent,
                                                    HttpServletRequest request) {
        return ResponseEntity.ok(new DeviceInfo(userAgent, request.getRemoteAddr()));
    }
}
