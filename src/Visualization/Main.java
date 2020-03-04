package Visualization;

//import java.awt.Color;
import java.awt.Panel;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import DataAnalysis.*;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
//import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


public class Main extends Application {
	
	static public ArrayList<diffClass> a = new ArrayList<>();
	//static List<Double> b = new ArrayList<Double>();
	static String docName = "project/gitdiff4.csv";//the doc which saving data
	static String address = " https://github.com/mrniko/netty-socketio.git ";

    FileListView fileList = new FileListView();
    TabPane tabPane = new TabPane();
    //Stage primaryStage = new Stage();
    String rootfile = "project";
    File path = new File(rootfile);
   
    @Override
    public void start(Stage primaryStage) {
        try {  	
        	
	    	 // file list
            initFileList(path);
            BorderPane root = new BorderPane();
            root.setLeft(fileList);
            root.setCenter(tabPane);
            Scene scene = new Scene(root,800,500);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Editing Hotspots Map");
            primaryStage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public void initFileList(File root){
        fileList.setPrefWidth(300);
        File[] files = root.listFiles();
/*        for(File f : files) {
            FileItem fitem = new FileItem(f);
            //System.out.println(fitem.fileName);
 			if (fitem.type == FileItem.FOLD) {
 				//path=dirTree(path);
 				initFileList(path);
 			}
			else {
				doFile(f);
				fileList.data().add(fitem); // add file to list
			}

		}*/
        for(File f : files) {
           FileItem fitem = new FileItem(f);
           System.out.println(fitem.fileName);

			if (fitem.type == FileItem.FOLD) {
				fileList.data().add(fitem);
				String setpath = path + "/"+ fitem.fileName;
				fitem.setAllPath(setpath);
		        System.out.println(setpath);
		        fitem.fileName = "*" + fitem.fileName;
				//initFileList(f);
			}
           else 
        	   fileList.data().add(fitem); // add file to list 
			
			fitem.fileName = path + "/" + fitem.fileName;
			
			//fileList.data().add(fitem);
        }

        // OnMouseClicked event
        fileList.setOnMouseClicked((MouseEvent event)->{
            if(event.getClickCount() == 1 && event.getButton() == MouseButton.PRIMARY) {
                oneClicked();
            }
        });

    }
        
/* 	public class generallPage extends Application { 
	    @Override
	    public void start(Stage primaryStage) {
	    	 // file list
            initFileList(path);
            BorderPane root = new BorderPane();
            root.setLeft(fileList);
            root.setCenter(tabPane);
            Scene scene = new Scene(root,800,500);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Editing Hotspots Map");
            primaryStage.show();
	    }    
	}*/
    


    // click event
    public void oneClicked() {

        // get the item clicked
        int index = fileList.getSelectionModel().getSelectedIndex();
        FileItem fitem = fileList.data().get(index);
		if (fitem.type == FileItem.TEXT) {
			
			// FileItem fitem = fileList.data().get(index);
			fitem = fileList.data().get(index);
			try {
				openFile(fitem);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		else if(fitem.type == FileItem.FOLD) {	
			
			//String subpath = path + "/"+ fitem.fileName;
			String subpath=fitem.getAllPath();
			System.out.println(subpath);
			path=new File(subpath);
			initFileList(path);
											
		}			
		
    }
    
    

    // open file
    public void openFile(FileItem fitem) throws Exception{

        Tab tab = findTab(fitem);
        if(tab != null) {

            // selected tab
            // int pos = tabPane.getTabs().indexOf(tab);  // id
            tabPane.getSelectionModel().select(tab);
            return;
        }
        // new tab selected
        Node currentView = null;
        BorderPane b = new BorderPane();
        ScrollPane scrollp = new ScrollPane();
        Group g = new Group();
        TextFlow t = new TextFlow();
        
        if(fitem.type == FileItem.TEXT) {
            // txt
            String str = TextFileUtils.read(fitem.file, "GBK");           
            
           // TextArea t = new TextArea();                  
           // ArrayList<Integer> positions=new ArrayList<Integer>();  
                    
            ArrayList<diffClass> tem = new ArrayList<diffClass>();           
            for(diffClass oneClass:a) {            	
            	//int posDot = oneClass.getFilename().lastIndexOf('.');
            	int posL=oneClass.getFilename().lastIndexOf('/');            	
            	
            	String filename = oneClass.getFilename().substring(posL+1);
            	//System.out.println(fitem.fileName+" : "+filename);
            	
            	if(fitem.fullName.equals(filename)) {
            		//positions.add(Integer.parseInt(oneClass.getChangedLines())-1);
            		tem.add(oneClass);
            		if(fitem.getlimit()<oneClass.getmaxcount()) {
            			fitem.setlimit(oneClass.getmaxcount());           			
            		}
            	}            	
            }
                       
            String[] strLines=str.split("\n");            
            //List<Text> texts = new ArrayList<Text>();           
            for(int i=0;i<strLines.length;i++) {      
          	
            	Text text =new Text(strLines[i]+"\n");
    			Color textColor2 = new Color(0,0.5,0,1);            			
    			text.setFill(textColor2); 
            	String a =  strLines[i]+"\n";
            	//for(int position:positions) {
 
            	for(diffClass oneDiff:tem) {
            		int position = Integer.parseInt(oneDiff.getChangedLines())-1;            		
            		if( i == position) {             	
            			String iii = String.valueOf(oneDiff.getCount());
            			double count = oneDiff.getCount();
            			//System.out.println(fitem.getlimit());
            			
            			double limitcolor = fitem.getlimit();
            			double colorNo=count/limitcolor;
            			System.out.println(colorNo);
            			Color textColor = new Color(colorNo,0,0,1);             			            			
            			text.setFill(textColor);           			
            			Text text2 = new Text("Changed time:" + iii+ "      ");
                      	//t.getChildren().add(text);
                    	//t.setStyle("-fx-background-color: blue");  
            			//Rectangle r = new Rectangle();
            			//r.setFill(Color.BLUE);           			
            			t.getChildren().add(text2);    
            			           			
            		}
            		//t.getChildren().addAll(0,texts);
            		
            	}        
            	t.getChildren().add(text);    	
           	
            	//texts.add(text);     
            	//t.getChildren().addAll(0,texts);            	
            }	            
                //Retrieving the observable list of the TextFlow Pane 
            g.getChildren().add(t);
               //t.getChildren().addAll(0,texts);             
           // t.setText(str);            
            //currentView = t;

        }
        else throw new Exception("unknow file type!");

        //creat new tab & select it
        scrollp.setContent(g);
        
        currentView = scrollp;
        tab = new Tab();
        tab.setText(fitem.fileName);
        tab.setContent(currentView);
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }


    // Check if the file is open on the right tab
    public Tab findTab(FileItem fitem) {
        ObservableList<Tab> tabs = tabPane.getTabs();
        for(Tab tab : tabs) {
            if(tab.getText().equals(fitem.firstName)) {
                return tab;
            }
        }
        return null;
    }

    public static void main(String[] args) {
    	
    	RunShell rs = new RunShell(address);
		readCSV datainput = new readCSV(docName);
		
		//ArrayList<diffClass> a = new ArrayList<>(); 
		 
		a = datainput.dflist;
		exportCSV dataoutput = new exportCSV(new File("CSVwithCount.csv"),a);
    	
        launch(args);
    }
}
