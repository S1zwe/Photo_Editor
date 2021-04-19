import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.github.sarxos.webcam.Webcam;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
/**
 * 
 * @author KUBEKA BS
 * @StudentNumber 217010763
 *
 */
public class client extends GridPane {
	
	private ImageView imgVTop, imgV;
	private MediaView vidTop;

	private Button  btnCon, btnGreyS,btnR, btnE,btnD,btnCr,btnWeb, btnCan,btnFastF, btnF,btnOrbF,btnOrb;
	private VBox layout;
	
private static Socket cs;
private static InetAddress host;
private static Socket link = null;
private static  int port;
private Image Img;
//text streams
	private BufferedReader txtin=null;
	private PrintWriter txtout= null;
	
	//binary Streams
	private DataInputStream dis=null;
	private DataOutputStream dos=null;
	
	//bytes streams
	private OutputStream out =null;
	private InputStream in =null;
	//Files
	private FileChooser fc;
	private File iFile ;
	
private String grayURL = "/api/GrayScale";
private String RotateURL = "/api/Rotate";
private String ErosionURL = "/api/Erosion";
private String DilationURL = "/api/Dilation";
private String cropURL = "/api/Crop";
private String CannyURL = "/api/Canny";
private String ORBFeaturesURL = "/api/ORBFeatures";
private String FastURL = "/api/Fast";
private String ORBURL = "/api/ORB";


