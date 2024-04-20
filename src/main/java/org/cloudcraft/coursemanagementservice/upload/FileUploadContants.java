package org.cloudcraft.coursemanagementservice.upload;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUploadContants {
    public final static String UPLOAD_DIR="uploads";
    public final static Path ROOT_DIR_PATH = Paths.get(FileUploadContants.UPLOAD_DIR);
    public final static Path IMAGES_PATH = ROOT_DIR_PATH.resolve("images");
    public final static Path PDF_PATH  = ROOT_DIR_PATH.resolve("pdfs");


}
