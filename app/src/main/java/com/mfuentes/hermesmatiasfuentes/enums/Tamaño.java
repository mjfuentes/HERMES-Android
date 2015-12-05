package com.mfuentes.hermesmatiasfuentes.enums;

public enum Tamaño {
    PEQUEÑO(1), MEDIANO(2), GRANDE(3);

    private int numero;
    Tamaño(int num) {
        this.numero = num;
    }

    public int getNumero() {
        return numero;
    }
}
