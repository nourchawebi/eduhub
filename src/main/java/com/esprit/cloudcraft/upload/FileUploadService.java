package com.esprit.cloudcraft.upload;

import com.esprit.cloudcraft.utils.UtilFunctions;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class FileUploadService implements IFileUploadService{


    private static final List<String> ALLOWED_PDF_CONTENT_TYPES = Arrays.asList("application/pdf");
    private static final List<String> ALLOWED_IMAGE_CONTENT_TYPES = Arrays.asList("image/jpeg", "image/png","image/webp");

    @Override
    public void init() {
        try{
            File tempDir = new File(FileUploadContants.ROOT_DIR_PATH.toUri());
            boolean dirExists = tempDir.exists();
            if(!dirExists){
                Files.createDirectory(FileUploadContants.ROOT_DIR_PATH);
                Files.createDirectory(FileUploadContants.IMAGES_PATH);
                Files.createDirectory(FileUploadContants.PDF_PATH);
            }
            File imageDir = new File(FileUploadContants.IMAGES_PATH.toUri());
            boolean imageDirExist = imageDir.exists();
            if(!imageDirExist){
                Files.createDirectory(FileUploadContants.IMAGES_PATH);
            }
            File pdfDir = new File(FileUploadContants.PDF_PATH.toUri());
            boolean pdfDirExist = pdfDir.exists();
            if(!pdfDirExist){
                Files.createDirectory(FileUploadContants.PDF_PATH);
            }
        System.out.println(pdfDirExist);
            System.out.println(imageDir);
            System.out.println(dirExists);
        }catch(IOException ex){
            throw new RuntimeException("Error creating root directory");
        }
    }


    @Override
    public FileObject save(MultipartFile file) {
        try{
            String fileName=UtilFunctions.makeRandomFileName(Objects.requireNonNull(file.getOriginalFilename()));
            System.out.println(file.getContentType());
            if (ALLOWED_PDF_CONTENT_TYPES.contains(file.getContentType())) {
                return saveFile(file,fileName);
            }if(ALLOWED_IMAGE_CONTENT_TYPES.contains(file.getContentType())) {
                return saveImage(file, fileName);
            }
        }catch(Exception ex){
            throw new RuntimeException("Error uploading files");
        }
        return null;
    }

    private FileObject saveFile(MultipartFile file,String fileName) throws IOException {
        Files.copy(
                file.getInputStream(),
                FileUploadContants.PDF_PATH.resolve(Objects.requireNonNull(fileName)
                ));

        Path filePath = FileUploadContants.PDF_PATH.resolve(fileName);
        return FileObject.builder()
                .fileName(fileName)
                .fileLocation(filePath)
                .fileType(FileType.PDF)
                .build();
    }
    private FileObject saveImage(MultipartFile image,String imageName) throws IOException {
        Files.copy(
                image.getInputStream(),
                FileUploadContants.IMAGES_PATH.resolve(Objects.requireNonNull(imageName)
                ));

        Path filePath = FileUploadContants.IMAGES_PATH.resolve(imageName);
        return FileObject.builder()
                .fileName(imageName)
                .fileLocation(filePath)
                .fileType(FileType.IMAGE)
                .build();
    }

    @Override
    public Resource getFileByName(String fileName) {
        try{
         Path filePath = FileUploadContants.ROOT_DIR_PATH.resolve(fileName);
         Resource resource=new UrlResource((filePath.toUri()));
         if(resource.exists()&&resource.isReadable()) return resource;
         else throw new RuntimeException("Could not read file");
        }catch(MalformedURLException ex){
            throw new RuntimeException("Error: "+ex.getMessage());
        }

    }
    public Path getFilePath(String fileName,FileType fileType){
        if(fileType==FileType.PDF) return  Paths.get(FileUploadContants.PDF_PATH.toUri()).resolve(fileName);
        if(fileType==FileType.IMAGE) return Paths.get(FileUploadContants.IMAGES_PATH.toUri()).resolve(fileName);
        return null;
    }
    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(FileUploadContants.ROOT_DIR_PATH.toFile());
    }
    @Override
    public void deleteFileByName(String fileName,FileType fileType) {
        Path filePath =null;
        if(fileType==FileType.IMAGE)
            filePath=FileUploadContants.IMAGES_PATH.resolve(fileName);
        if(fileType==FileType.PDF)
            filePath=FileUploadContants.PDF_PATH.resolve(fileName);
        try {
            // Check if the file exists
            if(filePath==null) return;
            if (Files.exists(filePath)) {
                // Delete the file
                Files.delete(filePath);
                System.out.println("File deleted successfully.");
            } else {
                System.out.println("File does not exist.");
            }
        } catch (Exception e) {
            System.out.println("Error deleting the file: " + e.getMessage());
        }
    }

    @Override
    public Stream<Path> loadAllFiles() {
        try{
            return Files.walk(FileUploadContants.ROOT_DIR_PATH,1)
                    .filter(path->!path.equals(FileUploadContants.ROOT_DIR_PATH))
                    .map(FileUploadContants.ROOT_DIR_PATH::relativize);
        }catch(IOException ex){
            throw new RuntimeException("Could not load files");
        }
    }
}
