package com.liuhao.application.pojo;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

@ServerEndpoint("/websocket/{sid}")
@Component
public class WebSocketServer {

	// session 会话集合
	private static ConcurrentHashMap<String, Session> map = new ConcurrentHashMap<String, Session>();

	// 当前会话
	private Session session;
	// sid
	private String sid = "";

	/**
	 * 连接
	 * 
	 * @param session 服务器与客户端建立建立连接，生成会话
	 * @param sid     客户端的唯一id
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("sid") String sid) {
		this.session = session;
		this.sid = sid;
		System.out.println("有一个连接sid:" + this.sid);
		this.map.put(this.sid, this.session);
	}

	/**
	 * 关闭当前会话
	 */
	@OnClose
	public void onClose() {
		try {
			// 从名单中清楚
			this.map.remove(this.sid);
			// 关闭会话
			this.session.close();

			System.out.println("sid:" + sid + "会话被关闭，移除");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 接受消息
	 * 
	 * @param message 接受到的消息
	 */
	@OnMessage
	public void onMessage(String message) {
		try {
			System.out.println("message:" + message);

			// 广播消息
			for (Entry<String, Session> entry : map.entrySet()) {
				entry.getValue().getBasicRemote().sendText(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送消息
	 * 
	 * @param massage 需要发送消息
	 */
	public void sendMassage(String massage) {
		try {
			this.session.getBasicRemote().sendText(massage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
