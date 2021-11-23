package kr.co.rap.agent.process;

import kr.co.rap.agent.model.ManufactureInfo;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProcessServiceImple implements ProcessService {

    @Override
    public Map<String, String> receiveManufacture(ManufactureInfo manufactureInfo) {
        return null;
    }

    @Override
    public void executeManufacture(ManufactureInfo manufactureInfo) {

    }

    @Override
    public boolean viewContactSwitch() {
        return false;
    }

    @Override
    public void controlPump(ManufactureInfo manufactureInfo) {

    }

    @Override
    public void controlLED(boolean LEDStatus) {

    }

    @Override
    public int measureProductWeight() {
        return 0;
    }

    @Override
    public void sendProductInfo(Map<String, Integer> productInfo) {

    }
}
