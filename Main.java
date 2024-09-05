import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {
    static ArrayList<Todo> todoList = new ArrayList<>();
    static Scanner input = new Scanner(System.in);
    static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    public static void main(String[] args) {
        loadDataFromFile();

        while (true) {
            System.out.println("Please make a choice [0-4]:");
            System.out.println("1. Add a todo");
            System.out.println("2. List all todos (numbered)");
            System.out.println("3. Delete a todo");
            System.out.println("4. Modify a todo");
            System.out.println("0. Exit");

            int choice = inputInt();
            switch (choice) {
                case 1 -> addTodo();
                case 2 -> listTodos();
                case 3 -> deleteTodo();
                case 4 -> modifyTodo();
                case 0 -> {
                    saveDataToFile();
                    System.out.println("Exiting. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option, please try again.");
            }
        }
    }

    static void addTodo() {
        System.out.println("Adding a todo.");
        input.nextLine(); // consume leftover newline
        try {
            System.out.print("Enter task description: ");
            String task = input.nextLine();
            System.out.print("Enter due Date (yyyy/MM/dd): ");
            Date dueDate = sdf.parse(input.nextLine());
            System.out.print("Enter hours of work (integer): ");
            int hoursOfWork = inputInt();
            Todo todo = new Todo(task, dueDate, hoursOfWork, Todo.TaskStatus.Pending);
            todoList.add(todo);
            System.out.println("You've created the following todo:");
            System.out.println(todo);
        } catch (IllegalArgumentException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void listTodos() {
        if (todoList.isEmpty()) {
            System.out.println("There are no todos.");
        } else {
            System.out.println("Listing all todos:");
            for (int i = 0; i < todoList.size(); i++) {
                System.out.println("#" + (i + 1) + ": " + todoList.get(i));
            }
        }
    }

    static void deleteTodo() {
        System.out.print("Which todo # would you like to delete? ");
        int index = inputInt() - 1;
        if (index >= 0 && index < todoList.size()) {
            todoList.remove(index);
            System.out.println("Deleted todo #" + (index + 1) + " successfully.");
        } else {
            System.out.println("Invalid todo number.");
        }
    }

    static void modifyTodo() {
        System.out.print("Which todo # would you like to modify? ");
        int index = inputInt() - 1;
        if (index >= 0 && index < todoList.size()) {
            Todo todo = todoList.get(index);
            System.out.println("Modifying todo #" + (index + 1) + ": " + todo);
            input.nextLine(); // consume leftover newline
            try {
                System.out.print("Enter new task description: ");
                todo.setTask(input.nextLine());
                System.out.print("Enter new due Date (yyyy/MM/dd): ");
                todo.setDueDate(sdf.parse(input.nextLine()));
                System.out.print("Enter new hours of work (integer): ");
                todo.setHoursOfWork(inputInt());
                System.out.print("Enter if task is 'Done' or 'Pending': ");
                todo.setStatus(Todo.TaskStatus.valueOf(input.nextLine().trim()));
                System.out.println("You've modified todo #" + (index + 1) + " as follows:");
                System.out.println(todo);
            } catch (IllegalArgumentException | ParseException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid todo number.");
        }
    }

    static int inputInt() {
        while (true) {
            try {
                return input.nextInt();
            } catch (Exception e) {
                input.nextLine(); // clear invalid input
                System.out.print("Invalid integer, please try again: ");
            }
        }
    }

    static void loadDataFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("todos.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    todoList.add(new Todo(line));
                } catch (Exception e) {
                    System.out.println("Invalid data line: " + line + " - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Could not load data: " + e.getMessage());
        }
    }

    static void saveDataToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("todos.txt"))) {
            for (Todo todo : todoList) {
                pw.println(todo.toDataString());
            }
        } catch (IOException e) {
            System.out.println("Could not save data: " + e.getMessage());
        }
    }
}
