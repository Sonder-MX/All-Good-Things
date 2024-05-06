package mx.sonder.scrbkend.websocket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import mx.sonder.scrbkend.utils.SpringUtils;

@Slf4j
@Component
@ServerEndpoint("/ws/{topic}")
public class WebSocketHandle {

    private static Map<String, Session> sessMap = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("topic") String topic) {
        String uqid = topic + "-" + session.getId();
        if (topic == null || topic.isEmpty()) {
            log.warn("未连接WebSocket!");
            return;
        }
        log.info("WebSocket opened: " + session.getId() + " for topic: " + topic);
        sessMap.put(uqid, session);
        sendToClient(session, topic);
    }

    @OnClose
    public void onClose(Session session, @PathParam("topic") String topic) {
        String uqid = topic + "-" + session.getId();
        sessMap.remove(uqid);
        log.info("WebSocket closed: " + uqid);
    }

    private void sendToClient(Session session, String topic) {
        try {
            StringRedisTemplate redisTemplate = SpringUtils.getBean(StringRedisTemplate.class);
            String data = redisTemplate.opsForValue().get(topic);
            session.getBasicRemote().sendText(data);
        } catch (Exception e) {
            log.error("发送消息失败", e);
        }
    }

    public void sendToAllClient(String topic, String data) {
        sessMap.entrySet().stream().filter(e -> e.getKey().startsWith(topic)).forEach(e -> {
            try {
                e.getValue().getBasicRemote().sendText(data);
            } catch (Exception ex) {
                log.error("发送消息失败", ex);
            }
        });
    }

}
