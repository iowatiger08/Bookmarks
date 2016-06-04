package liquibase.change.ext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * tdillon 
 */
public class CSVReader {

    private BufferedReader br;

    private boolean hasNext = true;

    private char separator;

    private int skipLines;

    private boolean linesSkiped;
    
    private int defaultMaxFieldLength = 2000;

    /** The default separator to use if none is supplied to the constructor. */
    public static final char DEFAULT_SEPARATOR = ',';
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final int DEFAULT_SKIP_LINES = 0;

    public CSVReader(Reader reader) {
        this(reader, DEFAULT_SEPARATOR);
    }

    public CSVReader(Reader reader, char separator) {
        this(reader, separator, DEFAULT_SKIP_LINES);
    }
    
    public CSVReader(Reader reader, char separator, int line) {
        this.br = new BufferedReader(reader);
        this.separator = separator;
        this.skipLines = line;
    }
    
    public List readAll() throws IOException {

        List allElements = new ArrayList();
        while (hasNext) {
            String[] nextLineAsTokens = readNext();
            if (nextLineAsTokens != null)
                allElements.add(nextLineAsTokens);
        }
        return allElements;

    }

    public String[] readNext() throws IOException {

        String nextLine = getNextLine();
        return hasNext ? parseLine(nextLine) : null;
    }

    private String getNextLine() throws IOException {
    	if (!this.linesSkiped) {
            for (int i = 0; i < skipLines; i++) {
                br.readLine();
            }
            this.linesSkiped = true;
        }
        String nextLine = br.readLine();
        if (nextLine == null) {
            hasNext = false;
        }
        return hasNext ? nextLine : null;
    }

    private String[] parseLine(String nextLine) throws IOException {

        if (nextLine == null) {
            return null;
        }

        List tokensOnThisLine = new ArrayList();
        StringBuffer sb = new StringBuffer();

        int charactorCount = 0;
        for (int i = 0; i < nextLine.length(); i++) {
            char c = nextLine.charAt(i);
            if(c == separator ) {
                tokensOnThisLine.add(sb.toString());
                sb = new StringBuffer(); // start work on next token
                charactorCount = 0;
            } else {
            	charactorCount++;
            	if (charactorCount <= defaultMaxFieldLength){
            		sb.append(c);
            	}
            }
        }
        
        tokensOnThisLine.add(sb.toString());
        return (String[]) tokensOnThisLine.toArray(new String[0]);

    }

    /**
     * Closes the underlying reader.
     * 
     * @throws IOException if the close fails
     */
    public void close() throws IOException{
    	br.close();
    }
    
}
