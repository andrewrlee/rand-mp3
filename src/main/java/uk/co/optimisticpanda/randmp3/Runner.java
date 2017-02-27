package uk.co.optimisticpanda.randmp3;

import static java.lang.Integer.parseInt;
import static javax.swing.SwingUtilities.invokeLater;
import static uk.co.optimisticpanda.randmp3.Util.checkState;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javafx.embed.swing.JFXPanel;
import javafx.util.Duration;

@SuppressWarnings("restriction")
public class Runner {

	public static void main(String... args) throws InterruptedException {
		checkState(() -> args.length == 2, "Usage: java -jar rand-mp3.jar <file-path> <longest sample in seconds>");
		checkState(() -> new File(args[0]).exists(), "First argument needs to be a file that exists");
		checkState(() -> new File(args[0]).isDirectory(), "First argument needs to be a directory");
		checkState(() -> parseInt(args[1]) > 0, "Second argument needs to be a number greater than 0");

		Player player = new Player(new File(args[0]), Duration.seconds(parseInt(args[1])));

		invokeLater(() -> {
			new JFXPanel();

			try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

				boolean quit = false;
				while (!quit) {

					player.play();

					switch (reader.readLine().trim()) {
					case "quit": case "q":
						quit = true;
						break;
					case "r":
						player.newSampleFromSameFile();
						break;
					case "f":
						player.newSampleFromNewFile();
						break;
					}
					player.printSampleStartTime();
					player.stop();
				}
				System.out.println("bye!");
				System.exit(0);

			} catch (IOException e) {
				throw new RuntimeException("problem:" + e.getMessage(), e);
			}
		});
	}
}
