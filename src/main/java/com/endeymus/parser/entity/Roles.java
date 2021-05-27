package com.endeymus.parser.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * @author Mark Shamray
 */
@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    public Roles(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "id_roles"),
            inverseJoinColumns = @JoinColumn(name = "id_user")
    )
    private List<User> users;
}
