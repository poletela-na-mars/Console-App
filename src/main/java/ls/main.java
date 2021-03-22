package ls;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class main {

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
        new main().start(args);
    }

    private void start(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException exp) {
            System.err.println(exp.getMessage());
            System.err.println("java -jar Ls.jar [-l] [-h] [-r] [-o output.file] directory_or_file");
            parser.printUsage(System.err);
            System.exit(1);
            return;
        }

        if (Objects.requireNonNull(ConsoleApp.path(forD)).length != 0) {    // Если есть аргументы
            File[] result = ConsoleApp.path(forD);
            if (rv) {
                assert result != null;
                Collections.reverse(Arrays.asList(result)); // Инвертируем массив с информацией о файлах,
                // из-за присутствия флага -r
            }
            if (op != null) {
                try (FileWriter writer = new FileWriter(op)) {  // Место, куда будем записывать информацию
                    assert result != null;
                    for (File file : result) {
                        writer.write(ConsoleApp.getName(file) + System.lineSeparator());
                        if (l) writer.write(ConsoleApp.l(file) + System.lineSeparator());
                        if (hr) writer.write(ConsoleApp.human(file) + System.lineSeparator());
                    }
                }
            } else {    // Если флаг отсутствует, то в консоль
                assert result != null;
                for (File file : result) {
                    System.out.println(ConsoleApp.getName(file));
                    if (l) System.out.println(ConsoleApp.l(file));
                    if (hr) System.out.println(ConsoleApp.human(file));
                }
            }
        }
        else System.out.println("Directory or file is empty");
    }
}




