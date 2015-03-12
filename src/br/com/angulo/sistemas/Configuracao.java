package br.com.angulo.sistemas;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

public final class Configuracao {
	//public static String LOCAL_CONFIGURACAO = "C:/importa/angulo.cfg";
	public static String LOCAL_CONFIGURACAO = "./angulo.cfg";	//arquivo de configuracao deverá estar na mesma pasta do jar executado (isso pode dar problemas, verificar outra opcao futuramente)

	//Atributos do arquivo de configuração 'angulo.cfg'
	private static final String ORIGEM = "[ORIGEM]";
	//private static final String DESTINO = "[DESTINO]";
	private static final String LOCAL_LOG_ERRO = "[LOCAL_LOG_ERRO]";
	private static final String LOCAL_LOG_ATUALIZA = "[LOCAL_LOG_ATUALIZA]";
	private static final String TABELA = "[TABELA]";
	//private static final String LOGIN = "[LOGIN]";
	//private static final String SENHA = "[SENHA]";
	private static final String EXIBIR_DIALOGO = "[EXIBIR_DIALOGO]";
	private static final String DELAY = "[DELAY]";
	private static final String QTDE_LINHAS = "[QTDE_LINHAS]";

	//Declarando atribulos a serem carregados
	public static File origem; // endereço do arquivo do banco angulo.txt
	public static File host = new File("mysql.angulo.kinghost.net"); // endereço do banco MySql
	public static String banco = "angulo";	//angulo01
	public static File localLogErro;
	public static File localLogAtualiza;
	public static String tabela;
	public static String login = "angulo";	//angulo01
	public static String senha = "droop33";
	public static Boolean exibirDialogo = true;
	public static long delay;
	public static int qtdeLinhas;

	private Configuracao(){};	//Contrutor private para evitar que esta classe seja instanciada

	//Este método realiza a leitura do arquivo de configuração 'angulo.cfg' e seta suas opções em suas respectivas variáveis
	public static void setConfiguracao(){
		String[] confSplited =  null;
		System.out.println("Lendo arquivo de configuracao 'angulo.cfg'");
		try{
			String configuracao = Utilitario.lerArquivoTxt(LOCAL_CONFIGURACAO);	//Realizando leitura de arquivo com configurações
			confSplited = Utilitario.splitTxt(configuracao);//Dividindo em linhas o arquivo de configurações

		}catch(IOException e){
			JOptionPane.showMessageDialog(null, "Arquivo de configuração 'angulo.cfg' não encontrado!");
			e.printStackTrace();
			Log.criarLogErro(e);  //Gravando o log de erro em C:/importa/erros.log
		}

		for(int i = 0; i<confSplited.length; i++){
			switch(confSplited[i].toUpperCase()){	//deixa o conteudo todo da linha em UpperCase para poder compará-la sem problemas com caracteres maiúsculo e minúsculo
			
			case (ORIGEM):
				origem = new File(confSplited[i+1]);
			break;
			
			/*
			case (DESTINO):
				destino = new File(confSplited[i+1].substring(7)); //utiliza substring para pular 7 caracteres de 'http://' do caminho dado: 'http://mysql.angulo.kinghost.net/angulo'
			break;
			*/
			case(LOCAL_LOG_ERRO):
				localLogErro = new File(confSplited[i+1]);
			break;
			
			case(LOCAL_LOG_ATUALIZA):
				localLogAtualiza = new File(confSplited[i+1]);
			break;
			
			case(TABELA):
				tabela = confSplited[i+1];
			break;
			/*
			case(LOGIN):
				login = confSplited[i+1];
			break;
			*/
			/*
			case(SENHA):
				senha = confSplited[i+1];
			break;
			*/
			case(EXIBIR_DIALOGO):
				if (confSplited[i+1].equalsIgnoreCase("S"))
					exibirDialogo = true;
				else
					exibirDialogo = false;
			break;
			
			case(DELAY):
				delay = Long.valueOf(confSplited[i+1]);
			break;
			
			case(QTDE_LINHAS):
				qtdeLinhas = Integer.valueOf(confSplited[i+1]);
			break;
			}
		}
		//Mostrando valores lidos no arquivo de configuração
		System.out.println("[ORIGEM]>> " + origem);
		System.out.println("[DESTINO]>> " + host);
		System.out.println("[LOCAL_LOG_ERRO]>> " + localLogErro);
		System.out.println("[LOCAL_LOG_ATUALIZA]>> " + localLogAtualiza);
		System.out.println("[TABELA]>> " + tabela);
		System.out.println("[LOGIN]>> " + login);
		System.out.println("[SENHA]>> " + senha);
		System.out.println("[EXIBIR_DIALOGO]>> " + exibirDialogo);
		System.out.println("[DELAY]>> " + delay);
		System.out.println("[QTDE_LINHAS]>> " + qtdeLinhas);
		System.out.println("Fim do metodo setConfiguracao()");

	}
}
