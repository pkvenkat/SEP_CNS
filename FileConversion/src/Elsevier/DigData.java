package Elsevier;



import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import journalReader.JournalNameIdReader;

import IdDiscipline.IdDisciplineProcessing;
import Normalizer.CaseNormalizer;
import Reader.ReaderCSV;
import au.com.bytecode.opencsv.CSVWriter;

public class DigData {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ReaderCSV reader = new ReaderCSV("C:/Documents and Settings/pkvenkat/My Documents/DiggingData/Elsevier Mapping 01.18.13.csv");		
		String outputFile = "C:/Documents and Settings/pkvenkat/My Documents/DiggingData/Elsevier Mapping 01.18.13_out.csv";
		//CsvWriter csvOutput= null;Pubs_Cleaned
		CSVWriter csvOutput= null;
		
		try {           
			csvOutput = new CSVWriter(new FileWriter(outputFile), ',', CSVWriter.DEFAULT_QUOTE_CHARACTER);
			//String[] strList = {"record_journal","record_pubtype","record_sep_entries","journal_id", "sub_discipline"};
			String[] strList = {"source_id","journal_name","journal_id", "sub_discipline_id","sub_disciplines"};
			csvOutput.writeNext(strList);

		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		String journalNameFile ="C:/Documents and Settings/pkvenkat/My Documents/DiggingData/journal_names.csv";
		JournalNameIdReader jidReader = new JournalNameIdReader(journalNameFile,"journal_name","journ_id");
		jidReader.setNormalizer(new CaseNormalizer());
		jidReader.read();
		
		String mapFile =  "C:/Documents and Settings/pkvenkat/My Documents/DiggingData/subd_journal.csv";
		String 	discFile =  "C:/Documents and Settings/pkvenkat/My Documents/DiggingData/subdisciplines.csv";
		
		
		IdDisciplineProcessing subIdProcess= new IdDisciplineProcessing(mapFile,discFile );
		subIdProcess.setDiscIdField("subd_id");
		subIdProcess.setDiscNameField("subd_name");
		subIdProcess.setMapDiscIdFieldName("journ_id");
		subIdProcess.setMapJournalIdFieldName("subd_id");
		subIdProcess.read();
		
		
		List<String> array = Arrays.asList("Original source ID","sourcetitle");		
		reader.setFilter(array);
		reader.registerNormalizer("sourcetitle", new CaseNormalizer());		
		
		//to count the hits 
		//HashMap<String,Integer> countMap =  new HashMap<String,Integer>();
		
		Integer numOfJournal = 0;
		Integer matchedJournal = 0;	
		
		while(reader.readNextRecord()) {
			String journalNameOrg =  reader.get("sourcetitle");
			  reader.normalizeRecord();
			  HashMap<String, String> map = reader.getRecordMap();	
			  
			  String journalName = map.get("sourcetitle");			 
			  if(journalName.isEmpty()) continue;
			  //for counting
			  numOfJournal++;
			  
			  String sourceId = map.get("Original source ID");
			  
			
			  Long journalId= jidReader.getId(journalName);
			  List<String> disciplines  = null;			  
			  if(journalId != -1)
			  {					  
				  disciplines = subIdProcess.getSubdisclineName(journalId);
				  matchedJournal++;
			  }	
			  
			  
			  //part of writing
			  String subDisc = "";
			  String subNum ="";
			  if(disciplines != null)
			  {				  
			      for (String disc: disciplines)
			      {
			    	  subDisc += disc;
			          subNum += subIdProcess.getSubdisclineId(disc);     
			    	  subDisc += "," ;
			          subNum += "," ;
			      }
			  }
		     subDisc =  StringUtils.stripEnd(subDisc, ",");
		     subNum =  StringUtils.stripEnd(subNum, ","); 
		     
			 String[] b = { sourceId, journalNameOrg,journalId.toString(),subNum,subDisc};
			 csvOutput.writeNext(b);			  
		}
		try {
			csvOutput.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Number of Jounrnals = " + numOfJournal);
		System.out.println("Matched Jounrnals = " + matchedJournal);
		
	}
}
