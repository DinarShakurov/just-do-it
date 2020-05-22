package ru.shakurov.spring_webapp.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "uzer")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Email
    private String email;

    @Column(name = "hash_password")
    private String hashPassword;

    private String name;

    private String alias;

    @Column(name = "confirmation_link")
    private String confirmationLink;

    @Enumerated(EnumType.STRING)
    private State state;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(mappedBy = "user")
    private MoneyStorage moneyStorage;
}
