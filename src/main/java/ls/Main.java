package ls;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    @Option(name = "-l", metaVar = "long")
    private boolean l;

    @Option(name = "-h", metaVar = "human-readable")
    private boolean hr;

    @Option(name = "-r", metaVar = "reverse")
    private boolean rv;

    @Option(name = "-o", metaVar = "output")
    private File op;

    @Argument(required = true, metaVar = "input name", usage = "input directory")
    private File forD;

    public static void main(String[] args) throws IOException {
        new Main().start(args);
    }

    private void start(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException exp) {
            System.err.println(exp.getMessage());
            parser.printUsage(System.err);
            System.exit(1);
            return;
        }

        if (Objects.requireNonNull(ConsoleApp.path(forD)).length != 0) {    // Если есть аргументы
            File[] result = ConsoleApp.path(forD);
            if (rv) {
                Collections.reverse(Arrays.asList(Objects.requireNonNull(result))); // Инвертируем массив с информацией о файлах,
                // из-за присутствия флага -r
            }
            if (op != null) {
                try (FileWriter writer = new FileWriter(op)) {  // Место, куда будем записывать информацию
                    for (File file : Objects.requireNonNull(result)) {
                        writer.write(ConsoleApp.getName(file) + System.lineSeparator());
                        if (l) writer.write(ConsoleApp.l(file, hr) + System.lineSeparator());
                        if (hr) writer.write(ConsoleApp.human(file) + System.lineSeparator());
                    }
                }
            } else {    // Если флаг отсутствует, то в консоль
                for (File file : Objects.requireNonNull(result)) {
                    System.out.println(ConsoleApp.getName(file));
                    if (l) System.out.println(ConsoleApp.l(file, hr));
                    if (hr) System.out.println(ConsoleApp.human(file));
                }
            }
        }
        else System.out.println("Directory or file is empty");
    }
}




