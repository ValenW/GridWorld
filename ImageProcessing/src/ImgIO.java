/* 
* @Author: anchen
* @Date:   2015-08-23 09:13:31
* @Last Modified by:   anchen
* @Last Modified time: 2015-08-24 08:44:39
*/

/* 
 * AP(r) Computer Science GridWorld Case Study:
 * Copyright(c) 2005-2006 Cay S. Horstmann (http://horstmann.com)
 *
 * This code is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * @author Chris Nevison
 * @author Barbara Cloud Wells
 * @author Cay Horstmann
 */

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

import imagereader.IImageIO;

public class ImgIO implements IImageIO {

    /* read bmp image from bytes */
    public Image myRead(String filePath) {
        Image image;
        Path path = Paths.get(filePath);
        byte[] data = null;
        try {
            data = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int offset = getIntFromBytes(data, 10, 13);
        int width = getIntFromBytes(data, 18, 21);
        int height = getIntFromBytes(data, 22, 25);
        int bitCount = getIntFromBytes(data, 28, 29);
        int size = getIntFromBytes(data, 34, 37);
        int offPerRow = (size - height * width * bitCount / 8) / height;

        /* 
         * System.out.println("\noffset: "+ offset + "\nbitCount: " + bitCount + "\nheight: " + height + "\nwidth: " + width + "\nsize: " + size + "\noffPerRow: " + offPerRow + "\n");
         *
         */

        int rgbs[] = new int[height * width];

        if (bitCount == 24) {
            for (int i = 0; i < height * width; i++) {
                int pos = (height - i / width - 1) * width + i % width;
                rgbs[pos] = getIntFromBytes(data, offset, offset + 2);
                rgbs[pos] |= 255 << 24;
                offset += 3;
                if ((i + 1) % width == 0) {
                    offset += offPerRow;
                }
            }
            image = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, rgbs, 0, width));
        } else {
            // TODO
            image = null;
        }
        return image;
    }

    /* call java API to store the Image */
    public Image myWrite(Image image, String filePath) {
        try {
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            File imageFile = new File(filePath);
            BufferedImage bufImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
            bufImage.getGraphics().drawImage(image, 0, 0, width, height, null);
            ImageIO.write(bufImage, "BMP", imageFile);
            return image;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    /* get int from the byte[] */
    public int getIntFromBytes(byte[] data, int start, int end) {
        int length = end - start + 1;
        if (length > 8) {
            return -1;
        }
        int re = 0;
        for (int i = 0; i < length; i++) {
            re |= ((data[start + i] & 0xff) << (i * 8));
        }
        return re;
    }
}
