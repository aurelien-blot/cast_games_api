package com.castruche.cast_games_api.dao;

import com.castruche.cast_games_api.entity.Contact;
import com.castruche.cast_games_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findByContactIdAndActiveIsFalseAndBlockedIsFalseAndRejectedIsFalse(Long contactId);
}
