package com.endeymus.parser.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author Mark Shamray
 */
@Entity
@Table(name = "client")
@Getter
@Setter
@NamedQueries(
        @NamedQuery(name = Client.FIND_BY_ID_INTERNAL,
        query = "select distinct c from Client c " +
                "where c.idInternal = :id_internal")
)
public class Client {
    public static final String FIND_BY_ID_INTERNAL = "Client.findByIdInternal";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "activity")
    @Temporal(TemporalType.DATE)
    private Date activity;

    @ManyToMany
    @JoinTable(name = "client_channel",
            joinColumns = @JoinColumn(name = "id_client"),
            inverseJoinColumns = @JoinColumn(name = "id_channel")
    )
    private List<Channel> channels;

    @Column(name = "id_internal")
    private Long idInternal;

    @Column(name = "phone")
    private String phone;
}
