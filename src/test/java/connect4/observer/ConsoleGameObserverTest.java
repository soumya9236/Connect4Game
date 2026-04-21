package connect4.observer;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConsoleGameObserverTest {

    @Test
    void onGameMessagePrintsToConsole() {
        ConsoleGameObserver observer = new ConsoleGameObserver();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(out));

        try {
            observer.onGameMessage("Hello Observer");
        } finally {
            System.setOut(original);
        }

        assertTrue(out.toString().contains("Hello Observer"));
    }
}