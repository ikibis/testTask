package ru.kibis.note.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.kibis.note.model.Note;
import ru.kibis.note.service.NoteService;

import java.util.List;

@Controller
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/add")
    @ResponseBody
    public Note addNote(
            @RequestParam("title") String title,
            @RequestParam("description") String description
    ) {
        return this.noteService.add(title, description);
    }

    @PostMapping(value = "/notes", produces = "application/json")
    @ResponseBody
    public List findNotesByString(
            @RequestParam("stringToSearch") String stringToSearch
    ) {
        return this.noteService.findByString(stringToSearch);
    }

    @GetMapping(value = "/notes", produces = "application/json")
    @ResponseBody
    public List findAllNotes() {
        return this.noteService.findAll();
    }

    @PostMapping("/delete")
    @ResponseBody
    public int deleteNote(
            @RequestParam("id") int id
    ) {
        return this.noteService.delete(id);
    }

    @PostMapping("/update")
    @ResponseBody
    public Note updateNote(
            @RequestParam("id") int id,
            @RequestParam("title") String title,
            @RequestParam("description") String description
    ) {
        return this.noteService.update(id, title, description);
    }
}