package ru.itmo.angry_beavers.model;

import lombok.*;

import javax.persistence.*;

@Table(name = "points")
@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PointQ {

    public PointQ(Double x, Double y, Double r, boolean inArea, String queryTime) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.inArea = inArea;
        this.queryTime = queryTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(nullable = false)
    private Double x;

    @Column(nullable = false)
    private Double y;

    @Column(nullable = false)
    private Double r;

    @Column(nullable = false)
    private boolean inArea;

    @Column(nullable = false)
    private String queryTime;

}
