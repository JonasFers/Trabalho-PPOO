import javax.swing.ImageIcon;

public class Ciclista extends Item implements ItemMovel{

    public Ciclista(Localizacao localizacao) {
        super(localizacao);
        setImagem(new ImageIcon(getClass().getResource("Imagens/bicicleta.jpg")).getImage());
    }

    @Override
    public void executarAcao(Mapa mapa){
        Localizacao destino = getLocalizacaoDestino();
        if(destino != null){
            Localizacao proximaLocalizacao = getLocalizacaoAtual().proximaLocalizacao(getLocalizacaoDestino());
            Item i = mapa.getItem(proximaLocalizacao);
            if( i == null) {
                setLocalizacaoAtual(proximaLocalizacao);
            } else {
                for(int x = -1; x <= 1 || i != null; x++){
                    for(int y = -1; y <= 1 || i != null; y++){
                        int dX = getLocalizacaoAtual().getX()+x;
                        int dY = getLocalizacaoAtual().getY()+y;
                        proximaLocalizacao = new Localizacao(dX,dY);
                        try {
                            i = mapa.getItem(proximaLocalizacao); 
                        } catch (IndexOutOfBoundsException e) {
                            System.out.println("Valor fora da matriz");
                            proximaLocalizacao = getLocalizacaoAtual();
                            i = mapa.getItem(proximaLocalizacao);
                        }
                        
                    }
                }
                if( i == null) {
                    setLocalizacaoAtual(proximaLocalizacao);
                }
            }
        }
    } 
    
    /*
    Função de Açao com verificação de colisão v1.0
    public void executarAcao(Mapa mapa){
        Localizacao destino = getLocalizacaoDestino();
        Localizacao anterior = getLocalizacaoAtual();
        if(destino != null){
            Localizacao proximaLocalizacao = getLocalizacaoAtual().proximaLocalizacao(super.getLocalizacaoDestino());
            while(mapa.getItem(proximaLocalizacao.getX(), proximaLocalizacao.getY()) != null){
                proximaLocalizacao = new Localizacao(anterior.getX(), anterior.getY());
            }
            setLocalizacaoAtual(proximaLocalizacao);
        }
    }
    */
}
