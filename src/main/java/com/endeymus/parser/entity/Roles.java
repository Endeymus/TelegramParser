package com.endeymus.parser.entity;

import javax.persistence.*;
import java.util.List;

/**
 * @author Mark Shamray
 */
@Entity
@Table(name = "roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "id_roles"),
            inverseJoinColumns = @JoinColumn(name = "id_user")
    )
    private List<User> users;
}
