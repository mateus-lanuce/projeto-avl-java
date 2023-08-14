import Cliente.Cliente;
import common.Veiculo;
import interfaces.Avl_Tree_interface;
import servidor.Avl_Tree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        Cliente cliente = new Cliente();

        //ler arquivo com 50 veiculos para pre cadastro
        String path = "src/carros_pre.txt";

        BufferedReader buffRead = new BufferedReader(new FileReader(path));
        String marca = "";
        String modelo = "";
        String data_fabricacao = "";
        String renavam = "";
        String placa = "";
        while (true) {
            marca = buffRead.readLine();
            if (marca != null) {
                Veiculo temp = new Veiculo();

                //marca + modelo
                modelo =  buffRead.readLine();
                temp.setModelo(marca + " " + modelo);

                data_fabricacao = buffRead.readLine();
                temp.setData_fabricacao(data_fabricacao);

                renavam = buffRead.readLine();
                temp.setRenavam(renavam);

                placa = buffRead.readLine();
                temp.setPlaca(placa);

                temp.setCpf("000.000.000-00");
                temp.setNome("veiculo pre cadastrado");

                cliente.cadastrarVeiculo(temp);
            } else
                break;
        }
        buffRead.close();

        Scanner s = new Scanner(System.in);

        boolean stop = true;
        while (stop) {

            System.out.println("\n\nFunções do sistema, digite o número para escolher");
            System.out.println("1 - cadastrar veiculo");
            System.out.println("2 - remover veiculo");
            System.out.println("3 - alterar veiculo");
            System.out.println("4 - buscar veiculo");
            System.out.println("5 - Acessar a quantidade de veiculos cadastrados");
            System.out.println("6 - Ver detalhes de todos os veiculos");
            System.out.println("7 - Sair");
            System.out.print("Função: ");
            int select = s.nextInt();

            switch (select) {

                case 1:
                    cliente.cadastrarVeiculo();
                    break;

                case 2:
                    cliente.removerVeiculo();
                    break;

                case 3:
                    cliente.alterarDados();
                    break;

                case 4:
                    cliente.buscarVeiculo();
                    break;

                case 5:
                    cliente.acessarQuantidade();
                    break;

                case 6:
                    cliente.listarVeiculos();
                    break;

                case 7:
                    stop = false;
                    break;

                default:
                    System.out.println("Digite um valor valido.");
                    break;
            }

        }

        s.close();

    }
}