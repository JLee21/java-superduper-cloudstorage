package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface NoteMapper {

    /**
     * Fetch all Notes belonging to a User
     *
     * @param userId
     * @return
     */
    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Note> listNotes(String userId);

    /**
     * Insert a Note
     *
     * @param note`
     * @return
     */
    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) " +
            "VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);

    /**
     * Update a Note
     *
     * @param note
     */
    @Update("UPDATE NOTES SET notetitle =# {noteTitle}, notedescription =# {noteDescription} " +
            "WHERE noteid =# {noteId}")
    void update(Note note);

    /**
     * Delete a Note
     *
     * @param noteId
     */
    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
    void delete(String noteId);
}
