package ls;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleAppTest {

    @Test
    void timeOfLastModification() {
        File test = new File("src//main//resources//java.PNG");
        assertEquals(ConsoleApp.timeOfLastModification(test), "22.03.2021 00:25:56");
        File test1 = new File("src//main//resources//kotlin.PNG");
        assertEquals(ConsoleApp.timeOfLastModification(test1), "22.03.2021 00:25:25");
        File test2 = new File("src//main//resources//one.txt");
        assertEquals(ConsoleApp.timeOfLastModification(test2), "22.03.2021 22:06:26");
        File test3 = new File("src//main//resources//no.txt");
        assertEquals(ConsoleApp.timeOfLastModification(test3), "01.01.1970 03:00:00");
        File test4 = new File("src//main//resources//two.txt");
        assertNotEquals(ConsoleApp.timeOfLastModification(test4), "22.03.2021 00:24:09");
        assertNotEquals(ConsoleApp.timeOfLastModification(test2), "12.12.2012 12:12:12");
        assertNotEquals(ConsoleApp.timeOfLastModification(test), "xyz");
        File test5 = new File("src//main//resources//5.txt");
        assertEquals(ConsoleApp.timeOfLastModification(test3), ConsoleApp.timeOfLastModification(test5));
        File test6 = new File("src//main//resources//folder");
        assertEquals(ConsoleApp.timeOfLastModification(test6), "22.03.2021 01:02:39");

    }

    @Test
    void sizeForHr() {
        File test = new File("src//main//resources//java.PNG");
        assertEquals(ConsoleApp.sizeForHr(test), "43 Kb");
        File test1 = new File("src//main//resources//kotlin.PNG");
        assertEquals(ConsoleApp.sizeForHr(test1), "22 Kb");
        File test2 = new File("src//main//resources//three.txt");
        assertEquals(ConsoleApp.sizeForHr(test2), "0 B");
        File test3 = new File("src//main//resources//two.txt");
        assertNotEquals(ConsoleApp.sizeForHr(test3), ConsoleApp.timeOfLastModification(test3));
        assertNotEquals(ConsoleApp.sizeForHr(test3), "2048 B");
        assertEquals(ConsoleApp.sizeForHr(test3), "1 Kb");
        File test5 = new File("src//main//resources//no.txt");
        assertNotEquals(ConsoleApp.sizeForHr(test5), "0 Kb");
        assertEquals(ConsoleApp.sizeForHr(test5), "0 B");
    }

    private final File[] result = ConsoleApp.path(new File("src//main//resources"));

    @Test
    void path() {
        File one = new File("src//main//resources//one.txt");
        File two = new File("src//main//resources//two.txt");
        File three = new File("src//main//resources//three.txt");
        File folder = new File("src//main//resources//folder");
        File java = new File("src//main//resources//java.PNG");
        File kotlin = new File("src//main//resources//kotlin.PNG");
        File[] result1 = new File[]{folder, java, kotlin, one, three, two}; // !Нумерация здесь
        assertArrayEquals(result1, result);
        assertArrayEquals(null, ConsoleApp.path(new File("src//main//error")));
    }

    @Test
    void name() {
        assert result != null;
        assertEquals("folder", ConsoleApp.getName(result[0]));
        assertEquals("one.txt", ConsoleApp.getName(result[3]));
        assertEquals("java.png", ConsoleApp.getName(result[1]));
    }

    @Test
    void l() {
        assert result != null;
        assertEquals("22.03.2021 01:02:39" + System.lineSeparator()
                + "96638 B" + System.lineSeparator() + "111", ConsoleApp.l(result[0]));
        assertEquals("22.03.2021 00:25:25" + System.lineSeparator()
                + "23198 B" + System.lineSeparator() + "111", ConsoleApp.l(result[2]));
        assertEquals("22.03.2021 22:06:26" + System.lineSeparator()
                + "127 B" + System.lineSeparator() + "111", ConsoleApp.l(result[3]));
        assertEquals("22.03.2021 00:59:22" + System.lineSeparator()
                + "0 B" + System.lineSeparator() + "111", ConsoleApp.l(result[4]));
    }

    @Test
    void human() {
        assert result != null;
        assertEquals("94 Kb" + System.lineSeparator() +
                "rwx", ConsoleApp.human(result[0]));
        assertEquals("43 Kb" + System.lineSeparator() +
                "rwx", ConsoleApp.human(result[1]));
        assertEquals("0 B" + System.lineSeparator() +
                "rwx", ConsoleApp.human(result[4]));
    }
}


