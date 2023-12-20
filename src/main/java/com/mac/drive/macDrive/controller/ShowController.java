package com.mac.drive.macDrive.controller;

import com.mac.drive.macDrive.pojo.File;
import com.mac.drive.macDrive.service.IFileService;
import com.mac.drive.macDrive.utils.MinioUtil;
import io.minio.StatObjectResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * @program: minio_sever
 * @description: File frontend display interface
 * @author: bin
 * @create: 2022-04-28 19:52
 **/
@RestController
@Api(value = "ShowController", tags = {"File Retrieval/Display Operations"})
@RequestMapping("/api/object")
public class ShowController {

    @Autowired
    private IFileService fileService;
    @Autowired
    private MinioUtil minioUtil;

    @ApiOperation(value = "Stream file display/Type")
    @GetMapping("/show/{fileId}")
    public void thumbnail(@PathVariable String fileId, HttpServletResponse response) {

        File FileShow = fileService.getById(fileId);
        // Get MinIO share link
        String objectName = FileShow.getFile_inbuck_name();
        String bucketName = FileShow.getFile_bucket_name();

        try {
            // Get the object's input stream.
            InputStream stream = minioUtil.getFileInputStream(objectName, bucketName);
            // Stream conversion
            IOUtils.copy(stream, response.getOutputStream());
            // Set return type
            response.addHeader("Content-Type", "audio/mpeg;charset=utf-8");
            // Close the stream
            stream.close();
        } catch (Exception e) {
            System.out.println("Error occurred: " + e);
        }
    }

    @ApiOperation(value = "File download/Disposition")
    @GetMapping("/send/{fileId}")
    public void ObjectSend(@PathVariable String fileId, HttpServletResponse response) {
        File FileShow = fileService.getById(fileId);
        // Get MinIO share link
        String objectName = FileShow.getFile_inbuck_name();
        String bucketName = FileShow.getFile_bucket_name();
        String fileName = FileShow.getFile_name() + "." + FileShow.getFile_type();

        try {
            // Get the object's input stream.
            InputStream stream = minioUtil.getFileInputStream(objectName, bucketName);

            // Stream conversion
            // IOUtils.copy(stream, response.getOutputStream());

            final StatObjectResponse stat = minioUtil.getObjectStat(objectName, bucketName);
            response.setContentType(stat.contentType());
            response.setCharacterEncoding("UTF-8");
            // Set return type
            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
            // Commented out, otherwise will throw an error
            // response.flushBuffer();
            // Stream conversion
            org.apache.tomcat.util.http.fileupload.IOUtils.copy(stream, response.getOutputStream());
            // Close the stream
            stream.close();
        } catch (Exception e) {
            System.out.println("Error occurred: " + e);
        }
    }

    @ApiOperation(value = "Text file display/Type")
    @GetMapping("/preview/txt")
    public void textShow(String userFileId, HttpServletResponse response) {
        File FileShow = fileService.getById(userFileId);
        // Get MinIO share link
        String objectName = FileShow.getFile_inbuck_name();
        String bucketName = FileShow.getFile_bucket_name();
        String fileName = FileShow.getFile_name() + "." + FileShow.getFile_type();

        try {
            // Get the object's input stream.
            InputStream stream = minioUtil.getFileInputStream(objectName, bucketName);

            // Stream conversion
            // IOUtils.copy(stream, response.getOutputStream());

            final StatObjectResponse stat = minioUtil.getObjectStat(objectName, bucketName);
            response.setContentType(stat.contentType());
            response.setCharacterEncoding("UTF-8");
            // Set return type
            response.setHeader("Content-Type", "text/html; charset=utf-8");
            // Commented out, otherwise will throw an error
            // response.flushBuffer();
            // Stream conversion
            org.apache.tomcat.util.http.fileupload.IOUtils.copy(stream, response.getOutputStream());
            // Close the stream
            stream.close();
        } catch (Exception e) {
            System.out.println("Error occurred: " + e);
        }
    }
}
