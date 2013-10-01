import jFraction.SEPDisciplineJournalName;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import journalReader.JournalNameIdReader;

import au.com.bytecode.opencsv.CSVWriter;
import IdDiscipline.IdDisciplineProcessing;
import Normalizer.CaseNormalizer;
import Reader.ReaderCSV;


public class JFractionCreator {
	
	private HashMap<SEPDiscipline,Double> finalSet;
	private List<SEPDisciplineJournalName>  dataArray;
	 
	public JFractionCreator()
	{
		finalSet = new HashMap<SEPDiscipline,Double>();
		dataArray = new ArrayList<SEPDisciplineJournalName>();
	}
		
	public void add(String SEPEntry, String subDiscipline, Double fraction)
	{
		SEPDiscipline sepObj = new SEPDiscipline(SEPEntry, subDiscipline);
		if(finalSet.containsKey(sepObj))
		{
			Double val = finalSet.get(sepObj);
			/*if( fraction + val > 1.0)
				finalSet.put(sepObj, 1.0);
			else */
				finalSet.put(sepObj, fraction + val);				
		}
		else 
		{
			finalSet.put(sepObj, fraction);
		}
	}
	public void add(String SEPEntry, String subDiscipline, Double fraction, String journalName)
	{
		SEPDisciplineJournalName sepObj = new SEPDisciplineJournalName(SEPEntry, subDiscipline,journalName,fraction);
		dataArray.add(sepObj);
	}
	

	public HashMap<SEPDiscipline, Double> getFinalSet() {
		return finalSet;
	}

	public void setFinalSet(HashMap<SEPDiscipline, Double> finalSet) {
		this.finalSet = finalSet;
	}

	public List<SEPDisciplineJournalName> getDataArray() {
		return dataArray;
	}

	public void setDataArray(List<SEPDisciplineJournalName> dataArray) {
		this.dataArray = dataArray;
	}

}
