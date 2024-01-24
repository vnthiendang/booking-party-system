package com.swp.repositories;

import com.swp.entity.Host;
import com.swp.entity.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HostRepository extends JpaRepository<Host, Integer> {
//    Optional<Host> findByUser(User user);
}
