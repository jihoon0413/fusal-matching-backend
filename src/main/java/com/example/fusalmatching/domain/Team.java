package com.example.fusalmatching.domain;

import com.example.fusalmatching.dto.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@ToString
@Table(indexes = {
        @Index(columnList = "teamName")
})
@Entity
public class Team extends AuditingFields{



    @Id
    @Setter
    private String id;

    @Setter private String password;

    @Setter private String teamName;

    @OneToMany(mappedBy = "team")
    @ToString.Exclude
    private final Set<TeamReview> teamReviews = new LinkedHashSet<>();


    @OneToMany(mappedBy = "team")
    @ToString.Exclude
    private final Set<TeamMatching> teamMatching = new LinkedHashSet<>();

    @Setter private String captainName;

    @Setter private String tel;

    @Setter private String email;

    @Setter private String imgUrl;

    @Setter private int manner;

    @Setter private int skill;

    @Setter private int evaluationCount;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Role userRole = Role.USER;




    protected Team() {};

    private Team(String teamId, String teamName, String password, String captainName, String tel, String email) {
        this.id = teamId;
        this.teamName = teamName;
        this.password = password;
        this.captainName = captainName;
        this.tel = tel;
        this.email = email;
//        hasRole.add(String.valueOf(Role.USER));
    }

    public static Team of(String teamId, String teamName, String password,  String captainName, String tel, String email) {
        return new Team(teamId, teamName, password, captainName, tel, email);
    }


//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Team team = (Team) o;
//        return manner == team.manner && skill == team.skill && evaluationCount == team.evaluationCount && Objects.equals(id, team.id) && Objects.equals(password, team.password) && Objects.equals(teamName, team.teamName) && Objects.equals(teamReviews, team.teamReviews) && Objects.equals(teamMatching, team.teamMatching) && Objects.equals(captainName, team.captainName) && Objects.equals(tel, team.tel) && Objects.equals(email, team.email) && Objects.equals(imgUrl, team.imgUrl);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, password, teamName, teamReviews, teamMatching, captainName, tel, email, imgUrl, manner, skill, evaluationCount);
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return manner == team.manner && skill == team.skill && evaluationCount == team.evaluationCount && Objects.equals(id, team.id) && Objects.equals(password, team.password) && Objects.equals(teamName, team.teamName) && Objects.equals(teamReviews, team.teamReviews) && Objects.equals(teamMatching, team.teamMatching) && Objects.equals(captainName, team.captainName) && Objects.equals(tel, team.tel) && Objects.equals(email, team.email) && Objects.equals(imgUrl, team.imgUrl) && userRole == team.userRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password, teamName, teamReviews, teamMatching, captainName, tel, email, imgUrl, manner, skill, evaluationCount, userRole);
    }
}
