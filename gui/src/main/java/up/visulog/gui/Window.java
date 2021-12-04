package up.visulog.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontFormatException;
import java.io.IOException;
import java.nio.file.Path;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Commit;
import up.visulog.gui.screens.GraphScreen;
import up.visulog.gui.screens.HomeScreen;
import up.visulog.gui.screens.ResultsScreen;
import up.visulog.gui.screens.StatScreen;

public class Window extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private ResultsScreen resultsScreen;
	private Configuration config;
	private String projectName;
	
	public Window(String projectName, int w, int h) throws FontFormatException, IOException {
		this.projectName = projectName;
		this.setIconImage(new ImageIcon("src/main/resources/dinosaur.png").getImage());
		this.setMinimumSize(new Dimension(w, h)); // On change la taille minimum de la fenetre.
		this.setResizable(true); // true -> la page est redimensionnable.
		this.setLocationRelativeTo(null); // null -> permet de centrer la fenetre au milieu de l'ecran.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // On indique qu'il faut que le programme s'arrete lorsqu'on ferme la fenetre.
		/* Exemple pour arrondir les coins. Mais tous les boutons disparaissent
		 * et la page n'est plus redimensionable. On ne peut plus la bouger non plus.
		this.setUndecorated(true);
		this.setBackground(new Color(0, 0, 0, 180));
		this.addComponentListener(new ComponentAdapter() {
		               @Override
		                public void componentResized(ComponentEvent e) {
		                    setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 80, 80));
		                }
		            });
		*/ 
		
		//setDefaultLookAndFeelDecorated(true);
		//this.setExtendedState(JFrame.MAXIMIZED_BOTH); // Met la fenetre en plein ecran.
		
		int padding = 15;
		((JComponent) getContentPane()).setBorder(new EmptyBorder(padding, padding, padding, padding));
		getContentPane().setBackground(new Color(88,205,113));
		
		openHomeScreen();
		
		this.setVisible(true); // On affiche la fenetre.
	}

	public void backToHomeScreen() throws FontFormatException, IOException {
		openHomeScreen();
	}
	
	public void openHomeScreen() throws FontFormatException, IOException {
		this.getContentPane().removeAll(); // On vide le panel principal.
		getContentPane().add(new HomeScreen(this));
		revalidate();
		repaint();
	}
	
	public void backToResultsScreen() throws FontFormatException, IOException {
		openResultsScreen();
	}
	
	public void openResultsScreen() throws FontFormatException, IOException {
		if(resultsScreen == null) {
			var gitlog = Commit.parseLogFromCommand(config.getGitPath());
			resultsScreen = new ResultsScreen(this, gitlog, config);
		}
		
		this.getContentPane().removeAll(); // On vide le panel principal.
		JScrollPane jScrollPane = new JScrollPane(resultsScreen);
		jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jScrollPane.setBorder(BorderFactory.createEmptyBorder());
		// getContentPane().add(resultsScreen);
		getContentPane().add(jScrollPane);
		revalidate();
		repaint();
	}
	
	public void openGraphScreen() throws FontFormatException, IOException {
		this.getContentPane().removeAll(); // On vide le panel principal.
		getContentPane().add(new GraphScreen(this, config));
		revalidate();
		repaint();
	}
	
	public void openStatsScreen() throws FontFormatException, IOException {
		this.getContentPane().removeAll(); // On vide le panel principal.
		getContentPane().add(new StatScreen(this, config));
		revalidate();
		repaint();
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setConfiguration(Path fileName) {
		setConfiguration(new Configuration(fileName));
	}
	
	public void setConfiguration(Configuration configuration) {
		this.config = configuration;
		this.resultsScreen = null;
	}
	
}
