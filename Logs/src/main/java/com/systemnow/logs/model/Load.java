package com.systemnow.logs.model;

import com.systemnow.logs.model.enums.EnumStep;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "load")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Load {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="load_seq")
    @SequenceGenerator(name = "load_seq", allocationSize=1)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="load_id")
    private List<FileLoad> fileLoads;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="load_id")
    private List<LoadStep> loadSteps;

    public LoadStep getByStep(EnumStep step) {
        for (LoadStep loadStep: this.loadSteps) {
            if (loadStep.getStep().equals(step.getId())) {
                return loadStep;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Load{");
        sb.append("id=").append(id);
        sb.append(", startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
        //sb.append(", fileLoads.size()=").append(fileLoads.size());
        //sb.append(", loadSteps=.size()").append(loadSteps.size());
        sb.append('}');
        return sb.toString();
    }
}
