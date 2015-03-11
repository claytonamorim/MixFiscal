package br.com.angulosistemas.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.angulo.sistemas.ConnectionFactory;
import br.com.angulo.sistemas.Log;
import br.com.angulo.sistemas.bean.Temp_ICMS_SAIDA;
import br.com.angulo.sistemas.reflection.DaoReflection;

public class DaoTemp_ICMS_SAIDA {
	private Connection conexao;
	private int countAtualizados;	

	public DaoTemp_ICMS_SAIDA(){
		this.conexao = new ConnectionFactory().getConnection();
	}

	public List<Temp_ICMS_SAIDA> getItensAtualizados(){
		ResultSet result =  null;										//Produtos do banco onLine
		List<Temp_ICMS_SAIDA> itensAtualizados = new ArrayList<Temp_ICMS_SAIDA>();	//lista com apenas os produtos que foram alterados no banco angulo.txt em relação ao banco onLine

		//Criando tabela, caso não exista...
		executarCreateTable();
		
		//Carregando lista de Produtos do banco onLine em ResultSet
		String query = "Select * from " + Temp_ICMS_SAIDA.TABELA_TEMP_ICMS_SAIDA;

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
	
	public String getNomeTabelaProduto(){
		return Temp_ICMS_SAIDA.TABELA_TEMP_ICMS_SAIDA;
	}

	public String createTableQuery(){
		System.out.println("nome da tabela concatenada=" + Temp_ICMS_SAIDA.TABELA_TEMP_ICMS_SAIDA);

		StringBuilder builder = new StringBuilder();
		builder.append("CREATE TABLE IF NOT EXISTS `" + Temp_ICMS_SAIDA.TABELA_TEMP_ICMS_SAIDA + "` (");
		builder.append("`codigo_produto` int(11) NOT NULL,");
		builder.append("'ean' int(16) NOT NULL,");
		builder.append("'sac_cst' varchar(3) NULL,");
		builder.append("'sac_alq' decimal(7,3),");
		builder.append("'sac_alqst' decimal(7,3),");
		builder.append("'sac_rbc' decimal(7,3),");
		builder.append("'sac_rbcst' decimal(7,3),");
		builder.append("'sas_cst' varchar(3),");
		builder.append("'sas_alq' decimal (7,3),");
		builder.append("'sas_alqst' decimal(7,3),");
		builder.append("'sas_rbc' decimal(7,3),");
		builder.append("'sas_rbcst' decimal(7,3),");
		builder.append("'svc_cst' varchar(3),");
		builder.append("'svc_alq' decimal(3),");
		builder.append("'svc_alqst' decimal(7,3),");
		builder.append("'svc_rbc' decimal(7,3),");
		builder.append("'svc_rbcst' decimal(7,3),");
		builder.append("'snc_cst' varchar(3),");
		builder.append("'snc_alq' decimal(7,3),");
		builder.append("'snc_alqst' decimal(7,3),");
		builder.append("'snc_rbc' decimal(7,3),");
		builder.append("'snc_rbcst' decimal(7,3),");
		builder.append("'fundamento_legal' varchar(500)");
		
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


	public Temp_ICMS_SAIDA getItemFromResultSet(ResultSet result){
		Temp_ICMS_SAIDA i = new Temp_ICMS_SAIDA();
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		DaoReflection.carregarDoResultSet(i, result, Temp_ICMS_SAIDA.class);
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		return i;
	}

	public List<Temp_ICMS_SAIDA> getItensFromResultSet(ResultSet result){
		try{
			result.beforeFirst(); //aponta o cursor para uma linha antes da primeira, pois ao entrar no loop na primeira vez já é chamado o método next e o cursor irá para o primeiro registro
			List<Temp_ICMS_SAIDA> itens = new ArrayList<Temp_ICMS_SAIDA>();
			Temp_ICMS_SAIDA i;

			while(result.next()){
				i = new Temp_ICMS_SAIDA();
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				DaoReflection.carregarDoResultSet(i, result, Temp_ICMS_SAIDA.class);
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
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


	public int getCountAtualizados(){
		return this.countAtualizados;
	}

}
