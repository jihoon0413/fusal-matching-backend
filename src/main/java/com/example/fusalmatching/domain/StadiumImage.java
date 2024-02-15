package com.example.fusalmatching.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Entity
public class StadiumImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageURl;

    @ManyToOne
    private Stadium stadium;


    protected StadiumImage() {};

    private StadiumImage(String imageURl) {
        this.imageURl =imageURl;

    }

    public StadiumImage of(String imageURl) {
        return new StadiumImage(imageURl);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StadiumImage that = (StadiumImage) o;
        return Objects.equals(id, that.id) && Objects.equals(imageURl, that.imageURl) && Objects.equals(stadium, that.stadium);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, imageURl, stadium);
    }
}
