package common;

import java.util.Date;

public class Veiculo extends Condutor{
    String placa = null;

    String renavam = null;

    String modelo = null;

    String data_fabricacao = null;

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getPlaca() {
        return this.placa;
    }

    public void setRenavam(String renavam) {
        this.renavam = renavam;
    }

    public String getRenavam() {
        return this.renavam;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getModelo() {
        return this.modelo;
    }

    public void setData_fabricacao(String data) {
        this.data_fabricacao = data;
    }

    public String getData_fabricacao() {
        return this.data_fabricacao;
    }

    public String toString() {
        return this.modelo + "\n" + this.data_fabricacao + "\n" + this.renavam + "\n" + this.placa
                + "\n" + this.cpf + "\n" + this.nome;
    } 

    public void transformFromString(String string) {
        String[] splittedString = string.split("\n");
        this.modelo = splittedString[0];
        this.data_fabricacao = splittedString[1];
        this.renavam = splittedString[2];
        this.placa = splittedString[3];
        this.cpf = splittedString[4];
        this.nome = splittedString[5];
    }
}
