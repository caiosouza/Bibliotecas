package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class Util {
//	private static final String PARAMETERS_PROPERTIES = "parameters.properties";
	public static DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");

	/**
	 * Recebe um mapa que contem a frequencia de cada terno e um termo.
	 * Retorna o mapa com a frequencia desse termo incrementada 
	 * @param mapTermoFrequencia
	 * @param termo
	 */
	public static void incrementaFrequencia(Map<String, Integer> mapTermoFrequencia, String termo) {
		
		int frequenciaTermo = 0;
		
		if (mapTermoFrequencia.containsKey(termo)){
			frequenciaTermo = mapTermoFrequencia.get(termo);
		}
		frequenciaTermo += 1;
		mapTermoFrequencia.put(termo, frequenciaTermo);
	}

	/**
	 * Recebe um mapa que contem a presenca de cada terno e um termo.
	 * Retorna o mapa com a presenca desse termo incrementada 
	 * @param mapTermoPresenca
	 * @param termo
	 */
	public static void incrementaPresenca(Map<String, Integer> mapTermoPresenca, String termo) {
		
		if (!mapTermoPresenca.containsKey(termo)){
			mapTermoPresenca.put(termo, 1);
		}
	}
	
	/**
	 * Recebe o nome do campo e o nome do arquivo de propriedade e retorna o valor referente a esse campo
	 * @param propertyName
	 * @param propertiesFileName
	 * @return
	 */
	public static String readFromProperties(String propertyName, String propertiesFileName){
		File propertiesFile = new File(propertiesFileName);
		return readFromProperties(propertyName, propertiesFile);
	}
	
	/**
	 * Recebe o nome do campo e um arquivo de propriedades e retorna o valor referente a esse campo
	 * @param propertyName
	 * @param propertiesFile
	 * @return
	 */
	public static String readFromProperties(String propertyName, File propertiesFile){
		
		Properties prop = new Properties();
		try {
			
			FileInputStream filePropertiesName = new FileInputStream(propertiesFile.getAbsoluteFile());
		 	prop.load(filePropertiesName);
			return prop.getProperty(propertyName);
		} catch (IOException e) {
			return null;
		}
		
	}
	/**
	 * Recebe dois mapas com a frequencia de NGramas e retorna um map com essas frequencias consolidadas
	 * 
	 * @param nGramaFrequencia1
	 * @param nGramaFrequencia2
	 */
	public static Map<String, Integer> consolidaNgramaFrequencia(Map<String, Integer> nGramaFrequencia1,
			Map<String, Integer> nGramaFrequencia2) {
		
		Map<String, Integer> nGramaFrequenciaConsolidado = new HashMap<String, Integer>();
		
		if (nGramaFrequencia1 == null || nGramaFrequencia1.size() == 0){
			return nGramaFrequencia2;
		}
		
		List<String> nGramas1 = new ArrayList<String>();
		nGramas1.addAll(nGramaFrequencia1.keySet());
		if (nGramaFrequencia2 != null && nGramaFrequencia2.size() > 0){
			nGramas1.addAll(nGramaFrequencia2.keySet());
		}
		
		Set<String> nGramas = new HashSet<String>();
		nGramas.addAll(nGramas1);
		
		for (String nGrama : nGramas) {
			int frequencia = 0;
			if(nGramaFrequencia1 != null && nGramaFrequencia1.containsKey(nGrama)){
				frequencia += nGramaFrequencia1.get(nGrama);
			}
			if(nGramaFrequencia2 != null && nGramaFrequencia2.containsKey(nGrama)){
				frequencia += nGramaFrequencia2.get(nGrama);
			}
			nGramaFrequenciaConsolidado.put(nGrama, frequencia);
		}
		
		return nGramaFrequenciaConsolidado;
	}

	public static List<String> removeFreqMin(Map<String, Integer> termosFrequencias, int freqMin) {
		
		List<String> removidos = new ArrayList<String>();
		for (Entry<String, Integer>  termoFrequencia : termosFrequencias.entrySet()) {
			if(termoFrequencia.getValue() < freqMin){
				if(!(termoFrequencia.getKey().startsWith("INICIO") || termoFrequencia.getKey().endsWith("FIM")))
					removidos.add(termoFrequencia.getKey());
				//termosFrequencias.remove(termoFrequencia);
			}
		}
		
		return removidos;
		
	}
	
	public static Boolean getBoolean(String string) {
		
		return string.contentEquals("1");
	}
	
	public static List<String> splitToArray(String file, String separador){
		String[] parts = file.split(separador);			
		List<String> arrayParts = new ArrayList<String>();
		for(String part : parts){
			arrayParts.add(part);
		}		
		return arrayParts;
	}
	
	public static String[] limpaTokens(String[] tokens) {
		
		List<String> tokensArray = new ArrayList<String>();
		for (String token : tokens) {
			if (!token.trim().contentEquals("")){
				tokensArray.add(token);
			}
		}
		
		String [] tokensFiltrados = new String[tokensArray.size()];
		for (int i = 0; i < tokensArray.size(); i++) {
			tokensFiltrados[i] = tokensArray.get(i);
		}
		
		return tokensFiltrados;
	}
	
    public static String limpacaracteres(String passa) {
		
    	passa = passa.replaceAll("[ÂÀÁÄÃ]","A");  
        passa = passa.replaceAll("[âãàáä]","a");  
        passa = passa.replaceAll("[ÊÈÉË]","E");  
        passa = passa.replaceAll("[êèéë]","e");  
        passa = passa.replaceAll("[ÎÍÌÏ]","I");  
        passa = passa.replaceAll("[îíìï]","i");  
        passa = passa.replaceAll("[ÔÕÒÓÖ]","O");  
        passa = passa.replaceAll("[ôõòóö]","o");  
        passa = passa.replaceAll("[ÛÙÚÜ]","U");  
        passa = passa.replaceAll("[ûúùü]","u");  
        passa = passa.replaceAll("Ç","C");  
        passa = passa.replaceAll("ç","c");   
        passa = passa.replaceAll("[ýÿ]","y");  
        passa = passa.replaceAll("Ý","Y");  
        passa = passa.replaceAll("ñ","n");  
        passa = passa.replaceAll("Ñ","N");  
        passa = passa.replaceAll("['<>|/]","");  
        return passa;  
    	
    }

	public static List<String> MapToListString(Map<String, Integer> mapStringInt, Integer minGlobalFreq) {
		
		List<String> linhas = new ArrayList<String>();
		
		for	(Entry<String, Integer> entry:	mapStringInt.entrySet()){
			// Imprime também o tamanho de cada NGrama
			if (entry.getValue() >= minGlobalFreq){
				linhas.add(entry.getKey().split(" ").length + ";" + entry.getValue() + ";" + entry.getKey());
			}
		}
		return linhas;
	}

	public static void geraLog(Date inicio, List<String> linhasParametrosEntrada,
			String arquivoGerado, Date fim, String nomeArquivoLog) {
		List<String> arquivosGerados = new ArrayList<String>();
		arquivosGerados.add(arquivoGerado);
		geraLog(inicio, linhasParametrosEntrada, arquivosGerados, fim, nomeArquivoLog);
		
	}
	public static void geraLog(Date inicio, List<String> linhasParametrosEntrada,
			List<String> arquivosGerados, Date fim, String nomeArquivoLog) {

		String pulaLinha = ""+ '\n';
		List<String> log = new ArrayList<String>();
		log.add(inicio.toString() + " INICIO");
		log.add(pulaLinha);
		log.add("Parametros de Entrada:");
		log.addAll(linhasParametrosEntrada);
		log.add(pulaLinha);
		log.add("Arquivos gerados:");
		log.addAll(arquivosGerados);
		log.add(pulaLinha);
		log.add(fim.toString() + " Fim");
		
		Arquivo.insereLinhas("log" + File.separatorChar + nomeArquivoLog, log);
	}

	public static List<String> intMatrizToListString(int[][] intMatrix) {
		String temp;
		List<String> textoMatrizTransicaoSparsa = new ArrayList<String>();
		for (int i = 0; i < intMatrix.length; i++) {
			int[] linhaMatriz = intMatrix[i];
			temp = Arrays.toString(linhaMatriz);
			temp = temp.replace("[","");
			temp = temp.replace("]","");
			textoMatrizTransicaoSparsa.add(temp);
		}
		return textoMatrizTransicaoSparsa;
	}

	
}
