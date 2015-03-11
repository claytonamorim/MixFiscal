package br.com.angulosistemas.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.angulo.sistemas.ConnectionFactory;
import br.com.angulo.sistemas.Log;
import br.com.angulo.sistemas.bean.Temp_PIS_COFINS;
import br.com.angulo.sistemas.reflection.DaoReflection;

public class DaoTemp_PIS_COFINS {
	private Connection conexao;
	private int countAtualizados;	

	public DaoTemp_PIS_COFINS(){
		this.conexao = new ConnectionFactory().getConnection();
	}

	public List<Temp_PIS_COFINS> getItensAtualizados(){
		ResultSet result =  null;										//Produtos do banco onLine
		List<Temp_PIS_COFINS> itensAtualizados = new ArrayList<Temp_PIS_COFINS>();	//lista com apenas os produtos que foram alterados no banco angulo.txt em relação ao banco onLine

		//Criando tabela, caso não exista...
		executarCreateTable();
		
		//Carregando lista de Produtos do banco onLine em ResultSet
		String query = "Select * from " + Temp_PIS_COFINS.TABELA_TEMP_PIS_COFINS;

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
	
	public String createTableQuery(){
		System.out.println("nome da tabela concatenada=" + Temp_PIS_COFINS.TABELA_TEMP_PIS_COFINS);

		StringBuilder builder = new StringBuilder();
		builder.append("CREATE TABLE IF NOT EXISTS `" + Temp_PIS_COFINS.TABELA_TEMP_PIS_COFINS + "` (");
		builder.append("`codigo_produto` int(11) NOT NULL,");
		builder.append("'ean' int(16) NOT NULL,");
		builder.append("'ncm' varchar(10) NULL,");
		builder.append("'ncm_ex' varchar(3) NULL,");
		builder.append("'cod_natureza_receita' int(4) NULL,");
		builder.append("'credito_presumido' int(1),");
		builder.append("'pis_cst_e' varchar(3),");
		builder.append("'pis_cst_s' varchar(3),");
		builder.append("'pis_alq_e' decimal(7,3),");
		builder.append("'pis_alq_s' decimal(7,3),");
		builder.append("'ofins_cst_e' varchar(3),");
		builder.append("'cofins_cst_s' varchar(3),");
		builder.append("'cofins_alq_e' decimal(7,3),");
		builder.append("'cofins_alq_s' decimal(7,3),");
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


	public Temp_PIS_COFINS getItemFromResultSet(ResultSet result){
		Temp_PIS_COFINS i = new Temp_PIS_COFINS();
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		DaoReflection.carregarDoResultSet(i, result, Temp_PIS_COFINS.class);
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		return i;
	}

	public List<Temp_PIS_COFINS> getItensFromResultSet(ResultSet result){
		try{
			result.beforeFirst(); //aponta o cursor para uma linha antes da primeira, pois ao entrar no loop na primeira vez já é chamado o método next e o cursor irá para o primeiro registro
			List<Temp_PIS_COFINS> itens = new ArrayList<Temp_PIS_COFINS>();
			Temp_PIS_COFINS i;

			while(result.next()){
				i = new Temp_PIS_COFINS();
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				DaoReflection.carregarDoResultSet(i, result, Temp_PIS_COFINS.class);
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

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
