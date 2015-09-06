/* 
* @Author: anchen
* @Date:   2015-08-23 09:55:32
* @Last Modified by:   anchen
* @Last Modified time: 2015-08-24 15:23:42
*/

import java.awt.Image;
import imagereader.Runner;

public class IImageRunner {
    public static void main(String[] args) {
        ImgIO imaIO = new ImgIO();
        ImgProcess imaPro = new ImgProcess();
        //Image m01 = imaIO.myRead("bmptest/1.bmp");
        //Image m02 = imaIO.myRead("bmptest/2.bmp");
        // Image mr1 = imaPro.showChanelR(m01);
        // Image mr2 = imaPro.showChanelR(m02);
        // Image mg1 = imaPro.showChanelG(m01);
        // Image mg2 = imaPro.showChanelG(m02);
        // Image mb1 = imaPro.showChanelB(m01);
        // Image mb2 = imaPro.showChanelB(m02);
        //Image mh1 = imaPro.showGray(m01);
        //Image mh2 = imaPro.showGray(m02);
        //imaIO.myWrite(m01, "01.bmp");
        //imaIO.myWrite(m02, "02.bmp");
        // imaIO.myWrite(mr1, "r1.bmp");
        // imaIO.myWrite(mr2, "r2.bmp");
        // imaIO.myWrite(mg1, "g1.bmp");
        // imaIO.myWrite(mg2, "g2.bmp");
        // imaIO.myWrite(mb1, "b1.bmp");
        // imaIO.myWrite(mb2, "b2.bmp");
        //imaIO.myWrite(mh1, "h1.bmp");
        //imaIO.myWrite(mh2, "h2.bmp");
        Runner.run(imaIO, imaPro);
    }
}
