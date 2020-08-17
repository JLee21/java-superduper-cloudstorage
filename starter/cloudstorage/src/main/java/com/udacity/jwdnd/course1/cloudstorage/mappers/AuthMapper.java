package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AuthMapper {

    /**
     * Fetch all Credentials belonging to a User
     */
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    Credential[] listCreds(String userId);

    /**
     * Insert a Credential
     */
    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) " +
            "VALUES(#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insert(Credential credential);

    /**
     * Update a Credential
     */
    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username} key = #{key} password=#{password} userId=#{userId}" +
            "WHERE credentialid =# {credentialId}")
    void update(Note note);

    /**
     * Delete a Credential
     */
    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    void delete (String credentialId);
}
