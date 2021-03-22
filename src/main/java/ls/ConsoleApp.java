package ls;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Polina Postylyakova (poletela-na-mars)
 */

public class ConsoleApp {
    public File file;

    public ConsoleApp(File file) {
        this.file = file;
    }

    /**
     * Метод возвращает файлы, список файлов (касаемо директории).
     */
    public static File[] path(File file) {
        if (file.isDirectory()) {
            return file.listFiles();
        } else {
            if (file.exists()) return new File[]{file};
            return null;
        }
    }

    public static String getName(File file) {
        return file.getName();
    }

    /**
     * Флаг, переключающий вывод в длинный формат. Указываются имя файла, права в виде битовой маски, время последней
     * модификации, размер в байтах.
     */
    public static String l(File file) {
        return timeOfLastModification(file) + System.lineSeparator()
                + size(file) + " B"
                + System.lineSeparator() + filePermissionsL(file);
    }

    /**
     * Метод, возвращающий размер файла или директории. Также яляется вспомогательным для метода sizeForH.
     */
    public static long size(File file) {
        if (file.isDirectory()) {
            long l = 0;
            File[] files = file.listFiles();
            assert files != null;
            for (File fileD: files) {
                if (fileD.isFile())
                    l += fileD.length();
                else l += size(fileD);  // Если директория, то повторем и анализируем размер каждого файла
            }
            return l;
        }
        else return file.length();
    }

    /**
     * Флаг, переключающий вывод в человеко-читаемый формат.
     * Указываются имя файла, размер файла в кило-, мега- или гигабайтах,
     * права в виде rwx.
     */
    public static String human(File file)  {
        return sizeForHr(file) + System.lineSeparator()
                + filePermissionsH(file);
    }

    /**
     * Время последней модификации файла для -l.
     */
    public static String timeOfLastModification(File file) {
        final long time = file.lastModified();
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return df.format(new Date(time));
    }

    /**
     * Размер файла в кило-, мега- или гигабайтах для -h.
     */
    public static String sizeForHr(File file) {
            long rightSize = size(file);
            int count = 0;
            while (rightSize >= 1024) {
                rightSize /= 1024;
                count++;
            }
            String measure = switch (count) {
            //case 0 -> "B";
            case 1 -> "Kb";
            case 2 -> "Mb";
            case 3 -> "Gb";
            default -> "B";
        };
            return rightSize + " " + measure;
        }

    /**
     * Права на выполнение/чтение/запись в виде rwx для -h.
     */
    public static String filePermissionsH(File file) {
        StringBuilder permission = new StringBuilder();
        if (file.canRead()) {
            permission.append("r");
        } else {
            permission.append("-");
        }
        if (file.canWrite()) {
            permission.append("w");
        } else {
            permission.append("-");
        }
        if (file.canExecute()) {
            permission.append("x");
        } else {
            permission.append("-");
        }
        return permission.toString();
    }

    /**
     * Права на выполнение/чтение/запись в виде битовой маски для -l.
     */
    public static String filePermissionsL(File file) {
        StringBuilder permission = new StringBuilder();
        if (file.canRead()) {
            permission.append("1");
        } else {
            permission.append("0");
        }
        if (file.canWrite()) {
            permission.append("1");
        } else {
            permission.append("0");
        }
        if (file.canExecute()) {
            permission.append("1");
        } else {
            permission.append("0");
        }
        return permission.toString();
    }
}


