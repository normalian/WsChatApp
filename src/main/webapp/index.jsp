<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.net.InetAddress" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/jquery-1.8.1.min.js"></script>
<script type="text/javascript" src="js/jquery.json-2.3.min.js"></script>
<script type="text/javascript" src="js/jquery.websocket-0.0.1.js"></script>
<%
	String url = request.getRequestURL().toString();
	String wsUrl = String.format("ws%swschat",
			url.substring(url.indexOf(":")));
%>
<title>Tomcat7 WebSocket Sample</title>
<script type="text/javascript">
	$(function() {
		// WebSocket通信用モジュール生成
		var ws = $.websocket("<%=wsUrl%>", {
			// メッセージイベント
			events : {
				// サーバ側からメッセージ通信を受信した場合の動作
				message : function(e) {
					$("#content").append(e.data + "<br>");
				}
			}
		});
		// フィールドの更新イベントの設定
		$("#broadcast").click(function() {
			// メッセージイベントで送信
			var message = $("#message").val();
			ws.send("message", message);
			$("#message").val("");
		});
	});
</script>
</head>
<body>
	<h2>Tomcat7 WebSocket Sample</h2>
	<form>
		<h2>メッセージの入力</h2>
		<dl>
			<dt>メッセージ入力</dt>
			<dd>
				<input type="text" placeholder="例）おはよう！" value="" id="message">
			</dd>
			<dt>送信ボタン</dt>
			<dd>
				<input type="button" id="broadcast" value="送信">
			</dd>
		</dl>
	</form>
	<h2>メッセージの履歴</h2>
	<section id="content"></section>
</body>
</html>