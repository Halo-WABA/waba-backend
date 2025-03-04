package com.halo.eventer.domain.notice;

import com.halo.eventer.domain.festival.Festival;
import com.halo.eventer.domain.image.Image;
import com.halo.eventer.domain.notice.dto.NoticeRegisterDto;
import com.halo.eventer.global.common.ArticleType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Notice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String tag;
  private String title;
  private String writer;

  @Column(columnDefinition = "varchar(3000)")
  private String content;

  private String thumbnail;

  private boolean picked;

  @Enumerated(EnumType.STRING)
  private ArticleType type;

  @OneToMany(
      mappedBy = "notice",
      fetch = FetchType.LAZY,
      cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
  private List<Image> images = new ArrayList<>();

  @CreatedDate private LocalDateTime updateTime;

  private Integer bannerRank;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "festival_id")
  private Festival festival;

  public Notice(NoticeRegisterDto n) {
    this.title = n.getTitle();
    this.content = n.getContent();
    this.thumbnail = n.getThumbnail();
    this.type = n.getType();
    this.picked = false;
    this.tag = n.getIndex();
    this.writer = n.getWriter();
    this.bannerRank = 11;
  }

  public void setFestival(Festival festival) {
    this.festival = festival;
  }

  public void setPicked(boolean picked) {
    if (picked == false) {
      this.bannerRank = 11;
    }
    this.picked = picked;
  }

  public void setAll(NoticeRegisterDto n) {
    this.title = n.getTitle();
    this.content = n.getContent();
    this.thumbnail = n.getThumbnail();
    this.tag = n.getIndex();
    this.writer = n.getWriter();
    this.type = n.getType();
  }

  public void setRank(Integer rank) {
    this.bannerRank = rank;
  }

  public void setImages(List<Image> images) {
    this.images = images;
    images.forEach(o -> o.setNotice(this));
  }
}
