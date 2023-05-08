package calendar;

import calendar.exceptions.WrongInputException;

import java.time.LocalDateTime;
import java.util.Objects;

public class Task {

    private String title;
    private String description;
    private TaskType taskType;
    private LocalDateTime firstDate;
    private boolean archived;
    private static Integer counter = 1;
    private Integer id;

    public Task(String title, String description, TaskType taskType, LocalDateTime firstDate) throws WrongInputException {
        this.title = CheckValues.CheckString(title);
        this.description = CheckValues.CheckString(description);
        this.taskType = taskType;
        this.firstDate = firstDate;
        id = counter++;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public LocalDateTime getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(LocalDateTime firstDate) {
        this.firstDate = firstDate;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }


    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Задача: " +
                "Наименование задачи - " + title + '\'' +
                ", описание задачи: " + description + '\'' +
                ", тип задачи: " + taskType +
                ", дата создания: " + firstDate +
                ", задача в архиве (выполнена): " + isArchived() +
                ", id = " + id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return archived == task.archived && Objects.equals(title, task.title) && Objects.equals(description, task.description) && taskType == task.taskType && Objects.equals(firstDate, task.firstDate) && Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, taskType, firstDate, archived, id);
    }
}
