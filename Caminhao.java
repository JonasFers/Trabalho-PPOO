import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

public class Caminhao extends Item implements ItemMovel{
    private List<Mercadoria> mercadoriasPendentes;
    private boolean carga;

    public Caminhao(Localizacao localizacao) {
        super(localizacao);
        setImagem(new ImageIcon(getClass().getResource("Imagens/veiculo.jpg")).getImage());
        mercadoriasPendentes = new ArrayList<Mercadoria>();
        carga = false;
    }
    
    
    public void addMercadoria(Mercadoria m) {
    	mercadoriasPendentes.add(m);
    	if (this.getLocalizacaoDestino()==null) {
    		this.setLocalizacaoDestino(m.getLocalizacaoAtual());
    	}
    }

    public void descarregar(){
        if (getProxMercadoria()!=null) {
            setLocalizacaoDestino(getProxMercadoria().getLocalizacaoAtual());
            setCarga(false);
        }
        else {
            setLocalizacaoDestino(null);
            setCarga(false);
        }
    }

    public Mercadoria carregar(){
        if (getProxMercadoria()!=null) {
            setLocalizacaoDestino(getProxMercadoria().getLocalizacaoDestino());
            setCarga(true);
        }
        return mercadoriasPendentes.remove(0);
    }
    
    public Mercadoria getProxMercadoria() {
        if(!mercadoriasPendentes.isEmpty())
    	    return mercadoriasPendentes.get(0);
        else
            return null;
    }

    public boolean estaCarregado() {
    	return carga;
    }
    
    private void setCarga(boolean bool) {
    	carga = bool;
    }
    
    @Override
    public void executarAcao(Mapa mapa) {
        Localizacao destino = getLocalizacaoDestino();
        if(destino != null){
            Localizacao proximaLocalizacao = getLocalizacaoAtual().proximaLocalizacao(getLocalizacaoDestino());
            Item i = mapa.getItem(proximaLocalizacao);
            if((i instanceof Ciclista || i instanceof Pedestre) == false)
                setLocalizacaoAtual(proximaLocalizacao);
        }
    }
    /*
        Função de Açao com verificação de colisão v1.0
        public void executarAcao(Mapa mapa, Mercadoria mercadoria){
        Localizacao destino = getLocalizacaoDestino();
        Localizacao anterior = getLocalizacaoAtual();
        int opcoes = 4;
        Random rand = new Random();
        if(destino != null){
            Localizacao proximaLocalizacao = getLocalizacaoAtual().proximaLocalizacao(super.getLocalizacaoDestino());
            try{
            while(mapa.getItem(proximaLocalizacao.getX(), proximaLocalizacao.getY()) != null && mapa.getItem(proximaLocalizacao.getX(), proximaLocalizacao.getY()) != mercadoria){
                if(rand.nextInt(opcoes) == 4){
                    proximaLocalizacao = new Localizacao(anterior.getX() + 1, anterior.getY());
                } else if(rand.nextInt(opcoes) == 3){
                    proximaLocalizacao = new Localizacao(anterior.getX() - 1, anterior.getY());
                } else if(rand.nextInt(opcoes) == 3){
                    proximaLocalizacao = new Localizacao(anterior.getX(), anterior.getY() + 1);
                } else {
                    proximaLocalizacao = new Localizacao(anterior.getX(), anterior.getY() - 1);
                }
            }
            } catch(ArrayIndexOutOfBoundsException e){
                if(rand.nextInt(opcoes) == 4){
                    proximaLocalizacao = new Localizacao(anterior.getX() + 1, anterior.getY());
                } else if(rand.nextInt(opcoes) == 3){
                    proximaLocalizacao = new Localizacao(anterior.getX() - 1, anterior.getY());
                } else if(rand.nextInt(opcoes) == 3){
                    proximaLocalizacao = new Localizacao(anterior.getX(), anterior.getY() + 1);
                } else {
                    proximaLocalizacao = new Localizacao(anterior.getX(), anterior.getY() - 1);
                }
            } finally{
              setLocalizacaoAtual(proximaLocalizacao);
            }
        }
    }
    */
}
