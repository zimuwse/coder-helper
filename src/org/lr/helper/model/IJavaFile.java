package org.lr.helper.model;

/**
 * @author: zimuwse
 * @time: 2018-01-04 10:50
 * @description:
 */
public interface IJavaFile {
    /**
     * file content
     *
     * @return
     */
    String fileContent();

    /**
     * file name
     *
     * @return
     */
    String fileName();

    /**
     * file path
     *
     * @return
     */
    String filePath();
}
