package com.JiuLing.BasicAlgorithm;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Description 基础算法-扫描线 BV1Po4y1Z7sm
 * Create by hfli11 on 2023/1/10 14:57
 */
public class A_ScanningLine {

    public static void main(String[] args) {
        countOfAirplanesDriver();
    }

// * * * * * * * * * * 第一题，LintCode第391题 https://www.lintcode.com/problem/391/ * * * * * * * * * *
    public static class Interval {
       int start, end;
       Interval(int start, int end) {
           this.start = start;
           this.end = end;
       }
   }

   public static int countOfAirplanes(List<Interval> airplanes){
        // sweep line 扫描线法

       //为airplanes里每个Interval的start和end都保存一个权值
       List<Point> list = new ArrayList<>(airplanes.size() * 2);
       for (Interval i : airplanes) {
           list.add(new Point(i.start, 1));
           list.add(new Point(i.end, 0));
       }

       //根据时间排序。如果时间(p.x)相同，则根据题干，降落优先级大于起飞，排在前面(p1.y - p2.y)
       Collections.sort(list, (Point p1, Point p2) ->{
           if(p1.x == p2.x) return p1.y - p2.y;
           return p1.x - p2.x;
       });

       //进行扫描(list已经排好序，直接按顺序循环,并维持一个最大值ans即可,cnt为当前天空的飞机数量)
       int cnt = 0;
       int ans = 0;
       for (Point p : list) {
           if(p.y == 1) cnt++;
           else cnt--;
           ans = Math.max(ans, cnt);
       }
       return ans;
   }

   public static void countOfAirplanesDriver(){
        List<Interval> airplanes = new ArrayList<>();
        airplanes.add(new Interval(1, 10));
        airplanes.add(new Interval(2, 3));
        airplanes.add(new Interval(5, 8));
        airplanes.add(new Interval(4, 7));
        airplanes.add(new Interval(3, 8));

       System.out.println(countOfAirplanes(airplanes));
   }
// * * * * * * * * * * 第一题，LintCode第391题 https://www.lintcode.com/problem/391/ Over * * * * * * * * * *

}
