package com.test.proj.services.implementation;

import com.test.proj.compositekeys.BillKey;
import com.test.proj.dto.BillDto;
import com.test.proj.entities.Bill;
import com.test.proj.exception.ResourceNotFoundException;
import com.test.proj.repository.BillRepository;
import com.test.proj.services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BillImpl implements BillService {
    private final BillRepository billRepository;

    @Autowired
    public BillImpl(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @Override
    public BillDto createBill(BillDto billDto) {
        Bill bill = mapToEntity(billDto);
        Bill newBill = billRepository.save(bill);
        return mapToDto(newBill);
    }

    @Override
    public List<BillDto> getAllBills() {
        List<Bill> bills = billRepository.findAll();
        return bills.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public BillDto getBillById(BillKey id) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bill", "id", id));
        return mapToDto(bill);
    }

    @Override
    public BillDto updateBill(BillDto billDto, BillKey id) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bill", "id", id));

        bill.setAmount(billDto.getAmount());
        bill.setIssue_date(billDto.getIssue_date());
        bill.setDue_date(billDto.getDue_date());

        Bill updatedBill = billRepository.save(bill);
        return mapToDto(updatedBill);
    }

    @Override
    public BillDto patchBill(Map<String, Object> updates, BillKey id) {
        BillDto billDto = getBillById(id);
        updates.forEach((key, value) -> {
            switch (key) {
                case "amount":
                    billDto.setAmount((Float) value);
                    break;
                case "issue_date":
                    billDto.setIssue_date((Timestamp) value);
                    break;
                case "due_date":
                    billDto.setDue_date((Timestamp) value);
                    break;
                default:
                    throw new RuntimeException("Invalid field: " + key);
            }
        });
        return updateBill(billDto, id);
    }

    @Override
    public void deleteBillById(BillKey id) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bill", "id", id));
        billRepository.delete(bill);
    }

    private BillDto mapToDto(Bill bill) {
        BillDto billDto = new BillDto();
        billDto.setBill_id(bill.getId().getBill_id());
        billDto.setReservation_id(bill.getId().getReservation_id());
        billDto.setAmount(bill.getAmount());
        billDto.setIssue_date(bill.getIssue_date());
        billDto.setDue_date(bill.getDue_date());
        return billDto;
    }

    private Bill mapToEntity(BillDto billDto) {
        Bill bill = new Bill();
        BillKey billKey = new BillKey(billDto.getBill_id(), billDto.getReservation_id());
        bill.setId(billKey);
        bill.setAmount(billDto.getAmount());
        bill.setIssue_date(billDto.getIssue_date());
        bill.setDue_date(billDto.getDue_date());
        return bill;
    }
}
