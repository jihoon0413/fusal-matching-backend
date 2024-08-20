package com.example.fusalmatching.service;

import com.example.fusalmatching.config.jwt.JwtToken;
import com.example.fusalmatching.config.jwt.JwtTokenProvider;
import com.example.fusalmatching.domain.Team;
import com.example.fusalmatching.domain.TeamMatching;
import com.example.fusalmatching.domain.TeamReview;
import com.example.fusalmatching.dto.request.CheckRandomNumDto;
import com.example.fusalmatching.dto.request.CheckRequestDto;
import com.example.fusalmatching.dto.request.TeamModifyProfileRequestDto;
import com.example.fusalmatching.dto.request.TeamSignRequestDto;
import com.example.fusalmatching.dto.response.*;
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

import java.util.*;
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
    public void createTeam(TeamSignRequestDto teamSignDto) throws Exception {
        try {
            String hashedPassword = passwordEncoder.encode(teamSignDto.getPassword());

                Team newTeam = Team.of(teamSignDto.getId(), teamSignDto.getTeamName(), hashedPassword, teamSignDto.getCaptainName(), teamSignDto.getTel(), teamSignDto.getEmail());
                teamRepository.save(newTeam);

        } catch (Exception e) {
            throw new Exception("에러 발생");
        }
    }
    @Transactional
    public void modifyTeam(TeamModifyProfileRequestDto modifiedProfile) {

        Optional<Team> optional = teamRepository.findById(modifiedProfile.getId());
        Team team = optional.get();

        StringTokenizer st = new StringTokenizer(modifiedProfile.getModifiedInfo(), "/");
        int count = st.countTokens();

        for(int i = 0 ; i < count ; i++) {
            switch (st.nextToken()) {
//                case "id":
//                    team.setId(modifiedProfile.getModifiedId());
//                    break;
                case "password":
                    String hashedPassword = passwordEncoder.encode(modifiedProfile.getPassword());
                    team.setPassword(hashedPassword);
                    break;
                case "teamName":
                    team.setTeamName(modifiedProfile.getTeamName());
                    break;
                case "captainName":
                    team.setCaptainName(modifiedProfile.getCaptainName());
                    break;
                case "tel":
                    team.setTel(modifiedProfile.getTel());
                    break;
                case "email":
                    team.setEmail(modifiedProfile.getEmail());
                    break;
                case "imgUrl":
                    team.setImgUrl(modifiedProfile.getImgUrl());
                    break;
            }
        }

        teamRepository.save(team);
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


    //TODO dto 바꾸기
    @Transactional
    public MyPageDto getMypageDto(String id) {
//        Optional<Team> collect = teamRepository.findById(id);
        Team team = teamRepository.findById(id).get();

        List<MatchingRecordDto> matchingRecordTempList = new ArrayList<>();

        List<TeamMatching> teamMatchingList = teamMatchingRepository.findAllByTeam_Id(team.getId());

        for (TeamMatching teamMatching : teamMatchingList) {
            Long matchingId = teamMatching.getMatchingRecord().getId();
            boolean confirm = matchingRecordRepository.findById(matchingId).get().isConfirm();
            boolean allRental = matchingRecordRepository.findById(matchingId).get().isAllRental();

            List<TeamMatching> teamMatching1 = teamMatchingRepository.findAllByMatchingRecord_Id(matchingId);

            MatchingRecordDto matchingRecordTemp = MatchingRecordDto.from(teamMatching.getMatchingRecord().getField(), matchingId, id, confirm, allRental, teamMatching1);
            matchingRecordTempList.add(matchingRecordTemp);
        }

        return MyPageDto.from(team,matchingRecordTempList);
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
        return teamRepository.findById(id.getIdORNick()).isEmpty();
    }
    
    @Transactional
    public boolean checkName(CheckRequestDto id) {
        return teamRepository.findByTeamName(id.getIdORNick()).isEmpty();
    }

    public boolean checkNum(CheckRandomNumDto randomNum) {

        return passwordEncoder.matches(randomNum.getUserWrite(), randomNum.getHashedNum());


    }


}
