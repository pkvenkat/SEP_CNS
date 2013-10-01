package SEPPRocessing;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import Normalizer.GeneralNormalizer;
import Reader.ReaderCSV;
import com.csvreader.CsvWriter;
import java.io.FileWriter;
import java.io.IOException;
import au.com.bytecode.opencsv.CSVWriter;

public class SEPProcessing {

	/**
	 * @param args
	 */
	
static void normalizeSEPFile(){		
		
		ReaderCSV reader = new ReaderCSV("C:/Documents and Settings/pkvenkat/My Documents/DiggingData/Pubs_Cleaned.csv");
		
		String outputFile = "C:/Documents and Settings/pkvenkat/My Documents/DiggingData/normalized_Pubs_Cleaned.csv";
		//CsvWriter csvOutput= null;Pubs_Cleaned
		CSVWriter csvOutput= null;
		
		try {
			
           
			csvOutput = new CSVWriter(new FileWriter(outputFile), ',', CSVWriter.DEFAULT_QUOTE_CHARACTER);
			String[] strList = {"record_journal","record_pubtype","record_sep_entries"} ;
			csvOutput.writeNext(strList);

		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		List<String> array = Arrays.asList("record_journal","record_pubtype","record_sep_entries");
		
		
	    reader.registerNormalizer("record_journal", new GeneralNormalizer());
	  
		reader.setFilter(array);
		while(reader.readNextRecord()) {
		   reader.normalizeRecord();
		   HashMap<String, String> map = reader.getRecordMap();		  
		   String journalName = map.get("record_journal");       
		   if(journalName.isEmpty()) continue;
		   String pub_type = map.get("record_pubtype");
		   if(pub_type.equals("book") ||pub_type.equals("inbook") )
			   continue;
		   String sepEntries = map.get("record_sep_entries");
		   if(sepEntries.isEmpty())
			   continue;
		   String[] b = {journalName, pub_type, sepEntries};
		   
			csvOutput.writeNext(b);
		    
	     }
		try {
			csvOutput.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reader.close();
   }
public static void main(String[] args) {
	
}

		
}
   
