package com.example.CDIO.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.CDIO.domain.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
