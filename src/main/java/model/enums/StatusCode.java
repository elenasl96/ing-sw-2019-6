package model.enums;

public enum StatusCode {
    OK(200),
    KO(400);

    StatusCode(int code) {
        this.code = code;
    }

    private int code;

    public int getCode() {
        return code;
    }
}
