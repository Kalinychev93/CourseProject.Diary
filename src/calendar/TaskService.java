package calendar;

import calendar.exceptions.WrongInputException;
import calendar.repeatabilityOfTask.*;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TaskService {

    private static final Map<Integer, Repeatable> actualTask = new HashMap<>();
    private static final Map<Integer, Repeatable> archivedTask = new HashMap<>();

    public static void addTask(Scanner scanner) {
        try {
            scanner.nextLine();
            System.out.println("Введите название задачи:");
            String title = CheckValues.CheckString(scanner.nextLine());
            System.out.println("Введите описание задачи:");
            String description = CheckValues.CheckString(scanner.nextLine());
            System.out.println("Выберите тип задачи: 0 - рабочая, 1 - личная");
            TaskType taskType = TaskType.values()[scanner.nextInt()];
            System.out.println("Введите повторяемость задачи: " +
                    "0 - однократная, " +
                    "1 - ежедневная, " +
                    "2 - еженедельная, " +
                    "3 - ежемесячная, " +
                    "4 - ежегодная");
            int occurrence = scanner.nextInt();
            System.out.println("Введите дату dd.MM.yyyy HH:mm");
            scanner.nextLine();
            createEvent(scanner, title, description, taskType, occurrence);
            System.out.println("Для выхода нажмите Enter\n");
            scanner.nextLine();
        } catch (WrongInputException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createEvent(Scanner scanner, String title, String description, TaskType taskType, int occurrence) {
        try {
            LocalDateTime eventDate = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
            Repeatable task = null;
            try {
                task = createTask(occurrence, title, description, taskType, eventDate);
                System.out.println("Создана задача: " + task);
            } catch (WrongInputException e) {
                System.out.println(e.getMessage());
            }
        } catch (DateTimeParseException e) {
            System.out.println("Проверьте формат даты и времени dd.MM.yyyy HH:mm и попробуйте еще раз");
            createEvent(scanner, title, description, taskType, occurrence);
        }
    }

    private static Repeatable createTask(int occurence,
                                         String title,
                                         String description,
                                         TaskType taskType,
                                         LocalDateTime localDateTime) throws WrongInputException {
        switch (occurence) {
            case 0:
                OneTimeTask oneTimeTask = new OneTimeTask(title, description, taskType, localDateTime);
                actualTask.put(OneTimeTask.getId(), oneTimeTask);
//                return oneTimeTask;
            case 1:
                DailyTask dailyTask = new DailyTask(title, description, taskType, localDateTime);
                actualTask.put(DailyTask.getId(), dailyTask);
                return dailyTask;
            case 2:
                WeeklyTask weeklyTask = new WeeklyTask(title, description, taskType, localDateTime);
                actualTask.put(WeeklyTask.getId(), weeklyTask);
                return weeklyTask;
            case 3:
                MonthlyTask monthlyTask = new MonthlyTask(title, description, taskType, localDateTime);
                actualTask.put(MonthlyTask.getId(), monthlyTask);
                return monthlyTask;
            case 4:
                YearlyTask yearlyTask = new YearlyTask(title, description, taskType, localDateTime);
                actualTask.put(YearlyTask.getId(), yearlyTask);
                return yearlyTask;
            }
            return null;
        }
    }
}