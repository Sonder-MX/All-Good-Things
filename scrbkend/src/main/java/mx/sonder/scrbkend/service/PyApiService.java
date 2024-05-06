package mx.sonder.scrbkend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import mx.sonder.scrbkend.entity.AnalyzeData;
import mx.sonder.scrbkend.websocket.WebSocketHandle;

@Service
public class PyApiService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private WebSocketHandle wsHandle;

    @Autowired
    private AnalyzeDataService analyzeDataService;

    public void handlePyData(String key) {
        String strData = redisTemplate.opsForValue().get(key);
        wsHandle.sendToAllClient(key, strData);
        analyzeDataService.insert(new AnalyzeData(key, strData));
    }

}
