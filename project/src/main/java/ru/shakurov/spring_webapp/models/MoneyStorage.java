package ru.shakurov.spring_webapp.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "money_storage")
@ToString(exclude = "user")
public class MoneyStorage  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "uzer_id")
    private User user;

    private Long balance;
}
