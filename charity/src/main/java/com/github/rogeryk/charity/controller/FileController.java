package com.github.rogeryk.charity.controller;

import com.github.rogeryk.charity.exception.ServiceException;
import com.github.rogeryk.charity.storage.Storage;
import com.github.rogeryk.charity.utils.ErrorCodes;
import com.github.rogeryk.charity.utils.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;

@RestController
@RequestMapping("/api/upload")
@Validated
public class FileController {

    private static Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private Storage storage;

    @PostMapping("/")
    public Response upload(@NotNull(message = "请选择文件") MultipartFile file) {
        String url = null;
        String name = file.getOriginalFilename();

        try {
            url = storage.store(file.getInputStream(), file.getContentType(), name);
        } catch (IOException e) {
            logger.error("文件保存错误", e);
            throw new ServiceException(ErrorCodes.FILE_ERROR, "文件保存失败");
        }
        return Response.ok(url);
    }
}
