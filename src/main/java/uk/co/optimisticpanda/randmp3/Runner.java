package uk.co.optimisticpanda.randmp3;

import static javax.swing.SwingUtilities.invokeLater;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import javafx.util.Duration;
import javafx.embed.swing.JFXPanel;

@SuppressWarnings("restriction")
public class Runner {

	public static void main(String[] args) throws InterruptedException {

		Player player = new Player(new File("/home/alee/Desktop/Cowboy Bebop - Original Soundtrack/"), Duration.seconds(10));

		invokeLater(() -> {
			new JFXPanel();

			try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

				boolean quit = false;
				while (!quit) {
					
					player.play();
					
					if (reader.readLine().equals("quit")) {
						quit = true;
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
