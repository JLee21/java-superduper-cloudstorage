package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class FileController {

    private NoteService noteService;
    private UserService userService;
    private FileService fileService;
    private CredentialService credentialService;

    public FileController(NoteService noteService,
                          FileService fileService,
                          UserService userService,
                          CredentialService credentialService) {
        this.noteService = noteService;
        this.fileService = fileService;
        this.userService = userService;
        this.credentialService = credentialService;
    }

    // Files
//    @GetMapping("/file/view/{fileId}")
//    public ResponseEntity<Resource> getFile(@PathVariable Long fileId, Authentication authentication) {
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(file.getType()))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
//                .body(new ByteArrayResource(file.getData()));
//    }

    @PostMapping("/file-upload")
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile fileUpload,
                                   Authentication auth, Model model) throws IOException {

        User currentUser = this.userService.getUser(auth.getName());

        // Check if the user already submitted a file with the same name
//        if (true) {
        if (this.fileService.isFilenameAvailable(fileUpload.getOriginalFilename(), currentUser.getUserId())) {
            try {
                this.fileService.addFile(fileUpload, currentUser.getUserId());
                model.addAttribute("fileSuccess", fileUpload.getOriginalFilename() + " uploaded successfully");
            } catch (Exception err) {
                model.addAttribute("fileError", "Failed to upload " + fileUpload.getOriginalFilename());
            }
        } else {
            model.addAttribute("fileError", fileUpload.getOriginalFilename() + " is already taken");
        }

        // Refresh data
        model.addAttribute("files", this.fileService.list(currentUser.getUserId()));
        model.addAttribute("notes", this.noteService.listNotes(currentUser.getUserId()));
        model.addAttribute("credentials", this.credentialService.listCredentials(currentUser.getUserId()));
        return "home";
    }

    @RequestMapping("/file-delete/{fileId}")
    public String deleteFile(@PathVariable("fileId") Long fileId,
                             Authentication auth,
                             Model model) {
        User currentUser = this.userService.getUser(auth.getName());

        try {
            this.fileService.deleteFile(fileId, currentUser.getUserId());
            model.addAttribute("fileSuccess", "File deleted");
        } catch (Exception err) {
            model.addAttribute("fileError", "Failed to delete file ");
        }

        // Refresh data
        model.addAttribute("files", this.fileService.list(currentUser.getUserId()));
        model.addAttribute("notes", this.noteService.listNotes(currentUser.getUserId()));
        model.addAttribute("credentials", this.credentialService.listCredentials(currentUser.getUserId()));
        return "home";
    }
}
