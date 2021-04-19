import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * 
 * @author KUBEKA BS
 * @StudentNumber 217010763
 *
 */
public class Main extends Application{

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
    launch(args);
	}
 
	@Override
	public void start(Stage myS) throws Exception {
		
	
	     Start myC = new Start();
		Scene sc = new Scene(myC);

		myS.setTitle("iEarthEditorv0.1");
		myS.setScene(sc);
		//Let's see
		myS.show();
	}

}
