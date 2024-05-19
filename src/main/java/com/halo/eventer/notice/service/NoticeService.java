package com.halo.eventer.notice.service;



import com.halo.eventer.common.ArticleType;
import com.halo.eventer.concert_info.ConcertInfo;
import com.halo.eventer.exception.common.NoDataInDatabaseException;
import com.halo.eventer.festival.Festival;
import com.halo.eventer.image.Image;
import com.halo.eventer.notice.Notice;
import com.halo.eventer.notice.dto.*;
import com.halo.eventer.festival.repository.FestivalRepository;
import com.halo.eventer.image.ImageRepository;
import com.halo.eventer.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final FestivalRepository festivalRepository;

    private final ImageRepository imageRepository;

    @Transactional
    public String registerNotice(NoticeReqDto noticeReqDto, Long id) throws NoDataInDatabaseException {
        Festival festival = festivalRepository.findById(id).orElseThrow(()-> new NoDataInDatabaseException("축제가 존재하지 않습니다."));
        Notice notice = new Notice(noticeReqDto);
        notice.setFestival(festival);
        notice.setImages(noticeReqDto.getImages().stream().map(Image::new).collect(Collectors.toList()));
        noticeRepository.save(notice);
        return "저장 완료";
    }

    @Transactional
    public List<GetAllNoticeResDto> inquireNotices(Long festivalId, ArticleType type) throws NoDataInDatabaseException{
        List<Notice> notices = noticeRepository.findAllByFestivalAndType(festivalRepository.findById(festivalId)
                .orElseThrow(() -> new NoDataInDatabaseException("공지사항이 존재하지 않습니다.")),type);

        return notices.stream().map(GetAllNoticeResDto::new).collect(Collectors.toList());
    }

    // 단일 게시글 조회
    @Transactional
    public GetNoticeResDto getNotice(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id + "에 해당하는 공지사항이 존재하지 않습니다."));

        return new GetNoticeResDto(notice);
    }

    //배너 등록,해제
    @Transactional
    public String changeBanner(Long noticeId, Boolean pick) throws NoDataInDatabaseException{
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(()->new NoDataInDatabaseException("실종자 정보가 존재하지 않습니다."));
        notice.setPicked(pick);
        return "반영 완료";
    }


    //공지사항 수정
    @Transactional
    public String updateNotice(Long noticeId, NoticeReqDto noticeReqDto) throws Exception {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new NotFoundException("존재하지 않습니다"));

        imageRepository.deleteByIdIn(noticeReqDto.getDeleteIds());
        notice.setAll(noticeReqDto);
        notice.setImages(noticeReqDto.getImages().stream().map(Image::new).collect(Collectors.toList()));
        return "수정완료";
    }


    @Transactional
    public String deleteNotice(Long noticeId) throws Exception{
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(()->new NotFoundException("존재하지 않습니다."));
        noticeRepository.delete(notice);
        return "삭제완료";
    }

    //등록된 배너 전체 조회
    public List<BannerResDto> getRegisteredBanner(Long festivalId) {
        return noticeRepository.findAllByPickedAndFestival_Id(true,festivalId).stream().map(BannerResDto::new).collect(Collectors.toList());
    }

    // 배너 순서 정하기
    @Transactional
    public String editBannerRank(List<BannerEditReqDto> bannerEditReqDtos) {
        List<Notice> notices = noticeRepository.findAllById(bannerEditReqDtos.stream().map(BannerEditReqDto::getNoticeId).collect(Collectors.toList()));

        for(Notice notice : notices){
            for(BannerEditReqDto b : bannerEditReqDtos) {
                if (b.getNoticeId() == notice.getId()) {
                    notice.setRank(b.getRank());
                    break;
                }
            }
        }

        return "수정완료";
    }
}

