/**
 *
 * @author Luiz Merschmann
 */
public class Principal {
    public static void main(String[] args) {
        MenuPrincipal janela = new MenuPrincipal();
        janela.exibirJanela();

        while (true){
            if (janela.simulacaoPronta()){
                break;
            }
            System.out.println("Loop..."); // Vai, tira esse print pra ocê ver o que acontece... '-'
        }

        Simulacao sim = new Simulacao();
        sim.executarSimulacao(400);
    }
}
