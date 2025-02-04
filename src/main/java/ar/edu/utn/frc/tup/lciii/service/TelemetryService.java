package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.common.TelemetryDto;
import ar.edu.utn.frc.tup.lciii.dtos.common.TelemetryInfoDto;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface TelemetryService {
    HttpStatus postTelemetry(TelemetryDto telemetryDto);
    List<TelemetryInfoDto>getAllTelemetry();
    List<TelemetryInfoDto>getByHost(String hostname);
}
