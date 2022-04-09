import javax.swing.ImageIcon;

public class Loja extends Item {
	
	public Loja(Localizacao localizacao) {
		super(localizacao);
		setImagem(new ImageIcon(getClass().getResource("Imagens/store.jpg")).getImage());
		setLocalizacaoDestino(null);
	}
}
