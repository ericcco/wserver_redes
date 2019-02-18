import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Fil extends Thread{

	OutputStream os;
	InputStream is;
	ServerSocket serverSocket;
	Socket socket2;
	
	public Fil(Socket socketAux) {
		this.socket2 = socketAux;
	}
	

	
	public void run() {
		
		try {
			
			is = socket2.getInputStream();
			os = socket2.getOutputStream();
			
			FileInputStream fis = new FileInputStream("index.html");
			//DataOutputStream dos = new DataOutputStream (os);
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			
			String var = reader.readLine();
						
			String cap = "HTTP/1.1 200 OK\n" + "Content-Type: text/html \n" + "Content-Disposition: filename = index.html \n\n";
			String capAsc = "HTTP/1.1 200 OK \n" + "Content-Type: text/plain \n\n";
			String capZip = "HTTP/1.1 200 OK \n" + "Content-Type: application/zip \n" + "Content-Disposition: filename = index.html.zip \n\n";
			String capGZip = "HTTP/1.1 200 OK \n" + "Content-Type: application/x-gzip \n" + "Content-Disposition: filename = index.html.zip.gz \n\n";
			String capTot = "HTTP/1.1 200 OK \n" + "Content-Type: application/x-gzip \n" + "Content-Disposition: filename = index.html.asc.zip.gz \n\n";
			
			//si hi ha index.html entra dins tot els ifs, sino dw
			
			if ( ((var.indexOf("index.html") >-1 ) && (var.indexOf("asc=true") > -1) && (var.indexOf("zip=true") >-1) && (var.indexOf("gzip=true") >-1)) ){
				
				AsciiInputStream isAsc = new AsciiInputStream(fis);
			 	os.write(capTot.getBytes());
			 	GZIPOutputStream gzos = new GZIPOutputStream(os);
			 	ZipOutputStream zos = new ZipOutputStream(gzos);
			 	ZipEntry ze = new ZipEntry("index.html");
			 	zos.putNextEntry(ze);
			 	int c;
		
			 	while((c = isAsc.read()) != -1) {
				
			 		zos.write(c);
			 	}
			 	
			 	zos.finish();
			 	zos.close();
			 	gzos.finish();
			 	gzos.close();
			}
			
			else if ( ((var.indexOf("index.html") >-1 ) && (var.indexOf("zip=true") >-1) && (var.indexOf("gzip=true") >-1)) ) {
				
				os.write(capGZip.getBytes());
				
				GZIPOutputStream gzos = new GZIPOutputStream(os);
			 	ZipOutputStream zos = new ZipOutputStream(gzos);
			 	ZipEntry ze = new ZipEntry("index.html");
			 	zos.putNextEntry(ze);
		
			 	int c;
			 	while ((c = fis.read()) != -1) {
				
			 		zos.write(c);
			 	}
			
			 	zos.finish();
			 	zos.close();
			 	gzos.finish();
			 	gzos.close();
			}
			
			else if ( (var.indexOf("index.html?zip=true") >-1 ) ) {
				
				 	os.write(capZip.getBytes());
				
				 	ZipOutputStream zos = new ZipOutputStream(os);
				 	ZipEntry ze = new ZipEntry("index.html");
				 	zos.putNextEntry(ze);
				 	
				 	int c;
				 	while ((c = fis.read()) != -1) {
					
				 		zos.write(c);
				 	}
				 	
				 	zos.finish();
				 	zos.close();
				}
				else if ( (var.indexOf("index.html?asc=true") >-1 ) ) {
				
					 	AsciiInputStream isAsc = new AsciiInputStream(fis);
					 	os.write(capAsc.getBytes());
					 	int c;
				
					 	while((c=isAsc.read()) != -1) {
						
					 		os.write(c);
					 	}
					 	
					 	os.close();
					 	fis.close();
					}
					else if(var.indexOf("index.html") > -1) {
						 	
						 	int c;
				
						 	os.write(cap.getBytes());
						 	
						 	while((c = fis.read()) != -1) {
						 		os.write(c);
						 	}
						 	
						 	os.close();
						 	fis.close();
						}
						else {					
							//System.out.println("No has posat cap opció valida");
							os.close();
						}
		}catch(Exception e) {
			System.out.println("NOPE BRO");
		}
	}
}
