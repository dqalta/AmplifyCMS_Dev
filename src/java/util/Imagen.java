/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import sun.misc.BASE64Encoder;

/**
 *
 * @author CR104978
 */
public class Imagen {

    public static boolean generar(String password) {
        boolean flag;
        try {
            BufferedImage image = ImageIO.read(Imagen.class.getResourceAsStream("/img/PasswordTemplate.png"));
            Graphics g = image.getGraphics();

            Font f = new Font("Arial", Font.BOLD, 18);
            g.setFont(f);
            Color co = new Color(255, 102, 0);
            g.setColor(co);

            //g.drawString(nombre, 180, 50);
            drawCenterText(g, password, 150, 0, 30);

            g.dispose();

            ImageWriter writer = ImageIO.getImageWritersByFormatName("png").next();
            ImageWriteParam param = writer.getDefaultWriteParam();
            //param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT); // Needed see javadoc
            //param.setCompressionQuality(1.0F); // Highest quality
            writer.setOutput(new FileImageOutputStream(new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + "password" + ".png")));
            writer.write(image);
            writer.dispose();
            flag = true;

            //flag = ImageIO.write(image, "jpeg", new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + correo + ".jpeg"));
            //flag = ImageIO.write(image, "jpg", new File("C:\\Users\\CR108002\\AppData\\Local\\Temp\\" + correo + ".png"));
        } catch (Exception x) {
            System.out.println(x.getMessage());
            flag = false;
        }
        return flag;
    }

    public static void drawCenterText(Graphics g, String texto, int width, int XPos, int YPos) {
        int stringLen = (int) g.getFontMetrics().getStringBounds(texto, g).getWidth();
        int start = width / 2 - stringLen / 2;
        g.drawString(texto, start + XPos, YPos);
    }

    public static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();

            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);

            bos.close();
        } catch (Exception x) {
            System.out.println(x.getMessage());
        }
        return imageString;
    }
}
