package com.example.fusalmatching.dto.response;

import com.example.fusalmatching.domain.Manager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StadiumResponseDto {
    private Long id;
    private String stadiumName;
    private String address;
    private String tel;
    private String time;
    private String cost;
    private int fieldCount;
    private boolean noRest;
    private boolean parking;
    private boolean shower;
    private int evaluationCount;
    private int gpa;
    private List<ImageDto> images;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ImageDto {
        private String url;
    }

}
