package kr.co.rap.agent.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProcessController {
    @Autowired
    private ProcessService processService;

    @GetMapping(value = "/go")
    public void executeLED() {
        processService.controlLED(true);
    }

    @PostMapping(value = "/manufacture-execute-info", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void reciveManufactureInfo(@RequestBody InputInfo inputInfo) {
        processService.controlLED(true);

//        System.out.println(inputInfo.getPumpInfo());
//        processService.receiveManufacture(inputInfo);
    }
}
