package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

import exceptions.FacilityNotFoundException;

//@author geezzzyyy
class MapTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Test
    void executeFindNearestCommand() throws FacilityNotFoundException, EmptyInputException, InvalidCommandException {
        captureOut();
        Map map = new Map();
        String input = "findFacility <library1> <Canteen> <2>";
        map.executeCommand(input, Command.FIND_FACILITY);
        String theOutput = getOut();
        String expectedOutput = "canteen1@N4-01-01\n" +
                "canteen3@N5-04-01\n";
        assertEquals(expectedOutput, theOutput);
    }

    @Test
    void executeFindNearestCommand_InvalidFacilityType() {
        captureOut();
        Map map = new Map();
        String input = "findFacility <library1> <shop> <2>";
        assertThrows(FacilityNotFoundException.class, () -> {
            map.executeCommand(input, Command.FIND_FACILITY);
        });

    }

    @Test
    void executeFindNearestCommand_NoOfCanteenLessThanTopK() {
        captureOut();
        Map map = new Map();
        String input = "findFacility <library1> <Canteen> <5>";
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            map.executeCommand(input, Command.FIND_FACILITY);
        });

    }

    @Test
    void invalidCommand() {
        Map map = new Map();
        assertThrows(InvalidCommandException.class, () -> {
            map.executeCommand("invalidCommand", Command.INVALID);
        });
    }

    /**
     * Turns on stdOut output capture
     */
    private void captureOut() {
        System.setOut( new PrintStream( outContent ) );
    }


    /**
     * Turns off stdOut capture and returns the contents
     * that have been captured
     *
     * @return
     */
    private String getOut() {
        System.setOut( new PrintStream( new FileOutputStream( FileDescriptor.out ) ) );
        return outContent.toString().replaceAll( "\r", "" );

    }


}