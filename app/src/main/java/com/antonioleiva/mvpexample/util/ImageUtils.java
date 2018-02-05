package com.antonioleiva.mvpexample.util;

import android.graphics.BitmapFactory;

/**
 * Created by Administrator on 2018-02-02.
 */

public class ImageUtils {
    /**
     * 避免Bitmap引起的OOM技巧小结
     *
     * 采用低内存占用量的编码方式
     * BitmapFactory.Options这个类，我们可以设置下其中的inPreferredConfig属性，
     * 默认是Bitmap.Config.ARGB_8888，我们可以修改成Bitmap.Config.ARGB_4444
     *Bitmap.Config ARGB_4444：每个像素占四位，即A=4，R=4，G=4，B=4，
     * 那么一个像素点占4+4+4+4=16位,Bitmap.Config ARGB_8888：每个像素占八位，
     * 即A=8，R=8，G=8，B=8，那么一个像素点占8+8+8+8=32位,默认使用ARGB_8888，即一个像素占4个字节！
     *
     *
     *图片压缩
     *
     * 同样是BitmapFactory.Options，我们通过inSampleSize设置缩放倍数，比如写2，
     * 即长宽变为原来的1/2，图片就是原来的1/4，如果不进行缩放的话设置为1即可！
     * 但是不能一味的压缩，毕竟这个值太小的话，图片会很模糊，而且要避免图片的拉伸变形，
     * 所以需要我们在程序中动态的计算，这个 inSampleSize的合适值，
     * 而Options中又有这样一个方法：inJustDecodeBounds，将该参数设置为 true后，
     * decodeFiel并不会分配内存空间，但是可以计算出原始图片的长宽，
     * 调用 options.outWidth/outHeight获取出图片的宽高，然后通过一定的算法，
     * 即可得到适合的 inSampleSize，
     */
    public static int caculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;
        if (width > reqWidth || height > reqHeight) {
            int widthRadio = Math.round(width * 1.0f / reqWidth);
            int heightRadio = Math.round(height * 1.0f / reqHeight);
            inSampleSize = Math.max(widthRadio, heightRadio);
        }
        return inSampleSize;
    }

}
/**
 * 然后使用下上述的方法即可：
 *
 *BitmapFactory.Options options = new BitmapFactory.Options();
 options.inJustDecodeBounds = true; // 设置了此属性一定要记得将值设置为false
 Bitmap bitmap = null;
 bitmap = BitmapFactory.decodeFile(url, options);
 options.inSampleSize = computeSampleSize(options,128,128);
 options.inPreferredConfig = Bitmap.Config.ARGB_4444;
 下面两个字段需要组合使用
options.inPurgeable = true;
        options.inInputShareable = true;
        options.inJustDecodeBounds = false;
        try {
        bitmap = BitmapFactory.decodeFile(url, options);
        } catch (OutOfMemoryError e) {
        Log.e(TAG, "OutOfMemoryError");
        }
 *
 *
 */
