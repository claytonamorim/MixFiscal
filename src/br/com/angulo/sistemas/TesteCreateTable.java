package br.com.angulo.sistemas;

import static br.com.angulo.sistemas.Configuracao.destino;
import static br.com.angulo.sistemas.Configuracao.login;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TesteCreateTable {
	private Connection conexao;
	
	public TesteCreateTable(){
		this.conexao = new ConnectionFactory().getConnection(destino + "/angulo",login);
		executarCreateTable();
	}
	
	
	public String createTableQuery(String nomeTabelaCliente){
		StringBuilder builder = new StringBuilder();
		builder.append("CREATE TABLE IF NOT EXISTS `" + nomeTabelaCliente + "` (");
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
		String queryCreateTable = createTableQuery("outraTabelaTeste");
		Statement statement;
		try {
			statement = conexao.createStatement();
			statement.execute(queryCreateTable);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
