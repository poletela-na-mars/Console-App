package ls;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Polina Postylyakova (poletela-na-mars)
 */

public class ConsoleApp {

    /**
     * Метод возвращает файлы, список файлов (касаемо директории).
     */
    public static File[] path(File file) {
        if (file.isDirectory()) {
            return file.listFiles();
        } else {
            if (file.exists()) return new File[]{file};
            return emptyFileArray();
        }
    }
    public static File[] emptyFileArray() {
        File[] file = null;
        return file;
    }

    public static String getName(File file) {
        return file.getName();
    }

    /**
     * Флаг, переключающий вывод в длинный формат. Указываются имя файла, права в виде битовой маски, время последней
     * модификации, размер в байтах.
     */
    public static String l(File file, boolean fl) {
        if (fl) return timeOfLastModification(file);
        else return timeOfLastModification(file) + System.lineSeparator()
                + size(file) + " B"
                + System.lineSeparator() + filePermissions(file, true, false);
    }

    /**
     * Метод, возвращающий размер файла или директории. Также яляется вспомогательным для метода sizeForH.
     */
    public static long size(File file) {
        if (file.isDirectory()) {
            long l = 0;
            File[] files = file.listFiles();
            for (File fileD : Objects.requireNonNull(files)) {
                if (fileD.isDirectory()) l += size(fileD);  // Для случая: директория в директории
                else l += fileD.length();
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
                + filePermissions(file, false, true);
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
     * Права на выполнение/чтение/запись битовой маски XXX для -l или rwx для -h.
     */
    public static String filePermissions(File file, boolean l, boolean hr){
        StringBuilder permission = new StringBuilder();
        if (l) {
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
        }
        if (hr) {
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
        }
        return permission.toString();
    }
}


