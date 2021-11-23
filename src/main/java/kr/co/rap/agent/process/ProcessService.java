package kr.co.rap.agent.process;

import kr.co.rap.agent.model.ManufactureInfo;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface ProcessService {
    public Map<String, String> receiveManufacture(ManufactureInfo manufactureInfo);

    public void executeManufacture(ManufactureInfo manufactureInfo);

    public boolean viewContactSwitch();

    public void controlPump(ManufactureInfo manufactureInfo);

    public void controlLED(boolean LEDStatus);

    public int measureProductWeight();

    public void sendProductInfo(Map<String, Integer> productInfo);
}
