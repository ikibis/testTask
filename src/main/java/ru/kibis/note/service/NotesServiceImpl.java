package ru.kibis.note.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kibis.note.model.Note;
import ru.kibis.note.storage.Storage;

import java.util.List;

@Service
public class NotesServiceImpl implements NotesService {

    private final Storage storage;

    @Autowired
    public NotesServiceImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public Note add(String title, String description) {
        String titleToWrite = title.equals("") ? description : title.substring(0, 200);
        Note note = new Note(titleToWrite, description);
        return this.storage.add(note);
    }

    @Override
    public void delete(int id) {
        this.storage.delete(id);
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
