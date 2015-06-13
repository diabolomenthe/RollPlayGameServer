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
         kryo.register(Player.class);
         kryo.register(MessageServer.class);
         server.addListener(new Listener()
         {
              public void connected (Connection connection){
                   nbreConnexions = nbreConnexions + 1;
              }
              public void received (Connection c, Object object){
                   if (object instanceof Player)
                     {
                       /* on prépare le message de retour à tous les joueurs*/
                       Player annonce = new Player();
                       annonce.message = "le joueur : vient de se connecter !";
                       server.sendToAllTCP(annonce);//on envoie l'objet à tous les joueurs
                       }
                   if (object instanceof MessageServer){
                	   System.out.println(((MessageServer)object).getMessage());
                	   server.sendToAllTCP((MessageServer)object);
                   }
               }                 
         });
   server.bind(port);
   server.start();
    }

   public static void main(String[] args) throws IOException {
       Log.set(Log.LEVEL_DEBUG);
       new RollPlayGameServer();
   }
}