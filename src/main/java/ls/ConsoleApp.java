package ls;

import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


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
            return emptyFileArray(); //null
        }
    }

    public static File[] emptyFileArray() {
        return ArrayUtils.toArray();
    }

    public static String getName(File file) {
        return file.getName();
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
     * Метод, возвращающий размер в байтах (отдельный метод, поскольку rightSize() может оптимизирровать до Кб, Мб ...).
     */
    public static String sizeBytes(File file) {
        return size(file) + " B";
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
     * Размер файла в кило-, мега- или гигабайтах.
     */
    public static String rightSize(File file) {
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
    public static String filePermissions(File file, String r, String w, String x, String none){
        StringBuilder permission = new StringBuilder();
        if (file.canRead()) {
            permission.append(r);
        } else {
            permission.append(none);
        }
        if (file.canWrite()) {
            permission.append(w);
        } else {
            permission.append(none);
        }
        if (file.canExecute()) {
            permission.append(x);
        } else {
            permission.append(none);
        }
        return permission.toString();
    }

    public static String infoHolder(File file, boolean name, boolean time, boolean size,
                                    boolean sizeExt, boolean permissionsH, boolean permissionsL) {
        String nameP = "";
        if (name) nameP = getName(file) + System.lineSeparator();
        String timeP = "";
        if (time) timeP = timeOfLastModification(file) + System.lineSeparator();
        String sizeP = "";
        if (size) sizeP = sizeBytes(file) + System.lineSeparator();
        String sizeExtP = "";
        if (sizeExt) sizeExtP = rightSize(file) + System.lineSeparator();
        String permissionsHP = "";
        if (permissionsH) permissionsHP = filePermissions(file, "r", "w", "x", "-") + System.lineSeparator();
        String permissionsLP = "";
        if (permissionsL) permissionsLP = filePermissions(file, "1", "1", "1", "0") + System.lineSeparator();
        return nameP + timeP + sizeP + sizeExtP + permissionsHP + permissionsLP;
    }

}


