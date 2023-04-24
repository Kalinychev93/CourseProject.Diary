package calendar.repeatabilityOfTask;

import calendar.Repeatable;
import calendar.Task;
import calendar.TaskType;
import calendar.exceptions.WrongInputException;

import java.time.LocalDateTime;

public class MonthlyTask extends Task implements Repeatable {
    public MonthlyTask(String title, String description, TaskType taskType, LocalDateTime firstDate) throws WrongInputException {
        super(title, description, taskType, firstDate);
    }

    @Override
    public boolean checkOccurrence(LocalDateTime requestedDate) {
        return getFirstDate().getDayOfMonth() == (requestedDate.getDayOfMonth());
    }
}
