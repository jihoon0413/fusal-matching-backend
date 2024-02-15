package com.example.fusalmatching.service;

import com.example.fusalmatching.config.jwt.JwtToken;
import com.example.fusalmatching.config.jwt.JwtTokenProvider;
import com.example.fusalmatching.domain.Field;
import com.example.fusalmatching.domain.Team;
import com.example.fusalmatching.domain.TeamMatching;
import com.example.fusalmatching.domain.TeamReview;
import com.example.fusalmatching.dto.request.CheckRandomNumDto;
import com.example.fusalmatching.dto.request.CheckRequestDto;
import com.example.fusalmatching.dto.request.TeamSignDto;
import com.example.fusalmatching.dto.response.FieldResponseDto;
import com.example.fusalmatching.dto.response.TeamResponseDto;
import com.example.fusalmatching.dto.response.TeamReviewResponseDto;
import com.example.fusalmatching.repository.MatchingRecordRepository;
import com.example.fusalmatching.repository.TeamMatchingRepository;
import com.example.fusalmatching.repository.TeamRepository;
import com.example.fusalmatching.repository.TeamReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class TeamService {

    private final TeamRepository teamRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final TeamReviewRepository teamReviewRepository;
    private final TeamMatchingRepository teamMatchingRepository;
    private final MatchingRecordRepository matchingRecordRepository;
    private final StadiumService stadiumService;

    @Transactional
    public void createTeam(TeamSignDto teamSignDto) {
        
        String hashedPassword = passwordEncoder.encode(teamSignDto.getPassword());           //TODO: 아이디, 닉네임 중복확인 해야함
        Team newTeam = Team.of(teamSignDto.getId(), teamSignDto.getTeamName(), hashedPassword, teamSignDto.getCaptainName(), teamSignDto.getTel(), teamSignDto.getEmail());
        teamRepository.save(newTeam);

    }


    @Transactional
    public JwtToken login(String id, String password) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 검증된 인증 정보로 JWT 토큰 생성

        return jwtTokenProvider.generateToken(authentication, id);

    }

    @Transactional
    public TeamResponseDto getTeam(String id) {
            Optional<Team> collect = teamRepository.findById(id);
            Team team = collect.get();
            return entityToDto(team);

    }

    private TeamResponseDto entityToDto(Team team) {
        var dto = new TeamResponseDto();
        dto.setId(team.getId());
        dto.setTeamName(team.getTeamName());
        dto.setCaptainName(team.getCaptainName());
        dto.setTel(team.getTel());
        dto.setImageUrl(team.getImgUrl());
        dto.setManner(team.getManner());
        dto.setSkill(team.getSkill());
        dto.setMatchingRecordList(getMatchingRecordList(team.getId()));
        return dto;
    }

    @Transactional
    private List<TeamResponseDto.MatchingRecordListDto> getMatchingRecordList(String id) {

        List<TeamMatching> teamMatchingList = teamMatchingRepository.findAllByTeam_Id(id);

        List<TeamResponseDto.MatchingRecordListDto> matchingRecordDtoList = new ArrayList<>();


        for (TeamMatching teamMatching : teamMatchingList) {
            var matchingRecordDto = new TeamResponseDto.MatchingRecordListDto();
            matchingRecordDto.setMatchingRecord(getMatchingRecordDto(teamMatching.getMatchingRecord().getField(), teamMatching.getMatchingRecord().getId(), id));
            matchingRecordDtoList.add(matchingRecordDto);
        }
        return matchingRecordDtoList;
    }


    public TeamResponseDto.MatchingRecordDto getMatchingRecordDto(Field field, Long matchingId, String id) {


        var dto =new TeamResponseDto.MatchingRecordDto();
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

        for (TeamMatching teamMatching : teamMatching1) {
            if (Objects.equals(id, teamMatching.getTeam().getId())) {
                TeamResponseDto.MyTeamDto myTeamDto = new TeamResponseDto.MyTeamDto();
                myTeamDto.setId(teamMatching.getTeam().getId());
                myTeamDto.setTeamMatchingId(teamMatching.getId());
                myTeamDto.setEvalOpposite(teamMatching.isEvalOpposite());
                myTeamDto.setEvalStadium(teamMatching.isEvalStadium());

                dto.setMyTeamDto(myTeamDto);
            } else {
                TeamResponseDto.OppositeTeamDto oppositeTeamDto = new TeamResponseDto.OppositeTeamDto();
                oppositeTeamDto.setId(teamMatching.getTeam().getId());
                oppositeTeamDto.setTeamName(teamMatching.getTeam().getTeamName());
                oppositeTeamDto.setImageUrl(teamMatching.getTeam().getImgUrl());
                oppositeTeamDto.setManner(teamMatching.getTeam().getManner());
                oppositeTeamDto.setSkill(teamMatching.getTeam().getSkill());
                dto.setOppositeTeamDto(oppositeTeamDto);
            }

            //            teams.add(setTeamDto(teamMatching1.get(i)));
        }


        return dto;
    }


    @Transactional
    public List<TeamReviewResponseDto> getReviewList(String id) {

        List<TeamReviewResponseDto> collect = teamReviewRepository.findAllByTeamId(id)
                .stream().map(it -> entityToDto(it))
                .collect(Collectors.toList());

        return collect;


    }

    private TeamReviewResponseDto entityToDto(TeamReview teamReview) {
        var dto = new TeamReviewResponseDto();
        dto.setManner(teamReview.getManner());
        dto.setSkill(teamReview.getSkill());
        return dto;
    }

    @Transactional
    public boolean checkId(CheckRequestDto id) {
        return this.teamRepository.findById(id.getIdORNick()).isEmpty();
    }
    
    @Transactional
    public boolean checkName(CheckRequestDto id) {
        return this.teamRepository.findByTeamName(id.getIdORNick()).isEmpty();
    }

    public boolean checkNum(CheckRandomNumDto randomNum) {

        return passwordEncoder.matches(randomNum.getUserWrite(), randomNum.getHashedNum());


    }
}
