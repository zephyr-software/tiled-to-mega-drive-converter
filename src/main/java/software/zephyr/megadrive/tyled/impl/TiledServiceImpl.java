package software.zephyr.megadrive.tyled.impl;

import software.zephyr.megadrive.tyled.TiledService;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TiledServiceImpl implements TiledService {

    public static final String OUTPUT_FILENAME = "tilemap.asm";
    public static final String TILEMAP_NAME = "tilemap";
    public static final String TILEMAP_DATA = "TILEMAP_DATA";

    @Override
    public void convertCsvToAsm(File file) throws IOException {
        System.out.println("convert csv to asm - file: " + file.getAbsoluteFile());

        List<List<String>> records = readRecords(file);

        PrintWriter printWriter = new PrintWriter(OUTPUT_FILENAME);
        writeHeader(printWriter, records);
        writeData(printWriter, records);

        printWriter.flush();
        printWriter.close();
    }

    private List<List<String>> readRecords(File file) throws IOException {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file.getPath()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }
        }

        return records;
    }

    private void writeHeader(PrintWriter printWriter, List<List<String>> records) {
        List<String> recordsLine = records.get(0);

        printWriter.println("; ******************************************************************************");
        printWriter.println("; " + TILEMAP_NAME);
        printWriter.println(";");
        printWriter.println("; tiles in line : " + recordsLine.size());
        printWriter.println("; tile lines    : " + records.size());
        printWriter.println("; total tiles   : " + records.size() * recordsLine.size());
        printWriter.println("; total bytes   : " + records.size() * recordsLine.size() * 2);
        printWriter.println("; ******************************************************************************");
        printWriter.println();
    }

    private void writeData(PrintWriter printWriter, List<List<String>> records) {
        printWriter.println(TILEMAP_DATA + ":");
        int lineCounter = 0;
        for (List<String> recordsLine : records) {

            boolean isFirstRecordInLine = true;
            for (String record : recordsLine) {
                String line = "    dc.w 0x000" + record;
                if (isFirstRecordInLine) {
                    line += "; tilemap line #" + lineCounter++;
                    isFirstRecordInLine = false;
                }

                printWriter.println(line);
            }

            printWriter.println();
        }
    }
}
