package Visualization;

//import test.FileListView.MyListCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
/*
 * �����ļ��б�
 */
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class FileListView extends ListView<FileItem> {
		
    private ObservableList<FileItem> listData = FXCollections.observableArrayList();
    
    public FileListView() {
        setItems(listData);

        // generate
        setCellFactory(new Callback<ListView<FileItem>, ListCell<FileItem>>()
        {
        	
            @Override
            public ListCell<FileItem> call(ListView<FileItem> param)
            {
                return new MyListCell();
            }
        });
    }

    // get data source
    public ObservableList<FileItem> data() {
        return listData;
    }
    // show filename in cell
    static class MyListCell extends ListCell<FileItem> {
        @Override
        protected void updateItem(FileItem item, boolean empty) {
            // using FX framework before must use super.updateItem()
        	       	
            super.updateItem(item, empty);
            if (item == null) {
                this.setText("");
            } else {
                this.setText(item.fileName);// left tab text fill
                
            }
        }
    }

}
