package cn.ljtnono.myblog.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

/**
 *  系统信息推送处理器
 *  @author ljt
 *  @date 2019/4/9
 *  @version 1.0
*/
@EnableWebMvc
@Component
public class SystemInfoWebSocketHandler extends AbstractWebSocketHandler {


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

//        1. 收到消息，从消息中取出下层交付上来的内容
        System.out.println("收到消息"+ message.getPayload());

//        2. 延时
        Thread.sleep(2000);

//        3. 发送文本消息给客户端
        System.out.println("发送消息: Hello world!");
        session.sendMessage(new TextMessage("hello world"));
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        System.out.println("建立连接！");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("关闭连接！");
    }
}
