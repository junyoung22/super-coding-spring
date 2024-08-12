package com.github.supercoding.service.mapper;


import com.github.supercoding.repository.items.ItemEntity;
import com.github.supercoding.web.dto.items.Item;
import com.github.supercoding.web.dto.items.ItemBody;
import org.hibernate.sql.Insert;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ItemMapper {
    // 싱글톤
    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    // 메소드
    @Mapping(target = "spec.cpu", source = "cpu")
    @Mapping(target = "spec.capacity", source = "capacity")
    @Mapping(target = "storeSales", ignore = true)
    @Mapping(target = "stock", expression = "java(0)")
    Item itemEntityToItem(ItemEntity itemEntity);

    ItemEntity idAndItemBodyToItemEntity(String id, ItemBody itemBody);
}
