package per.wsj.commonlib.utils;

import android.content.Context;

import java.io.InputStream;


public class Font32 {
    private Context context;
    public Font32(Context context){
        this.context = context;
    }

    private final static int[] mask = {128, 64, 32, 16, 8, 4, 2, 1};
    private final static String ENCODE = "GB2312";
    private final static String ZK32 = "HZK32F";

    private boolean[][] arr;
    public boolean[][] drawString(String str){
        byte[] data = null;
        int[] code = null;
        int byteCount;//到点阵数据的第几个字节了
        int lCount;//控制列

        arr = new boolean[32][32]; // 插入的数组
        //g.setColor(color);
        for(int i = 0;i < str.length();i ++){
            if(str.charAt(i) < 0x80){//非中文
                //g.drawString(str.substring(i,i+1),x+(i<<4),y,0);
                continue;
            }
            code = getByteCode(str.substring(i,i+1));
            data = read(code[0],code[1]);
            byteCount = 0;
            for(int line = 0;line < 32;line ++){
                lCount = 0;
                for(int k = 0;k < 4;k ++){
                    for(int j = 0;j < 8;j ++){
                        // if((data[byteCount]&mask[j])==mask[j]){
                        if (((data[byteCount] >> (7 - j)) & 0x1) == 1) {
                            arr[line][lCount] = true;
                            System.out.print("@");
                        }else{
                            System.out.print(" ");
                            arr[line][lCount] = false;
                        }
                        lCount++;
                    }
                    byteCount ++;
                }
                System.out.println();
            }
        }
        return arr;
    }



    /**
     *读取文字信息
     *@param areaCode 区码
     *@param posCode 位码
     *@return 文字数据
     */
    protected byte[] read(int areaCode,int posCode){
        byte[] data = null;
        try{
            int area = areaCode-0xa0;//获得真实区码
            int pos  = posCode-0xa0;//获得真实位码

            //InputStream in = getClass().getResourceAsStream(ZK32);
            InputStream in = Util.getInstance().getAssetsInputStream(context, ZK32);
            long offset = 128*((area-1)*94+pos-1);
            in.skip(offset);
            data = new byte[128];
            in.read(data,0,128);
            in.close();
        }catch(Exception ex){
        }
        return data;
    }

    /**
     *获得文字的区位码
     *@param str
     *@return int[2]
     */
    protected int[] getByteCode(String str){
        int[] byteCode = new int[2];
        try{
            byte[] data = str.getBytes(ENCODE);
            byteCode[0] = data[0] < 0?256+data[0]:data[0];
            byteCode[1] = data[1] < 0?256+data[1]:data[1];
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return byteCode;
    }
}