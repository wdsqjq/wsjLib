package per.wsj.commonlib.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 工具类，所有通用
 */
public class CommonUtil {

    public static String getVersionCode(Context context) {
        try {
            //applicationId 获取
            String pkName = context.getPackageName();
            //versionName获取
            String versionName = context.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            //versionCode获取
            int versionCode = context.getPackageManager().getPackageInfo(pkName, 0).versionCode;
            return String.valueOf(versionCode);
        } catch (Exception e) {
        }
        return "";
    }

    public static String getVersionName(Context context) {
        try {
            //applicationId 获取
            String pkName = context.getPackageName();
            //versionName获取
            String versionName = context.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            return String.valueOf(versionName);
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 判断是否安装某apk
     * @param context
     * @param PACK_NAME
     * @return
     */
    public static boolean isInstallApp(Context context, String PACK_NAME) {
        final PackageManager packageManager = context.getApplicationContext().getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName.toLowerCase(Locale.ENGLISH);
                if (pn.equals(PACK_NAME)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String[] getFlieName(String rootpath) {
        File root = new File(rootpath);
        File[] filesOrDirs = root.listFiles();
        if (filesOrDirs != null) {
            String[] dir = new String[filesOrDirs.length];
            int num = 0;
            for (int i = 0; i < filesOrDirs.length; i++) {
                if (filesOrDirs[i].isDirectory()) {
                    dir[i] = filesOrDirs[i].getName();

                    num++;
                }
            }
            String[] dir_r = new String[num];
            num = 0;
            for (int i = 0; i < dir.length; i++) {
                if (dir[i] != null && !dir[i].equals("")) {
                    dir_r[num] = dir[i];
                    num++;
                }
            }
            return dir_r;
        } else
            return null;
    }

    /**
     * //获得流
     *
     * @param path
     * @return
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    public BufferedWriter getWriter(String path) throws FileNotFoundException,
            UnsupportedEncodingException {
        FileOutputStream fileout = null;
        fileout = new FileOutputStream(new File(path));
        OutputStreamWriter writer = null;
        writer = new OutputStreamWriter(fileout, "UTF-8");
        return new BufferedWriter(writer);
    }

    public InputStream getInputStream(String path) throws FileNotFoundException {
        // if(Comments.DEBUG) System.out.println("path:"+path);
        FileInputStream filein = null;
        // if(Comments.DEBUG) System.out.println("2");
        // File file = creatFileIfNotExist(path);
        File file = new File(path);
        filein = new FileInputStream(file);
        BufferedInputStream in = null;
        if (filein != null)
            in = new BufferedInputStream(filein);
        return in;
    }

    public boolean StateXmlControl(String path) {
        File f = new File(path);
        if (!f.exists())
            return false;
        return f.length() != 0;
    }

    /**
     * 将InputStream转换成byte数组
     *
     * @param in InputStream
     * @return byte[]
     * @throws IOException
     */
    public static byte[] InputStreamTOByte(InputStream in) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[6 * 1024];
        int count = -1;
        while ((count = in.read(data, 0, 4 * 1024)) != -1)
            outStream.write(data, 0, count);
        data = null;
        return outStream.toByteArray();
    }

    /**
     * 将OutputStream转换成byte数组
     * <p>
     * OutputStream
     *
     * @return byte[]
     * @throws IOException
     */
    public static byte[] OutputStreamTOByte(OutputStream out)
            throws IOException {

        byte[] data = new byte[6 * 1024];
        out.write(data);
        return data;
    }

    /**
     * 将byte数组转换成InputStream
     *
     * @param in
     * @return
     * @throws Exception
     */
    public static InputStream byteTOInputStream(byte[] in) {
        return new ByteArrayInputStream(in);
    }

    /**
     * 将byte数组转换成OutputStream
     *
     * @param in
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static OutputStream byteTOOutputStream(byte[] in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(in);
        return out;
    }

    /**
     * 把输入流中的数据输入到Path里的文件里
     *
     * @param path
     * @param inputStream
     * @return
     */
    public File writeFromInputToSD(String path, InputStream inputStream) {
        File file = null;
        OutputStream output = null;
        try {
            file = new FileUtil().creatFileIfNotExist(path);
            output = new FileOutputStream(file);
            byte[] buffer = new byte[4 * 1024];
            int temp;
            while ((temp = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, temp);
            }
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 把数据输入到Path里的文件里
     *
     * @param path
     * @return
     */
    public File writeFromInputToSD(String path, byte[] b) {
        File file = null;
        OutputStream output = null;
        try {
            file = new FileUtil().creatFileIfNotExist(path);
            output = new FileOutputStream(file);
            output.write(b);
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 方法：把一段文本保存到给定的路径中.
     */
    public void saveTxtFile(String filePath, String text) {
        try {
            // 首先构建一个文件输出流,用于向文件中写入数据.
            new FileUtil().creatFileIfNotExist(filePath);
            String txt = readTextLine(filePath);
            text = text + txt;
            FileOutputStream out = new FileOutputStream(filePath);
            // 构建一个写入器,用于向流中写入字符数据
            OutputStreamWriter writer = new OutputStreamWriter(out, "gb2312");
            writer.write(text);
            // 关闭Writer,关闭输出流
            writer.close();
            out.close();
        } catch (Exception e) {
            String ext = e.getLocalizedMessage();
            // Toast.makeText(this, ext, Toast.LENGTH_LONG).show();
        }

    }

    public void clearTxtFile(String filePath) {
        try {
            // 首先构建一个文件输出流,用于向文件中写入数据.
            String text = "";
            FileOutputStream out = new FileOutputStream(filePath);
            // 构建一个写入器,用于向流中写入字符数据
            OutputStreamWriter writer = new OutputStreamWriter(out, "gb2312");
            writer.write(text);
            // 关闭Writer,关闭输出流
            writer.close();
            out.close();
        } catch (Exception e) {
            String ext = e.getLocalizedMessage();
            // Toast.makeText(this, ext, Toast.LENGTH_LONG).show();
        }
    }

    // 读取一个给定的文本文件内容,并把内容以一个字符串的形式返回
    public String readTextLine(String textFile) {
        try {
            // 首先构建一个文件输入流,该流用于从文本文件中读取数据
            FileInputStream input = new FileInputStream(textFile);
            // 为了能够从流中读取文本数据,我们首先要构建一个特定的Reader的实例,
            // 因为我们是从一个输入流中读取数据,所以这里适合使用InputStreamReader.
            InputStreamReader streamReader = new InputStreamReader(input,
                    "gb2312");
            // 为了能够实现一次读取一行文本的功能,我们使用了 LineNumberReader类,
            // 要构建LineNumberReader的实例,必须要传一个Reader实例做参数,
            // 我们传入前面已经构建好的Reder.
            LineNumberReader reader = new LineNumberReader(streamReader);
            // 字符串line用来保存每次读取到的一行文本.
            String line = null;
            // 这里我们使用一个StringBuilder来存储读取到的每一行文本,
            // 之所以不用String,是因为它每次修改都会产生一个新的实例,
            // 所以浪费空间,效率低.
            StringBuilder allLine = new StringBuilder();
            // 每次读取到一行,直到读取完成
            while ((line = reader.readLine()) != null) {
                allLine.append(line);
                // 这里每一行后面,加上一个换行符,LINUX中换行是”\n”,
                // windows中换行是”\r\n”.
                allLine.append("\n");
            }
            // 把Reader和Stream关闭
            streamReader.close();
            reader.close();
            input.close();
            // 把读取的字符串返回
            return allLine.toString();
        } catch (Exception e) {
            // Toast.makeText(this, e.getLocalizedMessage(),
            // Toast.LENGTH_LONG).show();
            return "";
        }
    }

    // 把字加长，使其可以滚动，在音乐界面
    public String dealString(String st, int size) {
        if (st.length() >= size)
            return "  " + st + "  ";
        else {
            int t = (size - st.length()) / 2;
            for (int i = 0; i < t; i++) {
                st = " " + st + "  ";
            }
            return st;
        }
    }

    public String getTimeByFormat(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.CHINESE);
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        return formatter.format(curDate);
    }

    public String getDateTimeBylong(long time_data, String dateformat_batt) {
        Date date = new Date(time_data);
        SimpleDateFormat format = new SimpleDateFormat(dateformat_batt, Locale.CHINESE);
        return format.format(date);
    }

    // 取前面的名字　"."
    public String getNameByFlag(String source, String flag) {
        // String[] source_spli = source.split(flag);
        String s = source.toLowerCase().replace(flag, "");
        return s.trim();
    }

    /**
     * 取Asset文件夹下文件
     *
     * @param paramContext
     * @param paramString
     * @return
     * @throws IOException
     */
    public static InputStream getAssetsInputStream(Context paramContext,
                                                   String paramString) throws IOException {
        return paramContext.getResources().getAssets().open(paramString);
    }

    //以省内存的方式读取图片
    public Bitmap getBitmap(InputStream is) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        opt.inSampleSize = 4;
        //获取资源图片
        //InputStream is = mContext.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    /**
     * List深拷贝
     *
     * @param src
     * @param <T>
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        @SuppressWarnings("unchecked")
        List<T> dest = (List<T>) in.readObject();
        return dest;
    }
}
