//package com.esprit.cloudcraft.controller;
//
//import com.esprit.cloudcraft.services.IEventService;
//import com.esprit.cloudcraft.entities.Event;
//import com.esprit.cloudcraft.utils.QRCodeGenerator;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.*;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@CrossOrigin(origins = "http://localhost:4200")
//@RequestMapping("/qr")
//public class QRcodeController {
//
/////////////////////////////////  HEDHA LKOL NTESTI BIH L FRONT plus au moins zeyd masla7 l chay
//
//        private static final String QR_CODE_IMAGE_PATH = "C:\\Users\\amena\\Desktop\\PICLOUD\\test2\\test\\src\\main\\java\\com\\event\\test\\utils\\example-1.png";
//
//        @Autowired
//        IEventService eventInterface;
//   @Autowired
//     IEventService eventService;
//
//
//    @GetMapping(value = "/generateQRCode/{eventId}/{userId}", produces = MediaType.IMAGE_PNG_VALUE)
//    public ResponseEntity<byte[]> generateQRCode(@PathVariable("eventId") Long eventId,
//                                                 @PathVariable("userId") Long userId) {
//        try {
//            // Assuming you have a method to fetch the event and user details by their IDs
//            Event event = eventService.findbyid(eventId);
//
//            // Generate QR code using event and user details
//            byte[] qrCodeImage = QRCodeGenerator.generateQRCode(event);
//
//            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(qrCodeImage);
//        } catch (Exception e) {
//            // Handle exception properly (e.g., log error)
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
//
//
//
//@GetMapping(value = "/generateAllQRCode")
//public ResponseEntity<List<byte[]>> generateAllQRCode() {
//    try {
//        List<Event> events = eventService.getallEvents();
//        List<byte[]> qrCodeImages = new ArrayList<>();
//        for (Event event : events) {
//            String codeText = event.getDescription(); // Assuming getDescription() method exists in Event class
//            byte[] qrCodeImage = QRCodeGenerator.generateQRCode(event);
//            qrCodeImages.add(qrCodeImage);
//        }
//        return ResponseEntity.ok().body(qrCodeImages);
//    } catch (Exception e) {
//        // Handle exception properly (e.g., log error)
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//    }
//}
//
//
//    @GetMapping(value = "/genrateQRCode/{idEvent}")
//    public ResponseEntity<byte[]> generateQRCode(
//            @PathVariable("idEvent") Long idEvent)
//            throws Exception {
//        Event event = eventInterface.findbyid(idEvent);
//        byte[] qrCodeImage = QRCodeGenerator.generateQRCode(event);
//        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_PNG).body(qrCodeImage);
//    }
//
//
//
//
//
//
//}
//  //  private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/QRcodeImage";
//
//
////
////    @GetMapping(value = "/genrateAndDownloadQRCode/{idEvent}/{width}/{height}")
////    public void download(
////            @PathVariable("idEvent") Long idEvent,
////            @PathVariable("width") Integer width,
////            @PathVariable("height") Integer height)
////            throws Exception {
////        Event event = eventInterface.findbyid(idEvent);
////        String codeText = event.getDescription(); // Assuming getDescription() method exists in Event class
////        String imagePath = QR_CODE_IMAGE_PATH + "/" + codeText + ".png";
////        QRCodeGenerator.generateQRCodeImage(codeText, width.intValue(), height.intValue(), imagePath);
////    }
//
//
//
//
