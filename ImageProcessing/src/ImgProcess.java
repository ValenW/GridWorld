/* 
* @Author: anchen
* @Date:   2015-08-23 09:13:54
* @Last Modified by:   anchen
* @Last Modified time: 2015-08-24 11:20:49
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

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.*;
import imagereader.IImageProcessor;

public class ImgProcess implements IImageProcessor {
    private ImageExec imgExec = new ImageExec();

    /* this mathod use imgExec and Show* class
     * to process the source Image in different ways
     *
     */
    public Image showChanelR(Image sourceImage) {
        return imgExec.act(sourceImage, new ShowR());
    }

    public Image showChanelG(Image sourceImage) {
        return imgExec.act(sourceImage, new ShowG());
    }

    public Image showChanelB(Image sourceImage) {
        return imgExec.act(sourceImage, new ShowB());
    }

    public Image showGray(Image sourceImage) {
        return imgExec.act(sourceImage, new ShowGray());
    }

    /* get BufferedImage from the Image. */
    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_BGR);

        // Draw the image on to the buffered image
        bimage.getGraphics().drawImage(img, 0, 0, img.getWidth(null), img.getHeight(null), null);

        return bimage;
    }

    /* the interface of RGB calculator. */
    public interface CalRGB {
        public int exec(int rgb);
    }

    /* the true class that calculate thr RGB. */
    public class ShowR implements CalRGB {
        public int exec(int rgb) {
            return rgb & 0xffff0000;
        }
    }

    public class ShowG implements CalRGB {
        public int exec(int rgb) {
            return rgb & 0xff00ff00;
        }
    }

    public class ShowB implements CalRGB {
        public int exec(int rgb) {
            return rgb & 0xff0000ff;
        }
    }

    public class ShowGray implements CalRGB {
        public int exec(int rgb) {
            int r = (rgb & 0x00ff0000) >> 16;
            int g = (rgb & 0x0000ff00) >> 8;
            int b = (rgb & 0x000000ff);
            int gray = (int)(0.299 * r + 0.587 * g + 0.114 * b);
            return (int) ((255 << 24) + (gray << 16) + (gray << 8) + gray);
        }
    }

    /* deal with the Image with different caler */
    private class ImageExec {
        public Image act(Image img, CalRGB caler) {
            int height = img.getHeight(null);
            int width = img.getWidth(null);
            BufferedImage image = toBufferedImage(img);
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int rgb = image.getRGB(j, i);
                    image.setRGB(j, i, caler.exec(rgb));
                }
            }
            return image;
        }
    }
}
