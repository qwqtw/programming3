import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Todo {
    private String task;
    private Date dueDate;
    private int hoursOfWork;
    private TaskStatus status;

    enum TaskStatus { Pending, Done };

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    // Constructor
    public Todo(String task, Date dueDate, int hoursOfWork, TaskStatus status) {
        setTask(task);
        setDueDate(dueDate);
        setHoursOfWork(hoursOfWork);
        setStatus(status);
    }

    // Constructor for file data
    public Todo(String dataLine) throws IllegalArgumentException, ParseException {
        String[] parts = dataLine.split(";");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid data format.");
        }
        setTask(parts[0]);
        setDueDate(sdf.parse(parts[1]));
        setHoursOfWork(Integer.parseInt(parts[2]));
        setStatus(TaskStatus.valueOf(parts[3]));
    }

    // Getters
    public String getTask() { return task; }
    public Date getDueDate() { return dueDate; }
    public int getHoursOfWork() { return hoursOfWork; }
    public TaskStatus getStatus() { return status; }

    // Setters
    public void setTask(String task) {
        if (task.length() < 2 || task.length() > 50 || task.matches(".*[;|`].*")) {
            throw new IllegalArgumentException("Task must be between 2-50 characters long and must not contain ';', '|', or '`'.");
        }
        this.task = task;
    }

    public void setDueDate(Date dueDate) {
        if (dueDate.before(new Date(0)) || dueDate.after(new Date(4102444800000L))) { // 1900 to 2100
            throw new IllegalArgumentException("Due date must be between 1900 and 2100.");
        }
        this.dueDate = dueDate;
    }

    public void setHoursOfWork(int hoursOfWork) {
        if (hoursOfWork < 0) {
            throw new IllegalArgumentException("Hours of work must be 0 or greater.");
        }
        this.hoursOfWork = hoursOfWork;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, will take %d hour(s) of work, %s",
                task, sdf.format(dueDate), hoursOfWork, status);
    }

    public String toDataString() {
        return String.format("%s;%s;%d;%s", task, sdf.format(dueDate), hoursOfWork, status);
    }
}
