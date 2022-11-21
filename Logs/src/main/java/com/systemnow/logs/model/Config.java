package com.systemnow.logs.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "config")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Config {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="config_seq")
    @SequenceGenerator(name = "config_seq", allocationSize=1)
    private Long id;

    @Column(name = "name_space")
    private String nameSpace;
}
