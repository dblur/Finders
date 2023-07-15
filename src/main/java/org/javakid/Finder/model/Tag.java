package org.javakid.Finder.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tag")
@EqualsAndHashCode(of = {"id", "tagName"})
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String tagName;

    /** TODO when user Entity will be done
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;
    */

}
