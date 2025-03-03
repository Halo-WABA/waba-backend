package com.halo.eventer.domain.map.service;


import com.halo.eventer.domain.festival.repository.FestivalRepository;
import com.halo.eventer.domain.map.MapCategory;
import com.halo.eventer.domain.map.dto.map.MapListDto;
import com.halo.eventer.domain.map.dto.mapcategory.CategoryEditDto;
import com.halo.eventer.domain.map.dto.mapcategory.CategoryEditListDto;
import com.halo.eventer.domain.map.dto.mapcategory.MapCategoryImageDto;
import com.halo.eventer.domain.map.dto.mapcategory.MapCategoryResDto;
import com.halo.eventer.domain.map.repository.MapCategoryRepository;
import com.halo.eventer.domain.map.repository.MapRepository;
import com.halo.eventer.global.exception.common.DuplicatedElementException;
import com.halo.eventer.global.exception.common.NoDataInDatabaseException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MapCategoryService {

    private final MapCategoryRepository mapCategoryRepository;
    private final MapRepository mapRepository;
    private final FestivalRepository festivalRepository;

    @Transactional
    public List<MapCategoryResDto> createMapCategory(Long festivalId, String categoryName) throws DuplicatedElementException, NoDataInDatabaseException{
        mapCategoryRepository.save(new MapCategory(festivalRepository.findById(festivalId).orElseThrow(()->new NoDataInDatabaseException("축제가 존재하지 않습니다.")),categoryName));
        return mapCategoryRepository.findAllByFestival_Id(festivalId).stream().map(MapCategoryResDto::new).collect(Collectors.toList());
    }

    public List<MapCategoryResDto> getMapCategoryList(Long festivalId){
        return mapCategoryRepository.findAllByFestival_Id(festivalId).stream().map(MapCategoryResDto::new).collect(Collectors.toList());
    }

    @Transactional
    public String addIcon(Long categoryId, MapCategoryImageDto mapCategoryImageDto) throws DuplicatedElementException, NoDataInDatabaseException {
        MapCategory mapCategory = mapCategoryRepository.findById(categoryId).orElseThrow(() -> new NoDataInDatabaseException("축제 정보가 존재하지 않습니다."));
        mapCategory.setImage(mapCategoryImageDto);
        return "아이콘, 핀 등록 완료";
    }

    @Transactional
    public List<MapCategoryResDto> deleteMapCategory(Long categoryId, Long festivalId) {
        mapCategoryRepository.deleteById(categoryId);
        return mapCategoryRepository.findAllByFestival_Id(festivalId).stream().map(MapCategoryResDto::new).collect(Collectors.toList());
    }

    public List<MapListDto> getLandMarks(Long mapCategoryId) {
        return mapCategoryRepository.findById(mapCategoryId)
                .orElseThrow().getMaps().stream().map(MapListDto::new).collect(Collectors.toList());
    }

    public MapCategoryImageDto getMapCategoryImages(Long mapCategoryId) throws NoDataInDatabaseException {
        return new MapCategoryImageDto(mapCategoryRepository.findById(mapCategoryId).orElseThrow(()->new NoDataInDatabaseException("카테고리가 존재하지 않습니다.")));
    }

    /** 배너 순서 등록 */
    @Transactional
    public String editCategoryRank(CategoryEditListDto categoryEditListDto) {
        List<MapCategory> categories = mapCategoryRepository.findAllById(categoryEditListDto.getCategoryEditDtos().stream()
                .map(CategoryEditDto::getCategoryId).collect(Collectors.toList()));

        for(MapCategory mapCategory : categories){
            for(CategoryEditDto c : categoryEditListDto.getCategoryEditDtos()) {
                if (c.getCategoryId() == mapCategory.getId()) {
                    mapCategory.setRank(c.getRank());
                    break;
                }
            }
        }
        return "수정완료";
    }

}
