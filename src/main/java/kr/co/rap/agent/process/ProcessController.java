package kr.co.rap.agent.process;


import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ProcessController {
    @Async
    @PostMapping(value = "/manufacture-execute-info")
    public Map<String, String> reciveInputInfo(@RequestBody InputInfo inputInfo) {
        Map<String, String> responseInfo = new HashMap<String, String>();

        List<Map<String, String>> pumpInfo = inputInfo.getPumpInfo();
        StringBuffer pumpNoAndInput = new StringBuffer();

        for (Map info : pumpInfo) {
            pumpNoAndInput.append(info.get("pumpNo"))
                          .append(":")
                          .append(info.get("input"))
                          .append("@");
        }

        BufferedReader bufferedReader = null;

        try {
            String command = "echo \"" + pumpNoAndInput + "\" > /home/pi/led/led.txt";

            Process process = Runtime.getRuntime().exec(command);
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }

            process.waitFor();
            System.out.println("exit: " + process.exitValue());
            process.destroy();
        } catch (Exception e) {
            responseInfo.put("code", "300");
            responseInfo.put("message", "생산 요청에 실패하였습니다.");

            return responseInfo;
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (Exception e) {
                responseInfo.put("code", "300");
                responseInfo.put("message", "생산 요청에 실패하였습니다.");

                return responseInfo;
            }
        }

        responseInfo.put("code", "200");

        return responseInfo;
    }
}
