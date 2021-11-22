package up.visulog.gui.screens;

import java.awt.FontFormatException;
import java.io.IOException;

import javax.swing.JFrame;
import up.visulog.gui.components.HomeComponents;
public class HomeScreen extends JFrame {
    public HomeScreen() throws FontFormatException, IOException {
        HomeComponents.setGridBagLayout(this, "DinoLog - Home", "src/main/resources/dinosaur.png");
    }
}