	public client() {
		//SetUp Gui
		Gui();
	   Action();
		/**
		    *@ChangePicture
		    */
	   imgV.setOnMouseClicked(e->{
		   
		   File newFile =new File("./src/data");
	    	fc.setTitle("Choose Image");
			fc.setInitialDirectory(newFile);
			File file = fc.showOpenDialog(null);
			 iFile=file;
							
			 imgV.setImage(new Image(file.toURI().toString()));
	   });
	   
	   /**
	    *@BtnGreyScale
	    */
	    
	    btnGreyS.setOnAction(e->{  	 
	    	SendCommand(grayURL);
	    	 Action();

	    });
	    /**
		    *@BtnCrop
		    */
	    btnCr.setOnAction(e->{
	    	SendCommand(cropURL);
	    	 Action();
	    });
	    /**
		    *@BtnDialation
		    */
	    btnD.setOnAction(e->{
	    	SendCommand(DilationURL);
	    	 Action();
	    });
	    /**
		    *@BtnEroision
		    */
	    btnE.setOnAction(e->{
	
	    	SendCommand(ErosionURL);
	    	 Action();
	    });
	    /**
		    *@BtnRotate
		    */
	    btnR.setOnAction(e->{
	    
	    	SendCommand(RotateURL);
	    	 Action();
	    });
	    /**
		    *@BtnWebCam
		    */
	    btnWeb.setOnAction(e->{	    	
		    Image newI= imgV.getImage();
		    BufferedImage bImage = SwingFXUtils.fromFXImage(newI, null);
		    int nM=(int)(Math.random() * 500 + 1);
       File neFile = new File("./src/data/"+nM+"newSaved.png");
      
			// save image to PNG file
		  
			try {
				ImageIO.write(bImage, "png", neFile);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	JOptionPane.showMessageDialog(null, "File saved!, continue editting");
			 Action();
	    });
	    /**
		    *@BtnCannyFeatures
		    */
	    btnCan.setOnAction(e->{
	   
	    	SendCommand(CannyURL);
	    	 Action();
	    });
	    /**
		    *@BtnFastFeatures
		    */
	    btnFastF.setOnAction(e->{
	    
	    	SendCommand(FastURL);
	    	 Action();
	    });
	    /**
		    *@BtnConnect
		    */
	    btnF.setOnAction(e->{
	    	SendCommand(FastURL);
	    	 Action();
	    });
	    /**
		    *@BtnOrb
		    */
	    btnOrb.setOnAction(e->{
	    
	    	SendCommand(ORBURL);
	    	 Action();
	    });
	   
	   
	    
	}

	/**
	 * accept  url and send 
	 * @param host
	 * @param port
	 */
	private  void SendCommand(String url) {
		String enF = null;
    	try {
    	//Create a File handle
        FileInputStream fIn = new	FileInputStream(iFile);	
    	byte[] bytes = new byte[(int)iFile.length()];
    	fIn.read(bytes);
    	//Encode the bytes into a base64 format string
    	enF = new String(Base64.getEncoder().encodeToString(bytes));
    	//get the bytes of this encoded string
    	byte[] bytesToSend = enF.getBytes();
    	//Construct a POST HTTP REQUEST
    	dos.write(("POST " + url +" HTTP/1.1\r\n").getBytes());
    	dos.write(("Content-Type: " +"application/text\r\n").getBytes());
    	dos.write(("Content-Length: " + enF.length()
    	+"\r\n").getBytes());
    	dos.write(("\r\n").getBytes());
    	dos.write(bytesToSend);
    	dos.write(("\r\n").getBytes());
    	dos.flush();
    	System.out.println("POST Request Sent\r\n");
    	//read text response
    	String response = "";
    	String line = "";
    	while(!(line = txtin.readLine()).equals(""))
    	{
    	response += line +"\n";
    	}
    	System.out.println(response);
    	
    	
    	String imgData = "";
    	while((line = txtin.readLine())!=null)
    	{
    	imgData += line;
    	}
    	String base64Str =
    	imgData.substring(imgData.indexOf('\'')+1,imgData.lastIndexOf('}')-1);
    	byte[] decodedString = Base64.getDecoder().decode(base64Str);
    	//Display the image
    	Image newImg = new Image(new ByteArrayInputStream(decodedString));
    	imgV.setImage(newImg);
    	}catch (IOException ex) {
    		 ex.printStackTrace();
    		 }finally
    		 {try {
    		 dos.close();
    		 cs.close();} catch (IOException ec) {
    		 ec.printStackTrace();
    		 }
    		 }
	}
	
	private  void Gui() 
	{
		this.setPrefSize(1060, 600);
		
		 Image mImg = new Image("./data/a/B.jpg");
		 BackgroundImage bb= new BackgroundImage(mImg, null, null, null, null);
		 
		Background myback = new Background(bb);
	
		this.setBackground(myback);
	
		//setup GUI
		fc= new FileChooser();
		btnCon= new Button("Connect");
		btnGreyS= new Button("Grey Scale");
		btnR= new Button("Rotate");
		btnE= new Button("Erosion");
		btnD= new Button("Dilation");
		btnCr= new Button("Crop");
		btnWeb=new Button("Save photo");
	btnCan=new Button("Canny");
	btnFastF=new Button("Fast Features");
	btnF=new Button("Fast");
	btnOrbF=new Button("ORB Features");
	btnOrb=new Button("ORB");
	

	  //setup image
Img= new Image("./data/icon.png");
Image myImg = new Image("./data/a/as.png");
	   imgV= new ImageView();
	 imgVTop= new ImageView();
	   imgVTop.setImage(Img);
	   imgV.setImage(myImg);

	   
	   //Control Layout view   
	  //add to root node 
	  this.add(imgV,0,0);
	   this.add(btnOrb,0,1);
	   this.add(btnGreyS,1,1);
	   
	   this.add(btnE,0,2);
	   this.add(btnD,1,2);
	   this.add(btnCr,0,4);
	  this.add(btnR, 1, 3);

	  this.add(btnWeb,0,3);
	  this.add(btnCan, 1, 4); 
	  this.add(btnFastF,0,5);
	  this.add(btnF, 1, 5);
	  this.add(btnOrbF,0,6); 
	  // this.add(btnOrb, 0,5);
	
		
		  //spacing between nodes 
	   this.setHgap(5);
	   this.setVgap(5);
		
	}
	
	
	
	/**
	 * Create socket and connect it to the required port
	 * @param host
	 * @param port
	 */
	
	private  void Action() {
		
		try {			
			cs = new Socket("localhost",5000); 		
			out = cs.getOutputStream();
			in = cs.getInputStream();	
			dos = new DataOutputStream(out);
			dis = new DataInputStream(in);		
			txtin= new BufferedReader(new InputStreamReader(in));
			txtout = new PrintWriter(out);
			}catch(UnknownHostException e) {
			e.printStackTrace();
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}	
	}
}
