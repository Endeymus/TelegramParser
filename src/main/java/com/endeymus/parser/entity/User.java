package com.endeymus.parser.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * @author Mark Shamray
 */
@Entity
@Table(name = "user")
@Getter
@Setter
@NamedQueries({
        @NamedQuery(name = User.SQL_FIND_WITH_DETAILS,
                query = "select u from User u " +
                        "left join fetch u.monitoringList m " +
                        "left join fetch m.settings s " +
                        "where u.id = :id"),
        @NamedQuery(name = User.SQL_FIND_ALL_WITH_DETAILS,
                query = "select u from User u " +
                        "left join fetch u.monitoringList m " +
                        "left join fetch m.settings s ")
})
public class User {
    public static final String SQL_FIND_WITH_DETAILS = "User.findWithDetails";
    public static final String SQL_FIND_ALL_WITH_DETAILS = "User.findAllWithDetails";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "idUser", fetch = FetchType.EAGER)
    private List<Monitoring> monitoringList;

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_roles")
    )
    private List<Roles> roles;

    @OneToOne(mappedBy = "idUser")
    private UserSettings settings;
}
