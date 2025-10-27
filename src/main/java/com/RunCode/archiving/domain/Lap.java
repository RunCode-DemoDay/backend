package com.RunCode.archiving.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA용 기본 생성자
@AllArgsConstructor(access = AccessLevel.PRIVATE)  // Builder 내부에서만 사용
@Builder
@Table(name = "laps")
public class Lap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lap_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "archiving_id", nullable = false)
    private Archiving archiving;

    @Column(name = "lap_number", nullable = false)
    private Integer lapNumber;

    @Column(name = "average_pace", nullable = false)
    private String averagePace;

    @Column(name = "pace_variation", nullable = false)
    private String paceVariation;

    @Column(name = "altitude", nullable = false)
    private Integer altitude;


    //생성자
    private Lap(Archiving archiving, Integer lapNumber, String averagePace, String paceVariation, Integer altitude) {
        this.archiving = archiving;
        this.lapNumber = lapNumber;
        this.averagePace = averagePace;
        this.paceVariation = paceVariation;
        this.altitude = altitude;
    }

    //----편의메서드----
    public static Lap create(Archiving archiving, Integer lapNumber, String averagePace, String paceVariation, Integer altitude) {
        Lap lap = new Lap(archiving, lapNumber, averagePace, paceVariation, altitude);
        archiving.getLaps().add(lap);
        return lap;
    }
}
