package com.mac.drive.macDrive.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mac.drive.macDrive.pojo.File;

import java.util.List;

public interface FileMapper extends BaseMapper<File> {


    /**
     * 根据桶名与路径遍历文件
     * @param bucketName
     * @param url
     * @return
     */
    List<File> getFilesBy(String bucketName, String url, Long beginCount, Long pageCount);

    /**
     * 根据类型搜索文件
     * @param bucketName
     * @param fileType
     * @return
     */
    List<File> getFilesBytype(String bucketName, String fileType, Long beginCount, Long pageCount);

    /**
     * 根据文件名模糊查询
     * @return
     */
    List<File> listByName(String bucketName, String filename, Long beginCount, Long pageCount);


    /**
     * 用户占用存储大小
     * @param username
     * @return
     */
    Long sumBybuck(String username);

    /**
     * 修改路径
     * @param bucketName
     * @param fileNewName
     * @param url
     * @param fileOldName
     */
    void updatePath(String bucketName, String fileNewName, String url, String fileOldName);

    /**
     * 遍历回收站文件
     * @param bucketName
     * @param beginCount
     * @param pageCount
     * @return
     */
    List<File> getfileRecycle(String bucketName, Long beginCount, Long pageCount);

    /**
     * 还原回收站
     * @param bucketName
     */
    void RestoreTrash(String bucketName);

    /**
     * 移动文件时批量路径修改
     * @param bucketName
     * @param fileNewPath
     * @param fileOldPath
     * @param fileURL
     */
    void UpFilePath(String bucketName, String fileNewPath, String fileOldPath, String fileURL);

    /**
     * 还原文件是设置删除时间为null
     * @param id
     */
    void setDelTimeNull(int id);
}
