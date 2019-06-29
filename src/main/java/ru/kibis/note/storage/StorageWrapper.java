package ru.kibis.note.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class StorageWrapper {
    private static final Logger LOGGER = LogManager.getLogger(StorageWrapper.class.getName());

    public <T> T tx(final Function<Session, T> command) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        final Session session = factory.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            return command.apply(session);
        } catch (final Exception e) {
            session.getTransaction().rollback();
            LOGGER.error(e.getMessage());
            throw e;
        } finally {
            tx.commit();
            session.close();
        }
    }
}
