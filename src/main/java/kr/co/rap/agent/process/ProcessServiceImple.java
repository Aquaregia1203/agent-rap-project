package kr.co.rap.agent.process;

import com.pi4j.io.gpio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProcessServiceImple {
    @Autowired
    private ProcessUtil processUtil;

    public boolean executeManufacture(InputInfo inputinfo) {
        processUtil.controlLED(true);

        return false;
    }
}
