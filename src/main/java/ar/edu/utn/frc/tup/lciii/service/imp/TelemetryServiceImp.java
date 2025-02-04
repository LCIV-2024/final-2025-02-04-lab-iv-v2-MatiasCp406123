package ar.edu.utn.frc.tup.lciii.service.imp;

import ar.edu.utn.frc.tup.lciii.dtos.common.TelemetryDto;
import ar.edu.utn.frc.tup.lciii.dtos.common.TelemetryInfoDto;
import ar.edu.utn.frc.tup.lciii.model.Device;
import ar.edu.utn.frc.tup.lciii.model.Telemetry;
import ar.edu.utn.frc.tup.lciii.repository.DeviceRepository;
import ar.edu.utn.frc.tup.lciii.repository.TelemetryRepository;
import ar.edu.utn.frc.tup.lciii.service.TelemetryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TelemetryServiceImp implements TelemetryService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private TelemetryRepository telemetryRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Override
    public HttpStatus postTelemetry(TelemetryDto telemetryDto) {
        Optional<Device>deviceOptional=this.deviceRepository.getDeviceByHostName(telemetryDto.getHostname());
        if(deviceOptional.isEmpty()){
            return HttpStatus.BAD_REQUEST;
        }
        Telemetry telemetry=modelMapper.map(telemetryDto, Telemetry.class);
        telemetry.setDataDate(LocalDateTime.now());
        telemetry=this.telemetryRepository.save(telemetry);
        return HttpStatus.CREATED;
    }

    @Override
    public List<TelemetryInfoDto> getAllTelemetry() {
        return telemetryRepository.findAll().stream()
                .map(telemetry -> modelMapper.map(telemetry, TelemetryInfoDto.class))
                .toList();
    }

    @Override
    public List<TelemetryInfoDto> getByHost(String hostname) {
        return telemetryRepository.findAllByDeviceHostName(hostname).stream()
                .map(telemetry -> modelMapper.map(telemetry, TelemetryInfoDto.class))
                .toList();
    }
}
