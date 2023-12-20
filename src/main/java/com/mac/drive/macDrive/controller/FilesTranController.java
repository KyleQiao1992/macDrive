package com.mac.drive.macDrive.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.google.common.collect.HashMultimap;
import com.mac.drive.macDrive.pojo.DTO.UpFileParam;
import com.mac.drive.macDrive.pojo.File;
import com.mac.drive.macDrive.pojo.Filechunk;
import com.mac.drive.macDrive.service.IFileService;
import com.mac.drive.macDrive.service.IFilechunkService;
import com.mac.drive.macDrive.utils.MinioUtil;
import io.minio.CreateMultipartUploadResponse;
import io.minio.ListPartsResponse;
import io.minio.messages.Part;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Test file upload, download, preview, and deletion
 **/
@RestController
@Api(value = "FilesTranController", tags = {"File Upload Interface"})
@RequestMapping("/api/FileTran")
public class FilesTranController {

    @Resource
    private MinioUtil minioUtil;
    @Resource
    private IFileService fileService;
    @Resource
    private IFilechunkService filechunkService;

    /**
     * Returns the signed URL and uploadId required for multipart upload
     *
     * @param upFileParam
     * @return
     */
    @GetMapping("/createMultipartUpload")
    @SneakyThrows
    @ResponseBody
    @ApiOperation(value = "Apply for the signed URL data needed for upload")
    public Map<String, Object> createMultipartUpload(UpFileParam upFileParam) {
        String bucketName = minioUtil.getUsername();
        int chunkSize = upFileParam.getChunkNumber();
        Filechunk upFile = new Filechunk();
//        upFile.setFile_name(upFileParam.getFilename());
        upFile.setFile_name(upFileParam.getFilename().substring(0, upFileParam.getFilename().lastIndexOf(".")));
        upFile.setFile_url(upFileParam.getFilePath());
        upFile.setUpbucker_name(bucketName);
        upFile.setFile_size(upFileParam.getFileSize());
        upFile.setFile_MD5(upFileParam.getIdentifier());

        String Name = upFileParam.getFilename();

        String TypeName = Name.substring(Name.lastIndexOf("."));
        upFile.setFile_type(TypeName.substring(1));
        upFile.setChunkNumber(chunkSize);

        // Rename using IdWorker
        String fileName = IdWorker.getId() + TypeName;
        upFile.setIdworker_name(fileName);

        // Create a signature based on the file name
        Map<String, Object> result = new HashMap<>();
        // Get uploadId
        String contentType = "application/octet-stream";
        HashMultimap<String, String> headers = HashMultimap.create();
        headers.put("Content-Type", contentType);
        if (!minioUtil.bucketExists(bucketName)) {
            minioUtil.createBucket(bucketName);
        }
        CreateMultipartUploadResponse response = minioUtil.uploadId(bucketName, null, fileName, headers, null);

        String uploadId = response.result().uploadId();
        result.put("uploadId", uploadId);

        upFile.setUploadId(uploadId);
        filechunkService.save(upFile);

        // Request Minio service, get the signed upload URL for each chunk
        Map<String, String> reqParams = new HashMap<>();
        reqParams.put("uploadId", uploadId);
        // Loop the chunk number starting from 1, as the MinIO storage service defines the chunk index starting from 1
        for (int i = 1; i <= chunkSize; i++) {
            reqParams.put("partNumber", String.valueOf(i));
            String uploadUrl = minioUtil.getPresignedObjectUrl(bucketName, fileName, reqParams);// Get the URL, note that the front end uploads binary stream, not file
            result.put("chunk_" + (i - 1), uploadUrl); // Add to the collection
        }
        return result;
    }


    /**
     * After the chunks are uploaded, merge them
     *
     * @param FileName file name
     * @param uploadId the returned uploadId
     * @return /
     */
    @GetMapping("/completeMultipartUpload")
    @ApiOperation(value = "Merge after uploading chunks")
    @SneakyThrows
    @ResponseBody
    public boolean completeMultipartUpload(String FileName, String uploadId) {
        String bucketName = minioUtil.getUsername();
        LambdaQueryWrapper<Filechunk> Wrapper = new LambdaQueryWrapper<>();
        Wrapper.eq(Filechunk::getUpbucker_name, bucketName).eq(Filechunk::getUploadId, uploadId);
        Filechunk upFile = filechunkService.getOne(Wrapper);
        String objectName = upFile.getIdworker_name();
        try {
            Part[] parts = new Part[10000];
            // 1. Query the chunks
            ListPartsResponse partResult = minioUtil.listMultipart(bucketName, null, objectName, 10000, 0, uploadId, null, null);
            int partNumber = 1; // Chunk sequence starts from 1
//            System.err.println(partResult.result().partList().size() + "========================");
            // 2. Loop through the retrieved chunk information
            List<Part> partList = partResult.result().partList();
            for (int i = 0; i < partList.size(); i++) {
                // 3. Pass the chunk marker to the Part object
                parts[partNumber - 1] = new Part(partNumber, partList.get(i).etag());
                partNumber++;
            }
            minioUtil.completeMultipartUpload(bucketName, null, objectName, uploadId, parts, null, null);
        } catch (Exception e) {
            return false;
        }
        File FileObj = new File();
        FileObj.setFile_bucket_name(bucketName);
        FileObj.setFile_inbuck_name(objectName);
        FileObj.setFile_type(upFile.getFile_type());
        FileObj.set_directory(false);
        FileObj.setFile_url(upFile.getFile_url());
        FileObj.setFile_size(upFile.getFile_size());
        FileObj.setFile_name(upFile.getFile_name());
        FileObj.setFile_MD5(upFile.getFile_MD5());
        fileService.save(FileObj);
        filechunkService.remove(Wrapper);
        return true;

    }


    /**
     * Download the file
     *
     * @param bucketName bucket name
     * @param objectName object name
     * @param response   response result
     */
    @GetMapping("downFile")
    @ApiOperation(value = "Download file")
    public void downLoad(@RequestParam(required = false) String bucketName, String objectName, HttpServletResponse response) {
        bucketName = StringUtils.hasLength(bucketName) ? bucketName : minioUtil.getUsername();
        // Get the file
        minioUtil.downResponse(bucketName, objectName, response);
    }
}
