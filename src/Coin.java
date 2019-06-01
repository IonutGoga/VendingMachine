public enum Coin {

    FIVE(5), TEN(10), TWENTY(20), FIFTY(50);

    private int value;

    Coin(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
