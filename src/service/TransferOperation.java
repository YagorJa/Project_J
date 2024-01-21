package service;

import data.FileParser;
import exception.TransferException;
import model.Account;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class TransferOperation {

    public static void performTransfers(List<Account> transfers) {
        for (Account transfer : transfers) {
            try {
                performSingleTransfer(transfer);
            } catch (TransferException e) {
                TransferException.handleTransferError(transfer, e.getMessage());
                System.err.println(e.getMessage());
            }
        }
    }

    private static void performSingleTransfer(Account transfer) throws TransferException {
        String fileName = "transfer_" + LocalDateTime.now().toString().replace(":", "-") + ".txt";
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Дата и время: " + LocalDateTime.now() + "\n");
            writer.write("Счет отправителя: " + transfer.getSenderAccount() + "\n");
            writer.write("Счет получателя: " + transfer.getReceiverAccount() + "\n");
            writer.write("Сумма перевода: " + transfer.getTransferAmount() + "\n");

            System.out.println("Переводная информация записана в файл: " + fileName);
        } catch (IOException e) {
            throw new TransferException("FileWritingError", "Ошибка при записи информации о переводе в файл.");
        }
    }
}

