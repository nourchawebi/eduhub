package com.event.test.utils;


import com.event.test.Entity.Event;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class QRCodeGenerator {

    public static void generateQRCode(Event event) throws WriterException, IOException {
        String qrCodePath = "C:\\Users\\amena\\Desktop\\PICLOUD\\test\\QRCode\\";
        String qrCodeName = qrCodePath+event.getTitle()+event.getIdEvent()+"-QRCODE.png";
        var qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(
                "ID: "+event.getIdEvent()+ "\n"+
                        "Firstname: "+event.getTitle()+ "\n"+
                        "Lastname: "+event.getLocation()+ "\n"+
                        "Email: "+event.getCapacity()+ "\n" , BarcodeFormat.QR_CODE, 400, 400);
        Path path = FileSystems.getDefault().getPath(qrCodeName);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

    }
}