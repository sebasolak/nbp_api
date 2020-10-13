package com.example.apinbp.dao;

import com.example.apinbp.model.dtos.CurrencyDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("currencyDtoDao")
public interface CurrencyDTODao extends JpaRepository<CurrencyDTO, Long> {
}
