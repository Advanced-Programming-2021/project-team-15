package view;

import com.opencsv.exceptions.CsvValidationException;
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
    public void loginCommandsTest() throws IOException, CsvValidationException {
        String allCommands = Files.readString(Path.of("src/test/resources/inputTests/userBuildTestInput.txt"),
                StandardCharsets.US_ASCII);
        String responses = Files.readString(Path.of("src/test/resources/inputTests/userBuildTestOutput.txt"),
                StandardCharsets.US_ASCII);
        System.setOut(new PrintStream(outContent));
        System.setIn(new ByteArrayInputStream(allCommands.getBytes()));
        LoginMenu.getInstance().scanInput();
        assertEquals(responses,outContent.toString().trim());
    }

    @Test
    public void shopDeckCommandsTest() throws IOException, CsvValidationException {
        String allCommands = Files.readString(Path.of("src/test/resources/inputTests/userBuyCardsBuildDeckTestInput.txt"),
                StandardCharsets.UTF_8);
        String responses = Files.readString(Path.of("src/test/resources/inputTests/userBuyCardsBuildDeckTestOutput.txt"),
                StandardCharsets.UTF_8);
        System.setOut(new PrintStream(outContent));
        System.setIn(new ByteArrayInputStream(allCommands.getBytes()));
        LoginMenu.getInstance().scanInput();
        assertEquals(responses.replaceAll("\r",""),outContent.toString().trim().replaceAll("\r",""));
    }

    @Test
    public void startGameTest() throws IOException, CsvValidationException {
        String allCommands = Files.readString(Path.of("src/test/resources/inputTests/startGameTestInput.txt"),
                StandardCharsets.UTF_8);
        String responses = Files.readString(Path.of("src/test/resources/inputTests/startGameTestOutput.txt"),
                StandardCharsets.UTF_8);
        System.setOut(new PrintStream(outContent));
        System.setIn(new ByteArrayInputStream(allCommands.getBytes()));
        LoginMenu.getInstance().scanInput();
        assertEquals(responses.replaceAll("\r",""),outContent.toString().trim().replaceAll("\r",""));
    }

    @Test
    public void duelCommandsTest() throws IOException, CsvValidationException {
        String allCommands = Files.readString(Path.of("src/test/resources/inputTests/duelCommandsTestInput.txt"),
                StandardCharsets.UTF_8);
        String responses = Files.readString(Path.of("src/test/resources/inputTests/duelCommandsTestOutput.txt"),
                StandardCharsets.UTF_8);
        System.setOut(new PrintStream(outContent));
        System.setIn(new ByteArrayInputStream(allCommands.getBytes()));
        LoginMenu.getInstance().scanInput();
        assertEquals(responses.replaceAll("\r",""),outContent.toString().trim().replaceAll("\r",""));
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
