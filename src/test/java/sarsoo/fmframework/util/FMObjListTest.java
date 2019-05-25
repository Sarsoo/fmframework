package sarsoo.fmframework.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import sarsoo.fmframework.music.Album;
import sarsoo.fmframework.music.Album.AlbumBuilder;
import sarsoo.fmframework.music.Artist;
import sarsoo.fmframework.music.Artist.ArtistBuilder;
import sarsoo.fmframework.music.Track;
import sarsoo.fmframework.music.Track.TrackBuilder;

@DisplayName("FMObjList tests")
public class FMObjListTest {

	@Test
	@DisplayName("empty instantiation not null")
	public void testNotNull() {
		FMObjList list = new FMObjList();
		
		assertNotNull(list);
	}
	
	@Test
	@DisplayName("test get name")
	public void testGetName() {
		String testName = "test name";
		
		FMObjList list = new FMObjList(testName);
		
		assertEquals(testName, list.getGroupName());
	}
	
	@Test
	@DisplayName("test artist total count")
	public void testArtistsGetScrobbleCount() {
		FMObjList list = new FMObjList();
		
		int scrobbleCount = 20;
		int scrobbleCount2 = 30;
		
		Artist artist = new ArtistBuilder("artist name").setUserPlayCount(scrobbleCount).build();
		Artist artist2 = new ArtistBuilder("artist name2").setUserPlayCount(scrobbleCount2).build();
		
		list.add(artist);
		list.add(artist2);
		
		assertEquals(scrobbleCount + scrobbleCount2, list.getTotalUserScrobbles());
	}
	
	@Test
	@DisplayName("test album count ignored with present artist")
	public void testArtistsAlbumsGetScrobbleCount() {
		FMObjList list = new FMObjList();
		
		int artistScrobbleCount = 20;
		int artistScrobbleCount2 = 30;
		
		Artist artist = new ArtistBuilder("artist name").setUserPlayCount(artistScrobbleCount).build();
		Artist artist2 = new ArtistBuilder("artist name2").setUserPlayCount(artistScrobbleCount2).build();
		
		Album album = new AlbumBuilder("album name", artist).setUserPlayCount(10).build();
		
		list.add(artist);
		list.add(artist2);
		list.add(album);
		
		assertEquals(artistScrobbleCount + artistScrobbleCount2, list.getTotalUserScrobbles());
	}
	
	@Test
	@DisplayName("test multiple album count ignored with present artist")
	public void testArtistsMultipleAlbumsGetScrobbleCount() {
		FMObjList list = new FMObjList();
		
		int artistScrobbleCount = 20;
		int artistScrobbleCount2 = 30;
		
		Artist artist = new ArtistBuilder("artist name").setUserPlayCount(artistScrobbleCount).build();
		Artist artist2 = new ArtistBuilder("artist name2").setUserPlayCount(artistScrobbleCount2).build();
		
		Album album = new AlbumBuilder("album name", artist).setUserPlayCount(10).build();
		Album album2 = new AlbumBuilder("album name2", artist2).setUserPlayCount(5).build();
		
		list.add(artist);
		list.add(artist2);
		list.add(album);
		list.add(album2);
		
		assertEquals(artistScrobbleCount + artistScrobbleCount2, list.getTotalUserScrobbles());
	}
	
	@Test
	@DisplayName("test track count ignored with present artist")
	public void testArtistsTrackGetScrobbleCount() {
		FMObjList list = new FMObjList();
		
		int artistScrobbleCount = 20;
		int artistScrobbleCount2 = 30;
		
		Artist artist = new ArtistBuilder("artist name").setUserPlayCount(artistScrobbleCount).build();
		Artist artist2 = new ArtistBuilder("artist name2").setUserPlayCount(artistScrobbleCount2).build();
		
		Track track = new TrackBuilder("track name", artist2).setUserPlayCount(5).build();
		
		list.add(artist);
		list.add(artist2);
		list.add(track);
		
		assertEquals(artistScrobbleCount + artistScrobbleCount2, list.getTotalUserScrobbles());
	}
	
	@Test
	@DisplayName("test track/album count ignored with present artist")
	public void testArtistAlbumTrackGetScrobbleCount() {
		FMObjList list = new FMObjList();
		
		int artistScrobbleCount = 20;
		int artistScrobbleCount2 = 30;
		
		Artist artist = new ArtistBuilder("artist name").setUserPlayCount(artistScrobbleCount).build();
		Artist artist2 = new ArtistBuilder("artist name2").setUserPlayCount(artistScrobbleCount2).build();
		
		Album album = new AlbumBuilder("album name", artist).setUserPlayCount(10).build();
		
		Track track = new TrackBuilder("track name", artist).setAlbum(album).setUserPlayCount(5).build();
		
		list.add(artist);
		list.add(artist2);
		list.add(album);
		list.add(track);
		
		assertEquals(artistScrobbleCount + artistScrobbleCount2, list.getTotalUserScrobbles());
	}
	
	@Test
	@DisplayName("test track count ignored with present album")
	public void testAlbumTrackGetScrobbleCount() {
		FMObjList list = new FMObjList();
		
		int artistScrobbleCount = 20;
		
		Artist artist = new ArtistBuilder("artist name").setUserPlayCount(artistScrobbleCount).build();
		
		Album album = new AlbumBuilder("album name", artist).setUserPlayCount(10).build();
		
		Track track = new TrackBuilder("track name", artist).setAlbum(album).setUserPlayCount(5).build();
		
		list.add(album);
		list.add(track);
		
		assertEquals(10, list.getTotalUserScrobbles());
	}
	
	@Test
	@DisplayName("test multiple tracks count ignored with present album")
	public void testAlbumTracksGetScrobbleCount() {
		FMObjList list = new FMObjList();
		
		int artistScrobbleCount = 20;
		
		Artist artist = new ArtistBuilder("artist name").setUserPlayCount(artistScrobbleCount).build();
		
		Album album = new AlbumBuilder("album name", artist).setUserPlayCount(20).build();
		
		Track track = new TrackBuilder("track name", artist).setAlbum(album).setUserPlayCount(5).build();
		Track track2 = new TrackBuilder("track name2", artist).setAlbum(album).setUserPlayCount(10).build();
		
		list.add(album);
		list.add(track);
		list.add(track2);
		
		assertEquals(20, list.getTotalUserScrobbles());
	}

}
