package ru.otus.crm.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table("client")
public class Client implements Cloneable {
    @Id
    private Long id;

    @Nonnull
    private String name;

    @MappedCollection(idColumn = "client_id")
    private Address address;

    @MappedCollection(idColumn = "client_id")
    private Set<Phone> phones = new HashSet<>();

    @Override
    public Client clone() {
        return new Client(this.id, this.name,
                Optional.ofNullable(this.address).map(Address::clone).orElse(null),
                Optional.ofNullable(this.phones).map(Collection::stream)
                        .map(phoneStream -> phoneStream.map(Phone::clone)
                                .collect(Collectors.toSet()))
                        .orElse(null)
        );
    }

    public String getPhoneValues() {
        return phones.stream().map(Phone::getNumber).collect(Collectors.joining(","));
    }
}
