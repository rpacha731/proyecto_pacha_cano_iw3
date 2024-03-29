package com.iua.iw3.proyecto.pacha_cano.security.authtoken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, String> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM auth_token WHERE to_date < ?", nativeQuery = true)
    void purgeToDate(Date toDate);

    Optional<AuthToken> findByUsername(String username);
}
