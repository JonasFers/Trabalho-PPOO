import java.util.Random;
import java.util.ArrayList;
import java.util.List;
/**
 * Responsavel pela simulacao.
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class Simulacao {
	private List <Caminhao> veiculos;
	private List <Pedestre> pedestres;
	private List <Ciclista> ciclistas;
	private List <Mercadoria> mercadorias;
	private List <Loja> lojas;
	private static final int qtdLojas = 1;
	private static final int qtdInicMerc = 1;
	private static int qtdCaminhoes = 1;
    private static int qtdCiclistas = 1;
	private static int qtdPedestres = 1;
    private final int bordaEsquerda;
    private final int bordaDireita;
    private JanelaSimulacao janelaSimulacao;
	private int contador = 0;
    private Mapa mapa;
    private Random rand;
    private int largura;
    private int altura;
    
    public Simulacao() {
        mapa = new Mapa();
        largura = mapa.getLargura() - 1;
        altura = mapa.getAltura() - 1;
        rand = new Random();
        bordaEsquerda = 0;
        bordaDireita = largura;

        veiculos = new ArrayList<Caminhao>();
        pedestres = new ArrayList<Pedestre>();
        ciclistas = new ArrayList<Ciclista>();
        mercadorias = new ArrayList<Mercadoria>();
        lojas = new ArrayList<Loja>();

        for (int i =0; i< qtdLojas; i++) {
        	Loja loja = new Loja(new Localizacao(17, 30));
        	lojas.add(loja);
        	mapa.adicionarItem(loja);
        }
        
        for (int i=0; i<qtdCiclistas; i++) {
        	Ciclista ciclista = new Ciclista(new Localizacao(bordaEsquerda, rand.nextInt(5, 30)));
        	ciclista.setLocalizacaoDestino(new Localizacao(bordaDireita, rand.nextInt(5, 30)));
        	ciclistas.add(ciclista);
        	mapa.adicionarItem(ciclista);
        }

        for (int i=0; i<qtdCaminhoes; i++) {
        	Caminhao caminhao = new Caminhao(new Localizacao(rand.nextInt(largura),rand.nextInt(altura)));
        	veiculos.add(caminhao);
        }
        for (int i=0; i<qtdPedestres; i++) {
        	Pedestre pedestre = new Pedestre(new Localizacao(bordaDireita, rand.nextInt(5, 30)));
        	pedestre.setLocalizacaoDestino(new Localizacao(bordaEsquerda, rand.nextInt(5, 30)));
        	pedestres.add(pedestre);
        }
        
        for (int i=0; i<qtdInicMerc; i++) {
        	Mercadoria mercadoria = new Mercadoria(new Localizacao(rand.nextInt(1, 33),rand.nextInt(1, 15)),lojas.get(rand.nextInt(lojas.size())));
        	mercadorias.add(mercadoria);
        	Caminhao c = veiculos.get(rand.nextInt(veiculos.size()));
        	c.addMercadoria(mercadoria);
        	mapa.adicionarItem(mercadoria);
        }
        
        janelaSimulacao = new JanelaSimulacao(mapa);
    }
    
    public void executarSimulacao(int numPassos){
        janelaSimulacao.executarAcao();
        for (int i = 0; i < numPassos; i++) {
            executarUmPasso();
            for (Loja loja : lojas) {
            	mapa.adicionarItem(loja);
            }
            for (Mercadoria mercadoria : mercadorias) {
            	mapa.adicionarItem(mercadoria);
            }
            if (contador >= 10) {
            	criarNovaMercadoria();
            	contador=0;
            }
            esperar(100);
        }        
    }

	private void executarUmPasso() {
        if (contador % 2 == 0){
            for (Ciclista c : ciclistas) {
                mapa.removerItem(c);
                if(c.chegouDestino()){
                    c.setLocalizacaoAtual(new Localizacao(bordaEsquerda, rand.nextInt(5, 30)));
                }
                c.executarAcao(mapa);
                mapa.adicionarItem(c);
            }
            System.out.println("ciclista ok");
        }

        if (contador % 3 == 0){
            for (Pedestre p : pedestres) {
                mapa.removerItem(p);
                if(p.chegouDestino()){
                    p.setLocalizacaoAtual(new Localizacao(bordaDireita, rand.nextInt(5, 30)));
                }
                p.executarAcao(mapa);
                mapa.adicionarItem(p);
            }
            System.out.println("pedestre ok");
        }

        for (Caminhao veiculo : veiculos) {
            if(mapa.getItem(veiculo.getLocalizacaoAtual()) instanceof Caminhao){
        	    mapa.removerItem(veiculo);
            }
        	if (veiculo.chegouDestino()) {
        		if (veiculo.estaCarregado()){
        			veiculo.descarregar();
        		} else {
        			Mercadoria carga = veiculo.carregar();
                    mercadorias.remove(carga);
                    mapa.removerItem(carga);
        		}
        	}
        	veiculo.executarAcao(mapa);
            if(mapa.getItem(veiculo.getLocalizacaoAtual()) instanceof Loja == false){
        	    mapa.adicionarItem(veiculo);
            }
        }
        System.out.println("carro ok");

        janelaSimulacao.executarAcao();
    }
    
    private void esperar(int milisegundos){
        try{
            Thread.sleep(milisegundos);
            contador++;
        }catch(InterruptedException e){
            System.out.println(e.getMessage());
        }
    }

    private void criarNovaMercadoria() {
    	Mercadoria mercadoria = new Mercadoria(new Localizacao(rand.nextInt(1, 33),rand.nextInt(1, 15)),lojas.get(rand.nextInt(lojas.size())));
    	mercadorias.add(mercadoria);
    	Caminhao c = veiculos.get(rand.nextInt(veiculos.size()));
    	c.addMercadoria(mercadoria);
    	mapa.adicionarItem(mercadoria);
    }

    public static void ajustarConfiguracoes(int pedestres, int ciclistas, int caminhoes){
        qtdPedestres = pedestres;
        qtdCiclistas = ciclistas;
        qtdCaminhoes = caminhoes;
        System.out.println(qtdPedestres + "\n" + qtdCiclistas + "\n" + qtdCaminhoes);
    }
}
