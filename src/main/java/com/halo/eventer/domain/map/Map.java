package com.halo.eventer.domain.map;

import com.halo.eventer.domain.duration_map.DurationMap;
import com.halo.eventer.domain.map.dto.map.MapCreateDto;
import com.halo.eventer.domain.map.enumtype.OperationTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Map {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String summary;

  @Column(columnDefinition = "varchar(2000)")
  private String content;

  private String location;

  private double latitude; // 위도
  private double longitude; // 경도

  private String operationHours;

  private String thumbnail;
  private String icon;

  private String buttonName;
  private String url;

  @Column(length = 1000)
  private String buttonImage;

  @Enumerated(EnumType.STRING)
  private OperationTime operationType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mapCategory_id")
  private MapCategory mapCategory;

  @OneToMany(mappedBy = "map", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
  private List<Menu> menus = new ArrayList<>();

  @OneToMany(
      mappedBy = "map",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<DurationMap> durationMaps = new ArrayList<>();

  @Builder
  public Map(MapCreateDto mapCreateDto, OperationTime operationType) {
    this.location = mapCreateDto.getLocation();
    this.content = mapCreateDto.getContent();
    this.name = mapCreateDto.getName();
    this.summary = mapCreateDto.getSummary();
    this.latitude = mapCreateDto.getLatitude();
    this.longitude = mapCreateDto.getLongitude();
    this.operationHours = mapCreateDto.getOperationHours();
    this.thumbnail = mapCreateDto.getThumbnail();
    this.operationType = operationType;
    this.icon = mapCreateDto.getIcon();
    this.buttonName = mapCreateDto.getButtonName();
    this.buttonImage = mapCreateDto.getButtonImage();
    this.url = mapCreateDto.getUrl();
  }

  public void setMap(MapCreateDto mapCreateDto) {
    this.location = mapCreateDto.getLocation();
    this.content = mapCreateDto.getContent();
    this.name = mapCreateDto.getName();
    this.summary = mapCreateDto.getSummary();
    this.latitude = mapCreateDto.getLatitude();
    this.longitude = mapCreateDto.getLongitude();
    this.operationHours = mapCreateDto.getOperationHours();
    this.thumbnail = mapCreateDto.getThumbnail();
    this.icon = mapCreateDto.getIcon();
    this.buttonName = mapCreateDto.getButtonName();
    this.buttonImage = mapCreateDto.getButtonImage();
    this.url = mapCreateDto.getUrl();
  }

  public void setMapCategory(MapCategory mapCategory) {
    this.mapCategory = mapCategory;
  }

  public void setDurationMaps(List<DurationMap> durationMaps) {
    this.durationMaps.addAll(durationMaps);
  }
}
