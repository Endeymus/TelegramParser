package com.endeymus.parser.entity;

import javax.persistence.*;

/**
 * @author Mark Shamray
 */
@Entity
@Table(name = "user_settings")
public class UserSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_user")
    private User idUser;

//    @Column(name = "count_message_monitoring")
//    private int countMessagesMonitoring;
}
