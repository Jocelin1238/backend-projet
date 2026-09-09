package SystemeReservationticket.example.SystemeReservation.Services;


import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;


@Service
public class QRCodeService {



    public String genererQRCode(String contenu) {


        try {


            // dossier de stockage
            String dossier = "qrcodes/";

            File repertoire = new File(dossier);


            if(!repertoire.exists()){

                repertoire.mkdirs();

            }



            // nom du fichier QR

            String nomFichier =
                    contenu + ".png";



            Path chemin =
                    Paths.get(
                    dossier + nomFichier
                    );



            QRCodeWriter writer =
                    new QRCodeWriter();



            BitMatrix matrix =
                    writer.encode(
                    contenu,
                    BarcodeFormat.QR_CODE,
                    300,
                    300
                    );



            MatrixToImageWriter.writeToPath(
                    matrix,
                    "PNG",
                    chemin
            );



            return chemin.toString();



        } catch(Exception e){


            throw new RuntimeException(
                    "Erreur lors de la génération du QR Code"
            );

        }

    }

}