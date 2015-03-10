package br.com.angulosistemas.dao;

import static br.com.angulo.sistemas.Configuracao.delay;
import static br.com.angulo.sistemas.Configuracao.destino;
import static br.com.angulo.sistemas.Configuracao.login;
import static br.com.angulo.sistemas.Configuracao.origem;
import static br.com.angulo.sistemas.Configuracao.qtdeLinhas;
import static br.com.angulo.sistemas.Configuracao.tabela;
import static br.com.angulo.sistemas.Utilitario.delay;
import static br.com.angulo.sistemas.Utilitario.getDouble;
import static br.com.angulo.sistemas.Utilitario.getInteger;
import static br.com.angulo.sistemas.bean.Produto.ProdutoE.COD_BARRAS;
import static br.com.angulo.sistemas.bean.Produto.ProdutoE.DESCRICAO;
import static br.com.angulo.sistemas.bean.Produto.ProdutoE.ESTAT1;
import static br.com.angulo.sistemas.bean.Produto.ProdutoE.ESTAT2;
import static br.com.angulo.sistemas.bean.Produto.ProdutoE.ESTAT3;
import static br.com.angulo.sistemas.bean.Produto.ProdutoE.FORNECEDOR;
import static br.com.angulo.sistemas.bean.Produto.ProdutoE.ID;
import static br.com.angulo.sistemas.bean.Produto.ProdutoE.MARGEM;
import static br.com.angulo.sistemas.bean.Produto.ProdutoE.PCUSTO;
import static br.com.angulo.sistemas.bean.Produto.ProdutoE.PVENDA;
import static br.com.angulo.sistemas.bean.Produto.ProdutoE.QTD_CPA;
import static br.com.angulo.sistemas.bean.Produto.ProdutoE.SALDO;

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

import br.com.angulo.sistemas.Banco;
import br.com.angulo.sistemas.ConnectionFactory;
import br.com.angulo.sistemas.Log;
import br.com.angulo.sistemas.Utilitario;
import br.com.angulo.sistemas.bean.ICMS;
import br.com.angulo.sistemas.bean.Produto;
import br.com.angulo.sistemas.gui.ProgressBarDemo;
import br.com.angulo.sistemas.reflection.DaoReflection;

public class DaoICMS {
	private Connection conexao;
	private int countAtualizados;	

	public DaoICMS(){
		this.conexao = new ConnectionFactory().getConnection(destino + "/angulo");
	}

	//_________________________________________________________________________________________________________________

