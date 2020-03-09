package com.feuoy.tally.util;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.regex.Pattern;


public class Test {

    public static void main(String[] args) {


//        LinkedList<Integer> list1 = new LinkedList<Integer>();
//        list1.add(1);
//        list1.add(2);
//        list1.add(3);
//
//        LinkedList<Integer> list2 = new LinkedList<Integer>();
//        list2.add(1);
//        list2.add(2);
//        list2.add(3);
//        list1.add(4);
//
//
//
//
//        LinkedList<Integer> result1 = new LinkedList<Integer>();
//        result1.clear();
//
//
//        result1.addAll(list1);
//        result1.retainAll(list2);
//        System.out.println("交集:" + result1);
//
//
//
//
//        LinkedList<Integer> temp1 = new LinkedList<Integer>(); //临时集合存放交集
//        temp1.addAll(result1);
//        result1.clear();
//        result1.addAll(list1);
//        result1.addAll(list2);
//        result1.removeAll(temp1);  //并集减去交集
//        System.out.println("差集：" + result1);
//
//
//
//
//        result1.clear();
//        result1.addAll(list1);
//        result1.removeAll(list2);
//        result1.addAll(list2);
//        System.out.println("并集:" + result1);


        // 有记录的日期链表
        LinkedList<String> dates = new LinkedList<>();
        dates.add("1");
        dates.add("2");
//        dates.add("3");
        dates.add("4");


        LinkedList<String> ifNewDates = new LinkedList<>();
        ifNewDates.add("1");
        ifNewDates.add("2");
        ifNewDates.add("3");
        ifNewDates.add("4");

        System.out.println(dates.get(0));
        System.out.println(dates.get(1));
        System.out.println(dates.get(2));


        String newDate = "";

        int newIndex = -1;


        if (ifNewDates.size() > dates.size()){



            for (int i = 0 ; i < dates.size() ; i++ ) {
                if (!ifNewDates.get(i).equals(dates.get(i))){
                    newDate = ifNewDates.get(i);
                    newIndex =i ;
                }
            }

            if (newDate.equals("")){
                newDate = ifNewDates.get(ifNewDates.size() - 1);
            }


            dates.add(newIndex, newDate);



        }


        System.out.println(newDate);
        System.out.println(dates.toString());
        System.out.println(dates.get(0));
        System.out.println(dates.get(1));
        System.out.println(dates.get(2));
        System.out.println(dates.get(3));
//        System.out.println(dates.get(4));



        String pattern = "^[0-9]{4}-[0-9]{2}$";
        boolean isMatch = Pattern.matches(pattern, "2019-02");

        System.out.println(isMatch);
    }





//2019-12-10 14:40:41.730 9031-9031/com.feuoy.tally D/getItem---position---: 6
//2019-12-10 14:40:41.734 9031-9031/com.feuoy.tally D/getItem---indexOf---: 0 2019-12-01
//2019-12-10 14:40:41.734 9031-9031/com.feuoy.tally D/getItem---position---: 6
//2019-12-10 14:40:41.734 9031-9031/com.feuoy.tally D/getItem---indexOf---: 1 2019-12-10
//2019-12-10 14:40:41.734 9031-9031/com.feuoy.tally D/getItem---position---: 6
//2019-12-10 14:40:41.734 9031-9031/com.feuoy.tally D/getItem---indexOf---: 2 2019-12-12
//2019-12-10 14:40:41.734 9031-9031/com.feuoy.tally D/getItem---position---: 6
//2019-12-10 14:40:41.734 9031-9031/com.feuoy.tally D/getItem---indexOf---: 3 2019-12-13
//2019-12-10 14:40:41.734 9031-9031/com.feuoy.tally D/getItem---position---: 6
//2019-12-10 14:40:41.734 9031-9031/com.feuoy.tally D/getItem---indexOf---: 4 2019-12-14
//2019-12-10 14:40:41.734 9031-9031/com.feuoy.tally D/getItem---position---: 6
//2019-12-10 14:40:41.734 9031-9031/com.feuoy.tally D/getItem---indexOf---: 5 2019-12-14
//2019-12-10 14:40:41.734 9031-9031/com.feuoy.tally D/getItem---position---: 6
//2019-12-10 14:40:41.734 9031-9031/com.feuoy.tally D/getItem---indexOf---: 6 2019-12-25
//2019-12-10 14:40:41.734 9031-9031/com.feuoy.tally D/getItem---indexOf---: ------------------
//2019-12-10 14:41:14.800 9148-9148/com.feuoy.tally D/getItem---position---: 6
//2019-12-10 14:41:14.800 9148-9148/com.feuoy.tally D/getItem---indexOf---: 0 2019-11-30
//2019-12-10 14:41:14.800 9148-9148/com.feuoy.tally D/getItem---position---: 6
//2019-12-10 14:41:14.800 9148-9148/com.feuoy.tally D/getItem---indexOf---: 1 2019-12-01
//2019-12-10 14:41:14.800 9148-9148/com.feuoy.tally D/getItem---position---: 6
//2019-12-10 14:41:14.800 9148-9148/com.feuoy.tally D/getItem---indexOf---: 2 2019-12-10
//2019-12-10 14:41:14.801 9148-9148/com.feuoy.tally D/getItem---position---: 6
//2019-12-10 14:41:14.801 9148-9148/com.feuoy.tally D/getItem---indexOf---: 3 2019-12-12
//2019-12-10 14:41:14.801 9148-9148/com.feuoy.tally D/getItem---position---: 6
//2019-12-10 14:41:14.801 9148-9148/com.feuoy.tally D/getItem---indexOf---: 4 2019-12-13
//2019-12-10 14:41:14.802 9148-9148/com.feuoy.tally D/getItem---position---: 6
//2019-12-10 14:41:14.803 9148-9148/com.feuoy.tally D/getItem---indexOf---: 5 2019-12-14
//2019-12-10 14:41:14.803 9148-9148/com.feuoy.tally D/getItem---position---: 6
//2019-12-10 14:41:14.803 9148-9148/com.feuoy.tally D/getItem---indexOf---: 6 2019-12-25
//2019-12-10 14:41:14.803 9148-9148/com.feuoy.tally D/getItem---indexOf---: ------------------







////////////////////////////////////////////////////////////
    // 有9条记录，原来的第 n - 1 条，重复成第 n 条， 原来的第 n 条 成为第 n + 1条
////////////////////////////////////////////////////////////

//2019-12-10 14:49:30.077 9431-9431/com.feuoy.tally D/getItem---indexOf---: ------------------
//2019-12-10 14:49:30.810 9431-9431/com.feuoy.tally D/getItem---position---: 7
//2019-12-10 14:49:30.810 9431-9431/com.feuoy.tally D/getItem---indexOf---: 0 2019-11-01
//2019-12-10 14:49:30.810 9431-9431/com.feuoy.tally D/getItem---position---: 7
//2019-12-10 14:49:30.810 9431-9431/com.feuoy.tally D/getItem---indexOf---: 1 2019-11-30
//2019-12-10 14:49:30.810 9431-9431/com.feuoy.tally D/getItem---position---: 7
//2019-12-10 14:49:30.811 9431-9431/com.feuoy.tally D/getItem---indexOf---: 2 2019-12-01
//2019-12-10 14:49:30.811 9431-9431/com.feuoy.tally D/getItem---position---: 7
//2019-12-10 14:49:30.811 9431-9431/com.feuoy.tally D/getItem---indexOf---: 3 2019-12-10
//2019-12-10 14:49:30.811 9431-9431/com.feuoy.tally D/getItem---position---: 7
//2019-12-10 14:49:30.811 9431-9431/com.feuoy.tally D/getItem---indexOf---: 4 2019-12-12
//2019-12-10 14:49:30.811 9431-9431/com.feuoy.tally D/getItem---position---: 7
//2019-12-10 14:49:30.811 9431-9431/com.feuoy.tally D/getItem---indexOf---: 5 2019-12-13
//2019-12-10 14:49:30.811 9431-9431/com.feuoy.tally D/getItem---position---: 7
//2019-12-10 14:49:30.811 9431-9431/com.feuoy.tally D/getItem---indexOf---: 6 2019-12-14
//2019-12-10 14:49:30.811 9431-9431/com.feuoy.tally D/getItem---position---: 7
//2019-12-10 14:49:30.811 9431-9431/com.feuoy.tally D/getItem---indexOf---: 7 2019-12-14
//2019-12-10 14:49:30.811 9431-9431/com.feuoy.tally D/getItem---position---: 7
//2019-12-10 14:49:30.811 9431-9431/com.feuoy.tally D/getItem---indexOf---: 8 2019-12-25
//2019-12-10 14:49:30.811 9431-9431/com.feuoy.tally D/getItem---indexOf---: ------------------
//2019-12-10 14:49:32.059 9431-9431/com.feuoy.tally D/getItem---position---: 8
//2019-12-10 14:49:32.060 9431-9431/com.feuoy.tally D/getItem---indexOf---: 0 2019-11-01
//2019-12-10 14:49:32.060 9431-9431/com.feuoy.tally D/getItem---position---: 8
//2019-12-10 14:49:32.060 9431-9431/com.feuoy.tally D/getItem---indexOf---: 1 2019-11-30
//2019-12-10 14:49:32.060 9431-9431/com.feuoy.tally D/getItem---position---: 8
//2019-12-10 14:49:32.061 9431-9431/com.feuoy.tally D/getItem---indexOf---: 2 2019-12-01
//2019-12-10 14:49:32.061 9431-9431/com.feuoy.tally D/getItem---position---: 8
//2019-12-10 14:49:32.061 9431-9431/com.feuoy.tally D/getItem---indexOf---: 3 2019-12-10
//2019-12-10 14:49:32.061 9431-9431/com.feuoy.tally D/getItem---position---: 8
//2019-12-10 14:49:32.061 9431-9431/com.feuoy.tally D/getItem---indexOf---: 4 2019-12-12
//2019-12-10 14:49:32.061 9431-9431/com.feuoy.tally D/getItem---position---: 8
//2019-12-10 14:49:32.061 9431-9431/com.feuoy.tally D/getItem---indexOf---: 5 2019-12-13
//2019-12-10 14:49:32.061 9431-9431/com.feuoy.tally D/getItem---position---: 8
//2019-12-10 14:49:32.062 9431-9431/com.feuoy.tally D/getItem---indexOf---: 6 2019-12-14
//2019-12-10 14:49:32.062 9431-9431/com.feuoy.tally D/getItem---position---: 8
//2019-12-10 14:49:32.062 9431-9431/com.feuoy.tally D/getItem---indexOf---: 7 2019-12-14
//2019-12-10 14:49:32.062 9431-9431/com.feuoy.tally D/getItem---position---: 8
//2019-12-10 14:49:32.062 9431-9431/com.feuoy.tally D/getItem---indexOf---: 8 2019-12-25
//2019-12-10 14:49:32.062 9431-9431/com.feuoy.tally D/getItem---indexOf---: ------------------
}
