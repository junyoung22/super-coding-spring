//package com.github.supercoding.service.mapper;
//
//import com.github.supercoding.repository.items.ItemEntity;
//import com.github.supercoding.repository.storeSales.StoreSales;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class ItemMapperUtilTest {
//
//    @DisplayName("ItemEntity의 itemEntityToItem 메소드 테스트")
//    @Test
//    void itemEntityToItem() {
//        // given
//        ItemEntity itemEntity = ItemEntity.builder()
//                .name("name").type("type").id(1).price(1000).stock(0)
//                .cpu("CPU 1").capacity("56").storeSales(new StoreSales()).build();
//
//        // when
//        Item item = ItemMapper.INSTANCE.itemEntityToItem(itemEntity);
//        // then
//        log.info("만들어진 item: " + item);
//        assertEquals(itemEntity.getPrice(), item.getPrice());
//        assertEquals(itemEntity.getId().toString(), item.getId());
//
//    }
//}