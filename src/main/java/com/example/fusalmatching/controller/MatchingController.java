package com.example.fusalmatching.controller;


import com.example.fusalmatching.dto.request.MatchingApplyRequestDto;
import com.example.fusalmatching.dto.request.MatchingCancelRequestDto;
import com.example.fusalmatching.dto.request.MatchingCreateRequestDto;
import com.example.fusalmatching.service.MatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/matching")
@RestController
public class MatchingController {
    private final MatchingService matchingService;

    @PostMapping("/create")
    public void createMatching(@RequestBody MatchingCreateRequestDto matchingCreateRequestDto) {
        matchingService.createMatching(matchingCreateRequestDto);

    }

    @PostMapping("/apply")
    public void applyMatching(@RequestBody MatchingApplyRequestDto matchingApplyRequestDto) {
        matchingService.applyMatching(matchingApplyRequestDto);
    }

    @PostMapping("/cancel")
    public void cancelMatching(@RequestBody MatchingCancelRequestDto matchingCancelRequestDto) {
        matchingService.cancelMatching(matchingCancelRequestDto);

    }
    



}
