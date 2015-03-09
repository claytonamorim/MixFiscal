package br.com.angulosistemas.dao;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.angulo.sistemas.ConnectionFactory;
import br.com.angulo.sistemas.Log;
import br.com.angulo.sistemas.Banco;
import br.com.angulo.sistemas.Utilitario;
import br.com.angulo.sistemas.bean.Produto;
import br.com.angulo.sistemas.gui.BarraProgresso;
import br.com.angulo.sistemas.gui.ProgressBarDemo;
import static br.com.angulo.sistemas.Configuracao.*;
import static br.com.angulo.sistemas.Utilitario.*;
import static br.com.angulo.sistemas.bean.Produto.ProdutoE.*;


public class ProdutoTabela {
	private static final String PRODUTO = "PRODUTO";
	private Connection conexao;
	private int countAtualizados;	
	//private String nomeTabelaCliente;

	public ProdutoTabela(){
		this.conexao = new ConnectionFactory().getConnection(destino + "/angulo");
	}

	//_________________________________________________________________________________________________________________
	public List<Produto> getProdutosAtualizados(){
		ResultSet result =  null;										//Produtos do banco onLine
		List<Produto> produtosTxt = new ArrayList<Produto>();			//Produtos do banco angulo.txt
		List<Produto> produtosAtualizados = new ArrayList<Produto>();	//lista com apenas os produtos que foram alterados no banco angulo.txt em relação ao banco onLine

		//		Carregando lista de Produtos do banco onLine em ResultSet

		//String query = "Select * from " + tabela;
		executarCreateTable();
		String query = "Select * from " + getNomeTabelaProduto();
		

		try {
			Statement stmt = conexao.createStatement();
			result = stmt.executeQuery(query);
		}catch (SQLException e) {
			e.printStackTrace();
			Log.criarLogErro(e);
			JOptionPane.showMessageDialog(null, "Não foi possível se comunicar com o banco web. Verifique sua conexão com a Internet");
		}

		//		Pegando lista de Produtos do banco txt em List<Produto>
		produtosTxt = getProdutosFromTxt(origem);
		System.out.println("Qtde de itens no banco angulo.txt:" + produtosTxt.size());
		try{
			Produto pTxt;
			//____Pegando a quantidade de linhas na ResultSet
			result.last();
			int qtdeRes=result.getRow();
			result.first();
			System.out.println("Qtde da ResultSet:" + qtdeRes);
			//________________________________________________ 

			if(qtdeRes == 0){
				System.out.println("Banco Web está vazio, realizando upload de TODOS os registros do banco angulo.txt...");
				produtosAtualizados = produtosTxt;

			}else{
				loopPrincipal:									//nomeando o loop principal
					for (int i=0; i<produtosTxt.size(); i++){
						pTxt = produtosTxt.get(i);				//1 produto do banco angulo.txt para comparação
						result.first();							//retorna ao primeiro item do resultSet a cada início de comparacao
						for(int j=0; j<qtdeRes; j++)	{	//vai comparar este produto do banco angulo com "cada" produto do banco onLine(ResultSet)
							if (pTxt.getId() == (result.getInt(1))){
								boolean verifica = verificaAlteracao(pTxt ,getProdutoFromResultSet(result));
								if(verifica){
									produtosAtualizados.add(pTxt);	//produto encontrado e possui alterações (UPDATE)
								}
								continue loopPrincipal;			//como o produto foi encontrado, então após realizadas as tarefas de comparação, o laço é pulado para o próximo item
							}	
							result.next();					//pulado para o próximo item para comparação
						}//fim do FOR
						produtosAtualizados.add(pTxt);		//verifica se a ResultSet chegou ao último objeto produto, e, neste caso onde chegou-se ao final mas não encontrou-se o Produto, significa se tratar de um novo produto, INSERT)	
					}	//fim de TODAS as comparaçoes

			}

			countAtualizados = produtosAtualizados.size();
			System.out.println("Qtde de produtos a serem atualizados:" + countAtualizados);
			executarUpdateQuery(produtosAtualizados);	//chamando método que envia de fato os produtos novos e alterados para o banco onLine
			return produtosAtualizados;		//Retornando lista de produtos novos e alterados (INSERT into table ON DUPLICATE KEY UPDATE), para posteriormente ser montada tabela a ser mostrada ao usuário

		}catch(Exception e){
			e.printStackTrace();
			Log.criarLogErro(e);
			return null;
		}finally{
			try {
				result.close();  //fechando a ResultSet
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getNomeTabelaProduto(){
		return PRODUTO.concat("_").concat(tabela);	//aqui é concatenado o nome da tabela do cliente, ficando algo como 'PRODUTO_123456'
	}

	public String createTableQuery(){
		String nomeTabelaProdutoCompleto = getNomeTabelaProduto();
		System.out.println("nome da tabela concatenada=" + nomeTabelaProdutoCompleto);

		StringBuilder builder = new StringBuilder();
		builder.append("CREATE TABLE IF NOT EXISTS `" + nomeTabelaProdutoCompleto + "` (");
		builder.append("`codinterno` int(11) NOT NULL,");
		builder.append("`codbarras` varchar(14) NOT NULL,");
		builder.append("`fornecedor` varchar(60) NOT NULL,");
		builder.append("`descricao` varchar(70) NOT NULL,");
		builder.append("`pcusto` double NOT NULL,");
		builder.append("`margem` double NOT NULL,");
		builder.append("`pvenda` double NOT NULL,");
		builder.append("`saldo` int(11) NOT NULL,");
		builder.append("`estat1` int(11) NOT NULL,");
		builder.append("`estat2` int(11) NOT NULL,");
		builder.append("`estat3` int(11) NOT NULL,");
		builder.append("`qtdcpa` int(11) NOT NULL,");
		builder.append("PRIMARY KEY (`codinterno`)");
		builder.append(") ENGINE=InnoDB DEFAULT CHARSET=latin1;");			

		return builder.toString();		
	}

	public void executarCreateTable(){
		String queryCreateTable = createTableQuery();
		Statement statement;
		try {
			statement = conexao.createStatement();
			statement.execute(queryCreateTable);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void executarUpdateQuery(List<Produto> produtosAtualizados){
		executarCreateTable();	//aqui é criada a tabela no banco web, caso já exista nada acontece

		PreparedStatement pstmt;
		String queryReplace = "REPLACE into " + getNomeTabelaProduto() + " (" +
				//String queryReplace = "REPLACE into " + tabela + " (" +
				ID + "," +
				COD_BARRAS + "," +
				FORNECEDOR + "," +
				DESCRICAO + "," +
				PCUSTO + "," +
				MARGEM + "," +
				PVENDA + "," +
				SALDO + "," +
				ESTAT1 + "," +
				ESTAT2 + "," +
				ESTAT3 + "," +
				QTD_CPA + ")" +
				"VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

		try {
			long startTimeAtAll = System.currentTimeMillis();
			long startTime = System.currentTimeMillis();
			conexao.setAutoCommit(false);
			pstmt = conexao.prepareStatement(queryReplace);
			//new BarraProgresso();
			new ProgressBarDemo();
			//BarraProgresso.progressBar.setMinimum(0);
			//BarraProgresso.progressBar.setMaximum(produtosAtualizados.size());
			for (int i=0; i<produtosAtualizados.size(); i++){	//percorrerá cada Produto da Lista de Produtos atualizados, carregando-los no PreparedStatement
				Produto p = produtosAtualizados.get(i);

				pstmt.setInt(1, p.getId());
				pstmt.setString(2, p.getCodBarra());
				pstmt.setString(3, p.getFornecedor());
				pstmt.setString(4, p.getDescricao());
				pstmt.setDouble(5, p.getPCusto());
				pstmt.setDouble(6, p.getMargem());
				pstmt.setDouble(7, p.getPVenda());
				pstmt.setInt(8, p.getEstoque());
				pstmt.setInt(9, p.getEstat1());
				pstmt.setInt(10, p.getEstat2());
				pstmt.setInt(11, p.getEstat3());
				pstmt.setInt(12, p.getQtdCpa());				

				pstmt.addBatch();
				
				if ((i+1) % qtdeLinhas == 0){		//i+1 pois 'i' começa a partir do 0 mas deve contar a partir do 1
					int[] count = pstmt.executeBatch();
					conexao.commit();
					//capiturando Array com códigos da execução de cada query e guardando-os para futuramente mostrar apenas as linhas atualizadas
					System.out.println("Realizado REPLACE de " + qtdeLinhas + " registros em " + (System.currentTimeMillis() - startTime)/1000 + " segundos");
					startTime = System.currentTimeMillis();
					delay(delay);
				}
			}

			System.out.println("Realizado REPLACE dos últimos registros em " + (System.currentTimeMillis() - startTime)/1000 + " segundos");
			int[] count = pstmt.executeBatch();
			conexao.commit();

			//Fechando as conexões
			pstmt.close();
			conexao.close();
			long endTime = System.currentTimeMillis();
			long elapsedTime = (endTime - startTimeAtAll)/1000;

			System.out.println("Tempo total para executar TODAS as queries: " + elapsedTime);
		}catch(Exception e){
			e.printStackTrace();
			Log.criarLogErro(e);
		}
	}


	public Produto getProdutoFromResultSet(ResultSet result){
		Produto p = new Produto();
		try{
			//Carregando 1 objeto tipo Produto para adiciona-lo a uma lista e depois compara-los à lista de Produto do banco angulo.txt
			p.setId(result.getInt(1));
			p.setCodBarra(result.getString(2));
			p.setFornecedor(result.getString(3));
			p.setDescricao(result.getString(4));
			p.setPCusto(result.getDouble(5));
			p.setMargem(result.getDouble(6));
			p.setPVenda(result.getDouble(7));
			p.setEstoque(result.getInt(8));
			p.setEstat1(result.getInt(9));
			p.setEstat2(result.getInt(10));
			p.setEstat3(result.getInt(11));
			p.setQtdCpa(result.getInt(12));

		}catch(SQLException e){
			e.printStackTrace();
			Log.criarLogErro(e);  //Gravando o log de erro em C:/importa/erros.log
		}
		return p;
	}

	public List<Produto> getProdutosFromResultSet(ResultSet result){
		try{
			result.beforeFirst(); //aponta o cursor para uma linha antes da primeira, pois ao entrar no loop na primeira vez já é chamado o método next e o cursor irá para o primeiro registro
			//result.first();	//aponta o cursor para a primeira linha para então itera-lo
			List<Produto> produtos = new ArrayList<Produto>();
			Produto p;

			while(result.next()){
				p = new Produto();
				p.setId(result.getInt(ID));
				p.setCodBarra(result.getString(COD_BARRAS));
				p.setFornecedor(result.getString(FORNECEDOR));
				p.setDescricao(result.getString(DESCRICAO));
				p.setPCusto(result.getDouble(PCUSTO));
				p.setMargem(result.getDouble(MARGEM));
				p.setPVenda(result.getDouble(PVENDA));
				p.setEstoque(result.getInt(SALDO));
				p.setEstat1(result.getInt(ESTAT1));
				p.setEstat2(result.getInt(ESTAT2));
				p.setEstat3(result.getInt(ESTAT3));
				p.setQtdCpa(result.getInt(QTD_CPA));

				produtos.add(p);
			}
			return produtos;
		}catch(SQLException e){
			e.printStackTrace();
			Log.criarLogErro(e);
			return null;
		}finally{
			try {
				result.close();		//fecha o ResultSet
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	public List<Produto> getProdutosFromTxt(File fileName){
		boolean achouTabela = false;

		List<Produto> produtosTxt = new ArrayList<Produto>();
		String bancoTxt = "";
		int qtdCampos = 13;
		try{
			bancoTxt = Utilitario.lerArquivoTxt(fileName.getAbsolutePath());	//retorna o banco 'angulo.txt' em 1 String
		}catch(IOException e){
			Log.criarLogErro(e);  //Gravando o log de erro em C:/importa/erros.log
			JOptionPane.showMessageDialog(null, "Banco angulo.txt não encontrado! Verifique se este arquivo está em '" + origem.getAbsolutePath() + "'" );
			e.printStackTrace();
		}
		String[] bancoTxtSeparado = Utilitario.splitTxt(bancoTxt);	//separa o banco 'angulo.txt' em linhas	
		Produto p;

		for (int i=0; i<bancoTxtSeparado.length; i++){
			//String[] linhaSplited = Utilitario.splitTab(bancoTxtSeparado[i], (qtdCampos + 1));
			String[] tabela = bancoTxtSeparado[i].split("\\t");//cria array de String com apenas um campo (o numero da tabela)

			if(tabela.length<=0)	//se a linha não conter nenhum campo, então o laço é iterado e a próxima linha é verificada
				continue;

			if(getInteger(tabela[0]) == Banco.PRODUTO){
				/*
				String[] linhaBuscandoTabela = bancoTxtSeparado[i].split("\\t");
				if(! achouTabela)
					if(linhaBuscandoTabela.length == 2){
						nomeTabelaCliente = "Produto_" + linhaBuscandoTabela[1];	//guardou
						achouTabela = true;
					}
				*/

				String[] linhaSplited = Utilitario.splitTab(bancoTxtSeparado[i], qtdCampos);	//cria array de Strings com 12 campos fixos(mesmo que em determinada linha do arquivo angulo.txt não contenha os 12 campos, isso evita Exception ArrayIndexOutOfBoundsException)
				p = new Produto();
				p.setId(getInteger(linhaSplited[1]));
				p.setCodBarra(linhaSplited[2]);
				p.setFornecedor(linhaSplited[3]);
				p.setDescricao(linhaSplited[4]);
				p.setPCusto(getDouble(linhaSplited[5]));
				p.setMargem(getDouble(linhaSplited[6]));
				p.setPVenda(getDouble(linhaSplited[7]));
				p.setEstoque(getInteger(linhaSplited[8]));
				p.setEstat1(getInteger(linhaSplited[9]));
				p.setEstat2(getInteger(linhaSplited[10]));
				p.setEstat3(getInteger(linhaSplited[11]));
				p.setQtdCpa(getInteger(linhaSplited[12]));

				produtosTxt.add(p);	//adiciona Produto à lista de Produtos
			}

		}//fim do for principal
		return produtosTxt;	//retorna a lista completa de Produtos do banco angulo.txt
	}


	public boolean verificaAlteracao(Produto p1, Produto p2){
		if(p1.getCodBarra().equalsIgnoreCase(p2.getCodBarra()))
			if(p1.getFornecedor().equalsIgnoreCase(p2.getFornecedor()))
				if(p1.getDescricao().equalsIgnoreCase(p2.getDescricao()))
					if(p1.getPCusto().equals(p2.getPCusto()))
						if(p1.getMargem().equals(p2.getMargem()))
							if(p1.getPVenda().equals(p2.getPVenda()))
								if(p1.getEstoque() == p2.getEstoque())
									if(p1.getEstat1() == p2.getEstat1())
										if(p1.getEstat2() == p2.getEstat2())
											if(p1.getEstat3() == p2.getEstat3())
												if(p1.getQtdCpa() == p2.getQtdCpa())
													return false;	//objetos Produto são iguais

		return true;	//caso alguma das comparações acima for falsa, então estes produtos SÃO diferentes
	}

	public int getCountAtualizados(){
		return this.countAtualizados;
	}

}


/*
	public void montaQuery(PreparedStatement pstmt,String campo, int j){
		try{
			if (campo != null){
				switch(j){
				case 0:
					//System.out.println("id: " + campo);
					pstmt.setString(j+1, campo);
					break;
				case 1:
					//System.out.println("codBarra: " + campo);
					pstmt.setString(j+1, campo);
					break;
				case 2:
					//System.out.println("Descricao: " + campo);
					pstmt.setString(j+1, campo);
					break;
				case 3:
					//System.out.println("Fornecedor: " + campo);
					pstmt.setString(j+1, campo);
					break;
				case 4:
					//System.out.println("PCusto: " + campo);
					if (Utilitario.isInteger(campo))	//Verifica se o campo está no formato de número, caso contrário seta vator 1 no campo
						pstmt.setDouble(j+1, Double.parseDouble(campo));
					else
						pstmt.setInt(j+1, 0);
					break;
				case 5:
					//System.out.println("Margem: " + campo);
					if (Utilitario.isInteger(campo))	//Verifica se o campo está no formato de número, caso contrário seta vator 1 no campo
						pstmt.setDouble(j+1, Double.parseDouble(campo));
					else
						pstmt.setInt(j+1, 0);
					break;
				case 6:
					//System.out.println("PVenda: " + campo);
					if (Utilitario.isInteger(campo))	//Verifica se o campo está no formato de número, caso contrário seta vator 1 no campo
						pstmt.setDouble(j+1, Double.parseDouble(campo));
					else
						pstmt.setInt(j+1, 0);
					break;
				case 7:
					//System.out.println("Estoque: " + campo);
					if (Utilitario.isInteger(campo))	//Verifica se o campo está no formato de número, caso contrário seta vator 1 no campo
						pstmt.setInt(j+1, Integer.parseInt(campo));
					else
						pstmt.setInt(j+1, 0);
					break;
				case 8:
					//System.out.println("Estat1: " + campo);
					if (Utilitario.isInteger(campo))	//Verifica se o campo está no formato de número, caso contrário seta vator 1 no campo
						pstmt.setInt(j+1, Integer.parseInt(campo));
					else
						pstmt.setInt(j+1, 0);
					break;
				case 9:
					//System.out.println("Estat2: " + campo);
					if (Utilitario.isInteger(campo))	//Verifica se o campo está no formato de número, caso contrário seta vator 1 no campo
						pstmt.setInt(j+1, Integer.parseInt(campo));
					else
						pstmt.setInt(j+1, 0);
					break;
				case 10:
					//System.out.println("Estat3: " + campo);
					if (Utilitario.isInteger(campo))	//Verifica se o campo está no formato de número, caso contrário seta vator 1 no campo
						pstmt.setInt(j+1, Integer.parseInt(campo));
					else
						pstmt.setInt(j+1, 0);
					break;
				case 11:
					//System.out.println("Levanta: " + campo);
					if (Utilitario.isInteger(campo))	//Verifica se o campo está no formato de número, caso contrário seta vator 1 no campo
						pstmt.setInt(j+1, Integer.parseInt(campo));
					else
						pstmt.setInt(j+1, 0);
					break;
				case 12:
					//System.out.println("QtdCpa: " + campo);
					if (Utilitario.isInteger(campo))	//Verifica se o campo está no formato de número, caso contrário seta vator 1 no campo
						pstmt.setInt(j+1, Integer.parseInt(campo));
					else
						pstmt.setInt(j+1, 0);
					break;
				}
			}else{
				//System.out.println("setando inteiro nulo com zero " + 0);
				pstmt.setInt(j+1, 0);
			}
		}catch(SQLException e){
			Log.criarLogErro(e);  //Gravando o log de erro em C:/importa/erros.log
			System.out.println("Erro na montagem das queries");
			e.printStackTrace();
			Log.criarLogErro(e);  //Gravando o log de erro em C:/importa/erros.log
		}catch(NumberFormatException e){
			Log.criarLogErro(e);  //Gravando o log de erro em C:/importa/erros.log
			JOptionPane.showMessageDialog(null, "Erro na formatação de Número, valor erroneamente atribuído como " + campo);
			System.out.println("Erro na formatação de Número, valor erroneamente atribuído como " + campo);
		}

	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Montando os Produtos atualizados para mostrar ao usuário
	public List<Produto> montaProduto(List<Produto> produtos, List<int[]> resultados, String[] bancoTxtSeparado, int qtdCampos){
		if(! resultados.isEmpty()){
			int indice;
			for (int i=0; i<resultados.size(); i++){ //ArrayList com todos os Arrays contendo os códigos de retorno
				int[] linhaAtualizada = resultados.get(i);
				for (int j=0; j<linhaAtualizada.length; j++){
					if (linhaAtualizada[j] == 2){
						//System.out.println("Código resultante " + linhaAtualizada[j]);14
						try{
							indice = ((1000*i) + j);

							countAtualizados++;
							System.out.println("criando primeiro Produto atualizado");
							System.out.println("Indice do ArrayList: " + i );
							System.out.println("Indice do Vetor Int que está no ArrayList: " + j);
							System.out.println("Indice Universal para a tabela: " + indice);
							Produto p =  new Produto();

							String[] linhaSplited = new String[qtdCampos];
							linhaSplited = Utilitario.splitTab(bancoTxtSeparado[indice]);

							p.setId(getInteger(linhaSplited[0]));
							//System.out.println("Setou campo ID");
							p.setCodBarra(linhaSplited[1]);
							//System.out.println("Setou campo CodBarra");
							p.setDescricao(linhaSplited[2]);
							//System.out.println("Setou campo Descricao");
							p.setFornecedor(linhaSplited[3]);
							//System.out.println("Setou campo Fornecedor");
							if (Utilitario.isInteger(linhaSplited[4]))
								p.setPCusto(Double.parseDouble(linhaSplited[4]));
							else
								p.setPCusto(0.0);
							//System.out.println("Setou campo Preco de Custo");
							if (Utilitario.isInteger(linhaSplited[5]))
								p.setMargem(Double.parseDouble(linhaSplited[5]));
							else
								p.setMargem(0.0);
							//System.out.println("Setou campo Margem");
							if (Utilitario.isInteger(linhaSplited[6]))
								p.setPVenda(Double.parseDouble(linhaSplited[6]));
							else
								p.setPVenda(0.0);
							//System.out.println("Setou campo Preco de Venda");
							if (Utilitario.isInteger(linhaSplited[7]))
								p.setEstoque(Integer.parseInt(linhaSplited[7]));
							else
								p.setEstoque(0);
							//System.out.println("Setou campo Estoque");
							if (Utilitario.isInteger(linhaSplited[8]))
								p.setEstat1(Integer.parseInt(linhaSplited[8]));
							else
								p.setEstat1(0);
							//System.out.println("Setou Estatistica 1");
							if (Utilitario.isInteger(linhaSplited[9]))
								p.setEstat2(Integer.parseInt(linhaSplited[9]));
							else
								p.setEstat2(0);
							//System.out.println("Setou Estatistica 2");
							if (Utilitario.isInteger(linhaSplited[10]))
								p.setEstat3(Integer.parseInt(linhaSplited[10]));
							else
								p.setEstat3(0);
							//System.out.println("Setou Estatistica 3");
							/*p.setLogin(linhaSplited[11]);
							System.out.println("Setou 12 campos no objeto");
							p.setLevanta(Integer.parseInt(linhaSplited[12]));
							System.out.println("Setou 13 campos no objeto");
							p.setQtdCpa(Integer.parseInt(linhaSplited[13]));
							System.out.println("Setou 14 campos no objeto");

							produtos.add(p);//fim do if
						}catch(IndexOutOfBoundsException e){
							e.printStackTrace();
							Log.criarLogErro(e);  //Gravando o log de erro em C:/importa/erros.log
						}
					}//fim do if
				}//fim do for
			}//fim do for
		}//fim do if
		return produtos;
	}
 */


/*
 //________________________________________________________________________________________________________________________
	public List<Produto> importData(File filename, String tabela){
		countAtualizados = 0;	//zerando esta variável para cada vez que apertar o botão 'Processar' ela recomeçar a contagem
		List<Produto> produtos = new ArrayList<Produto>();
		List<int[]> resultados = new ArrayList<int[]>();	//esta lista de Array guarda TODOS os códigos resultantes das querys executadas, para assim saber quais delas mostrar ao usuário
		int qtdCampos = 13;
		PreparedStatement pstmt;
		String bancoTxt = null;
		String queryReplace = "REPLACE into " + tabela + " (id,codbarra,descricao,fornecedor,pcusto,margem,pvenda,estoque,estat1,estat2,estat3,levanta,qtdcpa) " +
				"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try{
			bancoTxt = Utilitario.lerArquivoTxt(filename.toString());	//retorna o banco 'angulo.txt' em 1 String
		}catch(IOException e){
			Log.criarLogErro(e);  //Gravando o log de erro em C:/importa/erros.log
			JOptionPane.showMessageDialog(null, "Banco angulo.txt não encontrado! Verifique se este arquivo está em 'C:/importa/angulo.txt'");
			e.printStackTrace();
		}
		String[] bancoTxtSeparado = Utilitario.splitTxt(bancoTxt);	//separa o banco 'angulo.txt' em linhas		
		System.out.println("Final da importação do banco angulo.txt");

		try {
			long startTimeAtAll = System.currentTimeMillis();
			long startTime = System.currentTimeMillis();
			conexao.setAutoCommit(false);
			pstmt = conexao.prepareStatement(queryReplace);
			for (int i=0; i<bancoTxtSeparado.length; i++){
				String[] linhaSplited = Utilitario.splitTab(bancoTxtSeparado[i], qtdCampos);
				for (int j=0; j<qtdCampos; j++){ //seta campo a campo da linha no PreparedStatement
					montaQuery(pstmt, linhaSplited[j], j);
				}
				pstmt.addBatch();
				//System.out.println("pstmt.addBatch() " + (i + 1));
				if ((i+1) % 1000 == 0){	//i+1 pois 'i' começa a partir do 0 mas deve contar a partir do 1
					System.out.println("pstmt.executeBatch()...");
					int[] count = pstmt.executeBatch();
					//capiturando Array com códigos da execução de cada query e guardando-os para futuramente mostrar apenas as linhas atualizadas
					resultados.add(count);
					System.out.println("Realizando REPLACE de 1000 registros... em " + (System.currentTimeMillis() - startTime)/1000 + " segundos");
					startTime = System.currentTimeMillis();

				}
			}
			int[] count = pstmt.executeBatch();
			System.out.println("Realizando REPLACE dos últimos registros... em " + (System.currentTimeMillis() - startTime)/1000 + " segundos");
			resultados.add(count);
			conexao.commit();
			//Fechando as conexões
			pstmt.close();
			conexao.close();
			long endTime = System.currentTimeMillis();
			long elapsedTime = (endTime - startTimeAtAll)/1000;

			/*
			if (resultados.isEmpty()){
				JOptionPane.showMessageDialog(null, "Sem conexão com o Banco de Dados Web, verifique sua conexão!");
				System.exit(0); //Fechando a aplicação
			}


			System.out.println("Tempo total para executar TODAS as queries: " + elapsedTime);
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Montando e recuperando os Produtos atualizados
			produtos = montaProduto(produtos,resultados,bancoTxtSeparado,qtdCampos);

		} catch (SQLException e) {

			Log.criarLogErro(e);  //Gravando o log de erro em C:/importa/erros.log
			System.out.println("Excecao de SQL em ProdutoDAO.importData(): " + e);
			JOptionPane.showMessageDialog(null, "Erro de Consulta SQL, você indicou o caminho correto do arquivo 'angulo.txt'?");
			MainClass.areaStatus.setText("");
			Log.criarLogErro(e);
			e.printStackTrace();
			//e.printStackTrace(new PrintWriter(Log.stackTrace = new StringWriter()));
			//Log.criarLog();
			//e.printStackTrace();
		} catch (NullPointerException e){
			Log.criarLogErro(e);  //Gravando o log de erro em C:/importa/erros.log
			//Log.criarLog(e.getStackTrace().toString(),e.getStackTrace());
			//System.out.println("Excecao de fonte nula em ProdutoDAO.importData(): " + e);
			//TelaProcessaBanco.areaStatus.setText("");
		} catch (ArrayIndexOutOfBoundsException e){
			Log.criarLogErro(e);  //Gravando o log de erro em C:/importa/erros.log
			//Log.criarLog(e.getStackTrace().toString(),e.getStackTrace());
			//System.out.println("Exceção de estouro de array na montagem e envio das queries: " + e);
			//TelaProcessaBanco.areaStatus.setText("");
		} finally{

		}
		return produtos;
	}
 /*


}

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
/*String queryLoadData = "LOAD DATA LOCAL INFILE '" + filename + "' \n" +
" REPLACE into TABLE produto (id,codbarra,descricao,fornecedor,pcusto,margem,pvenda,estoque,estat1,estat2,estat3,login,levanta,qtdcpa);";

stmt = this.conexao.createStatement();
resultSet = stmt.executeQuery(queryLoadData);
 */

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
/*
String queryDuplicate = "INSERT into produto (id,codbarra,descricao,fornecedor,pcusto,margem,pvenda,estoque,estat1,estat2,estat3,login,levanta,qtdcpa) " +
		"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?) " +
		"On DUPLICATE Key UPDATE id=VALUES(id), codbarra=VALUES(codbarra), descricao=VALUES(descricao),fornecedor=VALUES(descricao), pcusto=VALUES(pcusto), margem=VALUES(margem), pvenda=VALUES(pvenda), estoque=VALUES(estoque), estat1=VALUES(estat1), estat2=VALUES(estat2), estat3=VALUES(estat3), login=VALUES(login), levanta=VALUES(levanta), qtdcpa=VALUES(qtdcpa)";
 */
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
/*
String queryDuplicate = "INSERT into produto (id,codbarra,descricao,fornecedor,pcusto,margem,pvenda,estoque,estat1,estat2,estat3,login,levanta,qtdcpa) " +
				"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?) \n" +
				"On DUPLICATE Key UPDATE id=VALUES(id), codbarra=VALUES(codbarra), descricao=VALUES(descricao),fornecedor=VALUES(descricao), pcusto=VALUES(pcusto), margem=VALUES(margem), pvenda=VALUES(pvenda), estoque=VALUES(estoque), estat1=VALUES(estat1), estat2=VALUES(estat2), estat3=VALUES(estat3), login=VALUES(login), levanta=VALUES(levanta), qtdcpa=VALUES(qtdcpa)";

pstmt = conexao.prepareStatement(queryDuplicate);
for (int i=1; i<bancoTxtSeparado.length; i++){
	String[] linhaSplited2 = new String[14];
	//String linhaSplited[] = ProdutoDAO.splitTab(bancoTxtSeparado[i]);	//Array[] linhaSplited recebe os campos de uma linha
	linhaSplited2 = ProdutoDAO.splitTab(bancoTxtSeparado[i]);
	//System.out.println(bancoTxtSeparado[i]);
	for (int j=0; j<linhaSplited2.length; j++){	//seta campo a campo da linha no PreparedStatement
		if (linhaSplited2[j] == null)
			pstmt.setString(j+1, "0");
		pstmt.setString(j+1, linhaSplited2[j]);
		//System.out.println(linhaSplited[j]);
	}
	pstmt.addBatch();

	if ((i+1) % 1000 == 0){
		int[] count = pstmt.executeBatch();
		//capiturando Array com códigos da execução de cada query e guardando-os para futuramente mostrar apenas as linhas atualizadas
		resultados.add(count);

	}
}
int[] count = pstmt.executeBatch();
resultados.add(count);
 */
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


