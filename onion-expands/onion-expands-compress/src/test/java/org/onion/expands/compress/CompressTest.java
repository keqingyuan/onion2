package org.onion.expands.compress;

import org.junit.Assert;
import org.onion.commons.file.FileUtils;
import org.onion.expands.compress.zip.ZIPReader;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * 压缩解压文件测试
 *
 * @author zhouhao
 */
public class CompressTest {

    @org.junit.Test
    public void testUnzip() throws Exception {
        ZIPReader reader = Compress.unzip(FileUtils.getResourceAsFile("test.zip"));
        List<String> files = reader.ls();
        Assert.assertEquals(files.size(), 2);
        reader.unpack(new File("target/test"));
    }

    @org.junit.Test
    public void testZip() throws Exception {
        Compress.zip()
                .addTextFile("test.txt", "test")
                .addTextFile("/test/test2.txt", "test2")
                .write(new FileOutputStream("target/test.zip"));
    }
}