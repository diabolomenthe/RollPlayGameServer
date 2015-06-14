package fr.yahoo.diabolomenthe75005.RollPlayGameServer;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import fr.yahoo.diabolomenthe75005.RollPlayGameServer.MessageServer.MessageServer;
import fr.yahoo.diabolomenthe75005.RollPlayGameServer.Player.Player;

public class RollPlayGameServer {
    Server server;
    int nbreConnexions = 0;
    static public final int port = 25565; //le port utilisé pour TCP
    public RollPlayGameServer () throws IOException {
         server = new Server(); 
         Kryo kryo = server.getKryo();
         kryo.register(MessageServer.class);
         server.addListener(new Listener()
         {
              public void connected (Connection connection){
                   nbreConnexions = nbreConnexions + 1;
                   System.out.println("Connexion détecté");
              }
              public void received (Connection c, Object object){
                   if (object instanceof MessageServer){
                	   System.out.println(((MessageServer)object).getMessage());
                	   server.sendToAllTCP(object);
                	   c.sendTCP(object);
                   }
               }
              public void disconnected(Connection connexion){
            	System.out.println("Disconnected");  
              }
         });
   server.bind(port,port+1);
   server.start();

    }

   public static void main(String[] args) throws IOException {
       Log.set(Log.LEVEL_DEBUG);
       new RollPlayGameServer();
   }
}