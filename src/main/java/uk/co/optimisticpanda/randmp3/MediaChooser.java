package uk.co.optimisticpanda.randmp3;

import static java.lang.Math.max;
import static javafx.util.Duration.seconds;

import java.io.File;
import java.util.Random;

import javafx.scene.media.Media;
import javafx.util.Duration;

@SuppressWarnings("restriction")
class MediaChooser {

	private final File[] files;
	private final Random r = new Random();

	MediaChooser(File rootDir) {
		this.files = rootDir.listFiles(file -> file.getName().endsWith(".mp3"));
	}
	
	Track chooseNextTrack() {
		File file = files[r.nextInt(files.length)];
		return new Track(file);
	}
	
	static class Track {
		
		private final Media media;
		private final File file;
		private final Random r = new Random();
		private Duration skipDuration;

		private Track(File file) {
			this.file = file;
			this.media = new Media(file.toURI().toString());
		}

		Media getMedia() {
			return media;
		}

		File getFile() {
			return file;
		}

		String getName() {
			return file.getName();
		}

		
		String getDescription() {
			return "That sample started at " + (skipDuration == null ? "unknown": skipDuration.toSeconds()) + " seconds";
		}

		synchronized Duration chooseNewDurationToSkip(Duration maxSampleSize) {
			double seconds = media.getDuration().toSeconds();
			this.skipDuration = seconds(r.nextInt(max(1, (int)seconds - (int) maxSampleSize.toSeconds() + 1)));
			return this.skipDuration;
		}
		
		synchronized Duration durationToSkip(Duration maxSampleSize) {
			if (skipDuration == null) {
				chooseNewDurationToSkip(maxSampleSize);
			}
			return skipDuration;
		}
	}
}
