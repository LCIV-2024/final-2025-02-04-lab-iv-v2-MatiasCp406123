package ar.edu.utn.frc.tup.lciii.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "DEVICE")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "HOSTNAME", unique = true)
    private String hostName;

    @OneToOne(mappedBy = "device")
    private Telemetry telemetry;
    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private DeviceType type;
    @Column(name = "os")
    private String os;
    @Column(name = "createdDate")
    private LocalDateTime createdDate;
    @Column(name = "macAddress",unique = true)
    private String macAddress;
}
