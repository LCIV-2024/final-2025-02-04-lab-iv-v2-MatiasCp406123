package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.dtos.common.TelemetryDto;
import ar.edu.utn.frc.tup.lciii.dtos.common.TelemetryInfoDto;
import ar.edu.utn.frc.tup.lciii.service.TelemetryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TelemetryController {
    @Autowired
    private TelemetryService telemetryService;
    @PostMapping("/api/telemetry")
    public ResponseEntity<HttpStatus>postTelemetry(@RequestBody TelemetryDto telemetryDto){
        return ResponseEntity.ok(telemetryService.postTelemetry(telemetryDto));
    }
    @GetMapping("/api/telemetry")
    public ResponseEntity<List<TelemetryInfoDto>> getTelemetrys(@RequestParam(required = false)String hostname){
        if(hostname.isEmpty()){
            return ResponseEntity.ok(telemetryService.getAllTelemetry());
        }
        return ResponseEntity.ok(telemetryService.getByHost(hostname));
    }
}