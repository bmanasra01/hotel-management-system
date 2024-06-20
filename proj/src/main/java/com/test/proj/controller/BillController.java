package com.test.proj.controller;

import com.test.proj.dto.BillDto;
import com.test.proj.services.BillService;
import com.test.proj.compositekeys.BillKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/bills")
public class BillController {

    private final BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }

    @PostMapping
    public ResponseEntity<BillDto> createBill(@RequestBody BillDto billDto) {
        BillDto createdBill = billService.createBill(billDto);
        return ResponseEntity.ok(createdBill);
    }

    @GetMapping
    public ResponseEntity<List<BillDto>> getAllBills() {
        List<BillDto> bills = billService.getAllBills();
        return ResponseEntity.ok(bills);
    }

    @GetMapping("/{billId}/{reservationId}")
    public ResponseEntity<BillDto> getBillById(@PathVariable long billId, @PathVariable long reservationId) {
        BillKey billKey = new BillKey(billId, reservationId);
        BillDto billDto = billService.getBillById(billKey);
        return ResponseEntity.ok(billDto);
    }

    @PutMapping("/{billId}/{reservationId}")
    public ResponseEntity<BillDto> updateBill(@RequestBody BillDto billDto, @PathVariable long billId, @PathVariable long reservationId) {
        BillKey billKey = new BillKey(billId, reservationId);
        BillDto updatedBill = billService.updateBill(billDto, billKey);
        return ResponseEntity.ok(updatedBill);
    }

    @PatchMapping("/{billId}/{reservationId}")
    public ResponseEntity<BillDto> patchBill(@RequestBody Map<String, Object> updates, @PathVariable long billId, @PathVariable long reservationId) {
        BillKey billKey = new BillKey(billId, reservationId);
        BillDto updatedBill = billService.patchBill(updates, billKey);
        return ResponseEntity.ok(updatedBill);
    }

    @DeleteMapping("/{billId}/{reservationId}")
    public ResponseEntity<Void> deleteBill(@PathVariable long billId, @PathVariable long reservationId) {
        BillKey billKey = new BillKey(billId, reservationId);
        billService.deleteBillById(billKey);
        return ResponseEntity.noContent().build();
    }
}
