package DataAnalysis;

public class diffClass implements CharSequence {
	
	public String hash;
	private String filename;
	private String changedLines;

	private int count=1;
	private int maxcount = 1;
	

	public diffClass(String hash, String filename, String changedLines) {
		this.hash = hash;
		this.filename = filename;
		this.changedLines = changedLines;
	}
	
	@Override
	public String toString() {
		return "diffClass [hash=" + hash + ", filename=" + filename + ", changedLines=" + changedLines + ", count="
				+ count + ", max=" + maxcount + "]";
	}
	
/*	diffClass(String hash, String filename, String changedLines,int count) {
		this.hash = hash;
		this.filename = filename;
		this.changedLines = changedLines;
		this.count= count;
	}
	*/
	
	public String getHash() {
		return this.hash;
	}
	


	public void setHash(String hash) {
		this.hash = hash;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
	public int getCount() {
		return count;
	}

	public String getChangedLines() {
		return changedLines;
	}

	public void setChangedLines(String changedLines) {
		this.changedLines = changedLines;
	}

	public void setCount(int count) {
		this.count = count;
	}

	
	public void setmaxcount(int maxcount) {
		this.maxcount = maxcount;
	}
	
	public int getmaxcount() {
		return maxcount;
	}
	@Override
	public char charAt(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int length() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}


}
