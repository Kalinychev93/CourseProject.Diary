package calendar;

import calendar.exceptions.TaskNotFoundException;
import calendar.exceptions.WrongInputException;
import calendar.repeatabilityOfTask.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

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
                System.out.println("Создана задача: " + title);
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
                actualTask.put(oneTimeTask.getId(), oneTimeTask);
                break;
            case 1:
                DailyTask dailyTask = new DailyTask(title, description, taskType, localDateTime);
                actualTask.put(dailyTask.getId(), dailyTask);
                break;
            case 2:
                WeeklyTask weeklyTask = new WeeklyTask(title, description, taskType, localDateTime);
                actualTask.put(weeklyTask.getId(), weeklyTask);
                break;
            case 3:
                MonthlyTask monthlyTask = new MonthlyTask(title, description, taskType, localDateTime);
                actualTask.put(monthlyTask.getId(), monthlyTask);
                break;
            case 4:
                YearlyTask yearlyTask = new YearlyTask(title, description, taskType, localDateTime);
                actualTask.put(yearlyTask.getId(), yearlyTask);
                break;
            }
            return null;
        }

    public static void editTask(Scanner scanner) {
        try {
            System.out.println("Редактирование задачи: введите id Задачи");
            printActualTask();
            int id = scanner.nextInt();
            if (!actualTask.containsKey(id)) {
                throw new TaskNotFoundException("Задача не найдена");
            }
            System.out.println("Редактировать задачу: 0 - название, 1 - описание");
            int menuCase = scanner.nextInt();
            switch (menuCase) {
                case 0:
                    scanner.nextLine();
                    System.out.println("Введите название задачи:");
                    String title = scanner.nextLine();
                    Task taskTitle = (Task) actualTask.get(id);
                    taskTitle.setTitle(title);
                    break;
                case 1:
                    scanner.nextLine();
                    System.out.println("Введите описание задачи:");
                    String description = scanner.nextLine();
                    Task taskDescription = (Task) actualTask.get(id);
                    taskDescription.setTitle(description);
                    break;
            }
        } catch (TaskNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void deleteTask(Scanner scanner) {
        System.out.println("Актуальные задачи:");
        printActualTask();
        try {
            System.out.println("Для удаления введите id задачи");
            int id = scanner.nextInt();
            if (actualTask.containsKey(id)) {
                Repeatable removedTask = actualTask.get(id);
                removedTask.setArchived(true);
                archivedTask.put(id, removedTask);
                System.out.println("Задача " + id + " была удалена");
            } else {
                throw new TaskNotFoundException("Задача не была найдена");
            }
        } catch (TaskNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void getTasksByDay(Scanner scanner) {
        System.out.println("Введите дату в формате dd.MM.yyyy");
        try {
            String date = scanner.next();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate requestedDate = LocalDate.parse(date, dateFormatter);
            List<Repeatable> foundEvents = findTasksByDay(requestedDate);
            System.out.println("События на " + requestedDate + " :");
            for (Repeatable task : foundEvents) {
                System.out.println(task);
            }
        } catch (DateTimeParseException e) {
            System.err.println("Проверьте формат даты dd.MM.yyyy и попробуйте еще раз");
        }
        scanner.nextLine();
        System.out.println("Для выхода нажмите Enter\n");
    }

    private static List<Repeatable> findTasksByDay(LocalDate date) {
        List<Repeatable> tasks = new ArrayList<>();
        for (Repeatable task : actualTask.values()) {
            if (task.checkOccurrence(date.atStartOfDay())) {
                tasks.add(task);
            }
        }
        return tasks;
    }

    public static void getGrouppedByDate() {
        Map<LocalDate, ArrayList<Repeatable>> taskMap = new HashMap<>();
        for (Map.Entry<Integer, Repeatable> entry : actualTask.entrySet()) {
            Repeatable task = entry.getValue();
            LocalDate localDate = task.getFirstDate().toLocalDate();
            if (taskMap.containsKey(localDate)) {
                ArrayList<Repeatable> tasks = taskMap.get(localDate);
                tasks.add(task);
            } else {
                taskMap.put(localDate, new ArrayList<>(Collections.singletonList(task)));
            }
            for (Map.Entry<LocalDate, ArrayList<Repeatable>> taskEntry : taskMap.entrySet()) {
                System.out.println(taskEntry.getKey() + " : " + taskEntry.getValue());
            }
        }
    }


    public static void printActualTask() {
        for (Repeatable task : actualTask.values()) {
            System.out.println(task);
        }
    }

    public static void printArchivedTask() {
        for (Repeatable task : archivedTask.values()) {
            System.out.println(task);
        }
    }


    }
