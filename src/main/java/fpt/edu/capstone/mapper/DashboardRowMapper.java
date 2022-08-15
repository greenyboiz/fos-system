package fpt.edu.capstone.mapper;

import fpt.edu.capstone.dto.DashboardDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardRowMapper implements RowMapper<DashboardDTO> {
    @Override
    public DashboardDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return DashboardDTO.builder().
                month(rs.getInt("month_order"))
                .year(rs.getInt("year_order"))
                .cost(rs.getBigDecimal("total_cost"))
                .revenue(rs.getBigDecimal("total_sale"))
                .profit(rs.getBigDecimal("profit"))
                .build();
    }
}
