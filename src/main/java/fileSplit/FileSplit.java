package fileSplit;

import java.io.*;

public class FileSplit {
    /**
     * Вариант 12 -- split
     * Разбивает большой текстовый файл на маленькие файлы фиксированного размера.
     * ● file задаёт имя входного файла.
     * <p>
     * <p>
     * ● Флаг -o ofile задаёт базовое имя выходного файла. Если параметр
     * отсутствует, базовое имя выходного файла равняется “x”. Если параметр
     * равен “-”, в качестве базового имени выходного файла следует использовать
     * имя входного файла.
     * <p>
     * ● Флаг -d означает, что выходные файлы следует называть “ ofile 1, ofile 2, ofile 3,
     * ofile 4 …”, где ofile это базовое имя выходного файла. Если же этот параметр не
     * указан, то файлы должны называться “ ofile aa, ofile ab, ofile ac, ofile ad … ofile az
     * ofile ba ofile bb … ”.
     * <p>
     * <p>
     * ● Флаг -l num указывает размер выходных файлов в строчках. По умолчанию
     * выходные файлы имеют размер в 100 строчек.
     * ● Флаг - с num указывает размер выходных файлах в символах .
     * ● Флаг -n num указывает количество выходных файлов. Размер файлов при
     * этом должен считаться автоматически.
     * <p>
     * <p>
     * Command line: split [-d] [-l num |-c num |-n num ] [-o ofile ] file
     * В случае, когда какое-нибудь из имён файлов указано неверно или одновременно
     * указано несколько флагов управления размером, следует выдать ошибку.
     * Кроме самой программы, следует написать автоматические тесты к ней.
     */

    public void separateFile(String file, String oFile, boolean d, boolean l, boolean c, boolean n, int num) {
        StringBuilder fileContent = new StringBuilder();
        int countStr = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            while (bufferedReader.ready()) {
                fileContent.append(bufferedReader.readLine()).append("\n");
                countStr++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int countFiles = 0;
        if (l)
            countFiles = countStr / num;
        if (c)
            countFiles = fileContent.length() / num;
        if (n)
            countFiles = num;
        String namePref = "";
        for (int i = 0; i < countFiles; i++) {
            namePref = createName(namePref);
            String f = d ? (i + 1) + "" : namePref;
            String fileName = oFile + " " + f + ".txt";
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(fileName)))) {
                if (l) {
                    bufferedWriter.write(getTextStr(0, num, fileContent.toString()));
                    fileContent.delete(0, num);
                }
                if (c) {
                    bufferedWriter.write(getTextSymb(num, fileContent.toString()));
                    fileContent.delete(0, num);
                }
                if (n) {
                    bufferedWriter.write(getTextSymb(fileContent.length() / num, fileContent.toString()));
                    fileContent.delete(0, fileContent.length() / num);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String createName(String name) {
        if (name.equals(""))
            return "aa";
        String newName = "";
        if (name.charAt(0) < 'z')
            newName += (char) name.charAt(0);
        if (name.charAt(1) < 'z')
            newName += (char) (name.charAt(1) + 1);
        else
            newName = (char) (name.charAt(0) + 1) + "a";
        return newName;

    }

    private String getTextStr(int start, int end, String text) {
        String[] split = text.split("\\n");
        String str = "";
        for (int i = start; i < end; i++) {
            str += split[i] + "\n";
        }
        return str;
    }

    private String getTextSymb(int num, String text) {
        String newText = "";
        for (int i = 0; i < num; i++) {
            newText += text.charAt(i);
        }
        return newText;
    }
}
