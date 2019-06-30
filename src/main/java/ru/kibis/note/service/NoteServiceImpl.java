package ru.kibis.note.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kibis.note.model.Note;
import ru.kibis.note.storage.Storage;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    private final Storage storage;

    @Autowired
    public NoteServiceImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public Note add(String title, String description) {
        Note note = new Note(title, description);
        return this.storage.add(note);
    }

    @Override
    public int delete(int id) {
        return this.storage.delete(id);
    }

    @Override
    public Note update(int id, String titleToUpdate, String descriptionToUpdate) {
        Note noteToUpdate = this.storage.findById(id);
        return this.storage.update(noteToUpdate, titleToUpdate, descriptionToUpdate);
    }

    @Override
    public List findAll() {
        return this.storage.findAll();
    }

    @Override
    public List findByString(String stringToSearch) {
        return this.storage.findByString(stringToSearch);
    }
}
