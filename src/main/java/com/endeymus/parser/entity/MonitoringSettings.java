package com.endeymus.parser.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Mark Shamray
 */
@Entity
@Table(name = "monitoring_settings")
@Getter
@Setter
public class MonitoringSettings {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "id_monitoring")
    private Monitoring idMonitoring;

    @Column(name = "analytic")
    private boolean needAnalytic;
}
