package com.itsol.train.mock.mapper;

import com.itsol.train.mock.dto.UserDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserNotActiveMapper implements RowMapper<UserDto> {
    @Override
    public UserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserDto dto = new UserDto();
        dto.setId(rs.getLong("ID"));
        dto.setEmail(rs.getString("EMAIL"));
        dto.setUsername(rs.getString("USERNAME"));
        dto.setCreateDate(rs.getDate("CREATED_DATE"));
        dto.setUpdateDate(rs.getDate("UPDATED_DATE"));
        dto.setLangKey(rs.getString("LANG_KEY"));
        dto.setImageUrl(rs.getString("IMAGE_URL"));
        return dto;
    }
}
