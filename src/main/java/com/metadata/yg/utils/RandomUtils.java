package com.metadata.yg.utils;
import java.util.List;
import java.util.Random;

public class RandomUtils {
    //随机生成数字字符串
    public static String getRandomString0(int length){
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
//            int number=random.nextInt(3);
            long result=0;
            sb.append(String.valueOf(new Random().nextInt(10)));
        }
        return sb.toString();
    }

    //随机生成大写加数字字符串
    public static String getRandomString1(int length){
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(2);
            long result=0;
            switch(number){
                case 0:
//                    result=Math.round(Math.random()*25+97);
//                    sb.append(String.valueOf((char)result));
                    sb.append(String.valueOf(new Random().nextInt(10)));
                    break;
                case 1:
                    result=Math.round(Math.random()*25+65);
                    sb.append(String.valueOf((char)result));
                    break;
            }
        }
        return sb.toString();
    }

    //随机生成数字大写字母小写字母字符串
    public static String getRandomString2(int length){
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(3);
            long result=0;
            switch(number){
                case 0:
                    result=Math.round(Math.random()*25+65);
                    sb.append(String.valueOf((char)result));
                    break;
                case 1:
                    result=Math.round(Math.random()*25+97);
                    sb.append(String.valueOf((char)result));
                    break;
                case 2:
                    sb.append(String.valueOf(new Random().nextInt(10)));
                    break;
            }
        }
        return sb.toString();
    }

    public static Integer getRandomNum(int min,int max) {
        return new Random().nextInt(max-min) + min;
    }


    public static String RandomListItem(List<String> list) {
        Random random = new Random();
//        list = new ArrayList<String>();
//        int i = 0;
//        while (i < 3) {
//            list.add(i + "");
//            i++;
//        }
         return list.get(random.nextInt(list.size()));
        }

}
