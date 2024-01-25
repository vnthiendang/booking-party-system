package com.swp.cms.controller;

import com.swp.cms.dto.UserDto;
import com.swp.cms.reqDto.ResetPasswordRequest;
import com.swp.exception.EmailAlreadyExistException;
import com.swp.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
@Slf4j
public class ProfileController {
    private final UserService userService;

    @GetMapping
    public UserDto getCurrentUser(){
        UserDto us = userService.getById();
        return us;
    }

    @PatchMapping("/resetPassword")
    public ResponseEntity<?> changePassword(
            @RequestBody ResetPasswordRequest request,
            Principal connectedUser
    ) {
        userService.changePassword(request, connectedUser);
        return ResponseEntity.ok("Reset Password Successfully");
    }

    @PostMapping("/edit")
    public ResponseEntity<String> editHostAccount(@Valid @RequestBody UserDto userDTO, BindingResult result) {

        if (result.hasErrors()) {
            log.warn("Validation errors occurred while editing account");
            return ResponseEntity.badRequest().body("Validation errors found");
        }

        try {
            userService.updateLoggedInUser(userDTO);
            log.info("Successfully edited account");
            return ResponseEntity.ok("Account information updated successfully");
        } catch (EmailAlreadyExistException e) {
            log.error("Username already exists error", e);
            result.rejectValue("username", "user.exists", "Username is already registered!");
            return ResponseEntity.badRequest().body("Username is already registered");
        }
    }


}
