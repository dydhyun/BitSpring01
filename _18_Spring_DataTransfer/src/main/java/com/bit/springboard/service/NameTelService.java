package com.bit.springboard.service;

import com.bit.springboard.dto.NameTelDto;

import java.util.List;

public interface NameTelService {
    void postNameTel(NameTelDto nameTelDto);

    List<NameTelDto> getNameTelList();
}
