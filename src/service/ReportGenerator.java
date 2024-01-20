package service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class ReportGenerator {

    public static void generateReport(List<String> processedFiles, List<String> errorFiles) {
        String reportFilePath = "report.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportFilePath))) {
            for (String file : processedFiles) {
                writer.write(LocalDateTime.now() + " - " + file + " - успешно обработан");
                writer.newLine();
            }

            for (String file : errorFiles) {
                writer.write(LocalDateTime.now() + " - " + file + " - ошибка во время обработки");
                writer.newLine();
            }

            System.out.println("Отчет успешно создан: " + reportFilePath);
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл отчета: " + e.getMessage());
        }
    }

    public static void appendOperationToReport(String operationInfo) {
        String reportFilePath = "report.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportFilePath, true))) {
            writer.write(operationInfo);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл отчета: " + e.getMessage());
        }
    }
}
