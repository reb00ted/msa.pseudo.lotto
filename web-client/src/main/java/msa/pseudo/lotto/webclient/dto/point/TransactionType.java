package msa.pseudo.lotto.webclient.dto.point;

public enum TransactionType {
    CHARGE("충전"), WITHDRAW("출금"), PAYMENT("결제"), PAYMENT_CANCELLED("결제 취소"), WINNING("당첨금");

    private final String value;

    private TransactionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
