package exception;

import model.Account;
import service.ReportGenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class TransferException extends Exception {
    private String errorCode;

    public TransferException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public static void handleParsingError(File file, String errorMessage) {
        String errorInfo = LocalDateTime.now() + " - " + file.getName() + " - " + errorMessage;
        appendOperationToReport(errorInfo);
    }


    public static void handleTransferError(Account transfer, String errorMessage) {
        String fileName = "transfer_" + transfer.getSenderAccount() + ".txt";
        String errorInfo = LocalDateTime.now() + " - " + fileName + " - " + errorMessage;
        ReportGenerator.appendOperationToReport(errorInfo);
    }
    public static void appendOperationToReport(String operationInfo) {
        String reportFilePath = "report.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportFilePath, true))) {
            writer.write(LocalDateTime.now() + " - " + operationInfo);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл отчета: " + e.getMessage());
        }
}
}

