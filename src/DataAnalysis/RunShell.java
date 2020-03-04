package DataAnalysis;

import java.io.BufferedReader; 
import java.io.InputStreamReader; 
  
public class RunShell {
	//public static void main(String[] args) {
		
	public RunShell(String paramater){
		try {
			String shpath = "collectData.sh";
			//String paramater = " https://github.com/mrniko/netty-socketio.git ";
			String[] cmd = { "/bin/sh", "-c", shpath + paramater };

			Process ps = Runtime.getRuntime().exec(cmd);
			ps.waitFor();

			BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
			String result = sb.toString();
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}