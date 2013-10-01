package Normalizer;
import org.apache.commons.lang3.StringUtils;
public class CaseNormalizer implements Normalizer {

	@Override
	public String normalize(String str) {
		 String out = str.toLowerCase();
		 out = out.replaceAll("&", "and");
		 out = out.replaceAll(",", " ");
		 out = out.replaceAll("û","u");
		 //replace double space with space
		/* out = out.replaceAll("\\bthe \\b", "");*/			
		 out = StringUtils.stripStart(out, "\"");	
		 out = StringUtils.stripEnd(out, "\"");
		 out = StringUtils.stripEnd(out, "s");
		 out = out.replaceAll("[^a-zA-Z0-9\\s]", "");
		 out = StringUtils.trim(out);
		 if(out.startsWith("the"))
		 {	
		     out =  out.substring(3);
		 }
		 out = StringUtils.trim(out);
		 if(out.startsWith("journal of"))
		 {	
		     out = out.substring(10);
		 }
		 out = StringUtils.trim(out);
		if(out.endsWith(" the"))
		{
			out = out.substring(0,out.length()-4 );
		}
		 out = StringUtils.trim(out);		 		 
		 return out;
	}

}
