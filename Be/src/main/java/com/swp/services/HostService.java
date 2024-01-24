package com.swp.services;

import com.swp.entity.Host;
import com.swp.entity.User;
import com.swp.exception.EntityNotFoundException;
import com.swp.repositories.HostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class HostService {
    private final HostRepository hostRepository;

    public Host findByUser(User user) {
        return hostRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException(("Host not found for user " + user.getUsername()).getClass()));
    }
}
