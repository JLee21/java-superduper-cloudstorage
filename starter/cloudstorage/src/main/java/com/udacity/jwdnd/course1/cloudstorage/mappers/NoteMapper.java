package com.udacity.jwdnd.course1.cloudstorage.mappers;

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
public interface NoteMapper {

    /**
     * Fetch all Notes belonging to a User
     *
     * @param userId
     * @return
     */
    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Note> listNotes(Integer userId);

    /**
     * Insert a Note
     *
     * @param note`
     * @return
     */
    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) " +
            "VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer insert(Note note);

    /**
     * Update a Note
     *
     * @param note
     */
    @Update("UPDATE NOTES SET notetitle = #{note.noteTitle}, notedescription = #{note.noteDescription}, userId = #{note.userId} WHERE noteid = #{note.noteId}")
    void update(@Param("note") Note note);

    /**
     * Delete a Note
     *
     * @param noteId
     */
    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
    void delete(Integer noteId);
}
