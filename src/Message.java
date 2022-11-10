public class Message {
    String senderId;
    String data;
    long time;

    public Message(String senderId, String data) {
        this.senderId = senderId;
        this.data = data;
        this.time = System.currentTimeMillis();
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Comparable<Long> getTime() {
        return time;
    }

    @Override
    public String toString() {
        return String.format(" - %s:%s", senderId, data);
    }
}
