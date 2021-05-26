package com.apgsga.testing.sample.db;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor = @__(@PersistenceConstructor))
@Table("PERSON_HOBBY")
public class PersonHobbyTable {
    @Id
    Integer id;
    Integer personId;
    Integer hobbyId;
}
