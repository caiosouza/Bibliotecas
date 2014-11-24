package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Arquivo {
	
	private static String codificacaoDefault = "UTF-8";
	private static String separadorDefault = " ";
	
	public static String pegaLinhas(String nomeArq){
	//TODO colocar poliformismo para chamar passando string separador entre as linhas e o booleano para dizer se mantem as linhas que vierem vazias	
		String linhaSaida = "";
		
		List<String> linhas;
		try {
			linhas = abreArquivo(nomeArq);
			for (String linha : linhas) {
				linhaSaida = linhaSaida + separadorDefault + linha;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return linhaSaida;
	}
	
	
	public static String pegaLinhas(File arquivo) {
		String nomeArq = arquivo.getAbsolutePath();
		return pegaLinhas(nomeArq);
	}

	public static List<String> abreArquivo(File arquivo){
		return abreArquivo(arquivo.getAbsolutePath());
	}
	
	public static List<String> abreArquivo(File arquivo, boolean mantemVazias){
		return abreArquivo(arquivo.getAbsolutePath(), mantemVazias);
	}

	public static List<String> abreArquivo(String nomeArq) {
		return abreArquivo(nomeArq, true);	
	}
	
	public static List<String> abreArquivo(String nomeArq, boolean mantemVazias) {
		return abreArquivo(nomeArq, mantemVazias, codificacaoDefault);
	}
	
	public static List<String> abreArquivo(String nomeArq, boolean mantemVazias, String codificacao) {
		
		List<String> linhas = new ArrayList<String>();
		
		try{
			BufferedReader txtBuffer = new BufferedReader(new InputStreamReader(
		    	    new FileInputStream(nomeArq), codificacaoDefault));
		    
		    String linha = txtBuffer.readLine();
		     
		    while (linha != null) {
		    	if (mantemVazias || linha.trim().length() > 0){
		    		linhas.add(linha);
		    	}
		        linha = txtBuffer.readLine();
		    }
		     
		    txtBuffer.close();
		} catch(Exception e) {  
			System.out.println("Erro ao abrir arquivo: "+nomeArq+" !");
		}
		
    	return linhas;
		
	}

	public static void salvaArquivo(String linha, String nomeArqSaida) {
		
		List<String> linhas = new ArrayList<String>();
		linhas.add(linha);
		salvaArquivo(linhas,nomeArqSaida);
	}
	
	public static void salvaArquivo(List<String> linhas, String nomeArqSaida) {
		salvaArquivo(linhas, nomeArqSaida, codificacaoDefault);
	}
	
	public static void salvaArquivo(List<String> linhas, String nomeArqSaida, String codificacao) {

		FileOutputStream arqSaida;
		PrintStream p;  
		
		try{  
			//cria o diretorio caso seja necessario
			criaDiretorioArquivo(nomeArqSaida);
						
		
			arqSaida = new FileOutputStream(nomeArqSaida);  
			p = new PrintStream(arqSaida, true, codificacao);
			 
			for (String linha : linhas) {
				p.println(linha);
			}	
			  
		    p.close();  
		} catch(Exception e) {  
			System.out.println("Erro ao salvar arquivo de saida: "+nomeArqSaida+" !");

		}  
	}
	
	public List<File> getAllFilesRecursive( File aStartingDir ) throws FileNotFoundException {

		List<File> result = new ArrayList<File>();
		File[] filesAndDirs = aStartingDir.listFiles();
		List<File> filesDirs = Arrays.asList(filesAndDirs);
		
    	for(File file : filesDirs) {
			if(file.isFile()){
				result.add(file); //add only files
			}
			else {
				//must be a directory
				//recursive call!
				List<File> deeperList = getAllFilesRecursive(file);
				result.addAll(deeperList);
			}
		}
		return result;
	}

    public void copia(File origem, File destino) throws IOException {
      
    	criaDiretorioArquivo(destino);
    	
    	InputStream in = new FileInputStream(origem);
        OutputStream out = new FileOutputStream(destino);          
        // Transferindo bytes de entrada para saida
        byte[] buffer = new byte[1024];
        int lenght;
        while ((lenght= in.read(buffer)) > 0) {
            out.write(buffer, 0, lenght);
        }
        in.close();
        out.close();
        
    }
    
    private static void criaDiretorioArquivo(String nomeArqSaida) {
		criaDiretorioArquivo(new File(nomeArqSaida));			
	}	
	
	private static void criaDiretorioArquivo(File arqSaida) {

		String dirPath = arqSaida.getParent();
		if (dirPath != null){
			File fDirPath = new File(dirPath);
			if (!fDirPath.exists()) {
				fDirPath.mkdirs();
			}
		}
	}
	
	public static String contatenaLinhas(List<String> linhas) {
		return contatenaLinhas(linhas, separadorDefault);
	}
	
	public static String contatenaLinhas(List<String> linhas, String separador) {
		
		String linhaFinal = "";
		for (String linha : linhas) {
			linhaFinal = linhaFinal + separador + linha;
		}
		return linhaFinal;
	}

	public static void insereLinhas(String nomeArquivo, List<String> novasLinhas)  {
		
		try {
			criaDiretorioArquivo(nomeArquivo);
			FileWriter arquivo = new FileWriter(nomeArquivo, true);
			arquivo.write("");
			for (String novaLinha : novasLinhas) {
				novaLinha += '\n';
				arquivo.write(novaLinha);  	
			}
			arquivo.close();  
		} catch (IOException e) {
			System.out.println("Erro ao inserir linhas no arquivo: " + nomeArquivo + ".");
			System.out.println(e);
		}  
	}


	public static void insereLinhas(String nomeArquivo, String novaLinha) {
		List<String> novasLinhas = new ArrayList<String>();
		novasLinhas.add(novaLinha);
		insereLinhas(nomeArquivo, novasLinhas);
	}
	
}
