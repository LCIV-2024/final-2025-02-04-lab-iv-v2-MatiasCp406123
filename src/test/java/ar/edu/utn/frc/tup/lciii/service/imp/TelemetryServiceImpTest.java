package ar.edu.utn.frc.tup.lciii.service.imp;

import ar.edu.utn.frc.tup.lciii.dtos.common.TelemetryDto;
import ar.edu.utn.frc.tup.lciii.dtos.common.TelemetryInfoDto;
import ar.edu.utn.frc.tup.lciii.model.Device;
import ar.edu.utn.frc.tup.lciii.model.DeviceType;
import ar.edu.utn.frc.tup.lciii.model.Telemetry;
import ar.edu.utn.frc.tup.lciii.repository.DeviceRepository;
import ar.edu.utn.frc.tup.lciii.repository.TelemetryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class TelemetryServiceImpTest {
    @InjectMocks
    private TelemetryServiceImp telemetryServiceImp;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private TelemetryRepository telemetryRepository;
    @Mock
    private DeviceRepository deviceRepository;
    private Telemetry telemetry;
    private TelemetryDto telemetryDto;
    private TelemetryInfoDto telemetryInfoDto;
    private Device device;

    @BeforeEach
    void setUp(){
        device=new Device().toBuilder().id(1L)
                .createdDate(LocalDateTime.now())
                .macAddress("00:1A:2B:3C:4D:5E")
                .os("Windows 11")
                .hostName("PC-001")
                .telemetry(telemetry)
                .type(DeviceType.Laptop).build();
        telemetry=new Telemetry().toBuilder()
                .id(1L).cpuUsage(40.0)
                .dataDate(LocalDateTime.now())
                .hostDiskFree(100.0)
                .device(device).build();
        telemetryDto=new TelemetryDto().toBuilder()
                .ip("1233")
                .hostname("PC-001")
                .dataDate(LocalDateTime.now())
                .hostDiskFree(100.0).build();
    }
    @Test
    void postTelemetry() {
        Mockito.when(modelMapper.map(telemetryDto, Telemetry.class)).thenReturn(telemetry);
        Mockito.when(telemetryRepository.save(telemetry)).thenReturn(telemetry);
        Mockito.when(deviceRepository.getDeviceByHostName(telemetryDto.getHostname())).thenReturn(Optional.empty());
        HttpStatus response=telemetryServiceImp.postTelemetry(telemetryDto);
        Assertions.assertEquals(HttpStatus.OK,response);
    }

    @Test
    void getAllTelemetry() {
    }

    @Test
    void getByHost() {
    }
}