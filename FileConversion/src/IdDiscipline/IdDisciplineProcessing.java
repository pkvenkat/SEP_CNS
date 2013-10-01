package IdDiscipline;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import Normalizer.CaseNormalizer;
import Normalizer.GeneralNormalizer;
import Reader.ReaderCSV;

public class IdDisciplineProcessing {	
		
		private ReaderCSV idsReader ;
		private ReaderCSV subReader ;
		
		private HashMap<Long,String>  idtoSubDisipline ;
		private HashMap<Long,List<Long>>  journalIdToSubId;
		private HashMap<String,Long>  subToIdDisipline ;
		
		// these are for the mappped file
		private String mapJournalIdFieldName;
		private String mapDiscIdFieldName;
		
		// These two are for the sub discipline files  
		private String discIdfield;
	

		private String discNameField;
		
		
		public IdDisciplineProcessing()
		{
			idtoSubDisipline = new  HashMap<Long,String>();
			journalIdToSubId = new HashMap<Long,List<Long>>();
			subToIdDisipline = new  HashMap<String,Long>();
			idsReader = new ReaderCSV("C:/Documents and Settings/pkvenkat/My Documents/DiggingData/subd_journal.csv");
			subReader = new ReaderCSV("C:/Documents and Settings/pkvenkat/My Documents/DiggingData/subdisciplines.csv");		
		}
		
		public IdDisciplineProcessing(String jIdToSubDiscpMapFile, String subDiscpFile )
		{
			idtoSubDisipline = new  HashMap<Long,String>();
			journalIdToSubId = new HashMap<Long,List<Long>>();
			idsReader = new ReaderCSV(jIdToSubDiscpMapFile);
			subReader = new ReaderCSV( subDiscpFile);
			subToIdDisipline = new  HashMap<String,Long>();
		}
		
		public void read()
		{
			readIdsMapData();
			readSubDisciplineData();
		}
		
	
		public void  readIdsMapData()
		{
	        List<String> array = Arrays.asList("journ_id","subd_id");			   
	        idsReader.setFilter(array);
		    while(idsReader.readNextRecord()) {
		    	idsReader.normalizeRecord();
		    	 HashMap<String, String> map = idsReader.getRecordMap();		  
				  Long journal_id= Long.parseLong(map.get("journ_id"));
				  Long subDisc_id= Long.parseLong(map.get("subd_id"));
				 
				  if(!journalIdToSubId.containsKey(journal_id))
				  {
					  List<Long> list = new ArrayList<Long>();
					  list.add(subDisc_id);
					  journalIdToSubId.put(journal_id, list);
				  }
				  else 
				  {
					  journalIdToSubId.get(journal_id).add(subDisc_id);
					  
				  }
		    }
		    idsReader.close();
		}
		
	
		public void  readSubDisciplineData()
		{

	        List<String> array = Arrays.asList("subd_id","subd_name");			   
	        		subReader.setFilter(array);
		    while(subReader.readNextRecord()) {
		    	subReader.normalizeRecord();
		    	 HashMap<String, String> map = subReader.getRecordMap();		  
				  Long subdiscpId= Long.parseLong(map.get("subd_id"));
				  String subdiscpName= map.get("subd_name");
				  if(!idtoSubDisipline.containsKey(subdiscpId))
				  {
					  idtoSubDisipline.put(subdiscpId, subdiscpName);
					  subToIdDisipline.put(subdiscpName, subdiscpId);
				  }	    	 
		    }
		    subReader.close();
		}
		
		public List<String> getSubdisclineName(Long journalId)
		{ 
			if(journalIdToSubId.containsKey(journalId))
			{
			    List<Long> subIds =journalIdToSubId.get(journalId);
			    List<String> subDiscNames = new ArrayList<String>();
 			    for(Long subId:subIds )
			    {
 			    	subDiscNames.add(idtoSubDisipline.get(subId));
 			    	
			    }
 			    return subDiscNames;
			}
			return null;
		}			
		
		public Long getSubdisclineId(String subName)
		{ 			
			return subToIdDisipline.get(subName);
		}	
		public String getMapJournalIdFieldName() {
			return mapJournalIdFieldName;
		}

		public void setMapJournalIdFieldName(String mapJournalIdFieldName) {
			this.mapJournalIdFieldName = mapJournalIdFieldName;
		}

		public String getMapDiscIdFieldName() {
			return mapDiscIdFieldName;
		}

		public void setMapDiscIdFieldName(String mapDiscIdFieldName) {
			this.mapDiscIdFieldName = mapDiscIdFieldName;
		}

		public String getDiscIdField() {
			return discIdfield;
		}

		public void setDiscIdField(String discIdfield) {
			this.discIdfield = discIdfield;
		}

		public String getDiscNameField() {
			return discNameField;
		}

		public void setDiscNameField(String discNameField) {
			this.discNameField = discNameField;
		}
	}	
	
