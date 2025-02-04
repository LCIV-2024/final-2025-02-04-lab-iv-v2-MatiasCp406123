package ar.edu.utn.frc.tup.lciii.service.imp;

import ar.edu.utn.frc.tup.lciii.dtos.common.DeviceDto;
import ar.edu.utn.frc.tup.lciii.dtos.common.DeviceInfoDto;
import ar.edu.utn.frc.tup.lciii.model.Device;
import ar.edu.utn.frc.tup.lciii.model.DeviceType;
import ar.edu.utn.frc.tup.lciii.model.Telemetry;
import ar.edu.utn.frc.tup.lciii.repository.DeviceRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class DeviceSeviceImpTest {

    @InjectMocks
    private DeviceSeviceImp deviceSeviceImp;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private DeviceRepository deviceRepository;

    private Device device;
    private Device device2;
    private DeviceDto deviceDto;
    private DeviceInfoDto deviceInfoDto;
    private DeviceInfoDto deviceInfoDto2;
    private List<Device> deviceList;
    private List<Device>deviceFilt;
    private List<DeviceInfoDto>deviceInfoDtoList;
    @BeforeEach
    void setUp(){
        Telemetry telemetry=new Telemetry();
        telemetry.setId(1L);
        telemetry.setDevice(device);
        telemetry.setCpuUsage(40.0);
        device=new Device().toBuilder().id(1L)
                .createdDate(LocalDateTime.now())
                .macAddress("00:1A:2B:3C:4D:5E")
                .os("Windows 11")
                .hostName("PC-001")
                .telemetry(telemetry)
                .type(DeviceType.Laptop).build();

        device2=new Device().toBuilder()
                .id(2L)
                .macAddress("00:2A:2B:3C:4D:5E")
                .os("Windows 10")
                .hostName("PC-002")
                .type(DeviceType.Laptop).build();
        deviceDto=new DeviceDto().toBuilder()
                .type(DeviceType.Laptop)
                .macAddress("00:1A:2B:3C:4D:5E")
                .os("Windows 11")
                .hostName("PC-001").build();
        deviceInfoDto=new DeviceInfoDto().toBuilder()
                .id(1L)
                .macAddress("00:1A:2B:3C:4D:5E")
                .os("Windows 11")
                .hostName("PC-001")
                .type(DeviceType.Laptop).build();
        deviceInfoDto2=new DeviceInfoDto().toBuilder()
                .id(2L)
                .macAddress("00:2A:2B:3C:4D:5E")
                .os("Windows 10")
                .hostName("PC-002")
                .type(DeviceType.Laptop).build();
        deviceList=new ArrayList<>();
        deviceList.add(device);
        deviceList.add(device2);
        deviceFilt=new ArrayList<>();
        deviceFilt.add(device);
    }
    @Test
    void postDevice() {
        Mockito.when(deviceRepository.getDeviceByHostName("PC-001")).thenReturn(Optional.empty());
        Mockito.when(modelMapper.map(deviceDto, Device.class)).thenReturn(device);
        Mockito.when(deviceRepository.save(device)).thenReturn(device);
        HttpStatus response=deviceSeviceImp.postDevice(deviceDto);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.CREATED,response);
    }
    @Test
    void postDeviceFailed(){
        Mockito.when(deviceRepository.getDeviceByHostName("PC-001")).thenReturn(Optional.of(device));
        HttpStatus response=deviceSeviceImp.postDevice(deviceDto);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST,response);
    }
    @Test
    void getDeviceByType() {
        Mockito.when(deviceRepository.getDeviceByType(DeviceType.Laptop)).thenReturn(deviceList);
        Mockito.when(modelMapper.map(device, DeviceInfoDto.class)).thenReturn(deviceInfoDto);
        Mockito.when(modelMapper.map(device2,DeviceInfoDto.class)).thenReturn(deviceInfoDto2);
        List<DeviceInfoDto>response=deviceSeviceImp.getDeviceByType(DeviceType.Laptop);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(2,response.size());
        Assertions.assertEquals(1L,response.get(0).getId());
        Assertions.assertEquals("PC-001",response.get(0).getHostName());
        Assertions.assertEquals(2L,response.get(1).getId());
    }

    @Test
    void getDeviceByCpu() {
        Mockito.when(deviceRepository.findAll()).thenReturn(deviceFilt);
        Mockito.when(modelMapper.map(device, DeviceInfoDto.class)).thenReturn(deviceInfoDto);
        List<DeviceInfoDto>response=deviceSeviceImp.getDeviceByCpu(40.0,50.0);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1,response.size());
    }
}