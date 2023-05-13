/**
 *
 *  @author Kutyła Karol S24232
 *
 */

package zad1;


import java.util.List;
import java.util.concurrent.ExecutionException;

public class ClientTask implements Runnable{

    Client client;
    List<String> reqs;
    boolean showSendRes;
    String clog;

    public ClientTask(Client client, List<String> reqs, boolean showSendRes)
    {
        this.client = client;
        this.reqs = reqs;
        this.showSendRes = showSendRes;
    }

    public static ClientTask create(Client c, List<String> reqs, boolean showSendRes)
    {
        ClientTask task = new ClientTask(c, reqs, showSendRes);
        return task;
    }

    public String get() throws InterruptedException, ExecutionException
    {
        return clog;
    }

    @Override
    public void run() {

        client.connect();

        client.send("login " + client.id);
        System.out.println("login " + client.id);
        for(String req : reqs) {
            System.out.println("REQ TO " + req);
            String res = client.send(req);
            if (showSendRes) System.out.println(res);
        }
        this.clog = client.send("bye and log transfer");
    }
}
