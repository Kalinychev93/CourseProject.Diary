import calendar.TaskService;
import calendar.TaskType;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            label:
            while (true) {
                System.out.println("Выберите пункт меню:" + "\n"
                        + "1. Добавить задачу" + "\n"
                        + "2. Редактировать задачу" + "\n"
                        + "3. Удалить задачу" + "\n"
                        + "4. Получить задачи на указанный день" + "\n"
                        + "5. Получить все актуальные задачи" + "\n"
                        + "6. Получить все архивные (выполненные) задачи" + "\n"
                        + "0. Выход");
                if (scanner.hasNextInt()) {
                    int menu = scanner.nextInt();
                    switch (menu) {
                        case 1:
                            TaskService.addTask(scanner);
                            break;
                        case 2:
                            TaskService.editTask(scanner);
                            break;
                        case 3:
                            TaskService.deleteTask(scanner);
                            break;
                        case 4:
                            TaskService.getTasksByDay(scanner);
                            break;
                        case 5:
                            TaskService.printActualTasks();
                        case 6:
                            TaskService.printArchivedTasks();
                            break;
                        case 0:
                            break label;
                    }
                } else {
                    scanner.next();
                    System.out.println("Выберите пункт меню из списка");
                }
            }
        }
    }
}
