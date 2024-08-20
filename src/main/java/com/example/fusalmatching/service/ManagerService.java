package com.example.fusalmatching.service;

import com.example.fusalmatching.config.jwt.JwtToken;
import com.example.fusalmatching.config.jwt.JwtTokenProvider;
import com.example.fusalmatching.domain.Field;
import com.example.fusalmatching.domain.Manager;
import com.example.fusalmatching.domain.MatchingRecord;
import com.example.fusalmatching.domain.Stadium;
import com.example.fusalmatching.dto.request.ManagerSignRequestDto;
import com.example.fusalmatching.dto.response.ManagerResponseDto;
import com.example.fusalmatching.repository.FieldRepository;
import com.example.fusalmatching.repository.ManagerRepository;
import com.example.fusalmatching.repository.MatchingRecordRepository;
import com.example.fusalmatching.repository.StadiumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final StadiumRepository stadiumRepository;
    private final MatchingRecordRepository matchingRecordRepository;
    private final FieldRepository fieldRepository;

    @Transactional
    public void createManager(ManagerSignRequestDto managerSignDto) throws Exception {

        try {
            String hashedPassword = passwordEncoder.encode(managerSignDto.getPassword());

            Manager manager = Manager.of(managerSignDto.getId(), hashedPassword, managerSignDto.getTel(), managerSignDto.getEmail());

            managerRepository.save(manager);
        } catch (Exception e) {
            throw new Exception("에러 발생");
        }

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
    public ManagerResponseDto getMyPage(String id) {

        Optional<Manager> collect = managerRepository.findById(id);
        Manager manager = collect.get();




        return null;
    }

    private ManagerResponseDto entityToDto(Manager manager) {

        List<Stadium> stadiumIds = stadiumRepository.findAllByManagerId(manager.getId());

        var dto = new ManagerResponseDto();
        dto.setId(manager.getId());
        dto.setTel(manager.getTel());
        dto.setStadiums(getstadiumList(manager.getId()));
        dto.setMatchingRecords(getMatchingRecordList(manager.getId()));
        return dto;
    }

    private List<ManagerResponseDto.Stadium> getstadiumList(String managerId) {

        List<ManagerResponseDto.Stadium> stadiumLists = new ArrayList<>();

        List<Stadium> stadiums = stadiumRepository.findAllByManagerId(managerId);

        for(Stadium stadium : stadiums) {
            var stadiumDto = new ManagerResponseDto.Stadium();
            stadiumDto.setId(stadium.getId());
            stadiumDto.setStadiumName(stadium.getStadiumName());
            stadiumDto.setAddress(stadium.getAddress());
            stadiumDto.setTel(stadium.getTel());
            stadiumDto.setFieldCount(stadium.getFieldCount());
            stadiumDto.setNoRest(stadium.isNoRest());
            stadiumDto.setParking(stadium.isParking());
            stadiumDto.setShower(stadium.isShower());
            stadiumDto.setGpa(stadium.getGpa());

            stadiumLists.add(stadiumDto);
        }

        return stadiumLists;
    }

    private List<ManagerResponseDto.MatchingRecordDto> getMatchingRecordList(String managerId) {

        List<ManagerResponseDto.MatchingRecordDto> matchingRecordDtoList = new ArrayList<>();

        List<Long> stadiumIds = new ArrayList<>();

        List<Stadium> stadiums = stadiumRepository.findAllByManagerId(managerId);

        for(Stadium stadium : stadiums) {

            Long stadiumId = stadium.getId();
            String stadiumName = stadium.getStadiumName();

            List<MatchingRecord> matchingRecordList = matchingRecordRepository.findAllByStadiumId(stadiumId)
                    .stream()
                    .filter(it -> !it.isConfirm())
                    .toList();

            for (MatchingRecord matchingRecord : matchingRecordList) {
                var matchingRecordDto = new ManagerResponseDto.MatchingRecordDto();

                Field field = fieldRepository.getReferenceById(matchingRecord.getField().getId());

                matchingRecordDto.setId(matchingRecord.getId());
                matchingRecordDto.setStadiumName(stadiumName);
                matchingRecordDto.setMatchingDate(String.valueOf(field.getMatchingDate()));
                matchingRecordDto.setFieldNum(field.getFieldNum());
                matchingRecordDto.setAllRental(matchingRecord.isAllRental());

                matchingRecordDtoList.add(matchingRecordDto);
            }

        }

        return matchingRecordDtoList;
    }

}
