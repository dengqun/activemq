/**
 * @package_name: socket
 * @file_name: Server.java
 * @author: dengqun
 * @date: 2018-3-26
 * @version 1.0
 */
package socket;

/**
 *@author: dengqun
 *@date: 2018-3-26 上午10:36:05
 *@version 1.0
 */
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
public class Server {
 private int port = 1122;
 private ServerSocket serverSocket;

 public Server() throws Exception{
  serverSocket = new ServerSocket(port);
  System.out.println("服务器启动!");
 }
 public void service(){
  while(true){
   Socket socket = null;
   try {
    socket = serverSocket.accept();
    System.out.println("New connection accepted "+
      socket.getInetAddress()+":"+socket.getPort());
   } catch (IOException e) {
    e.printStackTrace();
   }finally{
    if(socket!=null){
     try {
      socket.close();
     } catch (IOException e) {
      e.printStackTrace();
     }
    }
   }
  }
 }

 public static void main(String[] args) throws Exception{
  Server server = new Server();
  Thread.sleep(60000*10);
  server.service();
 }
} 