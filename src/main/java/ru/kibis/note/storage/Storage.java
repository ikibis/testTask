package ru.kibis.note.storage;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kibis.note.model.Note;

import java.util.List;

@Component
public class Storage {
    private final StorageWrapper storageWrapper;

    @Autowired
    public Storage(StorageWrapper storageWrapper) {
        this.storageWrapper = storageWrapper;
    }

    public Note add(Note note) {
        return storageWrapper.tx(session -> {
            session.saveOrUpdate(note);
            return note;
        });
    }

    public int delete(int id) {
        return this.storageWrapper.tx(session -> {
                    Query query = session.createQuery("delete from Note where id = " + id);
                    query.executeUpdate();
                    return id;
                }
        );
    }

    public Note update(Note note, String titleToUpdate, String descriptionToUpdate) {
        return this.storageWrapper.tx(session -> {
                    note.setTitle(titleToUpdate);
                    note.setDescription(descriptionToUpdate);
                    session.saveOrUpdate(note);
                    return note;
                }
        );
    }

    public Note findById(int id) {
        return this.storageWrapper.tx(session ->
                (Note) session.createQuery("from Note where id = " + id).uniqueResult()
        );
    }

    public List findAll() {
        return this.storageWrapper.tx(
                session -> session.createQuery("from Note").list()
        );
    }

    public List findByString(String stringToSearch) {
        return this.storageWrapper.tx(session ->
                session.createQuery("from Note where (title || description) like '%" + stringToSearch + "%'").list()
        );
    }
}