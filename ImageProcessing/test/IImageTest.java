/* 
* @Author: anchen
* @Date:   2015-08-23 16:33:22
* @Last Modified by:   anchen
* @Last Modified time: 2015-08-24 15:34:48
*/

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class IImageTest {
    public ImgIO iimgIO = null;
    public ImgProcess iimgPro = null;
    
    public BufferedImage goal_img1 = null;
    public BufferedImage goal_img2 = null;
    public BufferedImage goal_imgr1 = null;
    public BufferedImage goal_imgr2 = null;
    public BufferedImage goal_imgg1 = null;
    public BufferedImage goal_imgg2 = null;
    public BufferedImage goal_imgb1 = null;
    public BufferedImage goal_imgb2 = null;
    public BufferedImage goal_imgh1 = null;
    public BufferedImage goal_imgh2 = null;

    public BufferedImage m01 = null;
    public BufferedImage m02 = null;
    public BufferedImage mr1 = null;
    public BufferedImage mr2 = null;
    public BufferedImage mg1 = null;
    public BufferedImage mg2 = null;
    public BufferedImage mb1 = null;
    public BufferedImage mb2 = null;
    public BufferedImage mh1 = null;
    public BufferedImage mh2 = null;

    public IImageTest() {
        iimgIO = new ImgIO();
        iimgPro = new ImgProcess();

        try {
            goal_img1 = ImageIO.read(new File("../bmptest/1.bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            goal_img2 = ImageIO.read(new File("../bmptest/2.bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            goal_imgr1 = ImageIO.read(new File("../bmptest/goal/1_red_goal.bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            goal_imgr2 = ImageIO.read(new File("../bmptest/goal/2_red_goal.bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            goal_imgg1 = ImageIO.read(new File("../bmptest/goal/1_green_goal.bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            goal_imgg2 = ImageIO.read(new File("../bmptest/goal/2_green_goal.bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            goal_imgb1 = ImageIO.read(new File("../bmptest/goal/1_blue_goal.bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            goal_imgb2 = ImageIO.read(new File("../bmptest/goal/2_blue_goal.bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            goal_imgh1 = ImageIO.read(new File("../bmptest/goal/1_gray_goal.bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            goal_imgh2 = ImageIO.read(new File("../bmptest/goal/2_gray_goal.bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testImg(BufferedImage img1, BufferedImage img2) {
        assertEquals(img1.getHeight(), img2.getHeight());
        assertEquals(img1.getWidth(), img2.getWidth());
        for (int i = 0; i < img1.getHeight(); i++) {
            for (int j = 0; j < img1.getWidth(); j++) {
                assertEquals(img1.getRGB(j, i), img2.getRGB(j, i));
            }
        }
    }

    @Test
    public void testRead1() {
        m01 = iimgPro.toBufferedImage(iimgIO.myRead("../bmptest/1.bmp"));
        testImg(goal_img1, m01);
    }

    @Test
    public void testRead2() {
        m02 = iimgPro.toBufferedImage(iimgIO.myRead("../bmptest/2.bmp"));
        testImg(goal_img2, m02);
    }

    @Test
    public void testRed1() {
        m01 = iimgPro.toBufferedImage(iimgIO.myRead("../bmptest/1.bmp"));
        mr1 = iimgPro.toBufferedImage(iimgPro.showChanelR(m01));
        testImg(goal_imgr1, mr1);
    }

    @Test
    public void testRed2() {
        m02 = iimgPro.toBufferedImage(iimgIO.myRead("../bmptest/2.bmp"));
        mr2 = iimgPro.toBufferedImage(iimgPro.showChanelR(m02));
        testImg(goal_imgr2, mr2);
    }

    @Test
    public void testGreen1() {
        m01 = iimgPro.toBufferedImage(iimgIO.myRead("../bmptest/1.bmp"));
        mg1 = iimgPro.toBufferedImage(iimgPro.showChanelG(m01));
        testImg(goal_imgg1, mg1);
    }

    @Test
    public void testGreen2() {
        m02 = iimgPro.toBufferedImage(iimgIO.myRead("../bmptest/2.bmp"));
        mg2 = iimgPro.toBufferedImage(iimgPro.showChanelG(m02));
        testImg(goal_imgg2, mg2);
    }

    @Test
    public void testBlue1() {
        m01 = iimgPro.toBufferedImage(iimgIO.myRead("../bmptest/1.bmp"));
        mb1 = iimgPro.toBufferedImage(iimgPro.showChanelB(m01));
        testImg(goal_imgb1, mb1);
    }

    @Test
    public void testBlue2() {
        m02 = iimgPro.toBufferedImage(iimgIO.myRead("../bmptest/2.bmp"));
        mb2 = iimgPro.toBufferedImage(iimgPro.showChanelB(m02));
        testImg(goal_imgb2, mb2);
    }

    @Test
    public void testGray1() {
        m01 = iimgPro.toBufferedImage(iimgIO.myRead("../bmptest/1.bmp"));
        mh1 = iimgPro.toBufferedImage(iimgPro.showGray(m01));
        testImg(goal_imgh1, mh1);
    }

    @Test
    public void testGray2() {
        m02 = iimgPro.toBufferedImage(iimgIO.myRead("../bmptest/2.bmp"));
        mh2 = iimgPro.toBufferedImage(iimgPro.showGray(m02));
        testImg(goal_imgh2, mh2);
    }
}
