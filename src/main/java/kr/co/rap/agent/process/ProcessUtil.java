package kr.co.rap.agent.process;

import com.pi4j.io.gpio.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProcessUtil implements Runnable {
    private Pin OUTPUT_GPIO_01 = RaspiPin.GPIO_01;
    private GpioController controller = GpioFactory.getInstance();

    public void run() {
        executeManufacture(ProcessController.inputInfo);

        System.out.println("Thread Run.");
    }


    public void executeManufacture(InputInfo inputInfo) {
        try {
            while (!viewContactSwitch()) {
                System.out.println("View Switch");

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

            Map<String, Integer> productInfo = new HashMap<String, Integer>();
            productInfo.put("productWeight", productWeight);

            sendProductInfo(productInfo);

            controlLED(false);

            inputInfo = null;
        } catch (Exception e) {
            e.printStackTrace();
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

    public void flashLED() {
       /* try {
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
        }*/
    }

    public void onLED() {

    }

    public void offLED() {

    }

    public int measureProductWeight() {
        return 0;
    }

    public void sendProductInfo(Map<String, Integer> productInfo) {
        //172.16.30.115 상대 서버 측 IP
        String url = "http://172.16.30.115/product";
        StringBuffer message = new StringBuffer();
        message.append("{");
        message.append("  \"id\":수헌맨");
        message.append("  \"password\":미안해요ㅜ,.ㅜ");
        message.append("}");

        try {
            post(url, message.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(MediaType.get("application/json; charset=utf-8"), json);
        Request request = new Request.Builder()
                                            .url(url)
                                            .post(body)
                                            .build();
        try {
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
