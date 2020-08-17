package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> listNotes(String username){
        return this.noteMapper.listNotes(username);
    }

    public Integer addNote(Note note){
        return this.noteMapper.insert(note);
    }

    public void deleteNote(String noteId){
        this.noteMapper.delete(noteId);
    }
}
