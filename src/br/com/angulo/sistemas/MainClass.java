package br.com.angulo.sistemas;

import java.awt.Dimension;
import java.awt.TextArea;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import br.com.angulo.sistemas.Configuracao;
import br.com.angulo.sistemas.bean.Caixa;
import br.com.angulo.sistemas.bean.Produto;
import br.com.angulo.sistemas.bean.Venda;
import br.com.angulosistemas.dao.ProdutoTabela;
import static br.com.angulo.sistemas.Configuracao.*;

public class MainClass {

	private List<Produto> prodAtualizados;
	private List<Caixa> caixaAtualizados;
	private List<Venda> vendaAtualizados;

	public static void main(String[] args) {
		System.out.println("Main da TelaProcessaBanco");
		if (args.length > 0){
			System.out.println("Recebido parametro com local de arquivo de configuracao em:" + args[0]);
			Configuracao.LOCAL_CONFIGURACAO = args[0];	//Pegando o local do Arquivo de Configuração passado obrigatoriamente como argumento
			Configuracao.setConfiguracao();
		}else{
			System.out.println("Não foi passado argumentos na chamada, utilizando endereço padrão do angulo.cfg");
			Configuracao.setConfiguracao();
		}
		
		new MainClass();
		//System.exit(0); //Fechando a aplicação
	}

	public MainClass() {
		//Configuracao.setConfiguracao();
		//new TesteCreateTable();
		processar();
	}

	public void processar (){
		prodAtualizados = new ArrayList<Produto>();
		System.out.println("Criando objeto DAO...");
		ProdutoTabela daoProduto = new ProdutoTabela();
		System.out.println("nome completo da tabela=" + daoProduto.getNomeTabelaProduto());
		
		prodAtualizados = daoProduto.getProdutosAtualizados();		//método que envia 'apenas' os registros alterados ou novos (mais eficiente!)
		
		Log.criarLogUpdate(prodAtualizados.size(), UtilitarioHtml.montarTabelaProdutos(prodAtualizados));
		
		//Verificando a opção [Exibir_Dialogo] e agindo de acordo com seu valor definido
		if (exibirDialogo == true){
			//montando as atualizações e mostrando-as ao usuário
			JTextPane painel = new JTextPane();
			painel.setContentType("text/html");
			painel.setText(UtilitarioHtml.montarTodasTabelas(prodAtualizados, null, null));
			//painel.setText(UtilitarioHtml.montarTabelaProdutos(prodAtualizados));
			painel.setEditable(false);
			painel.setBackground(null);

			painel.setBorder(null);

			JScrollPane scrollPane = new JScrollPane(painel);
			scrollPane.setPreferredSize(new Dimension(800,500));
			JOptionPane.showMessageDialog(null, scrollPane);

		}
	}

	
}


	
	


	
	
	
	
	


