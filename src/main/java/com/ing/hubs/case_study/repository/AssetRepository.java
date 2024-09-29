package com.ing.hubs.case_study.repository;

import com.ing.hubs.case_study.entities.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    List<Asset> findByCustomerId(Long customerId);
}
