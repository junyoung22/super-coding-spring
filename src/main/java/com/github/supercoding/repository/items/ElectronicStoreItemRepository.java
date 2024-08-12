package com.github.supercoding.repository.items;

import java.util.List;
import java.util.Optional;

public interface ElectronicStoreItemRepository {
    List<ItemEntity> findAllItems();

    Integer saveItem(ItemEntity itemEntity);

    ItemEntity findItemById(Integer idInt);

    void deleteItem(Integer idInt);

    ItemEntity updateItemEntity(Integer idInt, ItemEntity itemEntity);

    void updateItemStock(Integer itemId, Integer i);


}
