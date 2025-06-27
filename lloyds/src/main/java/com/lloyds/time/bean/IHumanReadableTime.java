package com.lloyds.time.bean;

public interface IHumanReadableTime {

    String clock[] = {"Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
            "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen", "Twenty",
            "Twenty one", "Twenty two", "Twenty three", "Twenty four", "Twenty five", "Twenty six", "Twenty seven",
            "Twenty eight", "Twenty nine" };

    String getTime();

    String getTime(int hour, int time);

}

