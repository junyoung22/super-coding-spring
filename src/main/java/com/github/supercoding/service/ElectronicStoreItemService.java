package com.github.supercoding.service;

import com.github.supercoding.repository.items.ElectronicStoreItemJpaRepository;
import com.github.supercoding.repository.items.ItemEntity;
import com.github.supercoding.repository.storeSales.StoreSales;
import com.github.supercoding.repository.storeSales.StoreSalesJpaRepository;
import com.github.supercoding.service.exceptions.NotAcceptException;
import com.github.supercoding.service.exceptions.NotFoundException;
import com.github.supercoding.service.mapper.ItemMapper;
import com.github.supercoding.web.dto.items.BuyOrder;
import com.github.supercoding.web.dto.items.Item;
import com.github.supercoding.web.dto.items.ItemBody;
import com.github.supercoding.web.dto.items.StoreInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ElectronicStoreItemService {

    // private final ElectronicStoreItemRepository electronicStoreItemRepository;
    private final ElectronicStoreItemJpaRepository electronicStoreItemJpaRepository;
    // private final StoreSalesRepository storeSalesRepository;
    private final StoreSalesJpaRepository storeSalesJpaRepository;

    @Cacheable(value = "items", key = "#root.methodName")
    public List<Item> findAllItem() {
        List<ItemEntity> itemEntities = electronicStoreItemJpaRepository.findAll();
        return itemEntities.stream().map(ItemMapper.INSTANCE::itemEntityToItem).collect(Collectors.toList());
    }

    @CacheEvict(value = "items", allEntries = true)
    public Integer saveItem(ItemBody itemBody) {    // @@ stock을 파라미터 추가해서 넣었고 cpu, capacity값은 null로 뜸 @@
        System.out.println("@@@" + itemBody.getPrice());
        ItemEntity itemEntity = ItemMapper.INSTANCE.idAndItemBodyToItemEntity(null, itemBody);
        ItemEntity itemEntityCreated;
        System.out.println("@@@" + itemBody.getPrice());
        System.out.println("@@@" + itemEntity.getPrice());
        System.out.println("@@@" + itemEntity.getStock());
        System.out.println("@@@" + itemEntity.getId());
        try {
            System.out.println("@@@" + itemEntity.getId());
            System.out.println("@@@" + itemEntity.getStock());

            itemEntityCreated = electronicStoreItemJpaRepository.save(itemEntity);
            System.out.println("@@@@@@@@@@@@@2 : " + itemEntity.getId());

        } catch(RuntimeException exception){
            throw new NotAcceptException("Item을 저장하는 도중에 Error가 발생했습니다.");
        }

        return itemEntityCreated.getId();
    }

    @Cacheable(value = "items", key = "#id")
    public Item findItemById(String id) {
        Integer idInt = Integer.parseInt(id);
        ItemEntity itemEntity = electronicStoreItemJpaRepository.findById(idInt)
                .orElseThrow(() -> new NotFoundException("해당ID: " + idInt + "의 Item을 찾을수 없습니다."));
        Item item = ItemMapper.INSTANCE.itemEntityToItem(itemEntity);
        return item;
    }

    @Cacheable(value = "items", key = "#ids")
    public List<Item>findItemsByIds(List<String> ids){
        List<ItemEntity> itemEntities = electronicStoreItemJpaRepository.findAll();
        if (itemEntities.isEmpty()) throw new NotFoundException("아무 Items 들을 찾을 수 없습니다.");
        return itemEntities.stream()
                .map(ItemMapper.INSTANCE::itemEntityToItem)
                .filter((item -> ids.contains(item.getId())))
                .collect(Collectors.toList());
    }

    @CacheEvict(value = "items", allEntries = true)
    public void deleteItem(String id) {
        Integer idInt = Integer.parseInt(id);
        electronicStoreItemJpaRepository.deleteById(idInt);
    }
    @CacheEvict(value = "items", allEntries = true)
    @Transactional(transactionManager = "tmJpa1")   // 여기: JPA의 트랜잭션 / 아래 : jdbc의 트랜잭션
    public Item updateItem(String id, ItemBody itemBody) {
        Integer idInt = Integer.valueOf(id);
        ItemEntity itemEntityUpdated = electronicStoreItemJpaRepository.findById(idInt)
                .orElseThrow(() -> new NotFoundException("해당ID: " + idInt + "의 Item을 찾을수 없습니다."));

        itemEntityUpdated.setItemBody(itemBody);    // setItemBody : itemBody를 받는족족 this로 get을 받아서 itemEntityUpdated에 보관

        return ItemMapper.INSTANCE.itemEntityToItem(itemEntityUpdated);
    }
    @Transactional(transactionManager = "tmJpa1") // (config에서 설정하고옴)
    public Integer buyItems(BuyOrder buyOrder) {
        // 1. BuyOrder에서 상품ID와 수량을 얻어낸다.
        // 2. 상품을 조회하여 수량이 얼마나 있는지 확인한다.
        // 3. 상품의 수량과 가격을 가지고 계산하여 총 가격을 구한다.
        // 4. 상품의 재고에 기존 계산한 재고를 구매하는 수량을 뺀다.
        // 5, 상품 사용한 재고 * 가격만큼 가계 매상으로 올린다.
        // (단, 재고가 아예 없거나 매장을 찾을수 없으면 살수 없다.)

        Integer itemId = buyOrder.getItemId();      // 원하는 상품
        Integer itemNums = buyOrder.getItemNums();  // 원하는 수량
        // 상품 테이블을 보기위해 ItemEntity를 불러옴( 재고 )
        ItemEntity itemEntity = electronicStoreItemJpaRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("해당 이름: " + itemId + "은 찾을수 없습니다."));

        log.info("========= 동작 확인 로그 1 =========");
        // 매장없으면 에러
        if (itemEntity.getStoreSales().isEmpty()) throw new RuntimeException("매장을 찾을수 없습니다.");
        if (itemEntity.getStock() <= 0) throw new RuntimeException("상품의 재고가 없습니다.");

        //  원하는수량 >= 재고일 경우 재고만큼 구매 / 아닐경우 정상구매
        Integer sucessBuyItemNums;
        if (itemNums >= itemEntity.getStock()) sucessBuyItemNums = itemEntity.getStock();
        else sucessBuyItemNums = itemNums;

        // 총가격
        Integer totalPrice = sucessBuyItemNums * itemEntity.getPrice();

        // Item재고 감소 구매시 구매한 수량만큼 뺀다( DB에 반영해야한다. -> Jpa(itemEntity) 반영된다.) // 원하는 상품, 현재재고 - 구매량
        // electronicStoreItemRepository.updateItemStock(itemId, itemEntity.getStock() - sucessBuyItemNums);
        itemEntity.setStock(itemEntity.getStock() - sucessBuyItemNums);


        // (트랜잭션처리(config)) 4개구매시 에러 / 재고감소됨 - 매상추가 안됨
        if (sucessBuyItemNums == 4) {
            log.error("4개를 구매하는건 안됩니다.");
            throw new RuntimeException("4개를 구매하는건 허락하지않습니다.");
        }

        log.info("========= 동작 확인 로그 2 =========");

        // 매장 매상 추가 (어디매장=StoreId, 기존Amount + 총가격
        StoreSales storeSales = itemEntity.getStoreSales()
                .orElseThrow(() -> new NotFoundException("요청하신 Store에 해당하는 StoreId가 없습니다."));
        storeSales.setAmount(storeSales.getAmount() + totalPrice);
        // storeSalesRepository.updateSalesAmount(itemEntity.getStoreId(), storeSales.getAmount() + totalPrice);

        return sucessBuyItemNums;
    }

    public List<Item> findItemsByTypes(List<String> types) {
        List<ItemEntity> itemEntities = electronicStoreItemJpaRepository.findItemEntitiesByTypeIn(types);
        return itemEntities.stream().map(ItemMapper.INSTANCE::itemEntityToItem).collect(Collectors.toList());
    }

    public List<Item> findItemsByOrderByPrice(Integer maxValue) {
        List<ItemEntity> itemEntities = electronicStoreItemJpaRepository.findByPriceLessThanEqualOrderByPriceAsc(maxValue);
        return itemEntities.stream().map(ItemMapper.INSTANCE::itemEntityToItem).collect(Collectors.toList());
    }
    // http://localhost:8080/api/items-page?size=5&page=2
    public Page<Item> findAllWithPageable(Pageable pageable) {
        Page<ItemEntity> itemEntities = electronicStoreItemJpaRepository.findAll(pageable);
        return itemEntities.map(ItemMapper.INSTANCE::itemEntityToItem);
    }

    public Page<Item> findAllWithPageable(List<String> types, Pageable pageable) {
        Page<ItemEntity> itemEntities = electronicStoreItemJpaRepository.findAllByTypeIn(types, pageable);
        return itemEntities.map(ItemMapper.INSTANCE::itemEntityToItem);
    }
    // failed to lazily initialize a collection of role: could not initialize proxy - no Session
    @Transactional(transactionManager = "tmJpa1")
    public List<StoreInfo> findAllStoreInfo() {

        List<StoreSales> storeSales = storeSalesJpaRepository.findAllFetchJoin();   // select를 1번만 되게끔 설정함 // findAll() = 값수만큼 select
        log.info("=========== 확인용 로그1 ============");
        // StoreSales값을 찾아 StoreInfo에 id,name,amount,itemNames(Entity의 Name) 저장하여 List출력
        List<StoreInfo> storeInfos = storeSales.stream().map(StoreInfo::new).collect(Collectors.toList());
        return storeInfos;
    }
}
