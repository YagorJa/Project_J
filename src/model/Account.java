package model;

public class Account {
    private String senderAccount;
    private String receiverAccount;
    private int transferAmount;
    private String fileName;  // Добавляем поле для хранения имени файла

    public Account(String senderAccount, String receiverAccount, int transferAmount, String fileName) {
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
        this.transferAmount = transferAmount;
        this.fileName = fileName;
    }

    public String getSenderAccount() {
        return senderAccount;
    }

    public String getReceiverAccount() {
        return receiverAccount;
    }

    public int getTransferAmount() {
        return transferAmount;
    }

    public String getFileName() {
        return fileName;
    }
}
