/**
 * @package_name: socket
 * @file_name: Client.java
 * @author: dengqun
 * @date: 2018-3-26
 * @version 1.0
 */
package socket;

/**
 *@author: dengqun
 *@date: 2018-3-26 上午10:38:34
 *@version 1.0
 */
import java.net.Socket;
public class Client {
 public static void main(String[] args) throws Exception{
  final int length = 49;
  String host = "localhost";
  int port = 1122;
  Socket[] socket = new Socket[length];
  for(int i = 0;i<length;i++){
   socket[i] = new Socket(host,port);
   System.out.println("第"+(i+1)+"次连接成功！");
  }
  while(true){
//	  System.out.println("客户");
  }
//  Thread.sleep(3000);
//  for(int i=0;i<length;i++){
//   socket[i].close();
//  }
 }
}