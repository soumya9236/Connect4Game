package connect4.observer;

public class TestObserver implements GameObserver {

    private String lastMessage;

    @Override
    public void onGameMessage(String message) {
        this.lastMessage = message;
    }

    public String getLastMessage() {
        return lastMessage;
    }
}