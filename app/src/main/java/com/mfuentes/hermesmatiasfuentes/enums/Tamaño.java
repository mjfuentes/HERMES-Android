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

    public static Tamaño fromNumero(int num){
        for (Tamaño tam:Tamaño.values()){
            if (tam.getNumero() == num){
                return tam;
            }
        }
        return null;
    }
}
