import jFraction.SEPDisciplineJournalName;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import journalReader.JournalNameIdReader;

import org.apache.commons.lang3.StringUtils;

import IdDiscipline.IdDisciplineProcessing;
import Normalizer.CaseNormalizer;
import Reader.ReaderCSV;
import au.com.bytecode.opencsv.CSVWriter;


public class IdProcessor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
       ReaderCSV reader = new ReaderCSV("C:/Documents and Settings/pkvenkat/My Documents/DiggingData/Pubs_Cleaned.csv");		
		String outputFile = "C:/Documents and Settings/pkvenkat/My Documents/DiggingData/final_intermediate.csv";
		//CsvWriter csvOutput= null;Pubs_Cleaned
		CSVWriter csvOutput= null;
		
		try {           
			csvOutput = new CSVWriter(new FileWriter(outputFile), ',', CSVWriter.DEFAULT_QUOTE_CHARACTER);
			//String[] strList = {"record_journal","record_pubtype","record_sep_entries","journal_id", "sub_discipline"};
			//String[] strList = {"record_sep_entries", "sub_discipline_Id", "sub_discipline_name", "jfraction"};
			String[] strList = {"journal_name","record_sep_entries", "sub_discipline_Id", "sub_discipline_name", "jfraction"};
			csvOutput.writeNext(strList);

		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		JournalNameIdReader jid = new JournalNameIdReader();
		jid.readAndStoreData();
		IdDisciplineProcessing subIdProcess= new IdDisciplineProcessing();
		subIdProcess.read();
		
		List<String> array = Arrays.asList("record_journal","record_pubtype","record_sep_entries");		
		reader.setFilter(array);
		reader.registerNormalizer("record_journal", new CaseNormalizer());
		
		JFractionCreator jfrac = new JFractionCreator();
		
		//to count the hits 
		//HashMap<String,Integer> countMap =  new HashMap<String,Integer>();
		
		Integer numOfJournal = 0;
		Integer matchedJournal = 0;
		Integer matchedJournalwithSub = 0;
		
		
		while(reader.readNextRecord()) {
			String originalJournal =reader.get("record_journal");
			  reader.normalizeRecord();
			  HashMap<String, String> map = reader.getRecordMap();	
			  
			  String journalName = map.get("record_journal");			 
			  if(journalName.isEmpty()) continue;  //for counting
			  
			  
			  String pub_type = map.get("record_pubtype");
			  if(pub_type.equals("book") ||pub_type.equals("inbook") )
				   continue;
			  numOfJournal++;
			  String sepEntries = map.get("record_sep_entries");
			  String[] separray = sepEntries.split("\\|");
			  
			  if(sepEntries.isEmpty())
				   continue;
			  if(journalName.equals("science"))
					 System.out.println("test"); 
			  Long id= jid.getId(journalName);
			  			  List<String> disciplines  = null;
			  if(id != -1)
			  {
				  disciplines = subIdProcess.getSubdisclineName(id);
				  matchedJournal++;
			  }
			 
			  if(disciplines == null ||disciplines.isEmpty())
				  continue;
			     //String[] b = {journalName, pub_type, sepEntries, String.valueOf(id),discipline};
			    Double jfraction = 1.0/disciplines.size();
			    for(String sepref:separray)
			    {
			        for(String discName: disciplines)
			        {
			    	    //jfrac.add(sepref, discName, jfraction);
			        	jfrac.add(sepref, discName, jfraction,originalJournal);
			         }
			    }  
			  
		}
		/*HashMap<SEPDiscipline, Double> map = jfrac.getFinalSet();
		
		for (Map.Entry<SEPDiscipline, Double> entry : map.entrySet()) 
		{
			String[] b = { entry.getKey().getSEPref(), subIdProcess.getSubdisclineId(entry.getKey().getSubDiscipline()).toString(),entry.getKey().getSubDiscipline(), entry.getValue().toString()};
		    csvOutput.writeNext(b);
		}*/
		
		List<SEPDisciplineJournalName> dataArray = jfrac.getDataArray();
		
		for(SEPDisciplineJournalName data:dataArray)
		{
			String[] b = {data.getJournalName(),data.getSEPref(), subIdProcess.getSubdisclineId(data.getSubDiscipline()).toString()
					,data.getSubDiscipline(), data.getjFraction().toString()};
			csvOutput.writeNext(b);
		}	
		/*String[] b = { sepEntries, discNames, jfraction.toString()};
	    csvOutput.writeNext(b);*/
		try {
			csvOutput.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reader.close();
		
		System.out.println("Number of Jounrnals = " + numOfJournal);
		System.out.println("Matched Jounrnals = " + matchedJournal);
		System.out.println("Matched Jounrnals with subDisc= " + matchedJournalwithSub);
		
	}
	
}
