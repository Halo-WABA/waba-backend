package com.halo.eventer.domain.stamp.controller;

import com.halo.eventer.domain.stamp.dto.MissionInfoGetDto;
import com.halo.eventer.domain.stamp.dto.StampGetDto;
import com.halo.eventer.domain.stamp.dto.StampInfoGetListDto;
import com.halo.eventer.domain.stamp.dto.UserInfoGetDto;
import com.halo.eventer.domain.stamp.service.StampService;
import com.halo.eventer.domain.stamp.swagger.MissionInfoGetApi;
import com.halo.eventer.domain.stamp.swagger.StampGetApi;
import com.halo.eventer.domain.stamp.swagger.StampInfoListApi;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "스탬프 투어")
@RestController
@RequiredArgsConstructor
@RequestMapping("/stamp")
public class StampController {
    private final StampService stampService;

    /** 스탬프 처음 생성 및 조회 */
    @StampGetApi
    @PostMapping
    public StampGetDto getStampInfo(@RequestParam("festivalId") Long festivalId, @RequestBody UserInfoGetDto userInfoGetDto) {
        return stampService.getStampInfo(festivalId, userInfoGetDto);
    }

    @MissionInfoGetApi
    @GetMapping("/{uuid}")
    public MissionInfoGetDto getMissionInfo(@PathVariable String uuid) {
        return stampService.getMissionInfo(uuid);
    }

    @PatchMapping("/{uuid}/{missionId}")
    public String updateStamp(@PathVariable String uuid, @PathVariable int missionId) {
        return stampService.updateStamp(uuid, missionId);
    }

    @PatchMapping("/check/{uuid}")
    public String updateCheck(@PathVariable String uuid) {
        return stampService.checkFinish(uuid);
    }

    @StampInfoListApi
    @GetMapping
    public StampInfoGetListDto getStampInfoList(@RequestParam Long festivalId) {
        return stampService.getStampInfos(festivalId);
    }

    @DeleteMapping
    public String deleteStampByFestival(@RequestParam Long festivalId) {
        return stampService.deleteStamp(festivalId);
    }
}
