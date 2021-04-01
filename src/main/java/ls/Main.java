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

    void work(Appendable appendable, File file) throws IOException {
        ConsoleApp.InfoHolder inf = new ConsoleApp.InfoHolder(file);
        if (l && hr)
            appendable.append(inf.createStrH());
        else if (l)
            appendable.append(inf.createStrL());
        else if (hr)
            appendable.append(inf.createStrH());
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

        File[] result = ConsoleApp.path(forD);
        if (result.length != 0) {    // Если есть аргументы
            if (rv) {
                Collections.reverse(Arrays.asList(result)); // Инвертируем массив с информацией о файлах,
                // из-за присутствия флага -r
            }
            for (File file : result) {
                ConsoleApp.InfoHolder inf = new ConsoleApp.InfoHolder(file);
                if (op != null) {
                    try(FileWriter fileWriter = new FileWriter(op)) {
                        work(fileWriter, file);
                    }
                } else {
                    work(System.out, file);
                }


            }
        } else System.out.println("Directory or file is empty");
    }

}




