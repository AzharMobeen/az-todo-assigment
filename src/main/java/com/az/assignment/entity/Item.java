package com.az.assignment.entity;


import com.az.assignment.dto.ActivityStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Table
@AllArgsConstructor
@ToString
@Getter
@Setter
@NoArgsConstructor
public class Item implements Serializable {

    @GeneratedValue
    @Id
    private Long itemId;
    private String activity;
    private ActivityStatus status;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "toDoId")
    private ToDo toDo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Item item = (Item) o;
        return itemId != null && (Objects.equals(itemId, item.itemId) || Objects.equals(activity, item.activity));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}