package com.axur.challenge.DAO;

import com.axur.challenge.model.Whitelist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WhitelistDAO extends JpaRepository<Whitelist, Long> {
    Whitelist findByClient(String client);

    @Query("SELECT w FROM Whitelist w WHERE w.client = :client AND w.regex = :regex")
    Whitelist findByClientAndRegex(@Param("client") String client,@Param("regex") String regex);
}
