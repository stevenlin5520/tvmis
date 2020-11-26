package com.steven.television.util;

import com.alibaba.simpleimage.ImageFormat;
import com.alibaba.simpleimage.ImageWrapper;
import com.alibaba.simpleimage.SimpleImageException;
import com.alibaba.simpleimage.render.CropParameter;
import com.alibaba.simpleimage.render.ScaleParameter;
import com.alibaba.simpleimage.render.WriteParameter;
import com.alibaba.simpleimage.util.ImageCropHelper;
import com.alibaba.simpleimage.util.ImageReadHelper;
import com.alibaba.simpleimage.util.ImageScaleHelper;
import com.alibaba.simpleimage.util.ImageWriteHelper;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import javax.media.jai.PlanarImage;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author steven
 * @desc
 * @date 2020/11/13 16:25
 */
public class ImageUtil {

    public static void main(String[] args) throws Exception {
        // 输入输出文件路径/文件
        String src = "D:\\data\\surface6.jpg";
        String dest = "D:\\data\\surface6-2.jpg";
        File srcFile = new File(src);
        File destFile = new File(dest);

        // 将输入文件转换为字节数组
        byte[] bytes = getByte(srcFile);
        // 构造输入输出字节流
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // 处理图片
        zoomImage(is, os, 750, 400);
        // 将字节输出流写到输出文件路径下
        writeFile(os, destFile);
    }

    /**
     * 按照固定大小缩放
     * @param source
     * @param target
     * @param width
     * @param height
     * @throws Exception
     */
    public static void zoomImageRatio(File source,File target,int width,int height) throws Exception {
        // 将输入文件转换为字节数组
        byte[] bytes = getByte(source);
        // 构造输入输出字节流
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // 处理图片
        zoomImage(is, os, width, height);
        // 将字节输出流写到输出文件路径下
        writeFile(os, target);
    }


