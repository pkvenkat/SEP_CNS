package jFraction;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


public class SEPDisciplineJournalName {
	private String sEPref;
	private String subDiscipline ;
	private String journalName;
	private Double jFraction;
	
	public SEPDisciplineJournalName(String sEPref, String subDiscipline, String journalName,double jFraction)
	{
		this.sEPref =sEPref;
		this.subDiscipline= subDiscipline;
		this.journalName = journalName;
		this.jFraction =jFraction; 
	}
	 public int hashCode() {
	     // you pick a hard-coded, randomly chosen, non-zero, odd number
	     // ideally different for each class
	     return new HashCodeBuilder(17, 37).
	       append(sEPref).
	       append(subDiscipline).
	       toHashCode();
	   }
	
	 public boolean equals(Object obj) {
	        if (obj == null)
	            return false;
	        if (obj == this)
	            return true;
	        if (obj.getClass() != getClass())
	            return false;

	        SEPDisciplineJournalName rhs = (SEPDisciplineJournalName) obj;
	        return new EqualsBuilder().
	            // if deriving: appendSuper(super.equals(obj)).
	            append(sEPref, rhs.sEPref).
	            append(subDiscipline, rhs.subDiscipline).
	            isEquals();
	 }

	public String getSEPref() {
		return sEPref;
	}

	public void setSEPref(String sEPref) {
		this.sEPref = sEPref;
	}

	public String getSubDiscipline() {
		return subDiscipline;
	}

	public void setSubDiscipline(String subDiscipline) {
		this.subDiscipline = subDiscipline;
	}
	public String getJournalName() {
		return journalName;
	}
	public void setJournalName(String journalName) {
		this.journalName = journalName;
	}
	public Double getjFraction() {
		return jFraction;
	}
	public void setjFraction(Double jFraction) {
		this.jFraction = jFraction;
	}
	 
	 
	 
	 

}
