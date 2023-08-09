package common;

import java.util.Date;

public class Veiculo extends Condutor{
    String placa = null;

    String renavam = null;

    String modelo = null;

    Date data_fabricacao = null;

    String codigo_veiculo = null;

    public Veiculo(String placa, String renavam, String modelo, Date data_fabricacao, String cpf, String condutor) {
        this.setPlaca(placa);
        this.setModelo(modelo);
        this.setRenavam(renavam);
        this.setData_fabricacao(data_fabricacao);
        this.setCpf(cpf);
        this.setNome(condutor);
        this.setCodigo_veiculo();
    }

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

    public void setData_fabricacao(Date data) {
        this.data_fabricacao = data;
    }

    public Date getData_fabricacao() {
        return this.data_fabricacao;
    }

    public void setCodigo_veiculo() {
        this.codigo_veiculo = this.renavam + '-' + this.placa;
    }

    public String getCodigo_veiculo() {
        return this.codigo_veiculo;
    }
}
