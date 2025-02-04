package ar.edu.utn.frc.tup.lciii.service.imp;

import ar.edu.utn.frc.tup.lciii.Client.ClientRest;
import ar.edu.utn.frc.tup.lciii.dtos.common.DeviceDto;
import ar.edu.utn.frc.tup.lciii.dtos.common.DeviceInfoDto;
import ar.edu.utn.frc.tup.lciii.model.Device;
import ar.edu.utn.frc.tup.lciii.model.DeviceType;
import ar.edu.utn.frc.tup.lciii.repository.DeviceRepository;
import ar.edu.utn.frc.tup.lciii.service.DeviceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class DeviceSeviceImp implements DeviceService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private ClientRest clientRest;
    @Override
    public HttpStatus postDevice(DeviceDto deviceDto) {
        Optional<Device> deviceOptional=deviceRepository.getDeviceByHostName(deviceDto.getHostName());
        if(deviceOptional.isPresent()){
            return HttpStatus.BAD_REQUEST;
        }
        Device device=modelMapper.map(deviceDto, Device.class);
        device.setCreatedDate(LocalDateTime.now());
        device=this.deviceRepository.save(device);
        return HttpStatus.CREATED;
    }

    @Override
    public List<DeviceInfoDto> getDeviceByType(DeviceType deviceType) {
        return deviceRepository.getDeviceByType(deviceType).stream()
                .map(device -> modelMapper.map(device, DeviceInfoDto.class)).toList();
    }

    @Override
    public List<DeviceInfoDto> getDeviceByCpu(Double lowThreshold, Double upThreshold) {
        if(lowThreshold>upThreshold){
            throw new IllegalStateException("400 bad request");
        }
       List<Device>deviceList=deviceRepository.findAll();
       deviceList.stream().filter(device ->
               device.getTelemetry().getCpuUsage()>=lowThreshold&&device.getTelemetry().getCpuUsage()<=upThreshold);
       return deviceList.stream().map(device -> modelMapper.map(device, DeviceInfoDto.class)).toList();
    }

    @Override
    public HttpStatus postRest() {
        DeviceInfoDto[]deviceInfoDtoList=clientRest.deviceAll().getBody();
        Arrays.stream(deviceInfoDtoList).sorted();
        for(DeviceInfoDto dev :deviceInfoDtoList) {
            for (int i = 0; i < 5; i++) {
                Device device=modelMapper.map(dev, Device.class);
                deviceRepository.save(device);

            }
        }
        return HttpStatus.CREATED;
    }
}
