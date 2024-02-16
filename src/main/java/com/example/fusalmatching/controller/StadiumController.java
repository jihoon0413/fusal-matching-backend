package com.example.fusalmatching.controller;


import com.example.fusalmatching.dto.response.FieldResponseDto;
import com.example.fusalmatching.dto.response.StadiumResponseDto;
import com.example.fusalmatching.dto.response.StadiumReviewResponseDto;
import com.example.fusalmatching.dto.response.TeamReviewResponseDto;
import com.example.fusalmatching.service.StadiumService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/stadiums")
@RestController
public class StadiumController {

    private final StadiumService stadiumService;

    @GetMapping
    public List<StadiumResponseDto> getStadiums() {
        return stadiumService.getStadiumList();
    }

    @GetMapping("/reviews")
    public List<StadiumReviewResponseDto> getReviews(@RequestParam Long id) {
        return stadiumService.getReviewList(id);
    }

    @GetMapping("/fields")
    public List<FieldResponseDto> getFields(@RequestParam Long id, Date date, Time time) throws ParseException {

        return stadiumService.getFieldList(id, date, time);
    }




}
