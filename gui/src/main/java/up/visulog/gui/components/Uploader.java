package up.visulog.gui.components;
import java.awt.FontFormatException;
import java.awt.List;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import up.visulog.gitrawdata.Commit;
import up.visulog.gui.screens.ResultsScreen;
import up.visulog.gui.validators.HomeValidators;

// When user clicks upload, it will run this function bellow
public class Uploader {

    public static void uploadFile() throws FontFormatException, IOException {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setDialogTitle("Choose a directory to upload: ");
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int returnValue = jfc.showSaveDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			if (jfc.getSelectedFile().isDirectory()) {
				System.out.println("You selected the directory: " + jfc.getSelectedFile());
			}else {
				System.out.println("Empty or null");
			}
		}
		if (jfc.getSelectedFile() != null) {
			File file_path = jfc.getSelectedFile();
			if (HomeValidators.isGitFolder(file_path)) {
				System.out.println("Is git folder!");
				runAnalysis(file_path);
			} else {
				displayError();
				System.out.println("Is not a git folder!");
			}
		}
    }

	private static void displayError() {
		// TODO
	}

	private static void runAnalysis(File file) throws FontFormatException, IOException{
		var gitlog = Commit.parseLogFromCommand(file.toPath());
		var fileName = file.getName();
		new ResultsScreen(gitlog, fileName);
	}
}