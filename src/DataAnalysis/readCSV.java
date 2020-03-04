package DataAnalysis;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

//import com.sun.media.jfxmedia.events.NewFrameEvent;

import sheffield.*;


/**
 * reading doc from local
 * 
 */

public class readCSV {

	private EasyReader easyReaders;
	public ArrayList<diffClass> dflist = new ArrayList<>();
	public ArrayList<diffClass> dflist2 = new ArrayList<>();
	ArrayList<String> hashList = new ArrayList<>();
	ArrayList<String> fileList = new ArrayList<>();
	ArrayList<String> changedlinesList = new ArrayList<>();
	ArrayList<String> contentList = new ArrayList<>();


	public readCSV(String docName) {

		try {		
			easyReaders = new EasyReader(docName);//read doc
			boolean sign = false; //jump the first line
			while (easyReaders.ready()) {
				String line = easyReaders.readLine();
				StringTokenizer st = new StringTokenizer(line, ",");//  ,  as sign
				String hash, filename,changedLines,content;


				if (st.hasMoreTokens() && sign) {
					hash = String.valueOf(st.nextToken().trim());
					filename = String.valueOf(st.nextToken().trim());
					changedLines = String.valueOf(st.nextToken().trim());//iterate and get value
					content= String.valueOf(st.nextToken().trim());
					
					diffClass DF = new diffClass(hash, filename, changedLines);
					
					
					boolean ifcontain = false;
					
					for(diffClass showdata : dflist){
		                //System.out.println(showdata);
		            	if(DF.getHash().equals(showdata.getHash())&&DF.getChangedLines().equals(showdata.getChangedLines())) 
		            	{
		            		showdata.setCount(showdata.getCount()+1);
		            		showdata.setmaxcount(showdata.getmaxcount()+1);
		            		ifcontain=true;
		    				break;
		    			}

		            }					
					if(!ifcontain) {
						dflist.add(DF);
					}
				
					/**
					 * save date in list by category
					 * 
					 */
					hashList.add(DF.getHash());
					fileList.add(DF.getFilename());
					changedlinesList.add(DF.getChangedLines());
					
					
/*		            if(dflist!=null && !dflist.isEmpty()){
		                for(diffClass data : dflist){
		                    System.out.println(data);
		                }
		            }*/
		            
		            
/*		            if(hashList!=null && !hashList.isEmpty()){
		            	int i=0;
		                for(String showdata : hashList){
		                    //System.out.println(showdata);
		                    i++;
		                }
		                System.out.println(i);
		            }*/
					

				} else {
					sign = true;
				}
			}
			easyReaders.close();
		}

		catch (FileNotFoundException ee) {
			ee.printStackTrace();
		} catch (IOException ee2) {
			ee2.printStackTrace();
		}
	}

}
