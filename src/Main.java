import data.FileParser;
import exception.TransferException;
import model.Account;
import service.ReportGenerator;
import service.TransferOperation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            String directoryPath = getDirectoryPathFromConsole();
            List<Account> transfers = FileParser.parseFilesInDirectory(directoryPath);

            TransferOperation.performTransfers(transfers);

            List<String> processedFiles = new ArrayList<>();
            List<String> errorFiles = new ArrayList<>();

            for (Account transfer : transfers) {
                String fileName = transfer.getFileName();
                if (transfers.contains(transfer)) {
                    processedFiles.add(fileName);
                } else {
                    errorFiles.add(fileName);
                    ReportGenerator.appendOperationToReport(LocalDateTime.now()
                            + " - " + fileName + " - не обработан по причине не валидности входных данных.");
                }
            }

            ReportGenerator.generateReport(processedFiles, errorFiles);

            // Добавим вывод списка всех операций
            System.out.println("Список всех операций:");
            for (String file : processedFiles) {
                System.out.println(file + " - успешно обработан");
            }
            for (String file : errorFiles) {
                System.out.println(file + " - ошибка во время обработки");
            }

        } catch (TransferException e) {
            handleTransferException(e);

        } catch (IOException e) {
            System.err.println("Ошибка ввода/вывода: " + e.getMessage());
        }
    }

    private static String getDirectoryPathFromConsole() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Введите путь к каталогу с файлами для парсинга: ");
        return reader.readLine();
    }

    private static void handleTransferException(TransferException e) {
        System.err.println("Ошибка во время выполнения перевода: " + e.getMessage());
        if (e.getErrorCode().equals("FileParsingError") || e.getErrorCode().equals("FileWritingError")) {
            // Обработка ошибок парсинга или записи в файл
            TransferException.appendOperationToReport(e.getMessage());
        } else {
            // Обработка других ошибок
            TransferException.handleTransferError(null, e.getMessage()); // Передаем null, так как в данном контексте у нас нет объекта Account
        }
    }
}
