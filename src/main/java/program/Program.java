package program;

import fileSplit.CmdParser;
import fileSplit.FileSplit;

public class Program {
    public static void main(String[] args) {
        CmdParser cmdParser = new CmdParser();
        cmdParser.parse(args);
        System.out.println("File " + cmdParser.getFile());
        System.out.println("OFile " + cmdParser.getOFile());
        System.out.println("Num " + cmdParser.getNum());
        FileSplit fileSplit = new FileSplit();
        fileSplit.separateFile(cmdParser.getFile(), cmdParser.getOFile(), cmdParser.isFileName(), cmdParser.isFileSizeStr(), cmdParser.isFileSizeSym(),cmdParser.isFileCount(), cmdParser.getNum());
    }
}
