package kr.co.rap.agent.process;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ProcessController implements InitializingBean {
    public static InputInfo inputInfo;

//    @GetMapping(value = "/go")
//    public void executeLED() {
//        processUtil.controlLED(true);
//    }

    @Async
    @PostMapping(value = "/manufacture-execute-info")
    public Map<String, String> reciveManufactureInfo(@RequestBody InputInfo inputInfo) {

//        ProcessController.inputInfo = inputInfo;
//
////        Thread ManufactureProcessService = new Thread(new ManufactureProcessService());
//        ManufactureProcessService.start();
        ManufactureProcessService service = new ManufactureProcessService();
        service.onLED();

        Map<String, String> responseInfo = new HashMap<String, String>();
        responseInfo.put("code", "200");
        responseInfo.put("mesaage", "그거면 됐습니다..");

        return responseInfo;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("set up start");
    }
}
