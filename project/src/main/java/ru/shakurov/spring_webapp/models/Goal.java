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
    public static final Long MAX_DURATION = 31708800000L;
    public static final Long MIN_DURATION = 60000L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "uzer_id")
    private User user;

    private String title;

    private String description;

    private Long money;

    private Long duration;

    private Integer result;

    @Column(name = "visible_for_other")
    private boolean visibleFotOther;

    @Enumerated(EnumType.STRING)
    private GoalState state;
}
