package ls;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleAppTest {

    // Закомментируем, поскольку проверяться все в целом будет дальше
    /*@Test
    void timeOfLastModification() {
        File test = new File("src/test/java/resources/java.PNG");
        assertEquals(ConsoleApp.timeOfLastModification(test), "22.03.2021 00:25:56");
        File test1 = new File("src/test/java/resources/kotlin.PNG");
        assertEquals(ConsoleApp.timeOfLastModification(test1), "22.03.2021 00:25:25");
        File test2 = new File("src/test/java/resources/one.txt");
        assertEquals(ConsoleApp.timeOfLastModification(test2), "22.03.2021 22:06:26");
        File test3 = new File("src/test/java/resources/no.txt");
        assertEquals(ConsoleApp.timeOfLastModification(test3), "01.01.1970 03:00:00");
        File test4 = new File("src/test/java/resources/two.txt");
        assertNotEquals(ConsoleApp.timeOfLastModification(test4), "22.03.2021 00:24:09");
        assertNotEquals(ConsoleApp.timeOfLastModification(test2), "12.12.2012 12:12:12");
        assertNotEquals(ConsoleApp.timeOfLastModification(test), "xyz");
        File test5 = new File("src/test/java/resources/5.txt");
        assertEquals(ConsoleApp.timeOfLastModification(test3), ConsoleApp.timeOfLastModification(test5));
        File test6 = new File("src/test/java/resources/folder");
        assertEquals(ConsoleApp.timeOfLastModification(test6), "22.03.2021 01:02:39");
    }

    @Test
    void sizeForHr() {
        ConsoleApp.InfoHolder inf = new ConsoleApp.InfoHolder(result[1]);
        File test = new File("src/test/java/resources/java.PNG");
        assertEquals(inf.sizeExtP + inf.measureSizeExtP, "43 Kb");
        inf = new ConsoleApp.InfoHolder(result[2]);
        File test1 = new File("src/test/java/resources/kotlin.PNG");
        assertEquals(inf.sizeExtP + inf.measureSizeExtP, "22 Kb");
        inf = new ConsoleApp.InfoHolder(result[4]);
        File test2 = new File("src/test/java/resources/three.txt");
        assertEquals(inf.sizeExtP + inf.measureSizeExtP, "0 B");
        inf = new ConsoleApp.InfoHolder(result[5]);
        File test3 = new File("src/test/java/resources/two.txt");
        assertEquals(inf.sizeExtP + inf.measureSizeExtP, "1 Kb");
    }*/

    private final File[] result = ConsoleApp.path(new File("src/test/java/resources"));
    private final File[] file = new File[0];

    @Test
    void path() {
        File one = new File("src/test/java/resources/one.txt");
        File two = new File("src/test/java/resources/two.txt");
        File three = new File("src/test/java/resources/three.txt");
        File folder = new File("src/test/java/resources/folder");
        File java = new File("src/test/java/resources/java.PNG");
        File kotlin = new File("src/test/java/resources/kotlin.PNG");
        File[] result1 = new File[]{folder, java, kotlin, one, three, two}; // !Нумерация здесь
        assertArrayEquals(result1, result);
        assertArrayEquals(file , ConsoleApp.path(new File("src/test/java/error")));
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
        ConsoleApp.InfoHolder inf = new ConsoleApp.InfoHolder(result[0]);
        assertEquals("folder" + System.lineSeparator() + "22.03.2021 01:02:39" + System.lineSeparator()
                + "96638 B" + System.lineSeparator() + "111", inf.createStrL());
        inf = new ConsoleApp.InfoHolder(result[2]);
        assertEquals("kotlin.png" + System.lineSeparator() + "22.03.2021 00:25:25" + System.lineSeparator()
                + "23198 B" + System.lineSeparator() + "111", inf.createStrL());
        inf = new ConsoleApp.InfoHolder(result[3]);
        assertEquals("one.txt" + System.lineSeparator() + "22.03.2021 22:06:26" + System.lineSeparator()
                + "127 B" + System.lineSeparator() + "111", inf.createStrL());
        inf = new ConsoleApp.InfoHolder(result[4]);
        assertEquals("three.txt" + System.lineSeparator() + "22.03.2021 00:59:22" + System.lineSeparator()
                + "0 B" + System.lineSeparator() + "111", inf.createStrL());
    }

    @Test
    void human() {
        assert result != null;
        ConsoleApp.InfoHolder inf = new ConsoleApp.InfoHolder(result[0]);
        assertEquals("folder" + System.lineSeparator() + "22.03.2021 01:02:39" + System.lineSeparator() + "94 Kb" + System.lineSeparator() +
                "rwx", inf.createStrH());
        inf = new ConsoleApp.InfoHolder(result[1]);
        assertEquals("java.png" + System.lineSeparator() + "22.03.2021 00:25:56" + System.lineSeparator() + "43 Kb" + System.lineSeparator() +
                "rwx", inf.createStrH());
        inf = new ConsoleApp.InfoHolder(result[4]);
        assertEquals("three.txt" + System.lineSeparator() + "22.03.2021 00:59:22" + System.lineSeparator() + "0 B" + System.lineSeparator() +
                "rwx", inf.createStrH());
    }
}


