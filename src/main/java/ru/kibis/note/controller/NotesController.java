package ru.kibis.note.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.kibis.note.model.Note;
import ru.kibis.note.service.NotesService;

import java.util.List;

@Controller
public class NotesController {

    private final NotesService notesService;

    @Autowired
    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    @PostMapping("/add")
    @ResponseBody
    public Note addNote(
            @RequestParam("title") String title,
            @RequestParam("description") String description
    ) {
        return this.notesService.add(title, description);
    }

    @PostMapping(value = "/notes", produces = "application/json")
    @ResponseBody
    public List findNotesByString(
            @RequestParam("stringToSearch") String stringToSearch
    ) {
        return this.notesService.findByString(stringToSearch);
    }

    @GetMapping(value = "/notes", produces = "application/json")
    @ResponseBody
    public List findAllNotes() {
        return this.notesService.findAll();
    }

    @PostMapping("/delete")
    public void deleteNote(
            @RequestParam("id") int id
    ) {
        this.notesService.delete(id);
    }
}