package uk.co.optimisticpanda.randmp3;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.io.File;
import javafx.util.Duration;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.scene.media.MediaPlayer;
import uk.co.optimisticpanda.randmp3.MediaChooser.Track;

@SuppressWarnings("restriction")
class Player {

	private final AtomicBoolean toggle = new AtomicBoolean(true);
	private final MediaChooser chooser;
	private final Random random = new Random();
	private final Duration maxSampleLength;
	private MediaPlayer currentPlayer;
	private Track track;
	
	Player(File rootDir, Duration maxSampleLength) {
		this.chooser = new MediaChooser(rootDir);
		this.maxSampleLength = maxSampleLength;
	}

	void play() {
		this.toggle.set(true);
		this.track = chooser.nextTrack();
		this.currentPlayer = new MediaPlayer(track.getMedia());
		int sampleLength = getSampleLength();
		currentPlayer.setOnPlaying(() -> {
			while (toggle.get()) {
				try {
					currentPlayer.seek(track.durationToSkip());
					int i = sampleLength / 100;
					while (i-- > 0 && toggle.get()) {
						MILLISECONDS.sleep(100);
					}
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		});

		currentPlayer.setOnReady(() -> {
			currentPlayer.play();
		});
		
	}

	void stop() {
		toggle.set(false);
		CountDownLatch latch = new CountDownLatch(1);
		currentPlayer.setOnStopped(() -> {
			latch.countDown();
		});
		currentPlayer.stop();
		try {
			latch.await(2, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	
	void printSampleStartTime() {
		System.out.println(track.getDescription() + "\n\n");
		System.out.println("######################################");
	}

	private int getSampleLength() {
		int length = random.nextInt((int)maxSampleLength.toMillis());
		System.out.println("Starting new track, sample length: " + Duration.millis(length).toSeconds() + " seconds; For track: \"" + track.getName() + "\"");
		return length;
	}
}