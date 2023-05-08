package calendar;

public enum TaskType {
    WORK(0),
    PERSONAL(1);
    public final int value;

    TaskType(int value) {
        this.value = value;
    }
}
