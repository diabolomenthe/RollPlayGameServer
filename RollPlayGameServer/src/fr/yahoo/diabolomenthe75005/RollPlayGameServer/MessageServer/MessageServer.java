package fr.yahoo.diabolomenthe75005.RollPlayGameServer.MessageServer;

public class MessageServer {
	private String message = "";
	public MessageServer() {
		
	}
	public MessageServer(String m){
		this.message = m;
	}
	public String getMessage(){
		return message;
	}
}
