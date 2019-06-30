package ru.kibis.note;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.kibis.note.controller.NoteController;
import ru.kibis.note.model.Note;
import ru.kibis.note.service.NoteService;

import java.util.ArrayList;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(NoteController.class)
public class NoteControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private NoteService service;

    @Test
    public void checkIndexPage() throws Exception {
        this.mvc.perform(
                get("/index.html").accept(MediaType.TEXT_HTML)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void whenAddNoteThenCheckIt() throws Exception {
        this.mvc.perform(
                post("/add")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("title", "title")
                        .param("description", "description")
        ).andExpect(status().isOk());
        verify(this.service, times(1)).add("title", "description");
    }

    @Test
    public void whenGetNotesThenCheckIt() throws Exception {
        given(this.service.findAll())
                .willReturn(
                        new ArrayList<>(
                                Lists.newArrayList(new Note("description", "description"))));
        this.mvc.perform(
                get("/notes")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void whenSearchNotesThenCheckIt() throws Exception {
        given(this.service.findByString("stringToSearch"))
                .willReturn(
                        new ArrayList<>(
                                Lists.newArrayList(new Note("description", "description"))));
        this.mvc.perform(
                post("/notes")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("stringToSearch", "stringToSearch")
        ).andExpect(status().isOk());
    }
}