package ls;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.io.File;
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
        } else return file.length();
    }

    /**
     * Время последней модификации файла для -l.
     */
    public static long timeOfLastModification(File file) {
        return file.lastModified();
    }

    /**
     * Права на выполнение/чтение/запись битовой маски XXX для -l или rwx для -h.
     */
    public static List<Boolean> filePermissions(File file) {
        List<Boolean> listPerm = new ArrayList<>();
        // Создадим список с тремя значениями boolean, соответствующими возможности чтения/записи/выполнения
        // для последующего "гибкого" использования для вывода
        listPerm.add(file.canRead());
        listPerm.add(file.canWrite());
        listPerm.add(file.canExecute());
        return listPerm;
    }

    static class InfoHolder {
        String name;
        long time;
        static long sizeB;
        List<Boolean> permissions;

        String createStrL() {
            String permissionsLP = permissions(permissions, "1", "1", "1", "0");
            return String.format("%s%n%s%n%d%s%n%s", name, time(), sizeB, " B", permissionsLP);
        }

        String time(){
            SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            return df.format(new Date(time));
        }

        String createStrH() {
            Long sizeExtP = rightSize().left;
            String permissionsHP = permissions(permissions, "r", "w", "x", "-");
            return String.format("%s%n%s%n%d%s%n%s", name, time(), sizeExtP, rightSize().right, permissionsHP);
        }

        /**
         * Размер файла в кило-, мега- или гигабайтах.
         */
        public static ImmutablePair<Long, String> rightSize() {
            long rightSize = sizeB;
            int count = 0;
            while (rightSize >= 1024) {
                rightSize /= 1024;
                count++;
            }
            String measure = switch (count) {
                //case 0 -> "B";
                case 1 -> " Kb";
                case 2 -> " Mb";
                case 3 -> " Gb";
                default -> " B";
            };
            return new ImmutablePair<>(rightSize, measure);
        }

        /**
         * Метод преобразования списка в String. Необходим для перехода к выводу. Будем использовать в main.
         */
        public static String permissions(List<Boolean> filePermissions, String r, String w, String x, String none) {
            StringBuilder strB = new StringBuilder();
            Boolean read = filePermissions.get(0);
            if (read) strB.append(r);
            else strB.append(none);
            Boolean write = filePermissions.get(1);
            if (write) strB.append(w);
            else strB.append(none);
            Boolean execute = filePermissions.get(2);
            if (execute) strB.append(x);
            else strB.append(none);
            return strB.toString();
        }

        InfoHolder(File file) {
            name = getName(file);
            time = timeOfLastModification(file);
            sizeB = size(file);
            permissions = filePermissions(file);
        }
    }
}


