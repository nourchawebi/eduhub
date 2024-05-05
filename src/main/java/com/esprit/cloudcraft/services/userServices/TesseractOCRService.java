package com.esprit.cloudcraft.services.userServices;

import jakarta.annotation.Resource;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

@Service
public class TesseractOCRService {

    @Resource
    private Tesseract tesseract;

    public String recognizeText(InputStream inputStream) throws IOException {
        BufferedImage image = ImageIO.read(inputStream);
        try {
            return tesseract.doOCR(image);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        return "failed";
    }

}