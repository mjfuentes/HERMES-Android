package com.mfuentes.hermesmatiasfuentes.enums;

public enum Solapa {
    PISTA(1),ESTABLO(2),EMOCIONES(3),NECESIDADES(4);

    private int numero;
    Solapa(int num) {
        this.numero = num;
    }

    public int getNumero() {
        return numero;
    }
}
