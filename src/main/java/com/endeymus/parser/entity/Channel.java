package com.endeymus.parser.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * @author Mark Shamray
 */
@Entity
@Table(name = "channel")
@Getter
@Setter
@NamedQueries({
        @NamedQuery(name = Channel.FIND_BY_ID_INTERNAL,
                query = "select distinct c from Channel c " +
                        "left join fetch c.posts p " +
                        "where c.idInternal = :id_internal")
})
public class Channel {
    public static final String FIND_BY_ID_INTERNAL = "Channel.findByIdInternal";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_parent_channel")
    private Channel idParentChannel;

    @OneToMany(mappedBy = "idChannel", fetch = FetchType.EAGER)
    private List<Monitoring> monitoringList;

    @ManyToMany
    @JoinTable(name = "client_channel",
            joinColumns = @JoinColumn(name = "id_channel"),
            inverseJoinColumns = @JoinColumn(name = "id_client")
    )
    private List<Client> clients;

    @OneToMany(mappedBy = "idChannel")
    private List<Posts> posts;

    @OneToMany(mappedBy = "idParentChannel")
    private List<Channel> children;

    @Column(name = "id_internal")
    private Long idInternal;
}
