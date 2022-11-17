package mk.ukim.finki.wp.lab.model;

import lombok.Data;
import mk.ukim.finki.wp.lab.utils.RepoUtils;

@Data
public class Teacher {
    private Long id;
    private String name;
    private String surname;

    public Teacher(String name, String surname) {
        this.id = RepoUtils.generatePrimaryKey();
        this.name = name;
        this.surname = surname;
    }
}
