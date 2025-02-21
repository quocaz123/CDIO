package com.example.CDIO.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.CDIO.domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}
