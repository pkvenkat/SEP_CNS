package Reader;
import Normalizer.Normalizer;

import com.csvreader.CsvReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ReaderCSV implements Reader {
	private HashMap<String, Normalizer>  normalizeMap;  
	//stores the normalized current record
	private HashMap<String, String> recordMap;
	private  CsvReader reader;
	private List<String> columnNames;
	
    public  ReaderCSV(String fileName)
    {
    	
		try {
			reader = new CsvReader(fileName);
			reader.readHeaders();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		   }		
	      catch (IOException e) {
			e.printStackTrace();
		}
		normalizeMap = new HashMap<String, Normalizer>();
		recordMap = new  HashMap<String, String>();
    }
    
    public void setFilter(List<String> columnNames)
    {
    	this.columnNames = columnNames;
    }
    
	@Override
	public boolean readNextRecord() {
		boolean status = false;
		try {
			status = reader.readRecord();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//normalizeRecord();
		return status;
	}

	@Override
	public String get(String coulmnName) {
		    String value= null;
		    try {
		    	value =  reader.get(coulmnName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		    return value;
	}

	@Override
	public void close() {
		reader.close();
	}
	
	
	public void normalizeRecord()
	{
	    for (String colName:columnNames)
	    {
	     String value = get(colName);
	     //normalize
	     if(normalizeMap.containsKey(colName))
	     {
	    	 Normalizer n =normalizeMap.get(colName);
	    	 value = n.normalize(value);	    	 
	     }
	     value = recordMap.put(colName,value);
	      
	    }
	}
	
	public void registerNormalizer(String columnName, Normalizer normalizer )
	{
		if(!normalizeMap.containsKey(columnName))
		{	
	       normalizeMap.put(columnName, normalizer);
		}
	}
	
	public HashMap<String, String> getRecordMap() {
		return recordMap;
	}

	public void setRecordMap(HashMap<String, String> recordMap) {
		this.recordMap = recordMap;
	}
	
}
