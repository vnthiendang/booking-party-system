package com.swp.token;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    HOST_READ("management:read"),
    HOST_UPDATE("management:update"),
    HOST_CREATE("management:create"),
    HOST_DELETE("management:delete")

    ;

    @Getter
    private final String permission;
}
