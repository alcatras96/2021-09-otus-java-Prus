package ru.otus.crm.model;

import com.google.gson.annotations.Expose;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "admin")
public class Admin implements Cloneable {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Expose
    @Column(name = "login")
    private String login;
    @Expose
    @Column(name = "password")
    private String password;

    @Override
    public Admin clone() {
        return new Admin(this.id, this.login, this.password);
    }
}
