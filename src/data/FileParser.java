package data;

import exception.TransferException;
import model.Account;
import service.ReportGenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileParser {
    public static List<Account> parseFilesInDirectory(String directoryPath) throws TransferException {
        List<Account> transfers = new ArrayList<>();

        File directory = new File(directoryPath);
        if (!directory.isDirectory()) {
            throw new TransferException("InvalidDirectory", "Указанный путь не является директорией");
        }

        for (File file : directory.listFiles()) {
            if (file.isFile() && file.getName().toLowerCase().endsWith(".txt")) {
                try {
                    transfers.add(parseFile(file));
                } catch (TransferException e) {
                    TransferException.handleParsingError(file, e.getMessage());
                    System.err.println("Ошибка парсинга файла " + file.getName() + ": " + e.getMessage());
                }
            }
        }

        return transfers;
    }

    private static Account parseFile(File file) throws TransferException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String senderAccount = readLineSafe(reader);
            String receiverAccount = readLineSafe(reader);
            String transferAmountStr = readLineSafe(reader);

            if (senderAccount == null || receiverAccount == null || transferAmountStr == null) {
                throw new TransferException("FileParsingError", "Ошибка при парсинге файла " + file.getName());
            }

            senderAccount = senderAccount.trim();
            receiverAccount = receiverAccount.trim();
            transferAmountStr = transferAmountStr.trim();

            int transferAmount = Integer.parseInt(transferAmountStr);

            return new Account(senderAccount, receiverAccount, transferAmount);
        } catch (IOException | NumberFormatException e) {
            throw new TransferException("FileParsingError", "Ошибка при парсинге файла " + file.getName());
        }
    }

    private static String readLineSafe(BufferedReader reader) throws IOException {
        // Читаем строку, проверяем на null и возвращаем
        return reader.readLine();
    }
    private static void handleParsingError(File file, String errorMessage) {
        String errorInfo = LocalDateTime.now() + " - " + file.getName() + " - " + errorMessage;
        ReportGenerator.appendOperationToReport(errorInfo);
    }
}
