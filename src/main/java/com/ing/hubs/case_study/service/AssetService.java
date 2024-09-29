package com.ing.hubs.case_study.service;

import com.ing.hubs.case_study.entities.Asset;

import java.util.List;

public interface AssetService {

    List<Asset> getAssetsByCustomerId(Long customerId);
    Asset updateAssetSize(Long customerId, String assetName, int size);
}
