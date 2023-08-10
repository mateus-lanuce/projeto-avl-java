package Cliente;

import Protocolo.Protocolo;
import common.Veiculo;

import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public class Cliente {

    private Protocolo protocolo = new Protocolo();
    private Scanner scanner = new Scanner(System.in);

    public void cadastrarVeiculo() {
        clearConsole();

        Veiculo temp = new Veiculo();


        System.out.println("Bem vindo ao cadastro de um novo veiculo!");
        System.out.println("Digite os valores que forem pedidos.\n");

        //leitura dos valores
        System.out.print("Dados do veiculo:\nPlaca: ");
        temp.setPlaca(scanner.next());
        System.out.print("Renavam: ");
        temp.setRenavam(scanner.next());
        System.out.print("Modelo do veiculo: ");
        temp.setModelo(scanner.next());
        System.out.print("Data de Fabricação do veiculo (00/00/2003): ");
        temp.setData_fabricacao(scanner.next());

        //valores do condutos
        System.out.println("\nDados do Condutor");
        System.out.print("CPF: ");
        temp.setCpf(scanner.next());
        System.out.print("Nome do condutor: ");
        temp.setNome(scanner.next());

        protocolo.enviarMensagem(temp);

        System.out.println("Veiculo cadastrado com sucesso!");
    }

    public void buscarVeiculo() {
        clearConsole();

        String placa, renavam;

        System.out.println("Busca de veiculo por placa e renavam");

        //leitura
        System.out.print("Placa: ");
        placa = scanner.next();
        System.out.print("Renavam: ");
        renavam = scanner.next();

        Veiculo temp = protocolo.receberMensagem(renavam, false);

        if (temp == null) {
            System.out.println("Veiculo não encontrado!");
        } else {
            listDados(temp);
        }

    }

    public void removerVeiculo() {

        System.out.println("Remover Veiculo - está operação não pode ser desfeita!");

        String renavam;

        System.out.print("Digite o Renavam do veiculo a ser excluido: ");
        renavam = scanner.next();

        Veiculo temp = protocolo.receberMensagem(renavam, true);

        if (temp == null) {
            System.out.println("Veiculo não encontrado!");
        } else {
            System.out.println("O Veiculo: ");
            listDados(temp);
            System.out.println("Foi removido com sucesso");
        }

    }

    private void listDados(Veiculo v) {
        System.out.println("\n-------Dados do veiculo-------");
        System.out.println("Placa: " + v.getPlaca());
        System.out.println("Renavam: " + v.getRenavam());
        System.out.println("Modelo: " + v.getModelo());
        System.out.println("Data de Fabricação: " + v.getData_fabricacao());

        System.out.println("-------Dados do Condutor-------");
        System.out.println("Nome do Condutor: " + v.getNome());
        System.out.println("CPF: " + v.getCpf());
    }

    public final static void clearConsole()
    {
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            //  Handle any exceptions.
        }
    }

}
