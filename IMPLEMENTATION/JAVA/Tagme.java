package main;

import java.io.IOException; 
import java.io.InputStream;

import main.Tagme;
import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class Tagme {
	public static void main(String[] args){
	}
	public String Extract(String a){

//		String a= "this is a very good product";
		
		//Tokenizer
				InputStream modelIn = null;
				TokenizerModel model = null;
				try {
				modelIn = Tagme.class.getClassLoader().getResourceAsStream("en-token.bin");
				model = new TokenizerModel(modelIn);
				}
				catch (IOException e) {
				  e.printStackTrace();
				}
				finally {
				  if (modelIn != null) {
				    try {
				      modelIn.close();
				    }
				    catch (IOException e) {
				    }
				  }
				}
				Tokenizer tokenizer = new TokenizerME(model);
				//tokenize
				String tokens[] = tokenizer.tokenize(a);
				
				
				//POST Tagger
				InputStream modelIn1 = null;
				POSModel model1 = null ;
				try {
				  modelIn1 = Tagme.class.getClassLoader().getResourceAsStream("en-pos-maxent.bin");
				  model1 = new POSModel(modelIn1);
				}
				catch (IOException e) {
				  // Model loading failed, handle the error
				  e.printStackTrace();
				}
				finally {
				  if (modelIn != null) {
				    try {
				      modelIn.close();
				    }
				    catch (IOException e) {
				    }
				  }
				}

				POSTaggerME tagger = new POSTaggerME(model1);
				
				String tags[] = tagger.tag(tokens);
				

		//CHunking

		InputStream modelIn3 = null;
		ChunkerModel model3 = null;

		try {
		  modelIn3 = Tagme.class.getClassLoader().getResourceAsStream("en-chunker.bin");
		  model3 = new ChunkerModel(modelIn3);
		} catch (IOException e) {
		  // Model loading failed, handle the error
		  e.printStackTrace();
		} finally {
		  if (modelIn3 != null) {
		    try {
		      modelIn3.close();
		    } catch (IOException e) {
		    }
		  }
		}
		ChunkerME chunker = new ChunkerME(model3);
		String result[] = chunker.chunk(tokens, tags);

		String VP ="";
		int i=0,start=0,end=0;
		int k=5;
		int n=Math.min(Math.min(result.length,tags.length),tokens.length);
		while(i!=n){
		//result[i]=result[i].substring(2);
		i++;
		}

		i=0;
		int count=0;
		int flag=0;
		while(i!=n && count<=5){
			if(flag==1){
				break;
			}
		// extraction begins from VP with be verbs excluded 
		if(result[i].contains("VP")==true && tokens[i].contains("be")!=true){
			flag=1;
			count++;
			start=i;
			end=i+1;
			 // add optional NP to the left of chunk 
			if(i>0){
				if(result[i-1].contains("NP")==true)
				{
					start=i-1;
					count++;
			}
			}
			 // add optional PP, PRT, ADJP, ADVP, NP to the right 
			for(int j=i+1;j<n;j++){
				if(count==k){// the maxmimum number of tokens in verb expressions is reached 
					break;
				}
				else 
					if(result[j].contains("PP")==true || result[j].contains("PRT")==true || result[j].contains("ADJP")==true || result[j].contains("ADVP")==true || result[j].contains("NP")==true){
					end=j+1;
					count++;
				}
			}	
			}
		i++;
		}
		int	q = start;
		while(q<end){
			VP=VP+tokens[q]+" ";
			q++;
		}
//		i=0;
//		while(i!=n){
//			System.out.println(tokens[i]+"\t\t"+tags[i]+"\t\t"+result[i]);
//			i++;
//		}
return VP;
//System.out.println("VP= "+VP);
}

}
