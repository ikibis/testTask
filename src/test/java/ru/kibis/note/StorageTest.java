/*
package ru.kibis.note;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.kibis.note.model.Note;
import ru.kibis.note.service.NoteService;
import ru.kibis.note.storage.Storage;
import ru.kibis.note.storage.StorageWrapper;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.Assert.assertNotNull;


@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@TestPropertySource("/application.properties")

public class StorageTest {



    @Resource
    private NoteService service;


    @Test
    public void testSaveBank() throws Exception {
      //  service.add("title", "description");
    }
}

  */
/*  @Test
    public void whenLoadContextShouldGetUserStorage() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        DbStorage memory = context.getBean(DbStorage.class);
        memory.add(new User("Ivan"));
        assertNotNull(memory);
    }

    @Test
    public void whenAddUserToDbShouldSafeIt() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        DbStorage memory = context.getBean(DbStorage.class);
        User user = memory.add(new User("Eva"));
        assertThat(memory.findById(user.getId()).getName(), is(user.getName()));
    }*//*


*/
