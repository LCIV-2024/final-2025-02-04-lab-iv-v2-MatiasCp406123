package ar.edu.utn.frc.tup.lciii.Client;

import ar.edu.utn.frc.tup.lciii.dtos.common.DeviceInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ClientRest {
    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<DeviceInfoDto[]>deviceAll(){
        return restTemplate.getForEntity("https://67a106a15bcfff4fabe171b0.mockapi.io/api/v1/device/device", DeviceInfoDto[].class);
    }
}
