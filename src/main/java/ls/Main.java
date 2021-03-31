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

    public void start(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException exp) {
            System.err.println(exp.getMessage());
            parser.printUsage(System.err);
            System.exit(1);
            return;
        }

        if (ConsoleApp.path(forD).length != 0) {    // Если есть аргументы
            File[] result = ConsoleApp.path(forD);
            if (rv) {
                Collections.reverse(Arrays.asList(result)); // Инвертируем массив с информацией о файлах,
                // из-за присутствия флага -r
            }
            for (File file : result) {
                ConsoleApp.InfoHolder inf = new ConsoleApp.InfoHolder(file);
                String lS = inf.createStrL();
                String hS = inf.createStrH(file);

                if (op != null) {
                    try (FileWriter writer = new FileWriter(op)) {  // Место, куда будем записывать информацию
                        if (l && hr) writer.write(hS);
                        else if (l)
                            writer.write(lS);
                        else if (hr)
                            writer.write(hS);
                    }
                } else {    // Если флаг отсутствует, то в консоль
                    if (l && hr) System.out.println(hS);
                    else if (l)
                        System.out.println(lS);
                    else if (hr)
                        System.out.println(hS);
                }
            }
        } else System.out.println("Directory or file is empty");
    }
}




