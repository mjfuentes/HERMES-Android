package com.mfuentes.hermesmatiasfuentes.enums;

public enum Tamaño {
    PEQUEÑO(1,150,5), MEDIANO(2,200,4), GRANDE(3,250,3);

    private int numero;
    private int size;
    private int columnas;
    Tamaño(int num, int size, int columnas) {
        this.numero = num;
        this.size = size;
        this.columnas = columnas;
    }

    public int getNumero() {
        return numero;
    }

    public int getColumnas(){
        return columnas;
    }
    public int getSize() {
        return size;
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
