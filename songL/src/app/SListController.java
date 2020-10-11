package app;

/**
 * @author Travis Harrell (tsh61)
 * @author Elizaveta Belaya (edb81)
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Optional;

public class SListController {
	
	//our listView list which will display our songs
	@FXML ListView<Song> listView;
	
	@FXML Button Add;
	@FXML Button Edit;
	@FXML Button Delete;
	
	@FXML TextField Title;
	@FXML TextField Artist;
	@FXML TextField Album;
	@FXML TextField Year;
	
	private ObservableList<Song> obsList;
	
	
	public void start(Stage mainStage) throws NullPointerException{
		
		mainStage.setTitle("Song Library");
		
		obsList = FXCollections.observableArrayList();
		
		listView.setItems(obsList);
		
		try {
			File file = new File("./src/songL.txt");
			BufferedReader br = new BufferedReader(new FileReader(file));
			//String line = br.readLine();
			String line;
			while((line = br.readLine()) != null) {
				String ttl = line;
				String art = br.readLine();
				String alb = br.readLine();
				String yr = br.readLine();
				
				/*System.out.println(ttl);
				System.out.println(art);
				System.out.println(alb);
				System.out.println(yr);*/
				
				Song song = new Song(ttl, art, alb, yr);
				obsList.add(song);
			}
			br.close();
			
		}catch(IOException e) {
			//System.out.println("ouch");
		}//if no file present, for some reason
		
		
		listView.getSelectionModel().select(0);

		// set listener for the items
		listView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> showItemInputDialog(mainStage));
	}
	
	public void addSong(ActionEvent event) {
		Song song = new Song(Title.getText(), Artist.getText(), "", "");
		
		if(Album.getText() != null) {
			song.setAlbum(Album.getText());
		}
		
		if(Year.getText() != null) {
			song.setYear(Year.getText());
		}
		
		Alert a = new Alert(AlertType.ERROR);
		
		//System.out.println("adding");
		
		if((Button) event.getSource() == Add) {//first set of statements check for a valid input
			
			if(Title.getText().isEmpty() && Artist.getText().isEmpty()) {
				a.setTitle("Invalid Input");
				a.setContentText("You didn't even enter a song! A song requires both a title and an artist. (Isn't that obvious? :))");
				a.showAndWait();
			}
			else if(Title.getText().isEmpty()) {
				a.setTitle("Missing Song Title");
				a.setContentText("Please enter the song title.");
				a.showAndWait();
			}
			else if(Artist.getText().isEmpty()) {
				a.setTitle("Missing Song Artist");
				a.setContentText("Please enter the song artist.");
				a.showAndWait();
			}
			else if(Year.getText().length() > 4) {
				a.setTitle("Invalid Year");
				a.setContentText("Please enter a valid year. (Are you getting your music from the future?)");
				a.showAndWait();
			}
			else if((!Title.getText().isEmpty()) && (!Artist.getText().isEmpty())){//if valid input
				boolean isPresent = false;
				for(Song i : obsList){
					if(song.comparison(i) == 0) {
						isPresent = true;
						a.setTitle("Duplicate");
						a.setContentText("This song is already in your library.");
						a.showAndWait();
						break;
					}
				}
				if(isPresent == false) {//if the song isn't a duplicate
					Alert c = new Alert(AlertType.CONFIRMATION);
					c.setTitle("Just to confirm...");
					c.setContentText("Are you sure you would like to add " + Title.getText() + " by " + Artist.getText() + " to the library?");
					//c.showAndWait();
					
					Optional<ButtonType> feedback = c.showAndWait();
					
					if(feedback.get() == ButtonType.OK) {
						obsList.add(song);
						sorted(obsList);
						listWriter(obsList);
					}
				}
			}
			//sort this, first by title, then by artist
		}
	}
	
	public void deleteSong(ActionEvent event) {
		
		Alert c = new Alert(AlertType.CONFIRMATION);
		c.setTitle("Just to confirm...");
		c.setContentText("Are you sure you would like to delete this?");
		
		Optional<ButtonType> feedback = c.showAndWait();
		if(feedback.get() == ButtonType.OK) {
			obsList.remove(listView.getSelectionModel().getSelectedIndex());
			listWriter(obsList);
		}
		//System.out.println("deleting");
		
	}
	
	public void editSong(ActionEvent event) {
		Song selectedSong = listView.getSelectionModel().getSelectedItem();
		Alert a = new Alert(AlertType.ERROR);
		Alert c = new Alert(AlertType.CONFIRMATION);
		
		Song editOfSong = new Song(Title.getText(), Artist.getText(), "", "");
		if(Album.getText() != null) {
			editOfSong.setAlbum(Album.getText());
		}
		
		if(Year.getText() != null) {
			editOfSong.setYear(Year.getText());
		}
		
		if(selectedSong.comparison(editOfSong) == 0){
			if(!selectedSong.equals(editOfSong)) {
				
				c.setTitle("Just to confirm...");
				c.setContentText("Are you sure you would like to edit this?");
				
				Optional<ButtonType> feedback = c.showAndWait();
				
				if(feedback.get() == ButtonType.OK) {
					listView.getSelectionModel().getSelectedItem().setAlbum(editOfSong.album);
					listView.getSelectionModel().getSelectedItem().setYear(editOfSong.year);
					sorted(obsList);
					listWriter(obsList);
					return;
				}
				else {
					return;
				}
			}
		}
		
		boolean isPresent = false;
		
		for(Song i : obsList){
			if(editOfSong.comparison(i) == 0) {
				isPresent = true;
				
				a.setTitle("Duplicate");
				a.setContentText("This song is already in your library.");
				a.showAndWait();
				break;
			}
		}
		if(isPresent == false) {
			c.setTitle("Just to confirm...");
			c.setContentText("Are you sure you would like to edit this?");
			
			Optional<ButtonType> feedback = c.showAndWait();
			
			if(feedback.get() == ButtonType.OK) {
			
				listView.getSelectionModel().getSelectedItem().setTitle(editOfSong.title);
				listView.getSelectionModel().getSelectedItem().setArtist(editOfSong.artist);
				listView.getSelectionModel().getSelectedItem().setAlbum(editOfSong.album);
				listView.getSelectionModel().getSelectedItem().setYear(editOfSong.year);
				sorted(obsList);
				listWriter(obsList);
			}
		}
		
	}
	//public void writeToFile(ObservableList)
	
	private void showItemInputDialog(Stage mainStage) {                
		Song song = listView.getSelectionModel().getSelectedItem();
		
		if(song != null) {
			Title.setText(song.title);
			Artist.setText(song.artist);
			Album.setText(song.album);
			Year.setText(song.year);
		}
		else {
			Title.setText("");
			Artist.setText("");
			Album.setText("");
			Year.setText("");
		}
	}
	
	public void listWriter(ObservableList<Song> list) {
		try {
			File file = new File("./src/songL.txt");
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			//String line;
			for(Song song : list) {
				bw.write(song.title + "\n");
				bw.write(song.artist + "\n");
				bw.write(song.album + "\n");
				bw.write(song.year + "\n");
			
				/*System.out.println(ttl);
				System.out.println(art);
				System.out.println(alb);
				System.out.println(yr);*/
			}
			bw.close();
		}catch(IOException e) {}
	}
	
	//public
	
	public ObservableList<Song> sorted(ObservableList<Song> list) {
		FXCollections.sort(list, new Comparator<Object>() {
			public int compare(Object o1, Object o2) {
				String x1 = ((Song) o1).getTitle();
				String x2 = ((Song) o2).getTitle();
				int stringComp = x1.compareTo(x2);
				
				if(stringComp != 0) {
					return stringComp;
				}
				
				String y1 = ((Song) o1).getArtist();
				String y2 = ((Song) o2).getArtist();
				return y1.compareTo(y2);
			}});
		return list;
	}
	
}
