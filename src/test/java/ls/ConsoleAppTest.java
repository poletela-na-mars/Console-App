package ls;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleAppTest {

    @Test
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
        File test = new File("src/test/java/resources/java.PNG");
        assertEquals(ConsoleApp.rightSize(test), "43 Kb");
        File test1 = new File("src/test/java/resources/kotlin.PNG");
        assertEquals(ConsoleApp.rightSize(test1), "22 Kb");
        File test2 = new File("src/test/java/resources/three.txt");
        assertEquals(ConsoleApp.rightSize(test2), "0 B");
        File test3 = new File("src/test/java/resources/two.txt");
        assertNotEquals(ConsoleApp.rightSize(test3), ConsoleApp.timeOfLastModification(test3));
        assertNotEquals(ConsoleApp.rightSize(test3), "2048 B");
        assertEquals(ConsoleApp.rightSize(test3), "1 Kb");
        File test5 = new File("src/test/java/resources/no.txt");
        assertNotEquals(ConsoleApp.rightSize(test5), "0 Kb");
        assertEquals(ConsoleApp.rightSize(test5), "0 B");
    }

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
        ConsoleApp.infoHolder inf = new ConsoleApp.infoHolder(result[0]);
        assertEquals("folder" + System.lineSeparator() + "22.03.2021 01:02:39" + System.lineSeparator()
                + "96638 B" + System.lineSeparator() + "111", inf.nameP + System.lineSeparator() + inf.timeP + System.lineSeparator() + inf.sizeP + System.lineSeparator() + inf.permissionsLP);
        inf = new ConsoleApp.infoHolder(result[2]);
        assertEquals("kotlin.png" + System.lineSeparator() + "22.03.2021 00:25:25" + System.lineSeparator()
                + "23198 B" + System.lineSeparator() + "111", inf.nameP + System.lineSeparator() + inf.timeP + System.lineSeparator() + inf.sizeP + System.lineSeparator() + inf.permissionsLP);
        inf = new ConsoleApp.infoHolder(result[3]);
        assertEquals("one.txt" + System.lineSeparator() + "22.03.2021 22:06:26" + System.lineSeparator()
                + "127 B" + System.lineSeparator() + "111", inf.nameP + System.lineSeparator() + inf.timeP + System.lineSeparator() + inf.sizeP + System.lineSeparator() + inf.permissionsLP);
        inf = new ConsoleApp.infoHolder(result[4]);
        assertEquals("three.txt" + System.lineSeparator() + "22.03.2021 00:59:22" + System.lineSeparator()
                + "0 B" + System.lineSeparator() + "111", inf.nameP + System.lineSeparator() + inf.timeP + System.lineSeparator() + inf.sizeP + System.lineSeparator() + inf.permissionsLP);
    }

    @Test
    void human() {
        assert result != null;
        ConsoleApp.infoHolder inf = new ConsoleApp.infoHolder(result[0]);
        assertEquals("folder" + System.lineSeparator() + "94 Kb" + System.lineSeparator() +
                "rwx", inf.nameP + System.lineSeparator() + inf.sizeExtP + System.lineSeparator() + inf.permissionsHP);
        inf = new ConsoleApp.infoHolder(result[1]);
        assertEquals("java.png" + System.lineSeparator() + "43 Kb" + System.lineSeparator() +
                "rwx", inf.nameP + System.lineSeparator() + inf.sizeExtP + System.lineSeparator() + inf.permissionsHP);
        inf = new ConsoleApp.infoHolder(result[4]);
        assertEquals("three.txt" + System.lineSeparator() + "0 B" + System.lineSeparator() +
                "rwx", inf.nameP + System.lineSeparator() + inf.sizeExtP + System.lineSeparator() + inf.permissionsHP);
    }
}


