package br.com.angulo.sistemas;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static br.com.angulo.sistemas.Configuracao.localLogErro;
import static br.com.angulo.sistemas.Configuracao.localLogAtualiza;

public class Log {

	//este método cria um log com o último erro gerado para auxílio no suporte ao usuário
	public static void criarLogErro(Exception e){
		BufferedWriter buffer = null;
		try{
			//File fileName = new File("C:\\importa\\erros.log");
			File pai = localLogErro.getParentFile();	//extrai a estrutura de pastas pai do arquivo
			if(pai != null)
				Files.createDirectories(pai.toPath());	//cria todos os diretorios pais do arquivo "erros.log"
			
			if (! localLogErro.exists())
				Files.createFile(localLogErro.toPath());
			
			//Recuperando Log anterior para concatená-lo com o novo Log
			String logAnterior = Utilitario.lerArquivoTxt(localLogErro.getAbsolutePath());
			
			FileWriter fl = new FileWriter(localLogErro.getAbsolutePath());
			buffer = new BufferedWriter(fl);
			StringBuilder builder = new StringBuilder();
			
			builder.append(getDataHora());
			builder.append("\t" + "\t" + "\t");
			builder.append(e.getMessage());
			
			builder.append(System.getProperty("line.separator"));
			StringWriter erros = new StringWriter();
			e.printStackTrace(new PrintWriter(erros));
			builder.append(erros.toString());
			builder.append(System.getProperty("line.separator"));
			
			//desenhado uma linha para separar o novo log do anterior
			builder.append("____________________________________________________________________________________________________________________________________________");
			
			builder.append(System.getProperty("line.separator"));
			builder.append(logAnterior);	//adicionando o log anterior
			
			buffer.write(builder.toString());
			buffer.flush();
			buffer.close();
			fl.close();
		}catch(IOException io){
			System.out.println("Não foi possível salvar log de erro: " + io.getMessage());
		}
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void criarLogUpdate(int qtde, String atualizados){
		BufferedWriter buffer = null;
		try{
			//File fileName = new File("C:\\importa\\atualizacoes.log");
			File pai = localLogAtualiza.getParentFile();
			if(pai != null)
				Files.createDirectories(pai.toPath());	//cria todos os diretorios pai do arquivo "atualizacoes.log"
			
			if (! localLogAtualiza.exists())
				Files.createFile(localLogAtualiza.toPath());
			
			//Recuperando Log anterior para concatená-lo com o novo Log
			String logAnterior = Utilitario.lerArquivoTxt(localLogAtualiza.getAbsolutePath());
			logAnterior = logAnterior.replaceAll("<html>", "");	//retira a tag que inicia o html para iniciar um novo doc html
			logAnterior = logAnterior.replaceAll("</html>", "");	//retira a tag que fecha o html para continuar a concatenação
			
			FileWriter fl = new FileWriter(localLogAtualiza.getAbsolutePath());
			buffer = new BufferedWriter(fl);
			StringBuilder builder = new StringBuilder();
			
			builder.append("<html>");	//abre o <html>
	
			builder.append(getDataHora());
			builder.append(atualizados);	//adiciona o novo log ao arquivo
			builder.append(System.getProperty("line.separator"));
			//desenhado uma linha para separar o novo log do anterior
			builder.append("____________________________________________________________________________________________________________________________________________");
			builder.append(System.getProperty("line.separator"));
			builder.append("<br>");
			builder.append(logAnterior);	//adiciona o log anterior ao arquivo
			
			builder.append("</html>");	//fecha o <html>
			buffer.write(builder.toString());
			buffer.flush();
			buffer.close();
			fl.close();
		}catch(IOException io){
			System.out.println("Não foi possível salvar arquivo de atualizações: " + io.getMessage());
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	//método para recuperar Data e Hora atual
	public static String getDataHora(){
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date dataHora = new Date();
		return (format.format(dataHora));
	}
}
