package ru.kibis.note.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "notes",
        indexes = @Index(
                name = "idx_title_description",
                columnList = "title, description",
                unique = false)
)
public class Note {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;

    public Note(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Note() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
