package com.example.wallet.controller;

import com.example.wallet.dto.ApiResponse;
import com.example.wallet.dto.WalletOperationRequest;
import com.example.wallet.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/wallet")
    public ResponseEntity<ApiResponse<Void>> processWalletOperation(@Valid @RequestBody WalletOperationRequest request) {
        walletService.processOperation(request);
        return ResponseEntity.ok(ApiResponse.success(null, "Wallet operation processed successfully"));
    }

    @GetMapping("/wallets/{walletId}")
    public ResponseEntity<ApiResponse<Map<String, BigDecimal>>> getWalletBalance(@PathVariable UUID walletId) {
        BigDecimal balance = walletService.getBalance(walletId);
        return ResponseEntity.ok(ApiResponse.success(Collections.singletonMap("balance", balance), "Balance retrieved successfully"));
    }
}
