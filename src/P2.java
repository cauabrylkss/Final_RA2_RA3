import checkout.Pedido;
import checkout.Entrega;
import usuarios.Cliente;
import arquivos.EscritorCancelados;
import arquivos.EscritorFabricacao;
import arquivos.EscritorEntregas;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class P2 {
    public static void main(String[] args) {

        ArrayList<Cliente> clientes = null;
        ArrayList<ArrayList<Pedido>> pedidosPorSemana = null;


        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("dados.dat"))) {
            clientes = (ArrayList<Cliente>) ois.readObject();
            pedidosPorSemana = (ArrayList<ArrayList<Pedido>>) ois.readObject();
            System.out.println("Dados carregados com sucesso!");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao desserializar: " + e.getMessage());
            return;
        }

        EscritorCancelados escritorCancelados = new EscritorCancelados();
        EscritorFabricacao escritorFabricacao = new EscritorFabricacao();
        EscritorEntregas escritorEntregas = new EscritorEntregas();

        // Lógica de simulação das semanas
        for (int i = 0; i < pedidosPorSemana.size(); i++) {
            int numeroSemana = i + 1;
            List<Pedido> pedidosDestaSemana = pedidosPorSemana.get(i);

            // Listas auxiliares para separar o que acontece na semana
            List<Pedido> pedidosFabricar = new ArrayList<>();
            List<Pedido> pedidosCancelados = new ArrayList<>();
            List<Entrega> entregasDaSemana = new ArrayList<>();

            /* * AQUI ENTRA A SUA LÓGICA DE NEGÓCIO:
             * - Somar os kg de Spaguetti, Canelone e Talharim.
             * - Se passar do limite, joga o Pedido na lista 'pedidosCancelados'.
             * - Se estiver dentro do limite, joga o Pedido na lista 'pedidosFabricar'.
             * - Agrupar os pedidos aprovados na classe Entrega (1 entrega por Cliente).
             */


            try {
                escritorFabricacao.escrever(pedidosFabricar, "log_fabricacao.txt", numeroSemana);
                escritorCancelados.escrever(pedidosCancelados, "log_cancelados.txt", numeroSemana);


                if (numeroSemana >= 3) {
                    escritorEntregas.escrever(entregasDaSemana, "log_entregas.txt", numeroSemana);
                }
            } catch (IOException e) {
                System.out.println("Erro ao escrever log da semana " + numeroSemana + ": " + e.getMessage());
            }
        }

        // 4. CHAMAR A INTERFACE GRÁFICA
        // Após processar (ou em conjunto com o processamento), você deve instanciar a sua tela.
        // Exemplo:
        // TelaSimulacao tela = new TelaSimulacao(clientes, pedidosPorSemana);
        // tela.setVisible(true);
    }
}