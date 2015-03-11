package br.com.angulo.sistemas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.ResultSet;

public abstract class Utilitario {

	//este método lê o arquivo um arquivo txt
	public static String lerArquivoTxt(String filename) throws IOException{
		File file = new File(filename);
		FileReader reader;
		String bancoTxt = null;

		StringBuilder builder = new StringBuilder();
		reader = new FileReader(file);
		BufferedReader input = new BufferedReader(reader);
		String linha;
		while ((linha = input.readLine()) != null){
			linha = linha.trim();	//utilizando método trim() para retirar espaços do arquivo de configuracao
			builder.append(linha);
			builder.append(System.getProperty("line.separator"));
		}
		input.close();
		bancoTxt = builder.toString();

		return bancoTxt;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void salvarCSV(File arquivoCSV, String csv){
		BufferedWriter buffer = null;

		try{
			System.out.println(arquivoCSV.getAbsolutePath());
			File pai = arquivoCSV.getParentFile();	//pega o local completo do diretório pai
			
			if(pai != null)
				Files.createDirectories(pai.toPath());	//cria estrutura de diretorios pai do arquivo a ser gravado
			
			if(! arquivoCSV.exists())
				Files.createFile(arquivoCSV.toPath());	//cria o arquivo vazio para posteriormente ser gravado conteudo

			FileWriter fl = new FileWriter(arquivoCSV.getAbsolutePath());
			buffer = new BufferedWriter(fl);

			buffer.write(csv);
			buffer.flush();
			buffer.close();

		} catch (IOException e) {
			e.printStackTrace();
			Log.criarLogErro(e);
		}
	}
	
	public String converteResultsetParaTexto(ResultSet result){
		StringBuilder builder = new StringBuilder();
		try{
			result.beforeFirst();
			while(result.next()){
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
			Log.criarLogErro(e);
		}
		
		return builder.toString();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//método para separar as linhas do angulo.txt, que são distribuídas em um Array tipo String
	public static String[] splitTxt(String bancoTxt){
		String bancoSplited[] = bancoTxt.split("\\r\\n|\\r|\\n");
		return  bancoSplited;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//método para separar os campos das linhas, que são distribuídos em um Array tipo String
	public static String[] splitTab(String linha){
		String linhaSplited[] = linha.split("|");
		return linhaSplited;
	}

	public static String[] splitTab(String linha, int qtd){
		String[] linhaSplitedCompleta = new String[qtd];
		String[] linhaSplited = linha.split("|",qtd);
		for(int i = 0; i<linhaSplitedCompleta.length; i++){
			if(i<linhaSplited.length ){
				linhaSplitedCompleta[i] = linhaSplited[i];	
			}else
				linhaSplitedCompleta[i] = null;
		}
		return linhaSplitedCompleta;
	}
	/////////////////////////////////////////////////////////////////////////////
	//método para testar se uma variável é numérica ou não
	public static boolean isInteger(String s) {
		try { 
			Integer.parseInt(s); 
		} catch(NumberFormatException e) { 
			return false; 
		}
		// apenas chega aqui se o 'if' não retornar 'false'
		return true;
	}

	public static int getInteger(String s){
		try{
			return Integer.parseInt(s);
		}	catch(Exception e){
			return 0;
		}
	}

	public static Double getDouble(String s){
		try{
			return Double.parseDouble(s);
		}catch(Exception e){
			return 0.0;
		}
	}

	public static void delay(long time){
		try {
			Thread.sleep(time);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	
}
