package com.auto.boot.common.utils;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;

import static java.awt.image.BufferedImage.TYPE_4BYTE_ABGR;

/**
 * @program: isignings
 * @description: 图片处理工具
 * @author: zhl
 * @create: 2019-09-18 14:29
 **/
@Slf4j
public class ImageUtils {

    public static final String[] imageSuffixes = {".png", ".jpg", ".jpeg"};

    /**
     * 判断是否是图片
     *
     * @param bytes 图片
     * @return
     */
    @SneakyThrows
    public static boolean isImage(byte[] bytes) {
        if (bytes == null) {
            return false;
        }
        @Cleanup BufferedInputStream imgFile = null;
        try {
            imgFile = new BufferedInputStream(new ByteArrayInputStream(bytes));
            Image img;
            try {
                img = ImageIO.read(imgFile);
                return !(img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0);
            } catch (Exception e) {
                log.error("图片解析失败！{}", e);
                return false;
            }
        } catch (Exception e) {
            log.error("图片解析失败！", e);
        }
        return false;
    }

    /**
     * 图片数组转ImageIcon
     *
     * @param image
     * @return
     */
    public static ImageIcon byteToImageIcon(byte[] image) {
        return new ImageIcon(image);
    }

    /**
     * 获取图片的高度
     *
     * @param image
     * @return
     */
    public static int getImageHeight(ImageIcon image) {
        return image.getIconHeight();
    }

    /**
     * 获取图片的宽度
     *
     * @param image
     * @return
     */
    public static int getImageWidth(ImageIcon image) {
        return image.getIconWidth();
    }

