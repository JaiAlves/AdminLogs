package com.systemnow.logs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "file")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileLoad {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="file_seq")
    @SequenceGenerator(name = "file_seq", allocationSize=1)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "load_id", nullable = false)
    private Load load;

    @Column(name = "name")
    private String name;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="file_id")
    private List<FileContent> fileContents;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FileLoad{");
        sb.append("id=").append(id);
        //sb.append(", load=").append(load);
        sb.append(", name='").append(name).append('\'');
        sb.append(", startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
        //sb.append(", fileContents=").append(fileContents);
        sb.append('}');
        return sb.toString();
    }
}
