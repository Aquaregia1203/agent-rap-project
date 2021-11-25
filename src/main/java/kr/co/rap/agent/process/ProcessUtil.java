package kr.co.rap.agent.process;

import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class ProcessUtil implements Runnable {
    //final GpioController controller = GpioFactory.getInstance();
    //final Pin OUTPUT_GPIO_01 = RaspiPin.GPIO_01;


    public void run() {
        executeManufacture(ProcessController.inputInfo);
    }


    public void executeManufacture(InputInfo inputInfo) {
        try {
 //           while (!viewContactSwitch()) {  // 접촉 스위치 상태가 false인 경우는 0.5초 간격으로 LED 제어
//                onLED();
//                Thread.sleep(500);
//                offLED();
//                Thread.sleep(500);
//            }
//
//            controlPump(inputInfo);  // 접촉 스위치 상태가 true인 경우 원재료 통 별 펌프를 제어함.
//
//            Thread.sleep(10000);  //10초 대기.
//
//            int productWeight = measureProductWeight();  //10초가 흐른 후 무게를 측정한 값을 저장한다.
//
//            onLED();   //  무게 측정 후 LED를 점등.
//
//            while (viewContactSwitch()) {
//                continue;
//            }
//
//            Map<String, Integer> product = new HashMap<String, Integer>();
//            product.put("productWeight", productWeight);
//
//            sendProductInfo(product);

            flashLED();

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
