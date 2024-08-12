/*    package com.github.supercoding.repository.items;
    
    import lombok.RequiredArgsConstructor;
    import org.springframework.beans.factory.annotation.Qualifier;
    import org.springframework.jdbc.core.JdbcTemplate;
    import org.springframework.jdbc.core.RowMapper;
    import org.springframework.stereotype.Repository;
    
    import java.util.List;
    import java.util.Optional;

    // 데이터베이스에 데이터를 저장하고 검색하는 역할
    @Repository
    public class ElectronicStoreItemJdbcDao implements ElectronicStoreItemRepository{
    
        private final JdbcTemplate jdbcTemplate;  // config에 있는 jdbcTemplate이다.
    
        static RowMapper<ItemEntity> itemEntityRowMapper = (((rs, rowNum) ->
                    new ItemEntity.ItemEntityBuilder()
                            .id(rs.getInt("id"))
                            .name(rs.getNString("name"))
                            .type(rs.getNString("type"))
                            .price(rs.getInt("price"))
                            .storeId(rs.getInt("store_id"))
                            .stock(rs.getInt("stock"))
                            .cpu(rs.getNString("cpu"))
                            .capacity(rs.getNString("capacity"))
                            .build()
                )); // ItemEntityBuilder() : 순서가 바뀌어도 괜찮다.
    
        public ElectronicStoreItemJdbcDao(@Qualifier("jdbcTemplate1") JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }
    
        @Override
        public List<ItemEntity> findAllItems() {
           return jdbcTemplate.query("SELECT * FROM item", itemEntityRowMapper);
        }
    
        @Override
        public Integer saveItem(ItemEntity itemEntity) {
            jdbcTemplate.update("INSERT INTO item(name, type, price, cpu, capacity) VALUES(?, ?, ?, ?, ?)",
                    itemEntity.getName(), itemEntity.getType(), itemEntity.getPrice(),
                    itemEntity.getCpu(), itemEntity.getCapacity());
    
            ItemEntity itemEntityFound = jdbcTemplate.queryForObject("SELECT * FROM item WHERE name = ?", itemEntityRowMapper, itemEntity.getName());
            return itemEntityFound.getId();
        }
    
        @Override
        public ItemEntity findItemById(Integer idInt) {
            return jdbcTemplate.queryForObject("SELECT * FROM item WHERE id = ?", itemEntityRowMapper, idInt);
        }
    
        @Override
        public void deleteItem(Integer idInt) {
            jdbcTemplate.update("DELETE FROM item WHERE id = ?", idInt);
        }
    
        @Override
        public ItemEntity updateItemEntity(Integer idInt, ItemEntity itemEntity) {
            jdbcTemplate.update("UPDATE item " +
                            "SET name = ?, type = ?, price = ?, cpu = ?, capacity = ? " +
                            "WHERE id = ?",
                    itemEntity.getName(), itemEntity.getType(), itemEntity.getPrice(), itemEntity.getCpu(), itemEntity.getCapacity(), idInt);
    
            return jdbcTemplate.queryForObject("SELECT * FROM item WHERE id=?", itemEntityRowMapper ,idInt); // where id값을 들고온다.
    
        }
    
        @Override
        public void updateItemStock(Integer itemId, Integer stock) {
            jdbcTemplate.update("UPDATE item " +
                            " SET stock = ? " +
                            " WHERE id = ? ", stock, itemId);
        }

    }

 */