	public String createTableQuery(){
		System.out.println("nome da tabela concatenada=" + ICMS.TABELA_ICMS);

		StringBuilder builder = new StringBuilder();
		builder.append("CREATE TABLE IF NOT EXISTS `" + ICMS.TABELA_ICMS + "` (");
		builder.append("`codigo_produto` int(11) NOT NULL,");
		builder.append("'ean' int(6) NOT NULL,");
		builder.append("'tipo_mva' varchar(2) NULL,");
		builder.append("'mva' decimal(7,3),");
		builder.append("'sac_cst' varchar(3) NULL,");
		builder.append("'sac_alq' decimal(7,3),");
		builder.append("'sac_alqst' decimal(7,3),");
		builder.append("'sac_rbc' decimal(7,3),");
		builder.append("'sac_rbcst' decimal(7,3),");
		builder.append("'sas_cst' varchar(3),");
		builder.append("'sas_alq' decimal(7,3),");
		builder.append("'sas_alqst' decimal(7,3),");
		builder.append("'sas_rbc' decimal(7,3),");
		builder.append("'sas_rbcst' decimal(7,3),");
		builder.append("'svc_cst' varchar(3),");
		builder.append("'svc_alq' decimal(7,3),");
		builder.append("'svc_alqst' decimal(7,3),");
		builder.append("'svc_rbc' decimal(7,3),");
		builder.append("'svc_rbcst' decimal(7,3),");
		builder.append("'snc_cst' varchar(3),");
		builder.append("'snc_alq' decimal(7,3),");
		builder.append("'snc_alqst' decimal(7,3),");
		builder.append("'snc_rbc' decimal(7,3),");
		builder.append("'snc_rbcst' decimal(7,3)");
		
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

	public void executarUpdateQuery(List<ICMS> itensAtualizados){
		executarCreateTable();	//aqui é criada a tabela no banco web, caso já exista nada acontece

		PreparedStatement pstmt;
		String queryReplace = "REPLACE into " + ICMS.TABELA_ICMS + " (" +
				//String queryReplace = "REPLACE into " + tabela + " (" +
				"codigo_produto," +
				"ean," +
				"tipo_mva," +
				"mva," +
				"sac_cst," +
				"sac_alq," +
				"sac_alqst," +
				"sac_rbc," +
				"sac_rbcst," +
				"sas_cst," +
				"sas_alq," +
				"sas_alqst," +
				"sas_rbc," +
				"sas_rbcst," +
				"svc_cst," +
				"svc_alq," +
				"svc_alqst," +
				"svc_rbc," +
				"svc_rbcst," +
				"snc_cst," +
				"snc_alq," +
				"snc_alqst," +
				"snc_rbc," +
				"snc_rbcst)" +
				"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try {
			long startTimeAtAll = System.currentTimeMillis();
			long startTime = System.currentTimeMillis();
			conexao.setAutoCommit(false);
			pstmt = conexao.prepareStatement(queryReplace);
			//new BarraProgresso();
			new ProgressBarDemo();
			//BarraProgresso.progressBar.setMinimum(0);
			//BarraProgresso.progressBar.setMaximum(produtosAtualizados.size());
			for (int j=0; j<itensAtualizados.size(); j++){	//percorrerá cada Produto da Lista de Produtos atualizados, carregando-los no PreparedStatement
				ICMS i = itensAtualizados.get(j);
				
				//Utilizando Classe Reflection
				DaoReflection.carregarPreparedStatement(i, pstmt, ICMS.class);
				
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


	public ICMS getItemFromResultSet(ResultSet result){
		ICMS i = new ICMS();
		try{
			//Carregando 1 objeto tipo Produto para adiciona-lo a uma lista e depois compara-los à lista de Produto do banco angulo.txt
			DaoReflection.carregarDoResultSet(i, result, ICMS.class);

		}catch(SQLException e){
			e.printStackTrace();
			Log.criarLogErro(e);  //Gravando o log de erro em C:/importa/erros.log
		}
		return i;
	}

	public List<ICMS> getItensFromResultSet(ResultSet result){
		try{
			result.beforeFirst(); //aponta o cursor para uma linha antes da primeira, pois ao entrar no loop na primeira vez já é chamado o método next e o cursor irá para o primeiro registro
			//result.first();	//aponta o cursor para a primeira linha para então itera-lo
			List<ICMS> itens = new ArrayList<ICMS>();
			ICMS i;

			while(result.next()){
				i = new ICMS();
				i.setCodigo_produto(result.getInt(0));
				i.setEan(result.getInt(1));
				i.setTipo_mva(result.getString(2));
				i.setMva(result.getDouble(3));
				i.setSac_cst(result.getString(4));
				i.setSac_alq(result.getDouble(5));
				i.setSac_alqst(result.getDouble(6));
				i.setSac_rbc(result.getDouble(7));
				i.setSac_rbcst(result.getDouble(8));
				i.setSas_cst(result.getString(9));
				i.setSas_alq(result.getDouble(10));
				i.setSas_alqst(result.getDouble(11));
				i.setSas_rbc(result.getDouble(12));
				i.setSas_rbcst(result.getDouble(13));
				i.setSvc_cst(result.getString(14));
				i.setSvc_alq(result.getDouble(15));
				i.setSvc_alqst(result.getDouble(16));
				i.setSvc_rbc(result.getDouble(17));
				i.setSvc_rbcst(result.getDouble(18));
				i.setSnc_cst(result.getString(19));
				i.setSnc_alq(result.getDouble(20));
				i.setSnc_alqst(result.getDouble(21));
				i.setSnc_rbc(result.getDouble(22));
				i.setSnc_rbcst(result.getDouble(23));
				
				itens.add(i);
			}
			return itens;
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


	public List<ICMS> getItensFromTxt(File fileName){
		boolean achouTabela = false;

		List<ICMS> itensTxt = new ArrayList<ICMS>();
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
		ICMS i;

		for (int j=0; j<bancoTxtSeparado.length; j++){
			//String[] linhaSplited = Utilitario.splitTab(bancoTxtSeparado[i], (qtdCampos + 1));
			String[] tabela = bancoTxtSeparado[j].split("\\t");//cria array de String com apenas um campo (o numero da tabela)

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

				String[] linhaSplited = Utilitario.splitTab(bancoTxtSeparado[j], qtdCampos);	//cria array de Strings com 12 campos fixos(mesmo que em determinada linha do arquivo angulo.txt não contenha os 12 campos, isso evita Exception ArrayIndexOutOfBoundsException)
				i = new ICMS();
				i.setCodigo_produto(getInteger(linhaSplited[1]));
				i.setEan(getInteger(linhaSplited[2]));
				i.setTipo_mva(linhaSplited[3]);
				i.setMva(getDouble(linhaSplited[4]));
				i.setSac_cst(linhaSplited[5]);
				i.setSac_alq(getDouble(linhaSplited[6]));
				i.setSac_alqst(getDouble(linhaSplited[7]));
				i.setSac_rbc(getDouble(linhaSplited[8]));
				i.setSac_rbcst(getDouble(linhaSplited[9]));
				i.setSas_cst(linhaSplited[10]);
				i.setSas_alq(getDouble(linhaSplited[11]));
				i.setSas_alqst(getDouble(linhaSplited[12]));
				i.setSas_rbc(getDouble(linhaSplited[13]));
				i.setSas_rbcst(getDouble(linhaSplited[14]));
				i.setSvc_cst(linhaSplited[15]);
				i.setSvc_alq(getDouble(linhaSplited[16]));
				i.setSvc_alqst(getDouble(linhaSplited[17]));
				i.setSvc_rbc(getDouble(linhaSplited[18]));
				i.setSvc_rbcst(getDouble(linhaSplited[19]));
				i.setSnc_cst(linhaSplited[20]);
				i.setSnc_alq(getDouble(linhaSplited[21]));
				i.setSnc_alqst(getDouble(linhaSplited[22]));
				i.setSnc_rbc(getDouble(linhaSplited[23]));
				i.setSnc_rbcst(getDouble(linhaSplited[24]));

				itensTxt.add(i);	//adiciona Produto à lista de Produtos
			}

		}//fim do for principal
		return itensTxt;	//retorna a lista completa de Produtos do banco angulo.txt
	}


	public boolean verificaAlteracao(ICMS i1, ICMS i2){
		return DaoReflection.verificaAlteracao(i1, i2, ICMS.class);
	}

	public int getCountAtualizados(){
		return this.countAtualizados;
	}

}
