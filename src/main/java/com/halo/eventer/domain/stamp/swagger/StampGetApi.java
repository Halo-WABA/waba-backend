package com.halo.eventer.domain.stamp.swagger;

import com.halo.eventer.domain.stamp.dto.StampGetDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "유저 스탬프 정보 조회", description = "처음 생성 및 조회하는 경우, user name, phone으로 조회")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = StampGetDto.class)))
})
public @interface StampGetApi {
}
