package preProcessaTexto;

import java.util.ArrayList;
import java.util.List;

public class NGrama {

    public static List<String> ngrams(int n, String str) {
        List<String> ngrams = new ArrayList<String>();
        String[] words = str.split(" ");
        for (int i = 0; i < words.length - n + 1; i++)
            ngrams.add(concat(words, i, i+n));
        return ngrams;
    }

    public static String concat(String[] words, int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++)
            sb.append((i > start ? " " : "") + words[i]);
        return sb.toString();
    }

    public static void main(String[] args) {
        
    	/*
    	 * Exemplo de como usar a funcao
    	 * */
    	String texto = "This is my car. Oh my good this really works.";
    	String[] token = texto.split(" ");
    	for (int n = 1; n <= token.length; n++) {
    		System.out.println(n);
    		for (String ngram : ngrams(n, texto))
                System.out.println(ngram);
            System.out.println();
        }
    }

}
