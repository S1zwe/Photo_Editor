import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
/**
 * 
 * @author KUBEKA BS
 * @StudentNumber 217010763
 *
 */
public class Start extends StackPane {
	private Button  btnStart, btnClose;
	private ImageView imgV;
	private VBox layout;
	//text streams
		private BufferedReader txtin=null;
		private PrintWriter txtout= null;
		
		//binary Streams
		private DataInputStream dis=null;
		private DataOutputStream dos=null;
		
		//bytes streams
		private OutputStream out =null;
		private InputStream in =null;
		private Socket s;
	public Start() {
		this.setPrefSize(1060, 600);
		
		 Image mImg = new Image("./data/a/B.jpg");
		 BackgroundImage bb= new BackgroundImage(mImg, null, null, null, null);
		 
		Background myback = new Background(bb);
		this.setBackground(myback);
	
	layout= new VBox();
	
	GridPane gPane = new GridPane();
	 Image myImg = new Image("./data/icon.png");
	   imgV= new ImageView();
	   imgV.setImage(myImg);
	
		btnStart= new Button("Start Editing ");

		btnStart.setTranslateX(200);
		btnStart.setTranslateY(400);
		
		
		btnClose= new Button();
		btnClose.setTranslateX(250);
		btnClose.setTranslateY(400);

     	layout.getChildren().add(0, btnStart);
     	gPane.add(imgV, 0,3,2, 1);
		gPane.add(btnStart, 3,3);
		
		this.getChildren().add(gPane);

		/**
		 * button to start the app
		 */
		
		btnStart.setOnAction(e->{
			connect("localhost", 5000);
			layout.setVisible(false);
			client myClient = new client();
			this.getChildren().add(myClient);
			
			
		});
		
	}
	
	
	/**
	 * Create socket and connect it to the required port
	 * @param host
	 * @param port
	 */
	
	private  void connect(String host, int port) {
		
		try {
			System.out.println("Client Connecting...");
			
			s = new Socket(host,port); 
			
			out = s.getOutputStream();
			in = s.getInputStream();
			
			dos = new DataOutputStream(out);
			dis = new DataInputStream(in);
			
			txtin= new BufferedReader(new InputStreamReader(in));
			txtout = new PrintWriter(out);
			System.out.println("Connection Established");
			
			
		}catch(UnknownHostException e) {
			e.printStackTrace();
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}	
	}

}
