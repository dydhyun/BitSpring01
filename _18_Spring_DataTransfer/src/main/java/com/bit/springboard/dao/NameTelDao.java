package com.bit.springboard.dao;

import com.bit.springboard.dto.NameTelDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NameTelDao {
    private SqlSessionTemplate mybatis;

    @Autowired
    public NameTelDao(SqlSessionTemplate sqlSessionTemplate) {this.mybatis = sqlSessionTemplate;}

    public void postNameTel(NameTelDto nameTelDto){
        mybatis.insert("NameTelDao.postNameTel", nameTelDto);
    }

    public List<NameTelDto> nameTelListGet(){
        return mybatis.selectList("NameTelDao.getNameTelList");
    }

}
