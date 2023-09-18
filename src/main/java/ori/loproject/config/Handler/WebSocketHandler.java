package ori.loproject.config.Handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ori.loproject.domain.order.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebSocketHandler extends TextWebSocketHandler {

    private List<WebSocketSession> sessions = new ArrayList<>();
    private Map<String, String> sessionToUserIdMap = new HashMap<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 클라이언트로부터 메시지를 받으면 호출됩니다.
        String payload = message.getPayload();

        System.out.println("========핸들러 동작==========");

        // 메시지 처리 로직
        // JSON 문자열을 객체로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(payload);
        String type = jsonNode.get("type").asText();

        if ("order".equals(type)) {
            Order order = objectMapper.readValue(payload, Order.class);
            String orderJson = objectMapper.writeValueAsString(order);

            // 다른 클라이언트에게 메시지 전송
            for (WebSocketSession s : sessions) {
                Long storeId = (Long) s.getAttributes().get("storeId");
                if (storeId != null && storeId.equals(order.getStoreid()) && s.isOpen()) {
                    System.out.println("메세지 전달시도");
                    s.sendMessage(new TextMessage(orderJson));
                    System.out.println("메세지를 성공적으로 전달함");
                }
            }
        } else if ("Receipt".equals(type)){
            int orderId = jsonNode.get("orderid").asInt();
            Long memberid = (long) jsonNode.get("memberid").asInt();
            String key = jsonNode.get("key").asText();

            for (WebSocketSession s : sessions) {
                Long memberId = (Long) s.getAttributes().get("memberId");
                if (memberId != null && memberId.equals(memberid) && s.isOpen()) {
                    s.sendMessage(new TextMessage(jsonNode.toString()));
                    System.out.println("메세지를 성공적으로 전달함");
                }
                else {
                    System.out.println("해당하는 유저가 없음");
                }
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 클라이언트와 웹소켓 연결이 수립되면 호출됩니다.
        sessions.add(session); // 세션정보 저장

        // HTTP 세션에서 유저 ID 가져오기
        Map<String, Object> attributes = session.getAttributes();

        System.out.println(attributes.get("username"));

        if (attributes.get("storeId") != null){
            System.out.println(attributes.get("storeId") + "번 관리자 연결됨");
            String storeId = String.valueOf(attributes.get("storeId"));
            // 세션 ID와 유저 ID 매핑
            sessionToUserIdMap.put(session.getId(), storeId);
        }

        System.out.println("웹소켓 연결됨 : " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 클라이언트와 웹소켓 연결이 닫히면 호출됩니다.
        System.out.println("웹소켓 닫힘 : " + session.getId());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        // 웹소켓 통신 중 오류가 발생하면 호출됩니다.
        System.out.println("웹소켓 오류발생 : " + session.getId());
    }
}
