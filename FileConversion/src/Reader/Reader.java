package Reader;

public interface Reader {
	
       boolean readNextRecord();
       String get(String coulmnName);
       void close();
     }
