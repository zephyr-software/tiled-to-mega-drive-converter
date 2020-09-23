package software.zephyr.megadrive.tyled;

import java.io.File;
import java.io.IOException;

public interface TiledService {

    void convertCsvToAsm(File file) throws IOException;
}
