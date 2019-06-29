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

    public void delete(int id) {
        this.storageWrapper.tx(session -> {
                    Query query = session.createQuery("delete from Note where id = " + id);
                    query.executeUpdate();
                    return null;
                }
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