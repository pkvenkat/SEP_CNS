import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


public class SEPDiscipline {
	private String sEPref;
	private String subDiscipline ;
	
	public SEPDiscipline(String sEPref, String subDiscipline)
	{
		this.sEPref =sEPref;
		this.subDiscipline= subDiscipline;
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

	        SEPDiscipline rhs = (SEPDiscipline) obj;
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
	 
	 
	 
	 

}
