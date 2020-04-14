package ru.shakurov.spring_webapp.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "goal")
@ToString(exclude = "user")
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "uzer_id")
    private User user;

    private String title;

    private String description;

    @Min(1)
    private Long money;

    @Max(31708800000L)
    @Min(60000L)
    private Long duration;

    @Max(100)
    @Min(0)
    private Integer result;

    @Column(name = "visible_for_other")
    private boolean visibleFotOther;

    @Enumerated(EnumType.STRING)
    private GoalState state;
}
