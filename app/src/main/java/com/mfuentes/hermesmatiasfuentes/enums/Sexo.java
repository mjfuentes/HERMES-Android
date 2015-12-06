package com.mfuentes.hermesmatiasfuentes.enums;

public enum Sexo {
    MASCULINO(1),FEMENINO(2);

    private int numero;
    Sexo(int num) {
        this.numero = num;
    }

    public int getNumero() {
        return numero;
    }

    public static Sexo fromNumero(int num){
        for (Sexo sexo:Sexo.values()){
            if (sexo.getNumero() == num){
                return sexo;
            }
        }
        return null;
    }
}
