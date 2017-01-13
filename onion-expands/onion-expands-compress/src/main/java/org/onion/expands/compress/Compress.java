package org.onion.expands.compress;


import org.onion.expands.compress.zip.ZIPReader;
import org.onion.expands.compress.zip.ZIPWriter;

import java.io.File;

public class Compress {
    public static ZIPReader unzip(File file) {
        return new ZIPReader(file);
    }

    public static ZIPWriter zip() {
        return new ZIPWriter();
    }
}
