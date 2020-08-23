package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CredentialMapper {

    /**
     * Fetch all Credentials by UserID
     */
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<Credential> listCredentials(Integer userId);

    /**
     * Fetch a Credential by ID
     */
    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    Credential getCredentialById(Integer credentialId);

    /**
     * Insert a Credential
     */
    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) " +
            "VALUES(#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    Integer insert(Credential credential);

    /**
     * Update a Credential
     */
    @Update("UPDATE CREDENTIALS SET url=#{credential.url}, username=#{credential.username}, key=#{credential.key}, password=#{credential.password} " +
            "WHERE credentialId = #{credential.credentialId}")
    void update(@Param("credential") Credential credential);

    /**
     * Delete a Credential
     */
    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    void delete (Integer credentialId);
}
