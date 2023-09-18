package ori.loproject.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import ori.loproject.config.Handler.WebSocketHandler;

// 참고자료 : https://velog.io/@bagt/Spring에서-웹-소켓WebSocket-사용하기
@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    @Override // WebSocket 핸들러 등록
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WebSocketHandler(), "/webSocketHandler")
                .addInterceptors(new HttpSessionHandshakeInterceptor());
    }
}
