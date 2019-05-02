package cn.ljtnono.myblog.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Component
@EnableWebSocket
public class SystemInfoWebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private SystemInfoWebSocketHandler systemInfoWebSocketHandler;

    @Autowired
    private SystemInfoHandshakeInterceptor systemInfoHandshakeInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        registry.addHandler(systemInfoWebSocketHandler,"/websocket/systemInfo")
                .addInterceptors(systemInfoHandshakeInterceptor);
    }
}
