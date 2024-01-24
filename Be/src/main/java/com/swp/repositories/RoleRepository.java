package com.swp.repositories;

import com.swp.entity.Role;
import com.swp.entity.enums.Roles;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository {
    Role findByRoleType(Roles roleType);
}
