package com.ing.hubs.case_study.service.impl;

import com.ing.hubs.case_study.entities.Asset;
import com.ing.hubs.case_study.repository.AssetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AssetServiceTest {

    @Mock
    private AssetRepository assetRepository;

    @InjectMocks
    private AssetServiceImpl assetService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAssetsByCustomerId() {
        Asset asset1 = new Asset(1L, 1L, "Stock A", 100, 100);
        Asset asset2 = new Asset(2L, 1L, "Stock B", 50, 50);

        when(assetRepository.findByCustomerId(1L)).thenReturn(Arrays.asList(asset1, asset2));

        List<Asset> assets = assetService.getAssetsByCustomerId(1L);
        assertEquals(2, assets.size());
        verify(assetRepository, times(1)).findByCustomerId(1L);
    }

    @Test
    void testUpdateAssetSize() {
        Asset asset = new Asset(1L, 1L, "Stock A", 100, 50);
        when(assetRepository.findByCustomerId(1L)).thenReturn(Arrays.asList(asset));
        when(assetRepository.save(any(Asset.class))).thenReturn(asset);

        Asset updatedAsset = assetService.updateAssetSize(1L, "Stock A", 20);
        assertEquals(70, updatedAsset.getUsableSize());
        verify(assetRepository, times(1)).save(asset);
    }

    @Test
    void testUpdateAssetSize_AssetNotFound() {
        when(assetRepository.findByCustomerId(1L)).thenReturn(Arrays.asList());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            assetService.updateAssetSize(1L, "NonExistentStock", 20);
        });

        assertEquals("Asset not found.", exception.getMessage());
        verify(assetRepository, never()).save(any(Asset.class));
    }
}
