package com.example.news.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String email;

    @NotNull
    private String login;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    private boolean active;

    @ManyToMany
    @JoinTable(
            name = "author_role",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "roles"))
    private Collection<Role> roles;

}
