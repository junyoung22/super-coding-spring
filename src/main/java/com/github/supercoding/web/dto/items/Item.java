package com.github.supercoding.web.dto.items;

import com.github.supercoding.repository.items.ItemEntity;
import com.github.supercoding.repository.storeSales.StoreSales;
import com.github.supercoding.service.mapper.ItemMapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Item {
    @ApiModelProperty(name = "id", value = "Item Id", example = "1") private String id;
    @ApiModelProperty(name = "name", value = "Item 이름", example = "Dell XPS 15") private String name;
    @ApiModelProperty(name = "type", value = "Item 기기타입", example = "Laptop") private String type;
    @ApiModelProperty(name = "price", value = "Item 가격", example = "125000") private Integer price;
    private StoreSales storeSales;
    private Integer stock;
    private Spec spec;

    public Item(String id, String name, String type, Integer price, String cpu, String capacity) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.spec = new Spec(cpu, capacity);
    }
    //ItemMapper.INSTANCE::itemEntityToItem
    //ItemMapper.INSTANCE.itemEntityToItem
//    public Item(ItemEntity itemEntity){
//        this.id = itemEntity.getId().toString();
//        this.type = itemEntity.getType();
//        this.price = itemEntity.getPrice();
//        this.name = itemEntity.getName();
//        this.spec = new Spec(itemEntity.getCpu(), itemEntity.getCapacity());
//    }

}
