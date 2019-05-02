package cn.ljtnono.myblog.websocket;

import cn.ljtnono.myblog.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Date;
import java.util.Map;
/**
 *  传输服务器系统信息的websocket拦截器
 *  @author ljt
 *  @date 2019/4/9
 *  @version 1.0
*/
@Component
public class SystemInfoHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(SystemInfoHandshakeInterceptor.class);


    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        logger.info(DateUtil.dateTimeFormat(new Date()) + " 开始websocket连接！");
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        logger.info(DateUtil.dateTimeFormat(new Date()) + " websocket连接成功！");
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
