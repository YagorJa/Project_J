package model;

public class Account {
    private String senderAccount;
    private String receiverAccount;
    private int transferAmount;

    public Account(String senderAccount, String receiverAccount, int transferAmount) {
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
        this.transferAmount = transferAmount;
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
}
