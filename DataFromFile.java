import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class DataFromFile {

    // Static lists for names and numbers
    static ArrayList<String> namesList = new ArrayList<>();
    static ArrayList<Double> numsList = new ArrayList<>();

    public static void main(String[] args) {
        String inputFile = "input.txt";
        String duplicatesFile = "duplicates.txt";

        try {
            // Step 1: Read input from file
            loadData(inputFile);

            // Step 2: Sort names and numbers
            Collections.sort(namesList);
            Collections.sort(numsList);

            // Step 3: Display sorted names and numbers
            displaySortedData();

            // Step 4: Compute and display average length of names
            computeAndDisplayAverageNameLength();

            // Step 5: Find duplicate names
            Set<String> duplicates = findDuplicates(namesList);

            // Step 6: Display duplicate names
            displayDuplicates(duplicates);

            // Step 7: Write duplicates to file (append mode)
            writeDuplicatesToFile(duplicatesFile, duplicates);

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    // Load data from the input file
    public static void loadData(String inputFile) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    // Try to parse the line as a double
                    double num = Double.parseDouble(line);
                    numsList.add(num);
                } catch (NumberFormatException e) {
                    // If it's not a number, add it to the names list
                    namesList.add(line);
                }
            }
        }
        System.out.println("Data loaded.");
    }

    // Display sorted names and numbers
    public static void displaySortedData() {
        System.out.println("Names sorted: " + String.join(", ", namesList));
        System.out.println("Numbers sorted: " + numsList.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", ")));
    }

    // Compute and display the average length of names
    public static void computeAndDisplayAverageNameLength() {
        double totalLength = namesList.stream().mapToInt(String::length).sum();
        double averageLength = totalLength / namesList.size();
        System.out.printf("Average length of names: %.2f%n", averageLength);
    }

    // Find duplicate names
    public static Set<String> findDuplicates(List<String> list) {
        Set<String> allItems = new HashSet<>();
        Set<String> duplicates = new HashSet<>();

        for (String item : list) {
            if (!allItems.add(item)) {
                duplicates.add(item);
            }
        }
        return duplicates;
    }

    // Display duplicate names
    public static void displayDuplicates(Set<String> duplicates) {
        if (duplicates.isEmpty()) {
            System.out.println("No duplicate names.");
        } else {
            System.out.println("Duplicate names: " + String.join(", ", duplicates));
        }
    }

    // Write duplicates to a file in append mode
    public static void writeDuplicatesToFile(String fileName, Set<String> duplicates) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            for (String name : duplicates) {
                bw.write(name);
                bw.newLine();
            }
        }
        System.out.println("Duplicate names written to " + fileName);
    }
}
