package com.example.fusalmatching.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@ToString
@Getter
@Entity
public class Stadium extends AuditingFields{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Manager manager;

    @ToString.Exclude
    @OneToMany(mappedBy = "stadium")
    private final Set<Field> fields = new LinkedHashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "stadium")
    private final Set<StadiumReview> stadiumReviews = new LinkedHashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "stadium")
    private final Set<MatchingRecord> matchingRecords = new LinkedHashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "stadium")
    private final Set<StadiumImage> stadiumImages = new LinkedHashSet<>();

    @OneToOne
    private TimeTable timeTable;

    @Setter private String stadiumName;
    @Setter private String address;
    @Setter private String tel;
    @Setter private String time;
    @Setter private String cost;
    @Setter private int fieldCount;
    @Setter private boolean noRest;
    @Setter private boolean parking;
    @Setter private boolean shower;
    @Setter private int evaluationCount;
    @Setter private int gpa;






    protected Stadium() {}

    private Stadium(String stadiumName, String address, String tel, String time, int fieldCount, String cost, boolean noRest, boolean parking, boolean shower) {
        this.tel = tel;
        this.time = time;
        this.fieldCount =fieldCount;
        this.cost = cost;
        this.noRest = noRest;
        this.parking = parking;
        this.shower = shower;
    }

    public static Stadium of(String stadiumName, String address, String tel, String time, int fieldCount, String cost, boolean noRest, boolean parking, boolean shower) {
        return new Stadium(stadiumName, address, tel, time, fieldCount, cost, noRest, parking, shower);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stadium stadium = (Stadium) o;
        return fieldCount == stadium.fieldCount && noRest == stadium.noRest && parking == stadium.parking && shower == stadium.shower && evaluationCount == stadium.evaluationCount && gpa == stadium.gpa && Objects.equals(id, stadium.id) && Objects.equals(manager, stadium.manager) && Objects.equals(fields, stadium.fields) && Objects.equals(stadiumReviews, stadium.stadiumReviews) && Objects.equals(matchingRecords, stadium.matchingRecords) && Objects.equals(stadiumImages, stadium.stadiumImages) && Objects.equals(stadiumName, stadium.stadiumName) && Objects.equals(address, stadium.address) && Objects.equals(tel, stadium.tel) && Objects.equals(time, stadium.time) && Objects.equals(cost, stadium.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, manager, fields, stadiumReviews, matchingRecords, stadiumImages, stadiumName, address, tel, time, cost, fieldCount, noRest, parking, shower, evaluationCount, gpa);
    }
}
