package com.systemnow.logs.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "config_log")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ConfigLog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="config_log_seq")
    @SequenceGenerator(name = "config_log_seq", allocationSize=1)
    private Long id;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "start_pod_name")
    private String startPodName;

    @Column(name = "path_log")
    private String pathLog;

    @Column(name = "except_start_pod_name")
    private String exceptStartPodName;

    @Column(name = "log_name")
    private String logName;

    @Column(name = "name_space")
    private String nameSpace;

    @Column(name = "enum_status_id")
    private Integer enumStatusId;

    /*
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="config_log_id")
    private List<FileLoad> fileLoads;
     */
}
