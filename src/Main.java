import Cliente.Cliente;
import interfaces.Avl_Tree_interface;
import servidor.Avl_Tree;

import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        Cliente cliente = new Cliente();

        cliente.cadastrarVeiculo();

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

    }
}