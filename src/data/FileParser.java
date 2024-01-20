package data;

import exception.TransferException;
import model.Account;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
                    System.err.println("Ошибка парсинга файла " + file.getName() + ": " + e.getMessage());
                }
            }
        }

        return transfers;
    }

    private static Account parseFile(File file) throws TransferException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    String senderAccount = parts[0];
                    String receiverAccount = parts[1];
                    int transferAmount = Integer.parseInt(parts[2]);
                    return new Account(senderAccount, receiverAccount, transferAmount);
                } else {
                    throw new TransferException("FileParsingError", "Некорректный формат строки в файле " + file.getName());
                }
            }
            throw new TransferException("FileParsingError", "Пустой файл " + file.getName());
        } catch (IOException | NumberFormatException e) {
            throw new TransferException("FileParsingError", "Ошибка при парсинге файла " + file.getName());
        }
    }
}
