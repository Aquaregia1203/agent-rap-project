package kr.co.rap.agent.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ProcessController {
    public static InputInfo inputInfo;

//    @GetMapping(value = "/go")
//    public void executeLED() {
//        processUtil.controlLED(true);
//    }

    @PostMapping(value = "/manufacture-execute-info")
    public Map<String, String> reciveManufactureInfo(@RequestBody InputInfo inputInfo) {
        ProcessController.inputInfo = inputInfo;

        Thread manufacture = new Thread(new ProcessUtil());
        manufacture.start();

        Map<String, String> responseInfo = new HashMap<String, String>();
        responseInfo.put("code", "200");
        responseInfo.put("mesaage", "그거면 됐습니다..");

        System.out.println("Controller was run");

        return responseInfo;
    }
}
