package com.swp.cms.reqDto;

import com.swp.entity.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import static com.swp.entity.Roles.CUSTOMER;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String displayName;
    private String email;
    private String password;
    private String phone;
    public Roles getRole_id() {
        return CUSTOMER;
    }
//    public void setRole_id(Role role_id) {
//        this.role_id = role_id;
//    }
}
