package DataAnalysis;

import java.awt.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class exportCSV {
	
	
	 public exportCSV(File file, ArrayList<diffClass> dataList){
			boolean isSucess = false;

			FileOutputStream out = null;
			OutputStreamWriter osw = null;
			BufferedWriter bw = null;
			try {
				out = new FileOutputStream(file);
				osw = new OutputStreamWriter(out);
				bw = new BufferedWriter(osw);
				if (dataList != null && !dataList.isEmpty()) {
					for (diffClass data : dataList) {
						bw.append(data).append("\r");
					}
				}
				isSucess = true;
			} catch (Exception e) {
				isSucess = false;
			} finally {
				if (bw != null) {
					try {
						bw.close();
						bw = null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (osw != null) {
					try {
						osw.close();
						osw = null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (out != null) {
					try {
						out.close();
						out = null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			//return isSucess;
	    }

}
