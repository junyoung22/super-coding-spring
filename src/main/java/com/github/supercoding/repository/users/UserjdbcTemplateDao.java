//package com.github.supercoding.repository.users;
//
//import com.github.supercoding.repository.storeSales.StoreSales;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@Repository
//public class UserjdbcTemplateDao implements UserRepository{
//
//    private JdbcTemplate jdbcTemplate;
//
//    public UserjdbcTemplateDao(@Qualifier("jdbcTemplate2") JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//    // findUserById를 하려면 RowMapper를 해야한다.
//    static RowMapper<UserEntity> userEntityRowMapper = (((rs, rowNum) ->
//            new UserEntity(
//                    rs.getInt("user_id"),
//                    rs.getNString("user_name"),
//                    rs.getNString("like_travel_place"),
//                    rs.getNString("phone_num")
//            )
//    ));
//
//    @Override
//    public Optional<UserEntity> findUserById(Integer userId) {
//        try {
//            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM users WHERE user_id = ?", userEntityRowMapper, userId));
//        } catch (Exception e){
//            return Optional.empty();
//        }
//    }
//}
