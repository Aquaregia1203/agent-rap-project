package kr.co.rap.agent.process;

import kr.co.rap.agent.util.InputInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProcessController {
    @Autowired
    private ProcessService processService;

    @PostMapping(value = "/manufacture-execute-info", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void reciveManufactureInfo(@RequestBody InputInfo inputInfo) {
        System.out.println(inputInfo.getPumpInfo());
        processService.receiveManufacture(inputInfo);
    }
}
