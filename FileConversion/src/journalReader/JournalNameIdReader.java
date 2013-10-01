package journalReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import Normalizer.CaseNormalizer;
import Normalizer.GeneralNormalizer;
import Normalizer.Normalizer;
import Reader.ReaderCSV;


public class JournalNameIdReader {
	
	private ReaderCSV reader ;
	private HashMap<String,Long>  journalNametoId ;
	private String journalIdField;
	private String journalNameField;
	private Normalizer norm;

	public JournalNameIdReader()
	{
		journalNametoId = new  HashMap<String,Long>();
		reader = new ReaderCSV("C:/Documents and Settings/pkvenkat/My Documents/DiggingData/journal_names.csv");
	}

	public JournalNameIdReader(String file, String journalNameField, String journalidField)
	{
		journalNametoId = new  HashMap<String,Long>();
		reader = new ReaderCSV(file);
		this.journalNameField = journalNameField;
		this.journalIdField = journalidField;
	}	
	
	public void setNormalizer(Normalizer norm)
	{
	     this.norm = norm;
	}
	
	public void  readAndStoreData()
	{
        List<String> array = Arrays.asList("journ_id","journal_name");         
	     reader.registerNormalizer("journal_name", new CaseNormalizer());    
	    reader.setFilter(array);
	    while(reader.readNextRecord()) {
	    	reader.normalizeRecord();
	    	 HashMap<String, String> map = reader.getRecordMap();		  
			  Long id= Long.parseLong(map.get("journ_id"));
			
			  String journalName = map.get("journal_name");
			  if(id ==11056)
			  {
				 System.out.println("journalName =" +journalName); 
			  }
			  if(!journalNametoId.containsKey(journalName))
			  {
				  journalNametoId.put(journalName, id);
			  }	    	 
	    }
	    reader.close(); 
	    
	}
	
	public void  read()
	{
        List<String> array = Arrays.asList(journalIdField,journalNameField);
        if(norm != null)
        {
	        reader.registerNormalizer(journalNameField, norm);
        }
	    reader.setFilter(array);
	    while(reader.readNextRecord()) {
	    	reader.normalizeRecord();
	    	 HashMap<String, String> map = reader.getRecordMap();		  
			  Long id= Long.parseLong(map.get(journalIdField));
			  String journalName = map.get(journalNameField);
			  if(!journalNametoId.containsKey(journalName))
			  {
				  journalNametoId.put(journalName, id);
			  }	    	 
	    }
	    reader.close();
	}
	
	public Long getId(String journalName)
	{
	    if(journalNametoId.containsKey(journalName))
	    {
	    	return journalNametoId.get(journalName);
	    }
	    return new Long(-1);
	}
	

	public String getJournalNameField() {
		return journalNameField;
	}

	public void setJournalNameField(String journalNameField) {
		this.journalNameField = journalNameField;
	}
	
	public HashMap<String, Long> getJournalNametoId() {
		return journalNametoId;
	}

	public void setJournalNametoId(HashMap<String, Long> journalNametoId) {
		this.journalNametoId = journalNametoId;
	}
	
	public String getJournalIdField() {
		return journalIdField;
	}


	public void setJournalIdField(String journalIdField) {
		this.journalIdField = journalIdField;
	}
}
