package com.halo.eventer.domain.map.controller;



import com.halo.eventer.domain.map.dto.map.GetAllStoreResDto;
import com.halo.eventer.domain.map.dto.map.MapCreateDto;
import com.halo.eventer.domain.map.dto.map.MapCreateResDto;
import com.halo.eventer.domain.map.dto.map.MapResDto;
import com.halo.eventer.domain.map.enumtype.OperationTime;
import com.halo.eventer.domain.map.service.MapService;
import com.halo.eventer.domain.map.swagger.map.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "지도")
@RestController
@RequiredArgsConstructor
@RequestMapping("/map")
public class MapController {

    private final MapService mapService;

    @MapCreateApi
    @PostMapping()
    public MapCreateResDto createMap(@RequestBody MapCreateDto mapCreateDto,
                                     @RequestParam("mapCategoryId") Long mapCategoryId,
                                     @RequestParam("operationTime") OperationTime operationTime
                                     ){
        return mapService.createMap(mapCreateDto,mapCategoryId,operationTime);
    }


    @MapGetApi
    @GetMapping("/{mapId}")
    public MapResDto getMap(@PathVariable("mapId")Long mapId){
        return mapService.getMap(mapId);

    }

    @MapGetsApi
    @GetMapping()
    public List<GetAllStoreResDto> getMaps(@RequestParam("mapCategoryId") Long mapCategoryId){
        return mapService.getMaps(mapCategoryId);
    }


    @MapUpdateApi
    @PatchMapping("/{mapId}")
    public ResponseEntity<?> updateStore(@PathVariable("mapId") Long mapId,
                                         @RequestBody MapCreateDto mapCreateDto,
                                         @RequestParam("mapCategoryId") Long mapCategoryId){
        try{
            return ResponseEntity.status(HttpStatus.OK)
                    .body(mapService.updateStore(mapId, mapCreateDto, mapCategoryId));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }


    @MapDeleteApi
    @DeleteMapping("/{mapId}")
    public ResponseEntity<?> deleteStore(@PathVariable("mapId") Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK)
                    .body(mapService.deleteStore(id));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
