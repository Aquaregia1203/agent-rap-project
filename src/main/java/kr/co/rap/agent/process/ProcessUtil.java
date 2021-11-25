package kr.co.rap.agent.process;

import com.pi4j.io.gpio.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProcessUtil implements Runnable {
    public void run() {
        executeManufacture(ProcessController.inputInfo);
    }


    public void executeManufacture(InputInfo inputInfo) {
        try {
            while (!viewContactSwitch()) {
                controlLED(true);
                Thread.sleep(500);
                controlLED(false);
            }

            controlPump(inputInfo);

            Thread.sleep(10000);

            int productWeight = measureProductWeight();

            controlLED(true);

            while (viewContactSwitch()) {
                continue;
            }

            Map<String, Integer> product = new HashMap<String, Integer>();
            product.put("productWeight", productWeight);

            sendProductInfo(product);

            controlLED(false);

            inputInfo = null;
        } catch (Exception e) {

        }
    }

    public Map<String, String> receiveManufacture(InputInfo inputInfo) {
        System.out.println("확인");
        System.out.println(inputInfo.getPumpInfo());
        return null;
    }


    public boolean viewContactSwitch() {
        return false;
    }

    public void controlPump(InputInfo inputInfo) {

    }

    public void controlLED(boolean LEDStatus) {
        final Pin OUTPUT_GPIO_01 = RaspiPin.GPIO_01;
        final GpioController controller = GpioFactory.getInstance();

        try {
            final GpioPinDigitalOutput pin =
                    controller.provisionDigitalOutputPin(OUTPUT_GPIO_01,
                            "LED", PinState.LOW);

            for (int i = 0; i < 10; i ++) {
                pin.high();
                Thread.sleep(500);

                pin.low();
                Thread.sleep(500);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            controller.shutdown();
        }
    }

    public int measureProductWeight() {
        return 0;
    }

    public void sendProductInfo(Map<String, Integer> productInfo) {

    }
}
