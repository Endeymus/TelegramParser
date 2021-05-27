package com.endeymus.parser.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "login")
    private String login;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "hash_key")
    private int hashKey;

    public void generateHash() {
        int result = 17;
        int c = (int)(id ^ (id >>> 32));

        result = 37 * result + c;
        c = name.hashCode();
        result = 37 * result + c;
        c = password.hashCode();
        result = 37 * result + c;
        c = email.hashCode();
        result = 37 * result + c;

        hashKey = result;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "idUser", fetch = FetchType.EAGER)
    private List<Monitoring> monitoringList;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_roles")
    )
    private Set<Roles> roles;

    @JsonIgnore
    @OneToOne(mappedBy = "idUser")
    private UserSettings settings;
}
