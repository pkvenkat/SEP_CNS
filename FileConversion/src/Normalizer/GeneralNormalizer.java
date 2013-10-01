package Normalizer;

public class GeneralNormalizer implements Normalizer {

	@Override
	public String  normalize(String str) {
		  String output = str.toLowerCase();
		  output =output.replaceAll("[^a-zA-Z0-9\\s]", "");
		  return output;
	}

}
