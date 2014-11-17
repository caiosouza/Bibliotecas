package preProcessaTexto;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import util.Arquivo;

public class StopWords {
	
	//private static final String stopWordFile = "StopList.txt";
	
	public static void main(String[] args){
		
		StopWords stopWords = new StopWords();
		stopWords.exec(args);
	}
	
	public void exec(String[] args){
		
		String line;
		Scanner s = new Scanner(System.in);
		System.out.println("Entre com o nome do arquivo de stopList:");
		String stopListNameFile = s.next();
		System.out.println("Insira uma palavra por linha:");
		
		while(s.hasNext())
		{
			line = s.nextLine();
			System.out.println("Nova frase: "+removeStopWords(line, stopListNameFile, null));
		}
		
	}
	
	public String removeStopWords(String inPutText, String stopListNameFile, Set<String> stopList) {
		
		if (stopList == null){
			stopList = montaStopList(stopListNameFile);
		}
		
		String [] outStrings = inPutText.split(" ");
		List<String> outArray = new ArrayList<String>();
		for (int i = 0; i < outStrings.length; i++) {
			outArray.add(outStrings[i]);
		}
		outArray = deletStopWord(stopList, outArray);
		
		String outPutText = "";
		for (String string : outArray) {
			outPutText = outPutText +" " + string;
		}
		
		return outPutText;
	}
	
	public List<String> deletStopWord(Set<String> stopWords, List<String> arraylist){

		List<String> NewList = new ArrayList<String>();
        int i=0;
        while(i < arraylist.size() ){
        	if(!stopWords.contains(arraylist.get(i))){
        		NewList.add((String) arraylist.get(i));
        	}
            i++;        
        }
        return NewList;
	}
	
	public boolean isStopWord(Set<String> stopWords, String word){
		return stopWords.contains(word);
	}
      	
	public Set<String> montaStopList(String stopListNameFile){
		
		Set<String> stopList = new LinkedHashSet<String>();
		List<String> linhasStopWords = Arquivo.abreArquivo(stopListNameFile);
		
		for (String linhaStopWords : linhasStopWords) {
			stopList.add(linhaStopWords.trim().toLowerCase());
		}	
		return stopList;
	}

}
