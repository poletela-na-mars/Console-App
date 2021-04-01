package ls;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleAppTest {

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


