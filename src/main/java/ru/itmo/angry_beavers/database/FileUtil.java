package ru.itmo.angry_beavers.database;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FileUtil {
    public static String[] getFromFile(String fileName) {
        try {
            File file = new File(fileName);
            List<String> lines = FileUtils.readLines(file, StandardCharsets.UTF_8);
            String[] res = new String[lines.size()];
            lines.toArray(res);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[]{};
    }

}
