
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;


public class AsciiInputStream extends FilterInputStream {
	//private boolean asc;
	private boolean inside;
	
	int c;
	protected AsciiInputStream(InputStream is) {
		super(is);
	}
	
	public int read() throws IOException {
		try {
			do {
				c = super.read();
				if (c=='<') inside = true;
				else if (c == '>') inside = false;
				else if (!inside ) return c;		
			}while (inside || c == '>');		
		}
			catch (Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}
		
		return -1;
		
	}
}
