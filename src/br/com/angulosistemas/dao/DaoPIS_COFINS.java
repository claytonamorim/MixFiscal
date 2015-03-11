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
import br.com.angulo.sistemas.bean.PIS_COFINS;
import br.com.angulo.sistemas.gui.ProgressBarDemo;
import br.com.angulo.sistemas.reflection.DaoReflection;

public class DaoPIS_COFINS {
	private Connection conexao;
	private int countAtualizados;	
	//private String nomeTabelaCliente;

	public DaoPIS_COFINS(){
		this.conexao = new ConnectionFactory().getConnection();
	}
	
	public List<PIS_COFINS> enviarItensAtualizados(){
		ResultSet result =  null;										//Produtos do banco onLine
		List<PIS_COFINS> itensTxt = new ArrayList<PIS_COFINS>();			//Produtos do banco angulo.txt
		List<PIS_COFINS> itensAtualizados = new ArrayList<PIS_COFINS>();	//lista com apenas os produtos que foram alterados no banco angulo.txt em relação ao banco onLine

		//		Carregando lista de Produtos do banco onLine em ResultSet

		//String query = "Select * from " + tabela;
		executarCreateTable();
		String query = "Select * from " + PIS_COFINS.TABELA_PIS_COFINS;

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
			PIS_COFINS iTxt;
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
						iTxt = itensTxt.get(i);				//1 produto do banco angulo.txt para comparação
						result.first();							//retorna ao primeiro item do resultSet a cada início de comparacao
						for(int j=0; j<qtdeRes; j++)	{	//vai comparar este produto do banco angulo com "cada" produto do banco onLine(ResultSet)
							if (iTxt.getCodigo_produto() == (result.getInt("codigo_produto"))){
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
	

	public String createTableQuery(){
		System.out.println("nome da tabela concatenada=" + PIS_COFINS.TABELA_PIS_COFINS);

		StringBuilder builder = new StringBuilder();
		builder.append("CREATE TABLE IF NOT EXISTS `" + PIS_COFINS.TABELA_PIS_COFINS + "` (");
		builder.append("`codigo_produto` int(11) NOT NULL,");
		builder.append("'ean' int(16) NOT NULL,");
		builder.append("'descritivo_produto' varchar(255),");
		builder.append("'ncm' varchar(10),");
		builder.append("'ncm_ex' varchar(3) NULL,");
		builder.append("'cod_natureza_receita' int(4),");
		builder.append("'credito_presumido' int(1),");
		builder.append("'pis_cst_e' varchar(3),");
		builder.append("'pis_cst_s' varchar(3),");
		builder.append("'pis_alq_e' decimal(7,3),");
		builder.append("'pis_alq_s' decimal(7,3),");
		builder.append("'cofins_cst_e' varchar(3),");
		builder.append("'cofins_cst_s' varchar(3),");
		builder.append("'cofins_alq_e' decimal(7,3),");
		builder.append("'cofins_alq_s' decimal(7,3),");
		builder.append("'depto' varchar(50),");
		builder.append("'secao' varchar(50),");
		builder.append("'grupo' varchar(50),");
		builder.append("'subgrupo' varchar(50),");
		builder.append("'status' varchar(100)");
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

	public void executarUpdateQuery(List<PIS_COFINS> itensAtualizados){
		executarCreateTable();	//aqui é criada a tabela no banco web, caso já exista nada acontece

		PreparedStatement pstmt = null;
		String queryReplace = "REPLACE into " + PIS_COFINS.TABELA_PIS_COFINS + " (" +
				"codigo_produto," +
				"ean" +
				"descritivo_produto," +
				"ncm," +
				"ncm_ex," +
				"cod_natureza_receita," +
				"credito_presumido," +
				"pis_cst_e," +
				"pis_cst_s," +
				"pis_alq_e," +
				"pis_alq_s," +
				"cofins_cst_e," +
				"cofins_cst_s," +
				"cofins_alq_e," +
				"cofins_alq_s," +
				"depto," +
				"secao," +
				"grupo," +
				"subgrupo," +
				"status)" +
				"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try {
			long startTimeAtAll = System.currentTimeMillis();
			long startTime = System.currentTimeMillis();
			conexao.setAutoCommit(false);
			pstmt = conexao.prepareStatement(queryReplace);
			new ProgressBarDemo();
			
			for (int j=0; j<itensAtualizados.size(); j++){	//percorrerá cada Produto da Lista de Produtos atualizados, carregando-los no PreparedStatement
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				DaoReflection.carregarPreparedStatement(itensAtualizados.get(j), pstmt, PIS_COFINS.class);
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
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

	public PIS_COFINS getItemFromResultSet(ResultSet result){
		PIS_COFINS i = new PIS_COFINS();
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		DaoReflection.carregarDoResultSet(i, result, PIS_COFINS.class);
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		return i;
	}

	public List<PIS_COFINS> getItensFromResultSet(ResultSet result){
		try{
			result.beforeFirst(); //aponta o cursor para uma linha antes da primeira, pois ao entrar no loop na primeira vez já é chamado o método next e o cursor irá para o primeiro registro
			List<PIS_COFINS> itens = new ArrayList<PIS_COFINS>();
			PIS_COFINS i;

			while(result.next()){
				i = new PIS_COFINS();
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				DaoReflection.carregarDoResultSet(i, result, PIS_COFINS.class);
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				itens.add(i);
			}
			
			return itens;
			
		}catch(SQLException e){
			e.printStackTrace();
			Log.criarLogErro(e);
			return null;
		}
	}


	public List<PIS_COFINS> getItensFromTxt(File fileName){
		List<PIS_COFINS> itensTxt = new ArrayList<PIS_COFINS>();
		String bancoTxt = "";
		int qtdCampos = 20;
		try{
			bancoTxt = Utilitario.lerArquivoTxt(fileName.getAbsolutePath());	//retorna o banco 'angulo.txt' em 1 String
		}catch(IOException e){
			Log.criarLogErro(e);  //Gravando o log de erro em C:/importa/erros.log
			JOptionPane.showMessageDialog(null, "Banco angulo.txt não encontrado! Verifique se este arquivo está em '" + origem.getAbsolutePath() + "'" );
			e.printStackTrace();
		}
		String[] bancoTxtSeparado = Utilitario.splitTxt(bancoTxt);	//separa o banco 'angulo.txt' em linhas	
		PIS_COFINS i;

		for (int j=0; j<bancoTxtSeparado.length; j++){
			String[] tabela = bancoTxtSeparado[j].split("\\t");//cria array de String com apenas um campo (o numero da tabela)

			if(tabela.length<=0)	//se a linha não conter nenhum campo, então o laço é iterado e a próxima linha é verificada
				continue;

			String[] linhaSplited = Utilitario.splitTab(bancoTxtSeparado[j], qtdCampos);	//cria array de Strings com 12 campos fixos(mesmo que em determinada linha do arquivo angulo.txt não contenha os 12 campos, isso evita Exception ArrayIndexOutOfBoundsException)
			i = new PIS_COFINS();
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			DaoReflection.getItensFromTxt(i, linhaSplited, PIS_COFINS.class);
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			itensTxt.add(i);	//adiciona Produto à lista de Produtos
			
		}//fim do for principal
		return itensTxt;	//retorna a lista completa de Produtos do banco angulo.txt
	}


	public boolean verificaAlteracao(PIS_COFINS i1, PIS_COFINS i2){
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		return DaoReflection.verificaAlteracao(i1, i2, PIS_COFINS.class);	
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	}

	public int getCountAtualizados(){
		return this.countAtualizados;
	}

}
