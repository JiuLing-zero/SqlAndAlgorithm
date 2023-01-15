package com.JiuLing.BasicAlgorithm;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Description 基础算法-扫描线 BV1Po4y1Z7sm
 * Create by hfli11 on 2023/1/10 14:57
 */
public class A_ScanningLine {

    public static void main(String[] args) {
//        countOfAirplanes_Driver();
//        canAttendMeetings_Driver();
//        minMeetingRooms_Driver();
//        mergeIntervals_Driver();
//        insertInterval_Driver();
//        removeInterval_Driver();
//        eraseOverlapIntervals_Driver();
//        removeCoveredIntervals_Driver();
//        SummaryRanges_Driver();
//        minAvailableDuration_Driver();
        intervalIntersection_Driver();
    }

// * * * * * * * * * * 第一题，countOfAirplanes * * * * * * * * * *
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
       list.sort((Point p1, Point p2) -> {
           if (p1.x == p2.x) return p1.y - p2.y;
           return p1.x - p2.x;
       });
//       Collections.sort(list, new Comparator<Point>() {
//           @Override
//           public int compare(Point p1, Point p2) {
//               if(p1.x == p2.x) return p1.y - p2.y;
//               return p1.x - p2.x;
//           }
//       });

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
// * * * * * * * * * * 第一题，countOfAirplanes [Over] * * * * * * * * * *
// * * * * * * * * * * 第二题，Meeting Rooms * * * * * * * * * *
    public static boolean canAttendMeetings(Interval[] intervals){
        Arrays.sort(intervals, (a, b) -> a.start - b.start);
        for (int i = 0; i < intervals.length - 1; i++)
            if(intervals[i].end >intervals[i+1].start) return false;
        return true;
    }
// * * * * * * * * * * 第二题，Meeting Rooms [Over] * * * * * * * * * *
// * * * * * * * * * * 第三题，Meeting Rooms Ⅱ * * * * * * * * * *
    public static int minMeetingRooms(int[][] intervals){
        List<int[]> list = new ArrayList<>();
        for (int[] interval : intervals) {
            list.add(new int[]{interval[0], 1});
            list.add(new int[]{interval[1], -1});
        }
        list.sort((a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
        //count记录每前进一个记录点时当前会议室数量，res记录count数量最大时的值
        int res = 0, count = 0;
        for (int[] point : list) {
            count += point[1];
            res = Math.max(res, count);
        }
        return res;
    }

    //PQ解法 time O(nlogn) space O(n)
    public static int minMeetingRoomsPQ(int[][] intervals){
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        //创建一个有初始比较器的PriorityQueue优先队列
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        if(intervals.length != 0) heap.offer(intervals[0]);
        else return 0;
        for (int i = 1; i < intervals.length; i++) {
            //每次cur获取到的heap的会议，其结束时间在heap队列里都是最小的(因为比较器)
            int[] cur = heap.poll();
            assert cur != null;
            //如果cur的结束时间小，更新cur的结束时间(即cur与新会议不冲突)；否则heap增加一个会议室
            if (cur[1] <= intervals[i][0]) cur[1] = intervals[i][1];
            else heap.offer(intervals[i]);
            heap.offer(cur);
        }
        return heap.size();
    }

    //第三种解法，跟解法一类似，分别维护了一个会议房间数和一个已结束的会议数
    public static int minMeetingRooms3(int[][] intervals){
        int[] starts  = new int[intervals.length], ends = new int[intervals.length];
        for (int i = 0; i < intervals.length; i++) {
            starts[i] = intervals[i][0];
            ends[i] = intervals[i][1];
        }
        Arrays.sort(starts);
        Arrays.sort(ends);
        //endRoom即表示已结束的会议数，也当做ends数组下标
        int room = 0, endRoom = 0;
        //遍历starts数组，每次拿开始时间和ends[endRoom]相比，如果小那么ends[endRoom]保持不变
        for (int i = 0; i < starts.length; i++) {
            if(starts[i] < ends[endRoom]) room++;
            else endRoom++;
        }
        return room;
    }
// * * * * * * * * * * 第三题，Meeting Rooms Ⅱ [Over] * * * * * * * * * *
// * * * * * * * * * * 第四题，Merge Intervals * * * * * * * * * *
    public static int[][] mergeIntervals(int[][] intervals){
        List<int[]> res = new ArrayList<>();
        if(intervals == null || intervals.length == 0) return new int[0][];
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        int[] cur = intervals[0];
        //cur已确保开始时间最早，如果next的结束时间比cur早，那么cur可以更新到结果里，同时cur赋值为next
        for (int[] next : intervals) {
            if(cur[1] >= next[0]) cur[1] = Math.max(cur[1], next[1]);
            else {
                res.add(cur);
                cur = next;
            }
        }
        res.add(cur);
        return res.toArray(new int[0][]);
    }
// * * * * * * * * * * 第四题，Merge Intervals [Over] * * * * * * * * * *
// * * * * * * * * * * 第五题，Insert Interval * * * * * * * * * *
    public static int[][] insertInterval(int[][] intervals, int[] newInterval){
        List<int[]> res = new ArrayList<>();
        for (int[] cur : intervals) {
            if (newInterval == null || cur[1] < newInterval[0]) res.add(cur);//newInterval未到插入点
            else if (cur[0] > newInterval[1]) {//newInterval与cur不重叠，加入res
                res.addAll(Arrays.asList(newInterval, cur));
//                res.addAll(List.of(newInterval, cur));//java9特性
                newInterval = null;
            } else {//有overlap，进行合并，继续与后续cur进行判断
                newInterval[0] = Math.min(newInterval[0], cur[0]);
                newInterval[1] = Math.max(newInterval[1], cur[1]);
            }
        }
        if(newInterval != null) res.add(newInterval);
        return res.toArray(new int[0][]);
    }
// * * * * * * * * * * 第五题，Insert Interval [Over] * * * * * * * * * *
// * * * * * * * * * * 第六题，Remove Interval * * * * * * * * * *
    public static List<List<Integer>> removeInterval(int[][] intervals, int[] toBeRemoved){
        List<List<Integer>> res = new ArrayList<>();
        for (int[] i : intervals) {
            if(i[1] <= toBeRemoved[0] || i[0] >= toBeRemoved[1]) res.add(Arrays.asList(i[0],i[1]));//没有overlap
            else {// i[1] > toBeRemoved[0] && i[0] < toBeRemoved[1]
                if(i[0] < toBeRemoved[0]) res.add(Arrays.asList(i[0], toBeRemoved[0]));//left end extra, remain
                if(i[1] > toBeRemoved[1]) res.add(Arrays.asList(toBeRemoved[1], i[1]));//right end extra, remain
            }
        }
        return res;
    }
// * * * * * * * * * * 第六题，Remove Interval [Over] * * * * * * * * * *
// * * * * * * * * * * 第七题，Non-overlapping Intervals * * * * * * * * * *
    public static int eraseOverlapIntervals(int[][] intervals){
        if(intervals.length == 0) return 0;
        //按照结束时间排序
        Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
        int count = 0;
        int end = intervals[0][1];
        //如果冲突总是删除end靠后的，给下一个比较的interval留下更多的空间。(即md文件的解题思路里有overlap就删除current)
        for (int i = 1; i < intervals.length; i++) {
            int[] cur = intervals[i];
            if(end <= cur[0]) end = cur[1];
            else count++;
        }
        return count;
    }
// * * * * * * * * * * 第七题，Non-overlapping Intervals [Over] * * * * * * * * * *
// * * * * * * * * * * 第八题，Remove Covered Intervals * * * * * * * * * *
    public static int removeCoveredIntervals(int[][] intervals){
        //start相同就按end逆序，start不同就正序排
        Arrays.sort(intervals, (a, b) -> a[0] == b[0] ? b[1]-a[1] : a[0] - b[0]);
        int count = 0, curEnd = 0;
        for (int[] cur : intervals) {
            //满足if时没有被覆盖，剩余区间数+1，更新curEnd。不满足if时有覆盖，删除覆盖区间[cur]并用之前的curEnd继续判断
            if(curEnd < cur[1]){
                curEnd = cur[1];
                count++;
            }
        }
        return count;
    }
// * * * * * * * * * * 第八题，Remove Covered Intervals [Over] * * * * * * * * * *
// * * * * * * * * * * 第九题，Data Stream as Disjoint Intervals * * * * * * * * * *
    static class SummaryRanges {
        static TreeSet<int[]> set = new TreeSet<>((a, b) -> a[0] == b[0] ? a[1]-b[1] : a[0]-b[0]);

        public static void addNum(int val){
            int[] interval = new int[]{val, val};
            if(set.contains(interval)) return;
            int[] low = set.lower(interval);//这里因为interval是(val, val)，low[0]一定小于val
            int[] high = set.higher(interval);//这里的high可能是(val, val+)的形式，所以需要if判断
            if(high != null && high[0] == val) return;
            if(low != null && low[1]+1 == val && high != null && val+1 == high[0]){//两端都可合并
                low[1] = high[1];
                set.remove(high);
            }
            else if(low != null && low[1]+1 >= val) low[1] = Math.max(low[1], val);//可以和左侧的low合并
            else if(high != null && val+1 == high[0]) high[0] = val;//可以和右侧的high合并
            else set.add(interval);
        }
        public static int[][] getIntervals(){
            List<int[]> res = new ArrayList<>();
            for (int[] interval : set) res.add(interval);
            return res.toArray(new int[0][]);
        }
    }
// * * * * * * * * * * 第九题，Data Stream as Disjoint Intervals [Over] * * * * * * * * * *
// * * * * * * * * * * 第十题，Meeting Scheduler * * * * * * * * * *
    public static List<Integer> minAvailableDuration(int[][] slots1, int[][] slots2, int duration){
        Arrays.sort(slots1, (a, b) -> a[0] - b[0]);
        Arrays.sort(slots2, (a, b) -> a[0] - b[0]);
        int i = 0;
        int j = 0;
        int n1 = slots1.length;
        int n2 = slots2.length;
        while (i < n1 && j < n2){
            int intersectStart = Math.max(slots1[i][0], slots2[j][0]);//公共起始时间，较晚的起始
            int intersectEnd = Math.min(slots1[i][1], slots2[j][1]);//公共结束时间，较早的结束
            if(intersectEnd - intersectStart >= duration)
//                return List.of(intersectStart, intersectStart+duration);//JAVA9特性
                return Arrays.asList(intersectStart, intersectStart+duration);
                //结束时间早的换下一个time slot
            else if(slots1[i][1] < slots2[j][1]) i++;
            else j++;
        }
        return new ArrayList<>();
    }
// * * * * * * * * * * 第十题，Meeting Scheduler [Over] * * * * * * * * * *
// * * * * * * * * * * 第十一题，Interval List Intersections * * * * * * * * * *
    public static int[][] intervalIntersection(int[][] A, int[][] B){
        //题干已表名进行过排序，这里不再sort
        List<int[]> res = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (i < A.length && j < B.length){
            int low = Math.max(A[i][0], B[j][0]);
            int high = Math.min(A[i][1], B[j][1]);
            if(low <= high) res.add(new int[]{low, high});
            if(A[i][1] < B[j][1]) i++;
            else j++;
        }
        return res.toArray(new int[0][]);
    }
// * * * * * * * * * * 第十一题，Interval List Intersections [Over] * * * * * * * * * *
// * * * * * * * * * * 第十二题，Employee Free Time * * * * * * * * * *
    public static List<Interval> employeeFreeTime(List<List<Interval>> schedule){
        List<Interval> res = new ArrayList<>();
        PriorityQueue<Interval> pq = new PriorityQueue<>((a, b) -> (a.start - b.start));
        for (List<Interval> list : schedule) {
            for (Interval interval : list) {
                pq.add(interval);
            }
        }
        Interval cur = pq.poll();
        while (!pq.isEmpty()){
            if(cur.end >= pq.peek().start){
                cur.end = Math.max(cur.end, pq.poll().end);
            }else {
                res.add(new Interval(cur.end, pq.peek().start));
                cur = pq.poll();
            }
        }
        return res;
    }
    // BV1Po4y1Z7sm 46:43
// * * * * * * * * * * 第十二题，Employee Free Time [Over] * * * * * * * * * *







    //1
    public static void countOfAirplanes_Driver(){
        List<Interval> airplanes = new ArrayList<>();
        airplanes.add(new Interval(1, 10));
        airplanes.add(new Interval(2, 3));
        airplanes.add(new Interval(5, 8));
        airplanes.add(new Interval(4, 7));
        airplanes.add(new Interval(3, 8));

        System.out.println("天上飞机最多时有:"+countOfAirplanes(airplanes));
    }
    //2
    public static void canAttendMeetings_Driver(){
        Interval[] intervals = new Interval[]{new Interval(0, 30), new Interval(5, 10), new Interval(15, 20)};
        System.out.println("是否可以参加所有会议:"+canAttendMeetings(intervals));
    }
    //3
    public static void minMeetingRooms_Driver(){
        int[][] intervals = new int[][]{{1, 10}, {2, 7}, {3, 19}, {8, 12}, {10, 20}, {11, 30}};
        System.out.println("所需最少的会议室数量:"+minMeetingRooms(intervals));
        System.out.println("所需最少的会议室数量:"+minMeetingRooms3(intervals));
        System.out.println("所需最少的会议室数量:"+minMeetingRoomsPQ(intervals));//该方法很大概率改变intervals的数据，放在最后跑
    }
    //4
    public static void mergeIntervals_Driver(){
        int[][] intervals = new int[][]{{1, 10}, {2, 19}, {22, 30}};
        System.out.println(Arrays.deepToString(mergeIntervals(intervals)));
    }
    //5
    public static void insertInterval_Driver(){
        int[][] intervals = new int[][]{{1, 3}, {6, 19}, {22, 30}};
        System.out.println(Arrays.deepToString(insertInterval(intervals, new int[]{2 ,9})));
    }
    //6
    public static void removeInterval_Driver(){
        int[][] intervals = new int[][]{{1, 3}, {6, 19}, {22, 30}};
        System.out.println(removeInterval(intervals, new int[]{2 ,9}));
    }
    //7
    public static void eraseOverlapIntervals_Driver(){
        int[][] intervals1 = new int[][]{{1, 2}, {2, 3}, {3, 4}, {1, 3}};//1
        int[][] intervals2 = new int[][]{{1, 2}, {1, 2}, {1, 2}};//2
        System.out.println("删除的最小区间数:"+eraseOverlapIntervals(intervals2));
    }
    //8
    public static void removeCoveredIntervals_Driver(){
        int[][] intervals = new int[][]{{2, 3}, {1, 19}, {22, 30}};
        System.out.println("剩余的区间数:"+removeCoveredIntervals(intervals));
    }
    //9
    public static void SummaryRanges_Driver(){
        SummaryRanges.addNum(1);System.out.println(Arrays.deepToString(SummaryRanges.getIntervals()));
        SummaryRanges.addNum(3);System.out.println(Arrays.deepToString(SummaryRanges.getIntervals()));
        SummaryRanges.addNum(7);System.out.println(Arrays.deepToString(SummaryRanges.getIntervals()));
        SummaryRanges.addNum(2);System.out.println(Arrays.deepToString(SummaryRanges.getIntervals()));
        SummaryRanges.addNum(6);System.out.println(Arrays.deepToString(SummaryRanges.getIntervals()));
    }
    public static void minAvailableDuration_Driver(){
        int[][] slots1 = new int[][]{{10, 50}, {60, 120}, {140, 210}};
        int[][] slots2 = new int[][]{{0, 15}, {60, 70}};
        int duration = 8;
        System.out.println(minAvailableDuration(slots1, slots2, duration));
    }
    public static void intervalIntersection_Driver(){
        int[][] A = new int[][]{{0, 2}, {5, 10}, {13, 23}, {24, 25}};
        int[][] B = new int[][]{{1, 5}, {8, 12}, {15, 24}, {25, 26}};
        System.out.println(Arrays.deepToString(intervalIntersection(A, B)));
    }





}
