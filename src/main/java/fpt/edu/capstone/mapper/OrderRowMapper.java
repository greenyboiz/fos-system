package fpt.edu.capstone.mapper;

import fpt.edu.capstone.dto.DashboardDTO;
import fpt.edu.capstone.dto.OrderDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper implements RowMapper<OrderDTO> {
    @Override
    public OrderDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return OrderDTO.builder()
                .orderId(rs.getLong("order_id"))
                .fullName(rs.getString("full_name"))
                .contact(rs.getString("contact"))
                .submitDate(rs.getTimestamp("submit_time"))
                .employee(rs.getString("employee"))
                .paymentTypeName(rs.getString("payment_type_name"))
                .totalMoneyOfOrder(rs.getBigDecimal("total_money_of_order"))
                .build();
    }
}
