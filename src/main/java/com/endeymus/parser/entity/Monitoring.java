package com.endeymus.parser.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Mark Shamray
 */
@Entity
@Table(name = "monitoring")
@Getter
@Setter
@NamedQueries(
        {@NamedQuery(name = Monitoring.SQL_FIND_BY_ID_USER,
                query = "select m from Monitoring m " +
                        "left join fetch m.settings s " +
                        "where m.idUser.id = :id")
                ,
                @NamedQuery(name = Monitoring.SQL_FIND_BY_ID_USER_AND_ID_CHANNEL,
                        query = "select m from Monitoring m " +
                                "left join fetch m.settings s " +
                                "where m.idChannel.id = :id_channel and m.idUser.id = :id_user")
        })
public class Monitoring {
    public static final String SQL_FIND_BY_ID_USER = "Monitoring.findByIdUser";
    public static final String SQL_FIND_BY_ID_USER_AND_ID_CHANNEL = "Monitoring.findByIdUserAndIdChannel";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_channel")
    private Channel idChannel;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User idUser;

    @OneToOne(mappedBy = "idMonitoring", cascade = CascadeType.ALL)
    private MonitoringSettings settings;
}
