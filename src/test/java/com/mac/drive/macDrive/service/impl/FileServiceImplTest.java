package com.mac.drive.macDrive.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mac.drive.macDrive.mapper.FileMapper;
import com.mac.drive.macDrive.pojo.File;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@Slf4j
@SpringBootTest
public class FileServiceImplTest {
    @Mock
    private FileMapper fileMapper;

    @InjectMocks
    private FileServiceImpl fileService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetFilesByPath() {
        String bucketName = "testBucket";
        String url = "testUrl";
        Long currentPage = 1L;
        Long pageCount = 5L;
        List<File> mockedList = mock(List.class); // Use a real list if specific verification is needed

        when(fileMapper.getFilesBy(bucketName, url, 0L, pageCount)).thenReturn(mockedList);

        List<File> result = fileService.getFilesByPath(bucketName, url, currentPage, pageCount);

        assertEquals(mockedList, result);
    }

    @Test
    public void testGetFilesBytype() {
        String bucketName = "bucket";
        String fileType = "type";
        Long currentPage = 1L, pageCount = 10L;
        List<File> mockedList = mock(List.class);

        when(fileMapper.getFilesBytype(bucketName, fileType, 0L, pageCount)).thenReturn(mockedList);

        List<File> result = fileService.getFilesBytype(bucketName, fileType, currentPage, pageCount);

        assertEquals(mockedList, result);
    }

    @Test
    public void testListByName() {
        String bucketName = "bucket", filename = "name";
        Long currentPage = 1L, pageCount = 10L;
        List<File> mockedList = mock(List.class);

        when(fileMapper.listByName(bucketName, filename, 0L, pageCount)).thenReturn(mockedList);

        List<File> result = fileService.listByName(bucketName, filename, currentPage, pageCount);

        assertEquals(mockedList, result);
    }

    @Test
    public void testSumBybuck() {
        String username = "user";
        Long expectedSum = 100L;

        when(fileMapper.sumBybuck(username)).thenReturn(expectedSum);

        Long result = fileService.sumBybuck(username);

        assertEquals(expectedSum, result);
    }

    @Test
    public void testUpdatePath() {
        String bucketName = "bucket", oldName = "old", newName = "new", url = "url/";

        fileService.updatePath(bucketName, oldName, newName, url);

        verify(fileMapper).updatePath(bucketName, newName, url + oldName + "/%", oldName);
    }

    @Test
    public void testGetfileRecycle() {
        String bucketName = "bucket";
        Long currentPage = 1L, pageCount = 10L;
        List<File> mockedList = mock(List.class);

        when(fileMapper.getfileRecycle(bucketName, 0L, pageCount)).thenReturn(mockedList);

        List<File> result = fileService.getfileRecycle(bucketName, currentPage, pageCount);

        assertEquals(mockedList, result);
    }

    @Test
    public void testRestoreTrash() {
        String bucketName = "bucket";

        fileService.RestoreTrash(bucketName);

        verify(fileMapper).RestoreTrash(bucketName);
    }

    @Test
    public void testSelectFilePathTreeByBucket() {
        String bucketName = "bucket";
        List<File> mockedList = mock(List.class);

        when(fileMapper.selectList(any(LambdaQueryWrapper.class))).thenReturn(mockedList);

        List<File> result = fileService.selectFilePathTreeByBucket(bucketName);

        assertEquals(mockedList, result);
    }

    @Test
    public void testUpFilePath() {
        String bucketName = "bucket", oldPath = "oldPath", newPath = "newPath";

        fileService.UpFilePath(bucketName, oldPath, newPath);

        verify(fileMapper).UpFilePath(bucketName, newPath, oldPath, oldPath + "%");
    }


    @Test
    public void testSetDelTimeNull() {
        int id = 1;

        fileService.setDelTimeNull(id);

        verify(fileMapper).setDelTimeNull(id);
    }


}