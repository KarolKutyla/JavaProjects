/**
 *
 *  @author Kutyła Karol S24232
 *
 */

package zad1;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {

    final String id;
    final String host;
    final int port;
    SocketChannel socketChannel;
    private ByteBuffer readBuffer = ByteBuffer.allocateDirect(500);


    public Client(String host, int port, String id)
    {
        this.host = host;
        this.port = port;
        this.id = id;
        try {
//            System.out.println(log("Nawiązywanie połączenia... "));
            socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void connect()
    {

    }

    public String send(String req)
    {
        try {
            ByteBuffer writeBuffer = ByteBuffer.wrap(req.getBytes());
            socketChannel.write(writeBuffer);

            socketChannel.read(readBuffer);
            readBuffer.flip();
            byte[] responseBytes = new byte[readBuffer.remaining()];
            readBuffer.get(responseBytes);
            String response = new String(responseBytes);
            readBuffer.clear();
            return response;

        }catch (IOException e)
        {
            return "e";
        }

    }

    public String log(String message)
    {
        return "C: " + id + ". " + message;
    }
}
