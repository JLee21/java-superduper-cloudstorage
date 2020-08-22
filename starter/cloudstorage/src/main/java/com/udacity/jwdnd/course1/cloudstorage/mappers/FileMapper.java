package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileMapper {

    /**
     * Fetch all Files belonging to a User
     *
     * @param userId
     * @return
     */
    @Select("SELECT fileId, fileName FROM FILES WHERE userid=#{userId}")
    List<File> list(Integer userId);

    /**
     * Insert a File
     *
     * @param file`
     * @return
     */
    @Insert("INSERT INTO FILES (filename , filesize , filedata , contenttype , userid ) "
            + " VALUES ( #{fileName}, #{fileSize}, #{fileData}, #{contentType}, #{userId} ) ")
    @Options(useGeneratedKeys = true, keyColumn = "fileid", keyProperty = "fileId")
    Integer insert(File file);

    /**
     * Delete a File
     *
     * @param fileId
     */
    @Delete("DELETE FROM FILES WHERE fileid = #{fileId} AND userid = #{userId}")
    void delete(Long fileId, Integer userId);

    /**
     * Fetch a File with filename belonging to userId
     */
    @Select("SELECT * FROM FILES WHERE filename = #{fileName} AND userid = #{userId}")
    List<File> listFilesWithFilenameAndUserId(String fileName, Integer userId);

}
