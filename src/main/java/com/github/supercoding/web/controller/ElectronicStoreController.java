package com.github.supercoding.web.controller;

import com.github.supercoding.repository.items.ElectronicStoreItemJpaRepository;
import com.github.supercoding.service.ElectronicStoreItemService;
import com.github.supercoding.web.dto.items.BuyOrder;
import com.github.supercoding.web.dto.items.Item;
import com.github.supercoding.web.dto.items.ItemBody;
import com.github.supercoding.web.dto.items.StoreInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor    // 생성자 대신
@Slf4j
public class ElectronicStoreController {
    // 바뀔경우가 없으니 final정의도 괜찮다.

    private final ElectronicStoreItemService electronicStoreItemService;

//    public ElectronicStoreController(ElectronicStoreItemService electronicStoreItemService) {
//        this.electronicStoreItemService = electronicStoreItemService;
//    }

    private static int serialItemId = 1;
    private final ElectronicStoreItemJpaRepository electronicStoreItemJpaRepository;

    private List<Item> items = new ArrayList<>(Arrays.asList(
            new Item(String.valueOf(serialItemId), "Apple iPhone 12 ProMax1", "Smartphone1", 1111111, "A14 Bionic1", "512GB"),
            new Item(String.valueOf(serialItemId), "Apple iPhone 12 ProMax2", "Smartphone2", 2222222, "A14 Bionic2", "513GB"),
            new Item(String.valueOf(serialItemId), "Apple iPhone 12 ProMax3", "Smartphone3", 3333333, "A14 Bionic3", "514GB"),
            new Item(String.valueOf(serialItemId), "Apple iPhone 12 ProMax4", "Smartphone4", 4444444, "A14 Bionic4", "515GB"),
            new Item(String.valueOf(serialItemId), "Apple iPhone 12 ProMax5", "Smartphone5", 5555555, "A14 Bionic5", "516GB")));

    @ApiOperation("모든 Items을 검색")
    @GetMapping("/items")
    public List<Item> findAllItem(){
//        log.info("GET /items 요청이 들어왔습니다."); (★Filter로 대체)
        List<Item> items = electronicStoreItemService.findAllItem();
//        log.info("GET /items 응답: " + items); // 글자깨지면 dto에 @ToString 선언
        return items;
    }

    @ApiOperation("모든 Items 등록")
    @PostMapping("/items")
    public String registerItem(@RequestBody ItemBody itemBody){
        System.out.println("@@@" + itemBody.getPrice());    // @@@12000333

        Integer itemId = electronicStoreItemService.saveItem(itemBody);
        return "ID: " + itemId;
    }
    // api/items/2
    @ApiOperation("모든 Item id로 검색")
    @GetMapping("/items/{id}") // select where 해서 id찾는거
    public Item findItemByPathId(
            @ApiParam(name = "id", value ="item ID", example = "1")
            @PathVariable String id){
        return electronicStoreItemService.findItemById(id);
    }
    // api/items-query?id=2
    @ApiOperation("단일 Item id로 검색(쿼리문)")
    @GetMapping("items-query")
    public Item findItemByQueryId(
            @ApiParam(name = "id", value ="item ID", example = "1")
            @RequestParam("id") String id){
        return electronicStoreItemService.findItemById(id);
    }
    // api/items-queries?id=1&id=2&id=3&id=4 ( 4개선언 -> 3개나옴 )
    @ApiOperation("모든 Item ids로 검색(쿼리문)")
    @GetMapping("/items-queries")   // where문 sql로 가져오기
    public List<Item> findItemByQueryId(
            @ApiParam(name = "ids", value ="item IDs", example = "[1,2,3]")
            @RequestParam("id") List<String> ids){
        log.info("ids: " + ids);
        List<Item> items = electronicStoreItemService.findItemsByIds(ids);
        log.info("/items-queries 응답: " + items);
        return items;
    }
    @ApiOperation("단일 Item id로 삭제")
    @DeleteMapping("/items/{id}")   // delete sql문
    public String deleteItemByPathId(
            @ApiParam(name = "id", value ="item ID", example = "1")
            @PathVariable String id){
        electronicStoreItemService.deleteItem(id);
        return "Object with id = " + id + " has been deleted";
    }
    @ApiOperation("단일 Item id로 수정")
    @PutMapping("/items/{id}")
    public Item updateItem(@PathVariable String id, @RequestBody ItemBody itemBody){
        return electronicStoreItemService.updateItem(id, itemBody);
    }
    @ApiOperation("단일 Item 구매")
    @PostMapping("/items/buy")
    public String buyItem(@RequestBody BuyOrder buyOrder){
        Integer OrderItemNums = electronicStoreItemService.buyItems(buyOrder);
        return "요청하신 Item 중 " + OrderItemNums + "개를 구매하였습니다.";
    }
    @ApiOperation("모든 Item types 검색(쿼리문)")
    @GetMapping("/items-types")   // where문 sql로 가져오기
    public List<Item> findItemByTypes(
            @ApiParam(name = "ids", value ="item IDs", example = "[1,2,3]")
            @RequestParam("type") List<String> types){
        log.info("/items-types 요청 ids: " + types);
        List<Item> items = electronicStoreItemService.findItemsByTypes(types);
        log.info("/items-types 응답: " + items);
        return items;
    }
    @ApiOperation("단일 Item id로 검색(쿼리문)")
    @GetMapping("/items-prices")
    public List<Item> findItemByPrices(@RequestParam("max") Integer maxValue){
        return electronicStoreItemService.findItemsByOrderByPrice(maxValue);
    }

    @ApiOperation("pagination 지원")
    @GetMapping("/items-page")
    public Page<Item> findItemsPagination(Pageable pageable){
        return electronicStoreItemService.findAllWithPageable(pageable);
    }

    @ApiOperation("pagination 지원2")
    @GetMapping("/items-types-page")
    public Page<Item> findItemsPagination(@RequestParam("type") List<String> types, Pageable pageable){
        return electronicStoreItemService.findAllWithPageable(types, pageable);
    }

    @ApiOperation("전체 stores 정보 검색")
    @GetMapping("/stores")
    public List<StoreInfo> findAllStoreInfo(){
        return electronicStoreItemService.findAllStoreInfo();
    }
}
