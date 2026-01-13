package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.payload.ResourceTypeNotFoundException;
import com.bezkoder.springjwt.service.StrategyRegistry;
import com.bezkoder.springjwt.strategy.IDRDataFetcherStrategy;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/finance")
public class FinanceController {

    private final Map<String, IDRDataFetcherStrategy> strategyMap;

    public FinanceController(StrategyRegistry registry) {
        this.strategyMap = registry.strategyMap(); //mengam bil nama resourceType dan  incstance classdari masing" strategy
    }

    @GetMapping("/data/{resourceType}")
    public List<Object> getData(@PathVariable String resourceType) {//resourceType = latest_idr_rates
        // Ambil strategy berdasarkan resourceType, jalankan method getData(), jika strategy tidak ditemukan → lempar exception khusus.
        return Optional.ofNullable(strategyMap.get(resourceType))
                .map(IDRDataFetcherStrategy::getData)
                .orElseThrow(() -> new ResourceTypeNotFoundException(resourceType)); 
    }
    // HistoricalIdrUsdStrategy, LatestIdrRatesStrategy, dan SupportedCurrenciesStrategy mengimplementasikan IDRDataFetcherStrategy

    //this.strategyMap: {
    //     historical_idr_usd = com.bezkoder.springjwt.strategy.HistoricalIdrUsdStrategy@4e642ee1,
    //     supported_currencies = com.bezkoder.springjwt.strategy.SupportedCurrenciesStrategy@2fd954f,
    //     latest_idr_rates = com.bezkoder.springjwt.strategy.LatestIdrRatesStrategy@29ebbdf4
    //   }
      
}
