package exception;

public class TransferException extends Exception {
    private String errorCode;

    public TransferException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}

