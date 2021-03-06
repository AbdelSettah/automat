package de.bpgit.automat.domain;

public enum Muenze {

    ZEHN_CENT(10), ZWANZIG_CENT(20), FUENFZIG_CENT(50), EIN_EURO(100), ZWEI_EURO(200);

    private final int wert;

    Muenze(int wert) {
        this.wert = wert;
    }

    public int getwert() {
        return wert;
    }

    @Override
    public String toString() {
        return wert +" Cents";
    }
}
