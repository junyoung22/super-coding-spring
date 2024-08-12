package com.github.supercoding.repository.items;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// DTO와 기본키의 형태                                                 관리 엔티티 타입 / 기본키 타입
public interface ElectronicStoreItemJpaRepository extends JpaRepository<ItemEntity, Integer> {
                    // findItemEntitiesBy / TypeIn
    List<ItemEntity> findItemEntitiesByTypeIn(List<String> types);
                    // findItemEntitiesBy / PriceLessThanEqual / Or / OrderByPrice / Asc
    List<ItemEntity> findByPriceLessThanEqualOrderByPriceAsc(Integer maxValue);

    Page<ItemEntity> findAllByTypeIn(List<String> types, Pageable pageable);
}
