package com.example.apinbp.dao;

import com.example.apinbp.model.ExchangeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("exchangeRequestDao")
public interface ExchangeRequestDao extends JpaRepository<ExchangeRequest, Long> {

    @Query(value = "Select * from operations WHERE requester_login like ?1", nativeQuery = true)
    List<String> selectOperationsByUserLogin(String login);

}
