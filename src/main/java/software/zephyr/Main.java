package software.zephyr;

import software.zephyr.megadrive.tyled.impl.TiledServiceImpl;

import java.io.File;
import java.io.IOException;

public class Main {

    public static final String PROGRAM_NAME = "tiled to mega drive converter";
    public static final String PROGRAM_VERSION = "0.0.1";

    public static void main(String[] args) throws IOException {
        printProgramInfo();

        validateInput(args);
        new TiledServiceImpl().convertCsvToAsm(new File(args[0]));

        System.out.println("converting done");
    }

    private static void printProgramInfo() {
        System.out.println(PROGRAM_NAME);
        System.out.println("version: " + PROGRAM_VERSION);
        System.out.println();
    }

    private static void validateInput(String[] args) {
        if (args.length == 0) {
            System.out.println("please set csv file to convert");

            System.exit(0);
        }

        File file = new File(args[0]);
        if (!file.isFile() || !file.exists()) {
            System.out.println("file not found");

            System.exit(0);
        }
    }
}
