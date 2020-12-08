import java.awt.*;
import java.awt.image.*;
import java.awt.Image;
import java.math.*;
import java.awt.event.*;
import java.applet.*;
import java.net.*;

public class ImagenCalculator
extends java.applet.Applet

{
	boolean test,ready=false;
	int  a,b,c,d,x,y;
		
	Font font;

	MediaTracker mt;

	byte  sby[],  uby[];
	int  sint[],usint[];

	Image Uimage,Simage,Iimage,helper;

	Graphics g;

	String workim="Working...";

	PixelGrabber sgrab,ugrab;
	BigInteger BIs,BIu,BIbstart,BIbend,BIbtime,BIresult,BIsy,BItemp;

	public void paint(Graphics g)
	{
		if (ready)
		{
			g.drawImage(Iimage,  0,40,300,300, Color.black, this);
			g.drawImage(Simage,400,40,300,300, Color.black, this);

			g.setColor(Color.white);
			g.drawString("Your Image.",410,25);
			g.drawString("Actual System Image.",10,25);
			g.drawString(workim,10,375);
		}
		else
		{
			g.setColor(Color.white);
			g.drawString("Please Wait While Working...",25,200);
		}
	}

	public void update(Graphics g)
	{
		repaint();
	}

	public String getAppletInfo()
	{
		return "Imagen Calculator Applet (c) 2000 Leander Seige";
 	}

	public void start()
	{	

		repaint();
		
		gifholen();

		showStatus("CALCULATING - Please Wait...");

		usint=new int[4096];

		ugrab = new PixelGrabber(Uimage,0,0,64,64,usint,0,64);
		try	{	ugrab.grabPixels();	}
		catch (InterruptedException e) { showStatus("upixgrab ERROR");	}
		if ( (ugrab.getStatus()&ImageObserver.ABORT) !=0)
		{ showStatus("upixgrab ABORT ERROR");	}

			uby=new byte[4096+1]; uby[0]=0;
			for (x=0;x<4096;x++)
			{	a=usint[x];	if ((a&0xFFFFFF)!=0) 	//System.out.println("Detect:"+(new Integer(a)).toString()+" at "+(new Integer(x)).toString()+"\n");
				a=( (a&0xFF)+((a>>8)&0xFF)+((a>>16)&0xFF) )/3;
				uby[4096-x]=(byte)(a&0xFF);
				usint[x]=a|(a<<8)|(a<<16)|(0xFF<<24);	
			}
	
			Simage=createImage(new MemoryImageSource(64,64,usint,0,64));

		sint=new int[4096];

		sgrab = new PixelGrabber(Iimage,0,0,64,64,sint,0,64);
		try	{	sgrab.grabPixels();	}
		catch (InterruptedException e) { showStatus("upixgrab ERROR");	}
		if ( (ugrab.getStatus()&ImageObserver.ABORT) !=0)
		{ showStatus("upixgrab ABORT ERROR");	}

			sby=new byte[4096+1]; sby[0]=0;
			for (x=0;x<4096;x++)
			{	a=sint[x];
				a=( (a&0xFF)+((a>>8)&0xFF)+((a>>16)&0xFF) )/3; 
				sby[4096-x]=(byte)(a&0xFF);
			}

		BIu = new BigInteger(uby); //System.out.println(BIu.toString(2));
		BIs = new BigInteger(sby);
		BIbend   = new BigInteger("110010101000001111011110000101101101100111110",2);
		BIbstart = new BigInteger("110010100110001100110101100011011100111011111",2);
		BIbtime  = new BigInteger("743");
		BIsy = new BigInteger("31536000");
		
		BItemp   = ((BIbend.subtract(BIbstart)).divide(BIbtime)).multiply(BIsy);
		BIresult = (BIu.subtract(BIs)).divide(BItemp);

		if (BIresult.compareTo(new BigInteger("0"))>0)
		{
			BIu   =new BigInteger("1");    BIs     =new BigInteger("0"); test=true;
			BIbend=new BigInteger("1000000"); BIbstart=new BigInteger("6");
			do
			{
				if ( (BIresult.compareTo(BIu.multiply(BIbend)))<=0 )
				{	test=false;	}
				else
				{	BIs=BIs.add(BIbstart); BIu=BIu.multiply(BIbend);	}
			}
			while (test);

			if ( (BIs.compareTo(new BigInteger("0")))>0 )
			workim = (BIresult.divide(BIu)).toString()+"e+"+BIs.toString();
			else
			workim = (BIresult.divide(BIu)).toString();
			workim = new String("Your Image Will Be Generated In  "+workim+"  Years.");
		}
		else
		{
			workim = ("Your Image Has Already Been Generated.");
		}
		Uimage.flush();
		ready=true;
		repaint();
	}

	public void init()
	{
		showStatus("STARTING - Please Wait...");

		repaint();	

		g=getGraphics();
		g.setColor(Color.white);
		g.drawString("Please Wait While Working...",25,200);

		System.out.println("los!");
		
		setBackground(new Color(51,51,102));
		setForeground(Color.black);
	
		font = new Font("SansSerif",Font.PLAIN,12);
		setFont(font);

		setLayout(null);
	}

	public void gifholen()
	{
		showStatus("LOADING - Please Wait...");		
	
		Iimage = getImage (getDocumentBase(), "dimagen.cgi?64+8+1+1");
		Uimage = getImage (getDocumentBase(), "temp.cgi");	

		mt = new MediaTracker(this);

		mt.addImage(Iimage,0);
		mt.addImage(Uimage,1);		
			try{ mt.waitForAll();}
			catch (InterruptedException e) { showStatus("INTERNAL ERROR!"); }
		mt.removeImage(Iimage,0);
		mt.removeImage(Uimage,1);		

		Uimage=Uimage.getScaledInstance(64,64,0);		

		mt.addImage(Uimage,0);		
			try{ mt.waitForAll();}
			catch (InterruptedException e) { showStatus("INTERNAL ERROR!"); }
		mt.removeImage(Uimage,0);

	}

}