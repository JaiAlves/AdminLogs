package com.systemnow.logs.model;

import com.systemnow.logs.model.enums.EnumStatus;
import com.systemnow.logs.model.enums.EnumStep;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "load_step")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoadStep {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="load_step_seq")
    @SequenceGenerator(name = "load_step_seq", allocationSize=1)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "load_id", nullable = false)
    private Load load;

    @Column(name = "step")
    private Integer step;

    @Column(name = "status")
    private Integer status;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    public String getStepToString() {
        return EnumStep.getById(this.step);
    }

    public String getStatusToString() {
        return EnumStatus.getById(this.status);
    }

}
