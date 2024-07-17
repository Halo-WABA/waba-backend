package com.halo.eventer.domain.concert;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.halo.eventer.domain.duration.Duration;
import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.image.Image;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Concert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String thumbnail;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festival_id")
    @JsonBackReference
    private Festival festival;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "durationId")
    @JsonBackReference
    private Duration duration;


    @OneToMany(mappedBy = "concert", fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JsonManagedReference
    private List<Image> images = new ArrayList<>();

    public Concert(String thumbnail, Festival festival, Duration duration) {

        this.thumbnail = thumbnail;
        this.festival = festival;
        this.duration = duration;
    }

    public void setAll(String thumbnail, Duration duration){
        this.thumbnail = thumbnail;
        this.duration = duration;
    }

    public  void setImages(List<Image> images){
        this.images = images;
        images.forEach(o->o.setConcert(this));
    }



}
