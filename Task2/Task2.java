import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Task2 {
    private String inputFilePath;
    private String outputFilePath;
    private Map<String, Integer> logCount;
    private Map<String, Integer> successCount;

    public Task2(String inputFilePath, String outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.logCount = new HashMap<>();
        this.successCount = new HashMap<>();
    }

    public void analyzeLogs() {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processLogEntry(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processLogEntry(String entry) {
        String[] parts = entry.split(" - ");
        if (parts.length < 4) return; 

        String user = parts[2].trim(); 
        String status = parts[1].trim(); 

        logCount.put(status, logCount.getOrDefault(status, 0) + 1);

        if (status.equals("INFO")) { 
            successCount.put(user, successCount.getOrDefault(user, 0) + 1);
        }
    }

    public void writeResults() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            writer.write("Статистика логів:\n");
            for (Map.Entry<String, Integer> entry : logCount.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
            }

            writer.write("\nУспішність запитів:\n");
            int totalLogs = logCount.values().stream().mapToInt(Integer::intValue).sum();
            for (Map.Entry<String, Integer> entry : successCount.entrySet()) {
                double successRate = (double) entry.getValue() / totalLogs * 100;
                writer.write("Користувач: " + entry.getKey() + ", Успішність: " + successRate + "%\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printStats() {
        try {
            long lineCount = Files.lines(Paths.get(inputFilePath)).count();
            long wordCount = Files.lines(Paths.get(inputFilePath))
                                  .flatMap(line -> Arrays.stream(line.split(" "))).count();
            long charCount = Files.lines(Paths.get(inputFilePath)).mapToInt(String::length).sum();

            System.out.println("Кількість рядків: " + lineCount);
            System.out.println("Кількість слів: " + wordCount);
            System.out.println("Кількість символів: " + charCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String inputFilePath = "logfile.log"; 
        String outputFilePath = "analytic.results.txt";

        Task2 logAnalyzer = new Task2(inputFilePath, outputFilePath); 
        logAnalyzer.analyzeLogs();
        logAnalyzer.writeResults();
        logAnalyzer.printStats();
    }
}
