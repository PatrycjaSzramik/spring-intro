package pl.sda.projects.adverts.model.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="adverts")
@Getter @Setter
@ToString()
@EqualsAndHashCode(of="id")
@AllArgsConstructor
@NoArgsConstructor @Builder

public class Advert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable=false)
    @ColumnDefault("now()")
    private LocalDateTime posted;

    @ManyToOne(optional=false)
    @JoinColumn(name = "user_id")
    private User user_id;
    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;
}
