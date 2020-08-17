package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/home")
public class HomeController {

    private final NoteService noteService;
    private final Logger logger = LoggerFactory.getLogger(HomeController.class);

    public HomeController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public String getHomePage(@ModelAttribute("newNote") Note newNote, Authentication authentication, Model model) {

//        String username = authentication.getName();


        // Notes
//        model.addAttribute("notes", noteService.listNotes(username));


        // Files


        // Credentials


        return "home";
    }

    @PostMapping
    public String addNote(Note newNote, Authentication authentication, Model model) {
        newNote.setUserId(authentication.getName());
        this.noteService.addNote(newNote);
        return "home";
    }
}
