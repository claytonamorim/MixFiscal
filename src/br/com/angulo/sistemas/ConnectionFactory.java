package br.com.angulo.sistemas;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import static br.com.angulo.sistemas.Configuracao.*;

public class ConnectionFactory {
	//static String URL = "jdbc:mysql://192.92.1.1:3306/loja";
	//static String URL = "jdbc:mysql://mysql.angulo.kinghost.net/angulo";
	//static String usuario = "root";
	static String urlCompleta;
	static String driver = "jdbc:mysql://";

	public Connection getConnection(){
		try {
			urlCompleta = driver + host + "/" + banco + "?rewriteBatchedStatements=true";
			System.out.println("URL Completa: " + urlCompleta);
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(urlCompleta, login, senha);

		} catch (CommunicationsException e) {
			System.out.println("Excecao de Comunica��o: " + e);
			e.printStackTrace();
			Log.criarLogErro(e);  //Gravando o log de erro em C:/importa/erros.log
			JOptionPane.showMessageDialog(null, "Sem conex�o com o Banco de Dados Web, verifique sua conex�o!");
			System.exit(0); //Fechando a aplica��o
		} catch(ClassNotFoundException e){
			System.out.println("Classe n�o encontrada: " + e);
			Log.criarLogErro(e);  //Gravando o log de erro em C:/importa/erros.log
		} catch(SQLException e){
			System.out.println("Excecao de SQL: " + e);
			e.printStackTrace();
			Log.criarLogErro(e);  //Gravando o log de erro em C:/importa/erros.log
			JOptionPane.showMessageDialog(null, "N�o foi poss�vel realizar conex�o com o Banco de Dados Web");
		}
		return null;
	}
}
