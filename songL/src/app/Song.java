package app;

/** 
 * @author Travis Harrell (tsh61)
 * @author Elizaveta Belaya (edb81)
 */


public class Song {

	public String title;
	public String artist;
	public String album;
	public String year;
	
	public Song(String ttl, String art, String alb, String yr) {
		title = ttl;
		artist = art;
		album = alb;
		year = yr;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getArtist() {
		return artist;
	}
	
	public String getAlbum() {
		return album;
	}
	
	public String getYear() {
		return year;
	}
	
	public void setTitle(String ttl) {
		title = ttl;
	}
	
	public void setArtist(String art) {
		artist = art;
	}
	
	public void setAlbum(String alb) {
		album = alb;
	}
	
	public void setYear(String yr) {
		year = yr;
	}
	
	//returns 0 if equal, < 0 if lexicographically less than input, > 0 if greater than input
	public int comparison(Song input) {
		if(this.title.equals(input.title)) {
			return this.artist.toLowerCase().compareTo(input.artist.toLowerCase());
		}
		else {
			return this.title.toLowerCase().compareTo(input.title.toLowerCase());
		}
	}
	
	@Override
	public String toString() {
		return (this.title + " - " + this.artist);
	}
	
	/*@Override
	public boolean equals(Object o) {
		Song otherSong = (Song) o;
		if(this.title.equals(otherSong.title) && this.artist.equals(otherSong.artist) && this.album.equals(otherSong.album) && this.year.equals(otherSong.year)) {
			return true;
		}
		else {
			return false;
		}
	}*/
	
}
