package com.mfuentes.hermesmatiasfuentes.enums;

public enum Categoria {
    Pista(1),Establo(2),Necesidades(3),Emociones(4),Usuario(5);

    private int numero;
    Categoria(int num) {
        this.numero = num;
    }

    public int getNumero() {
        return numero;
    }

    public static Categoria fromNumero(int num){
        for (Categoria categoria:Categoria.values()){
            if (categoria.getNumero() == num){
                return categoria;
            }
        }
        return null;
    }
}
