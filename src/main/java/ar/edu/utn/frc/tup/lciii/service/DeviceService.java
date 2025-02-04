package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.common.DeviceDto;
import ar.edu.utn.frc.tup.lciii.dtos.common.DeviceInfoDto;
import ar.edu.utn.frc.tup.lciii.model.DeviceType;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface DeviceService {
    HttpStatus postDevice(DeviceDto deviceDto);
    List<DeviceInfoDto>getDeviceByType(DeviceType deviceType);
    List<DeviceInfoDto>getDeviceByCpu(Double lowThreshold,Double upThreshold);
    HttpStatus postRest();
}
