package com.github.supercoding.web.controller.sample;

import com.github.supercoding.service.ElectronicStoreItemService;
import com.github.supercoding.web.dto.items.Item;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/sample")
@RequiredArgsConstructor
@Slf4j
public class Chapter109Controller {
    private final ElectronicStoreItemService electronicStoreItemService;

    @ApiOperation("가성비 싼 거부터 검색")
    @GetMapping("/items-prices")
    public List<Item> findItemByPrices(
            HttpServletRequest httpServletRequest
//      변경      @RequestParam("max") Integer maxPrice
    ){  // 생성▽
        Integer maxPrice = Integer.valueOf(httpServletRequest.getParameter("max"));
        log.info("GET /items-prices 요청이 들어왔습니다.");
        List<Item> items = electronicStoreItemService.findItemsByOrderByPrice(maxPrice);
        log.info("GET /items-prices 요청이 들어왔습니다." + items);
        return items;
    }

    @ApiOperation("단일 Item id로 삭제")
    @DeleteMapping("/items/{id}")   // delete sql문
    public void deleteItemByPathId(
            @ApiParam(name = "id", value ="item ID", example = "1")
            @PathVariable String id,
            HttpServletResponse httpServletResponse
    ) throws IOException {
        log.info("DELETE /items/" + id + " 요청이 들어왔습니다.");
        electronicStoreItemService.deleteItem(id);
        String responseMessage = "Object with id = " + id + " has been deleted";
        log.info("DELETE /items/" + id + " 응답: " + responseMessage);
        httpServletResponse.getOutputStream().println(responseMessage);
    }
}
