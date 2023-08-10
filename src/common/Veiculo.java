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
}
