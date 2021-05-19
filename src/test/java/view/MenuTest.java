package view;

import org.junit.jupiter.api.*;
import org.testng.annotations.Test;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;

public class MenuTest {
    private static final PrintStream originalOut = System.out;
    private static final InputStream originalIn = System.in;
    private static ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Test
    public void LoginCommandsTest() throws IOException {
        String allCommands = Files.readString(Path.of("src/test/resources/inputTests/userBuildTestInput.txt"),
                StandardCharsets.US_ASCII);
        String responses = Files.readString(Path.of("src/test/resources/inputTests/userBuildTestOutput.txt"),
                StandardCharsets.US_ASCII);
        System.setOut(new PrintStream(outContent));
        System.setIn(new ByteArrayInputStream(allCommands.getBytes()));
        LoginMenu.getInstance().scanInput();
        assertEquals(responses,outContent.toString().trim());
    }

    @AfterEach
    public void resetUpStreams() {
        outContent.reset();
    }

    @AfterAll
    public static void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }
}