    /**
     * 按固定长宽进行缩放
     * @param is      输入流
     * @param os      输出流
     * @param width   指定长度
     * @param height  指定宽度
     * @throws Exception
     */
    public static void zoomImage(InputStream is, OutputStream os, int width, int height) throws Exception {
        //读取图片
        BufferedImage bufImg = ImageIO.read(is);
        is.close();
        //获取缩放比例
        double wRatio = width * 1.0/ bufImg.getWidth();
        double hRatio = height * 1.0 / bufImg.getHeight();

        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wRatio, hRatio), null);
        BufferedImage bufferedImage = ato.filter(bufImg, null);
        //写入缩减后的图片
        ImageIO.write(bufferedImage, "jpg", os);
    }

    /**
     * 按固定文件大小进行缩放
     * @param is     输入流
     * @param os     输出流
     * @param size   文件大小指定
     * @throws Exception
     */
    public static void zoomImage(InputStream is, OutputStream os, Integer size) throws Exception {
        /*FileInputStream的available()方法返回的是int类型，当数据大于1.99G(2147483647字节)后将无法计量，
            故求取流文件大小最好的方式是使用FileChannel的size()方法，其求取结果与File的length()方法的结果一致
            参考：http://blog.csdn.net/chaijunkun/article/details/22387305*/
        int fileSize = is.available();
        //文件大于size时，才进行缩放。注意：size以K为单位
        if(fileSize < size * 1024){
            return;
        }
        // 获取长*宽(面积)缩放比例
        double sizeRate = (size * 1024 * 0.5) / fileSize;
        // 获取长和宽分别的缩放比例，即面积缩放比例的2次方根
        double sideRate = Math.sqrt(sizeRate);
        BufferedImage bufImg = ImageIO.read(is);

        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(sideRate, sideRate), null);
        BufferedImage bufferedImage = ato.filter(bufImg, null);
        ImageIO.write(bufferedImage, "jpg", os);
    }

    /**
     * 等比例缩放，以宽或高较大者达到指定长度为准
     * @param src      输入文件路径
     * @param dest     输出文件路径
     * @param width    指定宽
     * @param height   指定高
     */
    public static void zoomToRatio(String src, String dest, Integer width, Integer height){
        try {
            File srcFile = new File(src);
            File destFile = new File(dest);
            BufferedImage bufImg = ImageIO.read(srcFile);
            int w0 = bufImg.getWidth();
            int h0 = bufImg.getHeight();
            // 获取较大的一个缩放比率作为整体缩放比率
            double wRatio = 1.0 * width / w0;
            double hRatio = 1.0 * height / h0;
            double ratio = Math.min(wRatio, hRatio);
            // 缩放
            AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
            BufferedImage bufferedImage = ato.filter(bufImg, null);
            // 输出
            ImageIO.write(bufferedImage, dest.substring(dest.lastIndexOf(".")+1), destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 等比例图片压缩，以宽或高较大者达到指定长度为准
     * @param is     输入流
     * @param os     输出流
     * @param width  宽
     * @param height 高
     * @throws IOException
     */
    public static void changeSize(InputStream is, OutputStream os, int width, int height) throws IOException {
        // 构造Image对象
        BufferedImage bis = ImageIO.read(is);
        is.close();
        // 得到源图宽
        int srcWidth = bis.getWidth(null);
        // 得到源图高
        int srcHeight = bis.getHeight(null);

        if (width <= 0 || width > srcWidth) {
            width = bis.getWidth();
        }
        if (height <= 0 || height > srcHeight) {
            height = bis.getHeight();
        }
        // 若宽高小于指定最大值，不需重新绘制
        if (srcWidth <= width && srcHeight <= height) {
            ImageIO.write(bis, "jpg", os);
            os.close();
        } else {
            double scale =
                    ((double) width / srcWidth) > ((double) height / srcHeight) ?
                            ((double) height / srcHeight)
                            : ((double) width / srcWidth);
            width = (int) (srcWidth * scale);
            height = (int) (srcHeight * scale);

            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            // 绘制缩小后的图
            bufferedImage.getGraphics().drawImage(bis, 0, 0, width, height, Color.WHITE, null);
            ImageIO.write(bufferedImage, "jpg", os);
            os.close();
        }
    }

    /**
     * 先等比例缩放，小边缩放至指定长度后， 大边直接裁剪指指定长度
     * @param is
     * @param os
     * @param width
     * @param height
     */
    public final static void zoomAndCut1(InputStream is, OutputStream os, int width, int height) throws SimpleImageException {
        // 读文件
        ImageWrapper imageWrapper = ImageReadHelper.read(is);
        int w = imageWrapper.getWidth();
        int h = imageWrapper.getHeight();
        double wRatio = 1.0 * width / w;
        double hRatio = 1.0 * height / h;
        double ratio = Math.max(wRatio, hRatio);
        /*1.缩放*/
        // 缩放参数  如果图片宽和高都小于目标图片则不做缩放处理
        ScaleParameter scaleParam = null;
        if (w < width && h < height) {
            scaleParam = new ScaleParameter(w, h, ScaleParameter.Algorithm.LANCZOS);
        }
        // 为防止强转int时小数部分丢失，故加1，防止出现异常错误
        scaleParam = new ScaleParameter((int)(w * ratio) + 1, (int)(h * ratio) + 1, ScaleParameter.Algorithm.LANCZOS);
        // 缩放
        PlanarImage planarImage = ImageScaleHelper.scale(imageWrapper.getAsPlanarImage(), scaleParam);
        /*2.裁切*/
        // 获取裁剪偏移量
        imageWrapper = new ImageWrapper(planarImage);
        int w2 = imageWrapper.getWidth();
        int h2 = imageWrapper.getHeight();
        int x = (w2 - width) / 2;
        int y = (h2 - height) / 2;
        // 裁切参数   如果图片宽和高都小于目标图片则处理
        CropParameter cropParam = new CropParameter(x, y, width, height);
        if (x < 0 || y < 0) {
            cropParam = new CropParameter(0, 0, w, h);
        }
        // 裁剪
        planarImage = ImageCropHelper.crop(planarImage, cropParam);
        /*输出*/
        imageWrapper = new ImageWrapper(planarImage);
        String prefix = "jpg";
        ImageWriteHelper.write(imageWrapper, os, ImageFormat.getImageFormat(prefix), new WriteParameter());
    }

    /**
     * 先等比例缩放，小边缩放至指定长度后， 大边直接裁剪指指定长度
     * @param is
     * @param os
     * @param width
     * @param height
     */
    public static void zoomAndCut2(InputStream is, OutputStream os, Integer width, Integer height) throws IOException, SimpleImageException {
        // 读文件
        BufferedImage bufferedImage = ImageIO.read(is);
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
        // 获取缩放比例
        double wRatio = 1.0 * width / w;
        double hRatio = 1.0 * height / h;
        double ratio = Math.max(wRatio, hRatio);
        // 缩放
        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
        bufferedImage = ato.filter(bufferedImage, null);
        // 对象转换
        ImageWrapper imageWrapper = new ImageWrapper(bufferedImage);
        // 获得裁剪偏移量
        int w2 = imageWrapper.getWidth();
        int h2 = imageWrapper.getHeight();
        float x = (w2 - width) / 2.0f;
        float y = (h2 - height) / 2.0f;
        // 裁剪参数   如果图片宽和高都小于目标图片则处理
        CropParameter cropParameter = new CropParameter(x, y, width, height);
        if (x < 0 && y < 0) {
            cropParameter = new CropParameter(0, 0, width, height);
        }
        PlanarImage crop = ImageCropHelper.crop(imageWrapper.getAsPlanarImage(), cropParameter);
        imageWrapper = new ImageWrapper(crop);
        // 后缀
        String prefix = "jpg";
        // 写文件
        ImageWriteHelper.write(imageWrapper, os, ImageFormat.getImageFormat(prefix), new WriteParameter());
    }

    /**
     * 从中间裁切需要的大小
     * @param is
     * @param os
     * @param width
     * @param height
     */
    public static void CutCenter(InputStream is, OutputStream os, Integer width, Integer height) {
        try {
            ImageWrapper imageWrapper = ImageReadHelper.read(is);

            int w = imageWrapper.getWidth();
            int h = imageWrapper.getHeight();

            int x = (w - width) / 2;
            int y = (h - height) / 2;

            CropParameter cropParam = new CropParameter(x, y, width, height);// 裁切参数
            if (x < 0 || y < 0) {
                cropParam = new CropParameter(0, 0, w, h);// 裁切参数
            }

            PlanarImage planrImage = ImageCropHelper.crop(imageWrapper.getAsPlanarImage(), cropParam);
            imageWrapper = new ImageWrapper(planrImage);
            String prefix = "JPG";
            ImageWriteHelper.write(imageWrapper, os, ImageFormat.getImageFormat(prefix), new WriteParameter());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    /**
     * 将file文件转为字节数组
     * @param file
     * @return
     */
    public static byte[] getByte(File file){
        byte[] bytes = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            bytes = new byte[fis.available()];
            fis.read(bytes);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * 将字节流写到指定文件
     * @param os
     * @param file
     */
    public static void writeFile(ByteArrayOutputStream os, File file){
        FileOutputStream fos = null;
        try {
            byte[] bytes = os.toByteArray();
            if (file.exists()) {
                file.delete();
            }
            fos = new FileOutputStream(file);
            fos.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
