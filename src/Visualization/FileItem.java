package Visualization;

import java.io.File;

public class FileItem {
    public String fileName;   // file name
    public String firstName;  // file name without type
    public File file;
    public String prefile;
    public String AllPath;
    public String fullName;
    public int type = BAD_FORMAT; // 1, text; -1, unkonw
    
    // type of file
    public static final int TEXT = 1;
    public static final int FOLD = 0;
    public static final int BAD_FORMAT = -1;
    
    public  String suffix;

    //private final String[] txtTypes = { "txt", "java", "xml","md"};

    // function get filename
    
    
	
	public void setAllPath(String AllPath) {
		this.AllPath = AllPath;
	}
	
	public String getAllPath() {
		return AllPath;
	}
	
	
	public void setfullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getfullName() {
		return fullName;
	}
	
	public int limit = 1;
	public void setlimit(int limit) {
		this.limit = limit;
	}
	
	public int getlimit() {
		return limit;
	}
	
    
    
    public FileItem(File file) {
        this.file = file;

        fileName = file.getName();
        firstName = getFileFirstName(fileName);
        fullName = file.getName();

        // Judging the Type of File by File Suffix
        suffix = getFileSuffix(fileName);
        type = BAD_FORMAT;
        //if (contains(txtTypes, suffix))
        
        if (file.isDirectory()) 
        	type = FOLD;
        else
        	type = TEXT;
      
    }

    public void doFile(File file) {
    	this.file = file;
        prefile= file.getAbsolutePath();
     }
    public boolean contains(String[] types, String suffix) {
        suffix = suffix.toLowerCase(); // toLowerCase
        for (String s : types) {
            if (s.equals(suffix))
                return true;
        }
        return false;
    }
    
    // get file suffix
    public String getFileSuffix(String name) {
        int pos = name.lastIndexOf('.');
        if (pos > 0)
            return name.substring(pos + 1);
        
        return ""; 
    }

    // get filename
    public String getFileFirstName(String name) {
        int pos = name.lastIndexOf('.');
        if(pos > 0) {
            return name.substring(0, pos);
        }
        return "";
    }


}
