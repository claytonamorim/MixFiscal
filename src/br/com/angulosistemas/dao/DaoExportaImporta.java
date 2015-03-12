package br.com.angulosistemas.dao;

import static br.com.angulo.sistemas.Configuracao.delay;
import static br.com.angulo.sistemas.Configuracao.origem;
import static br.com.angulo.sistemas.Configuracao.qtdeLinhas;
import static br.com.angulo.sistemas.Utilitario.delay;

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
import br.com.angulo.sistemas.Utilitario;
import br.com.angulo.sistemas.gui.ProgressBarDemo;
import br.com.angulo.sistemas.reflection.DaoReflection;

public class DaoExportaImporta {
	private Connection conexao;
	private int countAtualizados;	
	private BeanExportaImporta beanExportaImporta;
	
	public DaoExportaImporta(BeanExportaImporta novoBean){
		this.conexao = new ConnectionFactory().getConnection();
		this.beanExportaImporta = novoBean;	//dependency injection
		
		System.out.println("Contrutor DaoExportaImporta() ... classe=" + novoBean.getClass());
	}
	
	//+++++ Design Pattern Dependency Injection ++++++++
	public void setBeanExportaImporta(BeanExportaImporta novoBean){
		this.beanExportaImporta = novoBean;
	}
	
	public List<BeanExportaImporta> enviarItensAtualizados(){
		ResultSet result =  null;										//Produtos do banco onLine
		List<BeanExportaImporta> itensTxt;
		List<BeanExportaImporta> itensAtualizados;
		itensTxt = new ArrayList<BeanExportaImporta>();			//Produtos do banco angulo.txt
		itensAtualizados = new ArrayList<BeanExportaImporta>();	//lista com apenas os produtos que foram alterados no banco angulo.txt em relação ao banco onLine

		//		Carregando lista de Produtos do banco onLine em ResultSet

		executarTruncateTable(); //limpa a tabela antes de inserir novos registros
		executarCreateTable();
		String query = "Select * from " + beanExportaImporta.getTableName();

		try {
			Statement stmt = conexao.createStatement();
			result = stmt.executeQuery(query);
		}catch (SQLException e) {
			e.printStackTrace();
			Log.criarLogErro(e);
			JOptionPane.showMessageDialog(null, "Não foi possível se comunicar com o banco web. Verifique sua conexão com a Internet");
		}

		//		Pegando lista de Produtos do banco txt em List<Produto>
		itensTxt = getItensFromTxt(origem);	//"origem" é o local do txt com o csv da tabela
		System.out.println("Qtde de itens no banco angulo.txt:" + itensTxt.size());
		try{
			BeanExportaImporta iTxt;
			//____Pegando a quantidade de linhas na ResultSet
			result.last();
			int qtdeRes=result.getRow();
			result.first();
			System.out.println("Qtde da ResultSet:" + qtdeRes);
			//________________________________________________ 

			if(qtdeRes == 0){
				System.out.println("Banco Web está vazio, realizando upload de TODOS os registros do banco angulo.txt...");
				itensAtualizados = itensTxt;

			}else{
				loopPrincipal:									//nomeando o loop principal
					for (int i=0; i<itensTxt.size(); i++){
						iTxt = (BeanExportaImporta)itensTxt.get(i);				//1 produto do banco angulo.txt para comparação
						result.first();							//retorna ao primeiro item do resultSet a cada início de comparacao
						for(int j=0; j<qtdeRes; j++)	{	//vai comparar este produto do banco angulo com "cada" produto do banco onLine(ResultSet)
							if (iTxt.getCodigoProduto() == (result.getInt("codigo_produto"))){
								boolean verifica = verificaAlteracao(iTxt ,getItemFromResultSet(result));
								if(verifica){
									itensAtualizados.add(iTxt);	//produto encontrado e possui alterações (UPDATE)
								}
								continue loopPrincipal;			//como o produto foi encontrado, então após realizadas as tarefas de comparação, o laço é pulado para o próximo item
							}	
							result.next();					//pulado para o próximo item para comparação
						}//fim do FOR
						itensAtualizados.add(iTxt);		//verifica se a ResultSet chegou ao último objeto produto, e, neste caso onde chegou-se ao final mas não encontrou-se o Produto, significa se tratar de um novo produto, INSERT)	
					}	//fim de TODAS as comparaçoes
			}

			countAtualizados = itensAtualizados.size();
			System.out.println("Qtde de produtos a serem atualizados:" + countAtualizados);
			executarUpdateQuery(itensAtualizados);	//chamando método que envia de fato os produtos novos e alterados para o banco onLine
			return itensAtualizados;		//Retornando lista de produtos novos e alterados (INSERT into table ON DUPLICATE KEY UPDATE), para posteriormente ser montada tabela a ser mostrada ao usuário

		}catch(Exception e){
			e.printStackTrace();
			Log.criarLogErro(e);
			return null;
		}finally{
			try {
				result.close();  //fechando a ResultSet
				conexao.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	

	public void executarCreateTable(){
		String queryCreateTable = beanExportaImporta.createTableQuery();
		Statement statement;
		try {
			statement = conexao.createStatement();
			statement.execute(queryCreateTable);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void executarTruncateTable(){
		String queryTruncateTable = "truncate " + beanExportaImporta.getTableName();
		Statement statement;
		try{
			statement = conexao.createStatement();
			statement.execute(queryTruncateTable);
		}catch(SQLException e){
			e.printStackTrace();
			
		}
	}

	public void executarUpdateQuery(List<BeanExportaImporta> itensAtualizados){
		executarCreateTable();	//aqui é criada a tabela no banco web, caso já exista nada acontece
		executarTruncateTable(); //limpa a tabela antes de inserir novos registros
		
		PreparedStatement pstmt = null;
		String queryReplace = beanExportaImporta.createReplaceQuery();

		try {
			long startTimeAtAll = System.currentTimeMillis();
			long startTime = System.currentTimeMillis();
			conexao.setAutoCommit(false);	//desabilita commit automático, enviará apenas quando alcançar quantidade estipulada na variavel "qtdeLinhas"
			pstmt = conexao.prepareStatement(queryReplace);
			new ProgressBarDemo();
			
			for (int j=0; j<itensAtualizados.size(); j++){	//percorrerá cada Produto da Lista de Produtos atualizados, carregando-los no PreparedStatement
				//Object i = itensAtualizados.get(j);
				BeanExportaImporta i = (BeanExportaImporta) itensAtualizados.get(j);
				
				//++++++++++++ Utilizando Classe Reflection +++++++++++++++++++++++++++++++++++++
				DaoReflection.carregarPreparedStatement(i, pstmt, beanExportaImporta.getClass());	//Deveria retornar a propriedade .class da bean
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				pstmt.addBatch();
				
				if ((j+1) % qtdeLinhas == 0){		//i+1 pois 'i' começa a partir do 0 mas deve contar a partir do 1
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
			long endTime = System.currentTimeMillis();
			long elapsedTime = (endTime - startTimeAtAll)/1000;
			System.out.println("Tempo total para executar TODAS as queries: " + elapsedTime);

		}catch(Exception e){
			e.printStackTrace();
			Log.criarLogErro(e);
		}finally{
			try{
			//Fechando as conexões
			pstmt.close();
			conexao.close();
			}catch(Exception e){
				
			}
		}
	}


	public Object getItemFromResultSet(ResultSet result){
		Object obj;
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		obj = DaoReflection.carregarDoResultSet(result, beanExportaImporta.getClass());
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		return obj;
	}

	public List<Object> getItensFromResultSet(ResultSet result){
		try{
			result.beforeFirst(); //aponta o cursor para uma linha antes da primeira, pois ao entrar no loop na primeira vez já é chamado o método next e o cursor irá para o primeiro registro
			List<Object> itens = new ArrayList<Object>();
			Object obj;
			
			while(result.next()){
				obj = new Object();
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				obj = DaoReflection.carregarDoResultSet(result, beanExportaImporta.getClass());
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				itens.add((BeanExportaImporta)obj);
			}
			
			return itens;
			
		}catch(SQLException e){
			e.printStackTrace();
			Log.criarLogErro(e);
			return null;
		}
	}


	public List<BeanExportaImporta> getItensFromTxt(File fileName){
		BeanExportaImporta i;
		List<BeanExportaImporta> itensTxt = null;
		//BeanExportaImporta itensTxt;
		try{
		itensTxt = new ArrayList<BeanExportaImporta>();
		
		String bancoTxt = "";
		int qtdCampos = beanExportaImporta.getQtdeCampos();
		try{
			bancoTxt = Utilitario.lerArquivoTxt(fileName.getAbsolutePath());	//retorna o banco 'angulo.txt' em 1 String
		}catch(IOException e){
			Log.criarLogErro(e);  //Gravando o log de erro em C:/importa/erros.log
			JOptionPane.showMessageDialog(null, "Banco angulo.txt não encontrado! Verifique se este arquivo está em '" + origem.getAbsolutePath() + "'" );
			e.printStackTrace();
		}
		String[] bancoTxtSeparado = Utilitario.splitTxt(bancoTxt);	//separa o banco 'angulo.txt' em linhas	

		for (int j=0; j<bancoTxtSeparado.length; j++){
			
			String[] tabela = bancoTxtSeparado[j].split("\\t");//cria array de String com apenas um campo (o numero da tabela)

			if(tabela.length<=0)	//se a linha não conter nenhum campo, então o laço é iterado e a próxima linha é verificada
				continue;

			String[] linhaSplited = Utilitario.splitTab(bancoTxtSeparado[j], qtdCampos);	//cria array de Strings com campos fixos(mesmo que em determinada linha do arquivo angulo.txt não contenha os campos, isso evita Exception ArrayIndexOutOfBoundsException)
			i = beanExportaImporta.getClass().newInstance();
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			DaoReflection.getItensFromTxt(i, linhaSplited, beanExportaImporta.getClass());
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			itensTxt.add(i);	//adiciona Produto à lista de Produtos

		}//fim do for principal
		}catch(Exception e){
			
		}
		return itensTxt;	//retorna a lista completa de Produtos do banco angulo.txt
	}
	
	public List<Object> getItensAtualizados(){
		ResultSet result =  null;										//Produtos do banco onLine
		List<Object> itensAtualizados = new ArrayList<Object>();	//lista com apenas os produtos que foram alterados no banco angulo.txt em relação ao banco onLine

		//Criando tabela, caso não exista...
		executarCreateTable();
		
		//Carregando lista de Produtos do banco onLine em ResultSet
		String query = "Select * from " + beanExportaImporta.getTableName();

		try {
			Statement stmt = conexao.createStatement();
			result = stmt.executeQuery(query);

			//Convertendo ResultSet em lista de objetos
			itensAtualizados = getItensFromResultSet(result);
			return itensAtualizados;
		}catch(Exception e){
			e.printStackTrace();
			Log.criarLogErro(e);
			return null;
		}finally{
			try {
				result.close();  //fechando a ResultSet
				conexao.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	public boolean verificaAlteracao(Object i1, Object i2){
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		return DaoReflection.verificaAlteracao(i1, i2, beanExportaImporta.getClass());
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	}

	public int getCountAtualizados(){
		return this.countAtualizados;
	}
}
