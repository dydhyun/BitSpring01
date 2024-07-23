package com.bit.springboard.common;

import com.bit.springboard.dto.BoardFileDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

// 파일을 서버에 업로드 하고 BoardFileDto 형태로 변환해서 리턴해주는 클래스
public class FileUtils {

    public static BoardFileDto parseFileInfo(MultipartFile multipartFile, String attachPath){
        BoardFileDto boardFileDto = new BoardFileDto();


        // 다른 사용자가 같은 파일명의 파일을 업로드 했을 때
        // 덮어써지는 것을 방지하기 위해 파일명을 랜덤값_날짜시간_파일명 으로 지정한다.
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date nowDate = new Date();

        String nowDateStr = format.format(nowDate);

        UUID uuid = UUID.randomUUID();

        // 같은 파일명이 올라오면 랜덤값과 데이트 값을 주어 덮어씌워지지 않게 구분짓도록
        String fileName = uuid.toString() + "_" + nowDateStr + "_" + multipartFile.getOriginalFilename();

        File uploadFile = new File(attachPath + fileName);
        String type = "";

        try {
            // 경로에 파일명으로 업로드 처리하기
            multipartFile.transferTo(uploadFile);
            type = Files.probeContentType(uploadFile.toPath());
            // toPath() 메서드는 주어진 속성값으로 경로배열을 만들어주고,
            // probeContentType() 메서드는 파일의 확장자를 이용해 마임타입을 반환한다. 따라서 확장자가 없으면 null 이 반환된다.
        } catch (IOException ie){
            System.out.println(ie.getMessage());
        }

        if (!type.equals("")) {
            if (type.startsWith("image")){
                boardFileDto.setFiletype("image");
            } else {
                boardFileDto.setFiletype("etc");
            }
        }

        boardFileDto.setFilename(fileName);
        boardFileDto.setFileoriginname(multipartFile.getOriginalFilename());
        boardFileDto.setFilepath(attachPath);


        return boardFileDto;
    }
}
