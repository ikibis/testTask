package ru.kibis.note.service;

import ru.kibis.note.model.Note;

import java.util.List;

public interface NoteService {
    Note add(String title, String description);

    int delete(int id);

    Note update(int id, String title, String description);

    List findAll();

    List findByString(String stringToSearch);
}