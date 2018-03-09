package com.yrgyz;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by ZBaimanov on 27.02.2018.
 */
class ReadFileLast {

    boolean hasLogFileError(File file, int lines){
        int readLines = 0;
        String line = "";
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "r");
            long fileLength = file.length() - 1;
            // Set the pointer at the last of the file
            randomAccessFile.seek(fileLength);
            for (long pointer = fileLength; pointer >= 0; pointer--){
                randomAccessFile.seek(pointer);
                char c;
                // read from the last one char at the time
                c = (char)randomAccessFile.read();
                // break when end of the line
                if (c == '\n') {
                    if (line.contains("Unable to get a connection to resource") ||
                            line.contains("A connection was not available for request in pool")) {
                        return true;
                    }
                    line = "";
                    readLines++;
                    if(readLines == lines)
                        break;
                }
                line = c + line;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(randomAccessFile != null){
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

}