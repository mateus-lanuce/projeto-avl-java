package interfaces;

import java.util.Date;

public interface Veiculo_interface extends Condutor_interface {
    //Cada veículo possui dados como placa, renavam, nome e cpf do condutor, nome do
    //modelo e data de fabricação.

   String placa = null;

   String renavam = null;

   String modelo = null;

   String data_fabricacao = null;

}