    /**
     * BufferedImage转字节
     */
    public static byte[] BufferedImage2Byte(BufferedImage imgMap) throws Exception {
        OutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(imgMap, "png", out);
            return ((ByteArrayOutputStream) out).toByteArray();
        } catch (Exception e) {
            log.error("Exception: {}", e);
        } finally {
            imgMap.flush();
            out.close();
        }
        return null;
    }

    /**
     * BufferedImage转字节
     */
    public static byte[] BufferedImage2Byte(BufferedImage imgMap, String format) throws Exception {
        OutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(imgMap, format, out);
            return ((ByteArrayOutputStream) out).toByteArray();
        } catch (Exception e) {
            log.error("Exception: {}", e);
        } finally {
            imgMap.flush();
            out.close();
        }
        return null;
    }


    /**
     * 小图复制到大图
     *
     * @param small
     * @param big
     * @return
     */
    public static void smallCopyBig(int x, int y, ImageIcon small, Graphics2D big) {

        //得到Image对象。
        Image img = small.getImage();
        //默认是0,0开始
        big.drawImage(img, x, y, null);
    }

    /**
     * 图片转png
     *
     * @param data
     * @return
     */
    public static byte[] image2png(byte[] data) {
        BufferedImage bufferedImage;
        try {
            ImageIcon imageIcon = new ImageIcon(data);
            bufferedImage = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(),
                    BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();
            g2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon.getImageObserver());
            int alpha = 0;
            for (int j1 = bufferedImage.getMinY(); j1 < bufferedImage.getHeight(); j1++) {
                for (int j2 = bufferedImage.getMinX(); j2 < bufferedImage.getWidth(); j2++) {
                    int rgb = bufferedImage.getRGB(j2, j1);

                    int R = (rgb & 0xff0000) >> 16;
                    int G = (rgb & 0xff00) >> 8;
                    int B = (rgb & 0xff);
                    if (((255 - R) < 30) && ((255 - G) < 30) && ((255 - B) < 30)) {
                        rgb = ((alpha + 1) << 24) | (rgb & 0x00ffffff);
                    }
                    bufferedImage.setRGB(j2, j1, rgb);
                }
            }
            g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());
            return BufferedImage2Byte(bufferedImage);
        } catch (Exception e) {
            log.error("Exception: {}", e);
        } finally {
        }
        return null;
    }

    /**
     * 修改图片尺寸
     *
     * @param newWidth
     * @param newHeight
     * @param image
     * @return
     */
    public static byte[] changeSize(int newWidth, int newHeight, byte[] image) throws Exception {
        ImageIcon imageIcon = byteToImageIcon(image);
        OutputStream out = new ByteArrayOutputStream();
        try {
            //字节流转图片对象
            Image bi = imageIcon.getImage();
            //构建图片流
            BufferedImage tag = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            //绘制改变尺寸后的图
            tag.getGraphics().drawImage(bi, 0, 0, newWidth, newHeight, null);
            //输出流

            ImageIO.write(tag, "jpg", out);
        } catch (IOException e) {
            log.error("Exception: {}", e);
        } finally {
            out.close();
        }
        return image2png(((ByteArrayOutputStream) out).toByteArray());
    }

    /**
     * 修改图片尺寸
     *
     * @param newWidth
     * @param newHeight
     * @param image
     * @return
     */
    public static byte[] changeSizeNew(int newWidth, int newHeight, byte[] image) throws Exception {

        OutputStream out = new ByteArrayOutputStream();
        try {
            InputStream in = new ByteArrayInputStream(image);
            BufferedImage bi = ImageIO.read(in);
            BufferedImage to = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = to.createGraphics();
            to = g2d.getDeviceConfiguration().createCompatibleImage(newWidth, newHeight,
                    Transparency.TRANSLUCENT);
            g2d.dispose();
            g2d = to.createGraphics();
            @SuppressWarnings("static-access")
            Image from = bi.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_AREA_AVERAGING);
            g2d.drawImage(from, 0, 0, null);
            g2d.dispose();

            ImageIO.write(to, "png", out);
        } catch (IOException e) {
            log.error("Exception: {}", e);
        } finally {
            out.close();
        }
        return image2png(((ByteArrayOutputStream) out).toByteArray());
    }

    /**
     * 将image对象 转成 BufferedImage
     *
     * @param image
     * @return
     */
    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        image = new ImageIcon(image).getImage();
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            int transparency = Transparency.OPAQUE;
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(
                    image.getWidth(null), image.getHeight(null), transparency);
        } catch (HeadlessException e) {
            //........
        }

        if (bimage == null) {
            int type = BufferedImage.TYPE_INT_RGB;
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }
        Graphics g = bimage.createGraphics();

        g.drawImage(image, 0, 0, null);
        g.dispose();

        return bimage;

    }

    /**
     * 判断文件名的后缀
     *
     * @param fileName
     * @return
     */
    public static boolean judgeSuffix(String fileName) {
        int lastIndexOf = fileName.lastIndexOf(".");
        if (lastIndexOf < 0) {
            return false;
        }
        String fileSuffix = fileName.substring(lastIndexOf);
        //判断后缀 格式  是否 正常
        for (String suffix : imageSuffixes) {
            if (suffix.equalsIgnoreCase(fileSuffix)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取图片h
     *
     * @param image
     * @return
     */
    public static int getH(BufferedImage image) {
        return image.getHeight();
    }

    /**
     * 获取图片w
     *
     * @param image
     * @return
     */
    @SneakyThrows
    public static int getW(BufferedImage image) {
        return image.getWidth();
    }

    /**
     * 获取图片ARGB值
     *
     * @param image
     * @return
     */
    public static byte[] getARGB(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        int[] buffer = new int[w * h];
        image.getRGB(0, 0, w, h, buffer, 0, w);

        byte[] src = new byte[w * h * 4];
        for (int i = 0; i < w * h; i++) {
            src[i * 4] = (byte) (buffer[i] & 0xff);
            src[i * 4 + 1] = (byte) (buffer[i] >> 8 & 0xff);
            src[i * 4 + 2] = (byte) (buffer[i] >> 16 & 0xff);
            src[i * 4 + 3] = (byte) (buffer[i] >> 24 & 0xff);
        }
        return src;
    }

    @SneakyThrows
    public static BufferedImage byte2BufferedImage(byte[] data) {
        @Cleanup InputStream inputStream = new ByteArrayInputStream(data);
        return ImageIO.read(inputStream);
    }

    /**
     * argb2image
     *
     * @param dst
     * @param w
     * @param h
     * @return
     */
    public static BufferedImage ARGB2BufferedImage(byte[] dst, int w, int h) {
        BufferedImage img = new BufferedImage(w, h, TYPE_4BYTE_ABGR);

        int[] out = new int[w * h];
        for (int i = 0; i < w * h; i++) {
            out[i] = ((int) (dst[i * 4 + 3]) << 24 & 0xff000000)
                    | ((int) (dst[i * 4 + 2]) << 16 & 0xff0000)
                    | ((int) (dst[i * 4 + 1]) << 8 & 0xff00)
                    | ((int) (dst[i * 4 + 0]) & 0xff);
        }
        img.setRGB(0, 0, w, h, out, 0, w);
        return img;
    }

    /**
     * 生成透明的png
     *
     * @param w
     * @param h
     * @return
     */
    @SneakyThrows
    public static byte[] transparentPng(int w, int h) {
        // 创建BufferedImage对象
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        int[] argb = image.getRGB(0, 0, w, h, null, 0, w);
        Arrays.fill(argb, 0);
        image.setRGB(0, 0, w, h, argb, 0, w);
        // 保存文件
        return image2png(BufferedImage2Byte(image));
    }
}
