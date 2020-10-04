package fileSplit;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.util.ArrayList;

public class CmdParser {

    /**
     * Вариант 12 -- split
     * Разбивает большой текстовый файл на маленькие файлы фиксированного размера.
     * ● file задаёт имя входного файла.
     * ● Флаг -o ofile задаёт базовое имя выходного файла. Если параметр
     * отсутствует, базовое имя выходного файла равняется “x”. Если параметр
     * равен “-”, в качестве базового имени выходного файла следует использовать
     * имя входного файла.
     * ● Флаг -d означает, что выходные файлы следует называть “ ofile 1, ofile 2, ofile 3,
     * ofile 4 …”, где ofile это базовое имя выходного файла. Если же этот параметр не
     * указан, то файлы должны называться “ ofile aa, ofile ab, ofile ac, ofile ad … ofile az
     * ofile ba ofile bb … ”.
     * ● Флаг -l num указывает размер выходных файлов в строчках. По умолчанию
     * выходные файлы имеют размер в 100 строчек.
     * ● Флаг - с num указывает размер выходных файлах в символах .
     * ● Флаг -n num указывает количество выходных файлов. Размер файлов при
     * этом должен считаться автоматически.
     * Command line: split [-d] [-l num |-c num |-n num ] [-o ofile ] file
     * В случае, когда какое-нибудь из имён файлов указано неверно или одновременно
     * указано несколько флагов управления размером, следует выдать ошибку.
     * Кроме самой программы, следует написать автоматические тесты к ней.
     */

    //Если делать аргументы, то как определить их порядок?
    @Option(name = "-file", required = true)
    private String file;
    @Option(name = "-o")
    private boolean isOFile;
    @Option(name = "-d")
    private boolean fileName;
    @Option(name = "-l")
    private boolean fileSizeStr;
    @Option(name = "-c")
    private boolean fileSizeSym;
    @Option(name = "-n")
    private boolean fileCount;
    @Argument()
    private ArrayList<String> arguments;
    @Option(name = "-ofile")
    private String oFile = "";

    private int num;

    public void parse(String[] mass) {
        CmdLineParser cmdLineParser = new CmdLineParser(this);
        try {
            cmdLineParser.parseArgument(mass);
            this.num = Integer.parseInt(arguments.get(0));
        } catch (NumberFormatException | CmdLineException e) {
            System.err.println("Bad arguments");
            e.printStackTrace();
        }
        this.num = Integer.parseInt(arguments.get(0));
        File file = new File(this.file);
        File oFile = new File(this.oFile);
        if (!oFile.exists()) {
            System.out.println("Bad file to write");
            cmdLineParser.printUsage(System.out);
            throw new IllegalArgumentException();
        }
        if (!file.exists()) {
            System.out.println("Bad file to read");
            cmdLineParser.printUsage(System.out);
            throw new IllegalArgumentException();
        }
        if (!isOFile || this.oFile.equals(""))
            this.oFile = "x";
        if (isOFile && this.oFile.equals("_"))
            this.oFile = this.file;
        if (checkBool() > 1) {
            System.out.println("Only 0 or 1 argument for change files");
            cmdLineParser.printUsage(System.out);
            throw new IllegalArgumentException();
        }
        if (checkBool() == 0) {
            this.fileSizeStr = true;
            this.num = 100;
        }
    }

    private int checkBool() {
        int count = 0;
        if (fileSizeStr)
            count++;
        if (fileCount)
            count++;
        if (fileSizeSym)
            count++;
        return count;
    }

    public String getFile() {
        return file;
    }

    public String getOFile() {
        return this.oFile;
    }

    public boolean isOFile() {
        return isOFile;
    }

    public boolean isFileName() {
        return fileName;
    }

    public boolean isFileSizeStr() {
        return fileSizeStr;
    }

    public boolean isFileSizeSym() {
        return fileSizeSym;
    }

    public boolean isFileCount() {
        return fileCount;
    }

    public int getNum() {
        return num;
    }
}
