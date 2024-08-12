//package com.github.supercoding.repository.items;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//// 메모리 내 리스트에 데이터를 저장하고 검색하는 역할.
//public class ElectronicStoreItemJavaListDao implements ElectronicStoreItemRepository{
//
//    private  static int serialItemId = 1;
//
//    private List<ItemEntity> items = Arrays.asList(new ItemEntity(serialItemId++, "Apple iPhone 12 ProMax", "Smartphone", 111,2, "A14 Bionic", "512GB"),
//                                            new ItemEntity(serialItemId++, "Apple iPhone 12 ProMax2", "Smartphone", 222,2, "A14 Bionic", "512GB"),
//                                            new ItemEntity(serialItemId++, "Apple iPhone 12 ProMax3", "Smartphone", 333,2, "A14 Bionic", "512GB"),
//                                            new ItemEntity(serialItemId++, "Apple iPhone 12 ProMax4", "Smartphone", 444,2, "A14 Bionic", "512GB"),
//                                            new ItemEntity(serialItemId++, "Apple iPhone 12 ProMax5", "Smartphone", 555,2, "A14 Bionic", "512GB"));
//
//
//    @Override
//    public List<ItemEntity> findAllItems() {
//        return items;
//    }
//
//    @Override
//    public Integer saveItem(ItemEntity itemEntity) {
//        itemEntity.setId(serialItemId++);
//        items.add(itemEntity);
//
//        return itemEntity.getId();
//    }
//
//    @Override
//    public ItemEntity findItemById(Integer idInt) {
////        return items.stream()
////                .filter(item -> item.getId().equals(idInt))
////                .findFirst()
////                .orElseThrow(() -> new RuntimeException("Item not found with id: " + idInt));
//        return null;
//    }
//
//    @Override
//    public void deleteItem(Integer idInt) {
//        ItemEntity itemToDelete = items.stream()
//                .filter(item -> item.getId().equals(idInt))
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException("Item not found with id: " + idInt));
//        items.remove(itemToDelete);
//    }
//
//    @Override
//    public ItemEntity updateItemEntity(Integer idInt, ItemEntity itemEntity) {
//        ItemEntity itemFounded = items.stream()
//                .filter((item -> item.getId().equals("id")))
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException());
//
//        items.remove(itemFounded);
//
//        itemEntity.setId(idInt);
//        items.add(itemEntity);
//
//        return itemEntity;
//    }
//
//    @Override
//    public void updateItemStock(Integer itemId, Integer i) {
//
//    }
//
//
//}
