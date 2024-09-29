package com.ing.hubs.case_study.service.impl;

import com.ing.hubs.case_study.repository.AssetRepository;
import com.ing.hubs.case_study.service.AssetService;
import com.ing.hubs.case_study.entities.Asset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetServiceImpl implements AssetService {

    @Autowired
    private AssetRepository assetRepository;

    @Override
    public List<Asset> getAssetsByCustomerId(Long customerId) {
        return assetRepository.findByCustomerId(customerId);
    }

    @Override
    public Asset updateAssetSize(Long customerId, String assetName, int size) {
        List<Asset> assets = assetRepository.findByCustomerId(customerId);
        for (Asset asset : assets) {
            if (asset.getAssetName().equals(assetName)) {
                asset.setUsableSize(asset.getUsableSize() + size);
                return assetRepository.save(asset);
            }
        }
        throw new IllegalArgumentException("Asset not found.");
    }
}
