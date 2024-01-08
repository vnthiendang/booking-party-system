package com.swp.cms.dto;

import com.swp.entity.Roles;
import lombok.Data;

import java.awt.*;
import java.time.LocalDateTime;

@Data
public class UserDto {
    private Integer userId;
    private String display_name;
    private String password;
    private String email;
    private String phone;
    private LocalDateTime created_date;
    private Roles role_id;
    public String getRole_id(){
        return this.role_id.name();
    }
}
