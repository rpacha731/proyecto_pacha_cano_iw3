package com.iua.iw3.proyecto.pacha_cano.model.accounts;

import com.iua.iw3.proyecto.pacha_cano.model.accounts.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByEmail(String email);

}
