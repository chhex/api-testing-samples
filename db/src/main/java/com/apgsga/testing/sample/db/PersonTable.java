package com.apgsga.testing.sample.db;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor = @__(@PersistenceConstructor))
@Table("PERSON")
public class PersonTable {
    @Id
    Integer id;
    String firstName;
    String secondName;
    Date birthDay;
    Integer genderId;
    Integer addressId;
}
