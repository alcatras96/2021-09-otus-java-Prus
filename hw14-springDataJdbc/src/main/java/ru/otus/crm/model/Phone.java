package ru.otus.crm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.annotation.Nonnull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("phone")
public class Phone implements Cloneable {
    @Id
    private Long id;
    @Nonnull
    private String number;

    @Nonnull
    private String clientId;

    @Override
    public Phone clone() {
        return new Phone(id, number, clientId);
    }
}
