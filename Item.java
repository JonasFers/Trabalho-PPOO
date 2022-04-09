import java.awt.Image;
import java.util.Random;

/**
 * Representa os veiculos da simulacao.
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class Item {
    private Localizacao localizacaoAtual;
    private Localizacao localizacaoDestino;
    private Image imagem;

    public Item(Localizacao localizacao) {
        this.localizacaoAtual = localizacao;
        localizacaoDestino = null;
    }

    public Localizacao getLocalizacaoAtual() {
        return localizacaoAtual;
    }

    public Localizacao getLocalizacaoDestino() {
        return localizacaoDestino;
    }
    
    public Image getImagem(){
        return imagem;
    }

    public void setImagem(Image imagem){
        this.imagem = imagem;
    }

    protected void setLocalizacaoAtual(Localizacao localizacaoAtual) {
        this.localizacaoAtual = localizacaoAtual;
    }

    protected void setLocalizacaoDestino(Localizacao localizacaoDestino) {
        this.localizacaoDestino = localizacaoDestino;
    }

    public void destinoAleatorio(int largura, int altura){
        Random rand = new Random();
        setLocalizacaoDestino(new Localizacao(rand.nextInt(largura),rand.nextInt(altura)));
    }
    
    public boolean chegouDestino(){
        return getLocalizacaoAtual() == getLocalizacaoDestino();
    }
}
