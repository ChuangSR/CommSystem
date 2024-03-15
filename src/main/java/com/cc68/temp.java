package com.cc68;/*
根据输入的图形名称，输出对应图形的边数
*/
import java.util.Scanner;
class homework16{
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        System.out.println("请输入图形名称:");
        String shape = s.nextLine().toLowerCase();
        int edge = 0;

        switch(shape){
            case "三角形":
                edge = 3;
                break;
            case "四边形":
                edge = 4;
                break;
            case "五边形":
                edge = 5;
                break;
            default:
                System.out.println("对不起，暂不支持该图形。");
                return;
        }
        System.out.println("该图形有" + edge + "条边");
    }
}