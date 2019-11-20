package com.pf.play.common.utils;

import java.util.Random;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/12 21:42
 * @Version 1.0
 */
public class RandomUtil {
    public static String   getRandom(int   count){

        StringBuffer  random  =  new StringBuffer() ;
        Random r = new Random();
        for(int i=0 ; i<count ;  i++) {
            int ran1 = r.nextInt(10);
            random.append(ran1);
        }
        return random.toString();
    }

    public static void main(String  [] args){
//        getRandom(6);
        for(int i=0;i<100;i++){
            System.out.println(getRandom(6));
        }

    }
}
