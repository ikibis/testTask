package ru.kibis.note.service;

import ru.kibis.note.model.Note;

import java.util.List;

public interface NotesService {
    Note add(String title, String description);

    void delete(int id);

    List findAll();

    List findByString(String stringToSearch);
}