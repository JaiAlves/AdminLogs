package com.systemnow.logs.model;

import com.systemnow.logs.model.enums.EnumTypeLog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "file_content")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileContent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="file_content_seq")
    @SequenceGenerator(name = "file_content_seq", allocationSize=1)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "file_id", nullable = false)
    private FileLoad fileLoad;

    @Column(name = "line")
    private Integer line;

    @Column(name = "type")
    private Integer type;

    @Column(name = "text")
    private String text;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    public String getTypLogToString() {
        return EnumTypeLog.getById(this.type);
    }
}
