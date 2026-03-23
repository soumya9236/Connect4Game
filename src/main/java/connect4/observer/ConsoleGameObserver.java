package connect4.observer;

public class ConsoleGameObserver implements GameObserver {

    @Override
    public void onGameMessage(String message) {
        System.out.println(message);
    }
}
