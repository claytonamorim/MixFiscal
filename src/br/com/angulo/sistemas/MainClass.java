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
import br.com.angulo.sistemas.bean.Produto;
import br.com.angulosistemas.dao.BeanExportaImporta;
import br.com.angulosistemas.dao.DaoExportaImporta;
import static br.com.angulo.sistemas.Configuracao.*;

public class MainClass {

	private List<BeanExportaImporta> objAtualizados;
	//private List<Caixa> caixaAtualizados;
	//private List<Venda> vendaAtualizados;

	public static void main(String[] args) {
		System.out.println("Main da TelaProcessaBanco");
		if (args.length > 0){
			System.out.println("Recebido parametro com local de arquivo de configuracao em:" + args[0]);
			Configuracao.LOCAL_CONFIGURACAO = args[0];	//Pegando o local do Arquivo de Configura��o passado obrigatoriamente como argumento
			Configuracao.setConfiguracao();
		}else{
			System.out.println("N�o foi passado argumentos na chamada, utilizando endere�o padr�o do angulo.cfg");
			Configuracao.setConfiguracao();
		}
		
		new MainClass();
		//System.exit(0); //Fechando a aplica��o
	}

	public MainClass() {
		//Configuracao.setConfiguracao();
		//new TesteCreateTable();
		processar();
	}

	public void processar (){
		objAtualizados = new ArrayList<BeanExportaImporta>();
		
		System.out.println("Criando objeto DAO...");
		//________________________________________________________
		//ProdutoTabela daoProduto = new ProdutoTabela();
		Produto produto = new Produto();
		DaoExportaImporta dao = new DaoExportaImporta(produto);
		
		//__________________________________________________________
		
		System.out.println("nome completo da tabela=" + produto.getTableName());
		
		//___________________________________________________________
		//prodAtualizados = daoProduto.getProdutosAtualizados();		//m�todo que envia 'apenas' os registros alterados ou novos (mais eficiente!)
		//objAtualizados = dao.getItensAtualizados();
		objAtualizados = dao.getItensFromTxt(new File("C:\\importa\\angulo.txt"));
		dao.executarUpdateQuery(objAtualizados);
		//_________________________________________________________
		//Log.criarLogUpdate(prodAtualizados.size(), UtilitarioHtml.montarTabelaProdutos(prodAtualizados));
		
		//Verificando a op��o [Exibir_Dialogo] e agindo de acordo com seu valor definido
		if (exibirDialogo == true){
			//montando as atualiza��es e mostrando-as ao usu�rio
			JTextPane painel = new JTextPane();
			painel.setContentType("text/html");
			//__________________________________________________________________________________
			//painel.setText(UtilitarioHtml.montarTodasTabelas(prodAtualizados, null, null));
			//__________________________________________________________________________________
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


	
	


	
	
	
	
	


