import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PositionListener {

    // Must match GAME_PORT in the ESP32 sketch
    private static final int LISTEN_PORT = 4212;

    private static final Pattern DISTANCE_PATTERN =
        Pattern.compile("\"d1\":(-?\\d+\\.?\\d*),\"d2\":(-?\\d+\\.?\\d*)");

    private volatile boolean running = true;
    
    public volatile double latestD1 = 50;
    public volatile double latestD2 = 30;

    public void start() {
        Thread listenerThread = new Thread(this::listenLoop);
        listenerThread.setDaemon(true);
        listenerThread.start();
    }

    public void stop() {
        running = false;
    }

    public double getLatestD1() { return latestD1; }
    public double getLatestD2() { return latestD2; }



    private void listenLoop() {
        try (DatagramSocket socket = new DatagramSocket(LISTEN_PORT)) {
            byte[] buffer = new byte[256];
            System.out.println("Listening for ESP32 broadcast packets on UDP port " + LISTEN_PORT + "...");

            while (running) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String received = new String(packet.getData(), 0, packet.getLength());
                parseAndUpdate(received);
            }
        } catch (Exception e) {
            System.err.println("UDP listener error: " + e.getMessage());
        }
    }

    private void parseAndUpdate(String jsonLike) {
        Matcher matcher = DISTANCE_PATTERN.matcher(jsonLike);
        if (matcher.find()) {
            latestD1 = Double.parseDouble(matcher.group(1));
            latestD2 = Double.parseDouble(matcher.group(2));
            System.out.println("Received distances: d1=" + latestD1 + " d2=" + latestD2);
        } else {
            System.out.println("Received non-distance data: " + jsonLike);
        }
    }
}