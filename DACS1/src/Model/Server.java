package Model;

import java.net.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Server {
    private static final int PORT = 12345;
    private static Set<ClientInfo> clientInfos = new HashSet<>();

    public static void main(String[] args) {//Khởi động máy chủ bằng cách tạo một đối tượng Server & gọi phương thức start().
        System.out.println("Chat server started");
        new Server().start();
    }

    public void start() {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {//DatagramSocket lắng nghe trên một cổng cụ thể (PORT).
            byte[] buf = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);

            while (true) { //lặp để liên tục nhận các gói tin (DatagramPacket) chứa tin nhắn từ các client.
                socket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength());
                InetSocketAddress clientAddress = new InetSocketAddress(packet.getAddress(), packet.getPort());
                //Phân tích tin nhắn nhận được để trích xuất tên người gửi và nội dung tin nhắn.
                String[] parts = message.split(":", 2);
                String name = parts[0].trim();
                String text = parts.length > 1 ? parts[1].trim() : "";
                //Sử dụng đồng bộ hóa truy cập vào tập hợp clientInfos để quản lý thông tin client (tên và địa chỉ).
                synchronized (clientInfos) {
                    clientInfos.add(new ClientInfo(name, clientAddress));
                    for (ClientInfo clientInfo : clientInfos) {
                        if (!clientInfo.getAddress().equals(clientAddress)) {
                            String sendMessage = name + ": " + text;
                            byte[] sendBuf = sendMessage.getBytes();
                            DatagramPacket sendPacket = new DatagramPacket(
                                    sendBuf, sendBuf.length, clientInfo.getAddress().getAddress(), clientInfo.getAddress().getPort());
                            socket.send(sendPacket);
                        }
                    }
                }
                //Chuyển tiếp tin nhắn nhận được cho all các client kết nối khác, trừ người gửi.
                System.out.println("Nhận được từ " + name + ": " + text + clientAddress);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //thông tin của mỗi client kết nối
    private static class ClientInfo {
        private String name;
        private InetSocketAddress address;

        public ClientInfo(String name, InetSocketAddress address) {
            this.name = name;
            this.address = address;
        }

        public String getName() {
            return name;
        }
public InetSocketAddress getAddress() {
            return address;
        }
        //Ghi đè equals và hashCode để so sánh địa chỉ
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ClientInfo that = (ClientInfo) o;
            return address.equals(that.address);
        }

        @Override
        public int hashCode() {
            return Objects.hash(address);
        }
    }
}