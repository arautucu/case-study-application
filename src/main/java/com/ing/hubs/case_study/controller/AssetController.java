package com.ing.hubs.case_study.controller;

import com.ing.hubs.case_study.entities.Asset;
import com.ing.hubs.case_study.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @GetMapping("/{customerId}")
    public List<Asset> getAssetsByCustomerId(@PathVariable Long customerId) {
        return assetService.getAssetsByCustomerId(customerId);
    }

    @PostMapping("/{customerId}/update")
    public Asset updateAssetSize(@PathVariable Long customerId, @RequestParam String assetName, @RequestParam int size) {
        return assetService.updateAssetSize(customerId, assetName, size);
    }
}
