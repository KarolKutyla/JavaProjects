/**
 * @author Kutyła Karol S24232
 */

package zad1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class Server {

    List<String> log = new LinkedList<>();
    MyRunnable runnable;
    ServerSocketChannel serverSocketChannel;
    final private static Charset charset = StandardCharsets.UTF_8;
    HashMap<SocketChannel, SessionData> sessions = new HashMap<>();


    public Server(String host, int port) {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(host, port));
            serverSocketChannel.configureBlocking(false);
            runnable = new MyRunnable();

        } catch (IOException e) {

        }

    }

    public void startServer() {
        if (!runnable.running) {
            runnable.running = true;
            new Thread(runnable).start();
        }
    }

    public void stopServer() {
        if (runnable.running) {
            runnable.running = false;
        }
    }

    public String getServerLog() {
        try {
            StringBuilder builder = new StringBuilder();
            for (String line : log) {
                builder.append(line).append("\n");
            }
            return builder.substring(0, builder.length() - 1).toString();
        } catch (Exception e) {

        }
        return "";
    }


    private class MyRunnable implements Runnable {
        boolean running = false;
        Selector selector = Selector.open();
        SelectionKey key = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);


        private MyRunnable() throws IOException {
        }

        @Override
        public void run() {
            try {
                while (running) {
                    selector.select();
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = keys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey currentKey = iterator.next();
                        iterator.remove();
                        if (currentKey.isAcceptable()) {
                            SocketChannel cc = serverSocketChannel.accept();
                            if (cc != null) {
                                cc.configureBlocking(false);
                                cc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                            }
                        } else if (currentKey.isReadable()) {
                            SocketChannel cc = (SocketChannel) currentKey.channel();
                            String message = getMessage(cc);
                            respond(message, cc);
                        } else {

                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void respond(String message, SocketChannel channel) throws IOException {
            String response;
            SessionData sessionData = sessions.get(channel);
            if (message.matches("login\\s.*")) {
                SessionData data = new SessionData(message.substring(6));
                data.messages.add("logged in");
                sessions.put(channel, data);
                sessionData = sessions.get(channel);
                log.add(sessionData.login + " logged in at " + LocalTime.now());
                response = "logged in";
            } else if (message.matches("\\d{4}-\\d{2}-\\d{2}(T\\d{2}:\\d{2})?\\s\\d{4}-\\d{2}-\\d{2}(T\\d{2}:\\d{2})?")) {
                log.add(sessionData.login + " request at " + LocalTime.now() + ": \"" + message + "\"");
                String[] arr = message.split("\\s");
                response = Time.passed(arr[0], arr[1]);
                sessionData.messages.add("Result: " + response);
            } else if (message.equals("bye and log transfer")) {
                sessionData.messages.add("logged out");
                response = sessionData.getLog();
                log.add(sessionData.login + " logged out at " + LocalTime.now());
                ByteBuffer bb = ByteBuffer.wrap(response.getBytes());
                channel.write(bb);
                channel.close();
                return;
            } else if (message.equals("bye")) {
                sessionData.messages.add("logged out");
                response = "logged out";
                log.add(sessionData.login + " logged out at " + LocalTime.now());
                ByteBuffer bb = ByteBuffer.wrap(response.getBytes());
                channel.write(bb);
                channel.close();
                return;
            } else {
                response = "404 - Not found.";
            }

            ByteBuffer bb = ByteBuffer.wrap(response.getBytes());
            channel.write(bb);
        }

        private String getMessage(SocketChannel cc) throws IOException {
            ByteBuffer bf = ByteBuffer.allocateDirect(500);
            cc.read(bf);
            bf.flip();
            byte[] bytes = new byte[bf.remaining()];
            bf.get(bytes);
            String message = new String(bytes);
            return message;
        }

    }

    static class SessionData {
        private String login = null;
        private LinkedList<String> messages = new LinkedList<>();

        public SessionData(String login) {
            this.login = login;
        }

        private String getLog() {
            StringBuilder sb = new StringBuilder();
            sb.append("=== ").append(login).append(" log start ===\n");
            for (String s : messages) {
                sb.append(s).append("\n");
            }
            sb.append("=== ").append(login).append(" log end ===\n");
            return sb.toString();
        }
    }
}
