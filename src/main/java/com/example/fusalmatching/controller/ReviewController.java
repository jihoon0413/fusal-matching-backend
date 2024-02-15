package com.example.fusalmatching.controller;


import com.example.fusalmatching.dto.request.StadiumReviewWriteRequestDto;
import com.example.fusalmatching.dto.request.TeamReviewWriteRequestDto;
import com.example.fusalmatching.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/write-team")
    public void writeTeamReview(@RequestBody TeamReviewWriteRequestDto teamReviewWriteRequestDto) {
        reviewService.writeTeamReview(teamReviewWriteRequestDto);


    }

    @PostMapping("/write-stadium")
    public void writeStadiumReview(@RequestBody StadiumReviewWriteRequestDto stadiumReviewWriteRequestDto) {
        reviewService.writeStadiumReview(stadiumReviewWriteRequestDto);
    }

}
