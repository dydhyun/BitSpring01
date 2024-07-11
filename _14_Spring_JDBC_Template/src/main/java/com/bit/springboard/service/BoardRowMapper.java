package com.bit.springboard.service;

import com.bit.springboard.dto.BoardDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;

public class BoardRowMapper implements RowMapper<BoardDto> {

    @Override
    public BoardDto mapRow(ResultSet rs, int rowNum){
        BoardDto boardDto = new BoardDto();

        try {
            boardDto.setId(rs.getInt("ID"));
            boardDto.setTitle(rs.getString("TITLE"));
            boardDto.setContent(rs.getString("CONTENT"));
            boardDto.setWRITER_ID(rs.getInt("WRITER_ID"));
            boardDto.setNickname(rs.getString("NICKNAME"));
            boardDto.setRegdate(rs.getTimestamp("REGDATE").toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime());
            boardDto.setModdate(rs.getTimestamp("MODDATE").toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime());
            boardDto.setCnt(rs.getInt("CNT"));
        } catch (SQLException se){
            System.out.println(se.getMessage());
        }
        return boardDto;
    }

}
