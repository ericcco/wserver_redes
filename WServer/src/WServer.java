import java.net.*;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.io.*;

public class WServer {
	
	public static void main (String[] args) {
		
		try {
			
			ServerSocket serverSocketAux = new ServerSocket(8080);
			Socket socketAux = new Socket();
			
			while(true) {
				socketAux = serverSocketAux.accept();
				
				Fil f = new Fil(socketAux);
				f.start();
			}
			
		}catch(Exception e) {
			System.out.println("Wrong hole");
		}
	}
}
