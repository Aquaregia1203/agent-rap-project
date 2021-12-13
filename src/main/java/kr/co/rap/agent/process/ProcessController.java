package kr.co.rap.agent.process;


import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ProcessController {
    @Async
    @PostMapping(value = "/manufacture-execute-info")
    public Map<String, String> reciveInputInfo(@RequestBody InputInfo inputInfo) {
        FileWriter fileWriter = null;
        Map<String, String> responseInfo = new HashMap<String, String>();

        List<Map<String, String>> pumpInfo = inputInfo.getPumpInfo();
        StringBuffer pumpNoAndInput = new StringBuffer();

        for (Map info : pumpInfo) {
            pumpNoAndInput.append(info.get("pumpNo"))
                    .append(":")
                    .append(info.get("input"))
                    .append("@");
        }

        try {
            fileWriter = new FileWriter(new File("/home/pi/process/process-info.txt"));
            fileWriter.write(pumpNoAndInput.toString());
            fileWriter.flush();
        } catch (Exception e) {
            responseInfo.put("code", "300");
            responseInfo.put("message", "생산 요청에 실패하였습니다.");

            return responseInfo;
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        responseInfo.put("code", "200");

        return responseInfo;
    }
}
