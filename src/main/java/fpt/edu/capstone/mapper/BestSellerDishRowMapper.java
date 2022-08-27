package fpt.edu.capstone.mapper;

import fpt.edu.capstone.dto.BestSellerDishesDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BestSellerDishRowMapper implements RowMapper<BestSellerDishesDTO> {
    @Override
    public BestSellerDishesDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return BestSellerDishesDTO.builder()
                .dishesName(rs.getString("dishes_name"))
                .dishesImage(rs.getString("dish_image"))
                .quantity(rs.getInt("quantity"))
                .salePrice(rs.getBigDecimal("sale_price"))
                .build();
    }
}