import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PaginaCarrinho {
    private JPanel PainelCarrinho;
    private JButton Voltar;
    private JButton Comprar;
    private  JLabel LabelCarrrinho;
    private JPanel PainelTabelProdutos;
    private JScrollPane scrollProduto;


    public PaginaCarrinho() {}

    public JButton getBotaoVoltar(){
        return Voltar;
    }

    public JButton getBotaoComprar(){
        return Comprar;
    }

    public JPanel getPainelCarrinho(Cliente client){
        constroiTabelaProdutos(client);
        return PainelCarrinho;
    }

    public void constroiTabelaProdutos(Cliente cliente) {

        try {
            PainelTabelProdutos.removeAll();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        PainelTabelProdutos.setLayout(new BoxLayout(PainelTabelProdutos, BoxLayout.Y_AXIS));

        Carrinho car = cliente.getCarrinho();
        ArrayList<Produto> produtos = car.getCarrinho();

        for (Produto produto : produtos) {
            JPanel painelProdutosIntern = new JPanel(new FlowLayout(FlowLayout.LEFT));

            JLabel nomeProdutoLabel = new JLabel(produto.getNome());
            JSpinner quantidadeSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
            JLabel precoLabel = new JLabel(String.valueOf(produto.getPreco()));

            quantidadeSpinner.addChangeListener(e -> {
                double quantidade = (int) quantidadeSpinner.getValue();
                precoLabel.setText(String.format("%.2f", quantidade * produto.getPreco()));
                PainelTabelProdutos.repaint();
            });

            painelProdutosIntern.add(nomeProdutoLabel);
            painelProdutosIntern.add(quantidadeSpinner);
            painelProdutosIntern.add(precoLabel);

            PainelTabelProdutos.add(painelProdutosIntern);
        }

        PainelCarrinho.revalidate();
        PainelCarrinho.repaint();
    }


}