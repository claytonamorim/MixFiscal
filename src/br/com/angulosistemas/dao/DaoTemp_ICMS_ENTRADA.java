package br.com.angulosistemas.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.angulo.sistemas.ConnectionFactory;
import br.com.angulo.sistemas.Log;
import br.com.angulo.sistemas.bean.Temp_ICMS_ENTRADA;
import br.com.angulo.sistemas.reflection.DaoReflection;

public class DaoTemp_ICMS_ENTRADA {
	private Connection conexao;
	private int countAtualizados;	

	public DaoTemp_ICMS_ENTRADA(){
		this.conexao = new ConnectionFactory().getConnection();
	}

	public List<Temp_ICMS_ENTRADA> getItensAtualizados(){
		ResultSet result =  null;										//Produtos do banco onLine
		List<Temp_ICMS_ENTRADA> itensAtualizados = new ArrayList<Temp_ICMS_ENTRADA>();	//lista com apenas os produtos que foram alterados no banco angulo.txt em relação ao banco onLine

		//Criando tabela, caso não exista...
		executarCreateTable();
		
		//Carregando lista de Produtos do banco onLine em ResultSet
		String query = "Select * from " + Temp_ICMS_ENTRADA.TABELA_TEMP_ICMS_ENTRADA;

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
		return Temp_ICMS_ENTRADA.TABELA_TEMP_ICMS_ENTRADA;
	}

	public String createTableQuery(){
		System.out.println("nome da tabela concatenada=" + Temp_ICMS_ENTRADA.TABELA_TEMP_ICMS_ENTRADA);

		StringBuilder builder = new StringBuilder();
		builder.append("CREATE TABLE IF NOT EXISTS `" + Temp_ICMS_ENTRADA.TABELA_TEMP_ICMS_ENTRADA + "` (");
		builder.append("`codigo_produto` int(11) NOT NULL,");
		builder.append("'ean' int(16) NOT NULL,");
		builder.append("'tipo_mva' varchar(2) NULL,");
		builder.append("'mva' decimal(7,3),");
		builder.append("'mva_data_ini' date,");
		builder.append("'mva_data_fim' date,");
		builder.append("'credito_outorgado' int(1),");
		builder.append("'gera_debito' int(1),");
		builder.append("'sub_rbc_alq' int(1),");
		builder.append("'ei_cst' varchar(3),");
		builder.append("'ei_alq' decimal(7,3),");
		builder.append("'ei_alqst' decimal(7,3),");
		builder.append("'ei_rbc' decimal(7,3),");
		builder.append("'ei_rbcst' decimal(7,3),");
		builder.append("'ed_cst' varchar(3),");
		builder.append("'ed_alq' decimal(7,3),");
		builder.append("'ed_alqst' decimal(7,3),");
		builder.append("'ed_rbc' decimal(7,3),");
		builder.append("'ed_rbcst' decimal(7,3),");
		builder.append("'es_cst' varchar(3),");
		builder.append("'es_alq' decimal(7,3),");
		builder.append("'es_alqst' decimal(7,3),");
		builder.append("'es_rbc' decimal(7,3),");
		builder.append("'es_rbcst' decimal(7,3),");
		builder.append("'nfi_cst' varchar(3),");
		builder.append("'nfd_cst' varchar(3),");
		builder.append("'nfs_csosn' varchar(4),");
		builder.append("'nf_alq' decimal(7,3),");
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


	public Temp_ICMS_ENTRADA getItemFromResultSet(ResultSet result){
		Temp_ICMS_ENTRADA i = new Temp_ICMS_ENTRADA();
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		DaoReflection.carregarDoResultSet(i, result, Temp_ICMS_ENTRADA.class);
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		return i;
	}

	public List<Temp_ICMS_ENTRADA> getItensFromResultSet(ResultSet result){
		try{
			result.beforeFirst(); //aponta o cursor para uma linha antes da primeira, pois ao entrar no loop na primeira vez já é chamado o método next e o cursor irá para o primeiro registro
			//result.first();	//aponta o cursor para a primeira linha para então itera-lo
			List<Temp_ICMS_ENTRADA> itens = new ArrayList<Temp_ICMS_ENTRADA>();
			Temp_ICMS_ENTRADA i;

			while(result.next()){
				i = new Temp_ICMS_ENTRADA();
				//++++++++++++++++++++++++++++++++++++++++++++
				DaoReflection.carregarDoResultSet(i, result, Temp_ICMS_ENTRADA.class);
				//++++++++++++++++++++++++++++++++++++++++++++
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
