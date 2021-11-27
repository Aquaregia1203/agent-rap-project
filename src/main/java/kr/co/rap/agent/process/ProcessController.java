package kr.co.rap.agent.process;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
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
                          .append("\r\n");
        }

        File file = new File("C://java/test.txt");
        BufferedOutputStream bufferedOutputStream = null;

        try {
            bufferedOutputStream = new BufferedOutputStream(
                                      new FileOutputStream(file));

            bufferedOutputStream.write(pumpNoAndInput.toString().getBytes(StandardCharsets.UTF_8));

        } catch (Exception e) {
            responseInfo.put("code", "300");
            responseInfo.put("message", "생산 요청에 실패하였습니다.");

            return responseInfo;
        } finally {
            try {
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
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
