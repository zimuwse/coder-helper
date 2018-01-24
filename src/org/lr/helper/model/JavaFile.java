package org.lr.helper.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author: zimuwse
 * @time: 2018-01-03 18:57
 * @description:
 */
public class JavaFile {
    private String path;
    private String name;
    private String content;

    public JavaFile(String basePath, IJavaFile javaFile) {
        this.path = basePath;
        this.name = javaFile.fileName();
        this.content = javaFile.fileContent();
    }

    public boolean save() {
        File folder = new File(path);
        if (!folder.exists()) {
            boolean mk = folder.mkdirs();
            if (!mk)
                return false;
        }
        File file = new File(path + "/" + name);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(content.getBytes("utf-8"));
            fos.close();
        } catch (IOException e) {
            return false;
        } finally {
            if (null != fos)
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return true;
    }
}
