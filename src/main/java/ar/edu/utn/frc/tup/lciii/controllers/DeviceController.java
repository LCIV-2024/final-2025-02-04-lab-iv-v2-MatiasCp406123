package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.common.DeviceDto;
import ar.edu.utn.frc.tup.lciii.dtos.common.DeviceInfoDto;
import ar.edu.utn.frc.tup.lciii.model.DeviceType;
import ar.edu.utn.frc.tup.lciii.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @PostMapping("/api/device")
    public ResponseEntity<HttpStatus>postDevice(@RequestBody DeviceDto deviceDto){
        return ResponseEntity.ok(deviceService.postDevice(deviceDto));
    }
    @PostMapping("/api/save-consumed-devices")
    public ResponseEntity<HttpStatus>postDeviceRest(){
        return ResponseEntity.ok(deviceService.postRest());
    }


    @GetMapping("/api/device")
    public ResponseEntity<List<DeviceInfoDto>>getAllByType(@RequestParam(required = false)DeviceType type,@RequestParam(required = false) Double lowThreshold,@RequestParam(required = false) Double upThreshold){
        if(lowThreshold!=null&&upThreshold!=null){
            return ResponseEntity.ok(deviceService.getDeviceByCpu(lowThreshold,upThreshold));
        }
        return ResponseEntity.ok(deviceService.getDeviceByType(type));
    }

}