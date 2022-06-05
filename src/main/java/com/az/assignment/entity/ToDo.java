package com.az.assignment.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table
@AllArgsConstructor
@ToString
@Getter
@Setter
@NoArgsConstructor
public class ToDo implements Serializable {

    @GeneratedValue
    @Id
    private Long toDoId;
    @NotEmpty(message = "Please provide to do activity type")
    private String type;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "toDo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Item> todoItems;

    @JsonIgnore
    @Column(name = "userId")
    private Long createdBy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ToDo toDo = (ToDo) o;
        return toDoId != null && Objects.equals(toDoId, toDo.toDoId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
