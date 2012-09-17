package org.example;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import net.arnx.jsonic.JSON;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;
import org.example.model.Message;

@WebServlet(name = "WsChat", urlPatterns = "/wschat/*")
public class WsChatServlet extends WebSocketServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final AtomicInteger ids = new AtomicInteger(0);

	private final Set<SampleMessageInbound> connections = new CopyOnWriteArraySet<WsChatServlet.SampleMessageInbound>();

	@Override
	protected StreamInbound createWebSocketInbound(String arg0,
			HttpServletRequest arg1) {
		// TODO Auto-generated method stub
		return new SampleMessageInbound(ids.incrementAndGet());
	}

	private class SampleMessageInbound extends MessageInbound {

		private String name;

		public SampleMessageInbound(int id) {
			name = String.format("Guest_%02d", id);
		}

		@Override
		protected void onBinaryMessage(ByteBuffer arg0) throws IOException {
			// TODO Auto-generated method stub

		}

		@Override
		protected void onTextMessage(CharBuffer arg0) throws IOException {
			Message m = JSON.decode(arg0.toString(), Message.class);
			String filteredMessage = String.format("%s: %s", name, m.getData());
			broadcast(filteredMessage);
		}

		@Override
		protected void onOpen(WsOutbound outbound) {
			connections.add(this);
			String message = String.format("%s %s", name, "has joined.");
			System.out.println(message);
			broadcast(message);
		}

		@Override
		protected void onClose(int status) {
			connections.remove(this);
			String message = String.format("%s %s", name, "has disconnect.");
			System.out.println(message);
			broadcast(message);
		}

		private void broadcast(String message) {
			for (SampleMessageInbound connection : connections) {
				try {
					CharBuffer buffer = CharBuffer.wrap(JSON
							.encode(new Message(message)));
					connection.getWsOutbound().writeTextMessage(buffer);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
