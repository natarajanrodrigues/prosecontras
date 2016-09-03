/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author kieckegard
 */
public class PhotoUtils {

    public static String salvarFoto(String nomePasta, String nomeFoto, InputStream foto) {
        BufferedImage bf = null;
        String path = nomePasta;
        
        try {
            bf = ImageIO.read(foto);
            path = path.replaceAll("%20", " ");
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            file = new File(path + nomeFoto);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(getByteImage(bf));

//            return path.substring(path.indexOf("img/")) + nomeFoto;
            return path + nomeFoto;
        } catch (IOException ex) {
            throw new CouldNotCreateFileException("O arquivo " + (path+nomeFoto) + " não pôde ser criado.");
        }
    }
    
    public static byte[] getByteImage(BufferedImage imagem) throws IOException{
        if(imagem == null)
            return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(imagem, "jpg", baos);
        byte[] bytes = baos.toByteArray();
        
        return bytes;
    }
}
