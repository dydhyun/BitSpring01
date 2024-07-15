package com.bit.springboard.service.impl;

import com.bit.springboard.dao.NameTelDao;
import com.bit.springboard.dto.NameTelDto;
import com.bit.springboard.service.NameTelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NameTelServiceImpl implements NameTelService {

    private NameTelDao nameTelDao;

    @Autowired
    public void postNameTel(NameTelDao nameTelDao) {
        this.nameTelDao = nameTelDao;
    }

    @Override
    public void postNameTel(NameTelDto nameTelDto) {
        nameTelDao.postNameTel(nameTelDto);
    }

    @Override
    public List<NameTelDto> getNameTelList() {
        return nameTelDao.nameTelListGet();
    }


}
