package com.test.proj.services;

import com.test.proj.compositekeys.BillKey;
import com.test.proj.dto.BillDto;

import java.util.List;
import java.util.Map;

public interface BillService {
    BillDto createBill(BillDto billDto);
    List<BillDto> getAllBills();
    BillDto getBillById(BillKey id);
    BillDto updateBill(BillDto billDto, BillKey id);
    BillDto patchBill(Map<String, Object> updates, BillKey id);
    void deleteBillById(BillKey id);
}
