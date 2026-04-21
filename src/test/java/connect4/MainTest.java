package connect4;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MainTest {

    @Test
    void mainRunsThroughACompleteGame() {
        InputStream original = System.in;
        try {
            String input = String.join("\n",
                    "0", // P1
                    "1", // P2
                    "0", // P1
                    "1", // P2
                    "0", // P1
                    "1", // P2
                    "0"  // P1 wins vertically
            ) + "\n";

            System.setIn(new ByteArrayInputStream(input.getBytes()));

            assertDoesNotThrow(() -> Main.main(new String[]{}));
        } finally {
            System.setIn(original);
        }
    }
}