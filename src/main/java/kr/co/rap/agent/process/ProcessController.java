package kr.co.rap.agent.process;

import kr.co.rap.agent.model.ManufactureInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProcessController {
    @Autowired
    private ProcessService processService;

    @PostMapping("/manufacture-execute-info")
    public void reciveManufactureInfo(ManufactureInfo manufactureInfo) {

    }
}
