package calendar.repeatabilityOfTask;

import calendar.Repeatable;
import calendar.Task;
import calendar.TaskType;
import calendar.exceptions.WrongInputException;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class WeeklyTask extends Task implements Repeatable {
    public WeeklyTask(String title, String description, TaskType taskType, LocalDateTime firstDate) throws WrongInputException {
        super(title, description, taskType, firstDate);
    }

    @Override
    public boolean checkOccurrence(LocalDateTime requestedDate) {
        return getFirstDate().getDayOfWeek() == (requestedDate.getDayOfWeek());
    }
}
