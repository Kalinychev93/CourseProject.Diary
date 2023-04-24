package calendar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class ClassForTestDifferentThings {
    public static void main(String[] args) throws ParseException {
/*        LocalDateTime currentDate = LocalDateTime.now();
        System.out.println(currentDate);

        Scanner scanner = new Scanner(System.in);


        System.out.println("Enter your age");

        int age = scanner.nextInt();

        System.out.println("Enter your name");

        String name = scanner.next();

        if (age < 18) {
            System.out.println(name + " this page is adults only");
        } else {
            System.out.println("Welcome to the application " + name);
        }*/

        Date date = new Date();
        System.out.println(date);

        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.getTime());
        calendar.add(Calendar.APRIL, 1);
        System.out.println(calendar.getTime());

        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        System.out.println(dateFormat.format(calendar.getTime()));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        System.out.println(simpleDateFormat.format(calendar.getTime()));

//        09/03/1993
        Date newDate = simpleDateFormat.parse("1993/03/09");
        System.out.println(newDate);
    }
}
