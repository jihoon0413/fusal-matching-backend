package com.example.fusalmatching.service;

import com.example.fusalmatching.domain.*;
import com.example.fusalmatching.dto.response.FieldResponseDto;
import com.example.fusalmatching.dto.response.StadiumResponseDto;
import com.example.fusalmatching.dto.response.StadiumReviewResponseDto;
import com.example.fusalmatching.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StadiumService {

    private final StadiumRepository stadiumRepository;
    private final FieldRepository fieldRepository;
    private final StadiumReviewRepository stadiumReviewRepository;
    private final MatchingRecordRepository matchingRecordRepository;
    private final TeamMatchingRepository teamMatchingRepository;
    private final StadiumImageRepository stadiumImageRepository;

    @Transactional
    public List<StadiumResponseDto> getStadiumList() {
        return stadiumRepository.findAll()
                .stream().map(it -> entityToDto(it))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<StadiumReviewResponseDto> getReviewList(Long id) {

        List<StadiumReviewResponseDto> collect = stadiumReviewRepository.findAllByStadiumId(id)
                .stream().map(it -> entityToDtoReview(it))
                .collect(Collectors.toList());

        return collect;


    }

    private StadiumReviewResponseDto entityToDtoReview(StadiumReview stadiumReview) {
        var dto = new StadiumReviewResponseDto();
        dto.setGpa(stadiumReview.getGpa());
        dto.setReview(stadiumReview.getReview());
        dto.setWriter(stadiumReview.getCreatedBy());
        return dto;
    }




//    public void createFieldAuto(Long id) {
//
//        Optional<Stadium> stadium1 = stadiumRepository.findById(id);
//
//        Stadium stadium = stadium1.get();
//        int fieldCount1 = stadium.getFieldCount();
//        String cost = stadium.getCost();
//
//        SimpleDateFormat time1 = new SimpleDateFormat("HH:mm");
//        SimpleDateFormat date1 = new SimpleDateFormat("yy-MM-dd");
//        Time startTime = Time.valueOf(time1.format("18:00"));
//        Time endTime = Time.valueOf(time1.format("20:00"));
//
//
//
//
//        Field field = Field.of(stadium, java.sql.Date.valueOf("2022-04-09"), startTime, endTime, "50,000");
//
//        fieldRepository.save(field);
//
//    }




    private StadiumResponseDto entityToDto(Stadium stadium) {
        var dto = new StadiumResponseDto();
        dto.setId(stadium.getId());
        dto.setStadiumName(stadium.getStadiumName());
        dto.setAddress(stadium.getAddress());
        dto.setTel(stadium.getTel());
        dto.setTime(stadium.getTime());
        dto.setCost(stadium.getCost());
        dto.setFieldCount(stadium.getFieldCount());
        dto.setNoRest(stadium.isNoRest());
        dto.setParking(stadium.isParking());
        dto.setShower(stadium.isShower());
        dto.setEvaluationCount(stadium.getEvaluationCount());
        dto.setGpa(stadium.getGpa());

        List<StadiumResponseDto.ImageDto> images = new ArrayList<>();

        List<StadiumImage> images1 = stadiumImageRepository.findAllByStadiumId(stadium.getId());

        for(int i = 0 ; i < images1.size() ; i++){
            images.add(setImageDto(images1.get(i)));
        }

        dto.setImages(images);

        return dto;
    }

    public StadiumResponseDto.ImageDto setImageDto(StadiumImage stadiumImage) {
        var imageDto = new StadiumResponseDto.ImageDto();
        imageDto.setUrl(stadiumImage.getImageURl());

        return imageDto;
    }


    public List<FieldResponseDto> getFieldList(Long id, Date date, Time time) {

        List<FieldResponseDto> collect = fieldRepository.findAllByStadiumId(id)
                        .stream().map(it -> compareDate(it, date, time))
                        .collect(Collectors.toList());

        return collect;

    }

    public FieldResponseDto compareDate(Field field, Date date, Time time) {

        if(Objects.equals(String.valueOf(date), String.valueOf(field.getMatchingDate())) && Objects.equals(String.valueOf(time), String.valueOf(field.getStartTime()))) {
            var dto =new FieldResponseDto();
            dto.setId(field.getId());
            dto.setStadiumId(field.getStadium().getId());
            dto.setStadiumName(field.getStadium().getStadiumName());
            dto.setMatchingDate(String.valueOf(field.getMatchingDate()));
            dto.setStartTime(String.valueOf(field.getStartTime()));
            dto.setEndTime(String.valueOf(field.getEndTime()));
            dto.setFieldNum(field.getFieldNum());


            if(matchingRecordRepository.findByField_Id(field.getId()).isPresent()){
                Optional<MatchingRecord> matchingRecord1 = matchingRecordRepository.findByField_Id(field.getId());
                MatchingRecord matchingRecord = matchingRecord1.get();
                dto.setMatchingId(matchingRecord.getId());
                dto.setConfirm(matchingRecord.isConfirm());
                dto.setAllRental(matchingRecord.isAllRental());

                List<FieldResponseDto.TeamDto> teams = new ArrayList<>();

                List<TeamMatching> teamMatching1 = teamMatchingRepository.findAllByMatchingRecord_Id(matchingRecord.getId());

                for(int i = 0 ; i < teamMatching1.size() ; i++) {
                    teams.add(setTeamDto(teamMatching1.get(i)));
                }
                dto.setTeam(teams);
            }


            return dto;
        }
        return null;
    }

    public FieldResponseDto getFieldResponseDto(Field field, Long matchingId) {


            var dto =new FieldResponseDto();
            dto.setId(field.getId());
            dto.setStadiumId(field.getStadium().getId());
            dto.setStadiumName(field.getStadium().getStadiumName());
            dto.setMatchingDate(String.valueOf(field.getMatchingDate()));
            dto.setStartTime(String.valueOf(field.getStartTime()));
            dto.setEndTime(String.valueOf(field.getEndTime()));
            dto.setFieldNum(field.getFieldNum());
            dto.setMatchingId(matchingId);
            dto.setConfirm(matchingRecordRepository.findById(matchingId).get().isConfirm());
            dto.setAllRental(matchingRecordRepository.findById(matchingId).get().isAllRental());

            List<FieldResponseDto.TeamDto> teams = new ArrayList<>();
            List<TeamMatching> teamMatching1 = teamMatchingRepository.findAllByMatchingRecord_Id(matchingId);

            for(int i = 0 ; i < teamMatching1.size() ; i++) {
                teams.add(setTeamDto(teamMatching1.get(i)));
            }
            dto.setTeam(teams);



            return dto;
    }


    public FieldResponseDto.TeamDto setTeamDto(TeamMatching teamMatching) {
        var teamDto = new FieldResponseDto.TeamDto();
        Team team = teamMatching.getTeam();
                teamDto.setId(String.valueOf(team.getId()));
                teamDto.setTeamName(team.getTeamName());
                teamDto.setImageUrl(team.getImgUrl());
                teamDto.setManner(team.getManner());
                teamDto.setSkill(team.getSkill());
                teamDto.setTeamMatchingId(teamMatching.getId());
                teamDto.setEvalOpposite(teamMatching.isEvalOpposite());
                teamDto.setEvalStadium(teamMatching.isEvalStadium());
        return teamDto;
    }
}
