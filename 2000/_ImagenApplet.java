import java.awt.*;
import java.awt.image.*;
import java.math.*;
import java.awt.event.*;
import java.applet.*;
import java.net.*;
import netscape.javascript.*;

public class ImagenApplet
extends java.applet.Applet
implements ActionListener,ItemListener
{

	int  i,j,tempres,tempbit, m, k, trunctest, waitsecs,a,b,c,d,visize,vecy,vecx,vecr,vecg,vecb,circsize,VPnum;

	int vint[],VPy[],VPx[],VPr[],VPg[],VPb[];

	Integer resolution, bitdepth, colormode, mixermode;

	int goff=10;

	Panel panel, npanel;

	Font font;

	String SSString;

	Button ubutton, abutton, dbutton, ebutton, sbutton, aubutton, calcbutton, lbutton, vrbutton;
	Button decbut,hexbut,chrbut;
	Choice rlist, blist;
	CheckboxGroup cbg,cbgexp;
	Checkbox fcheck,gcheck,maxfac,cbmix,cbdiag,cbexp,cbwire,cbshad;
	Label rlabel, blabel, slabel, altlabel, prgnlabel, mflabel;
	TextField autopause;

	MediaTracker mt;

	Thread leothread;
	boolean running=false,lastscale=false,gotdot=false;

	PixelGrabber vgrab;

	Image image;
	AudioClip ImAu;
	URL docurl,lurl,emailurl,useurl,calcurl,decurl,hexurl,chrurl;

	Graphics g;

	boolean test,imflu=false;

	public void paint(Graphics g)
	{
		g.setColor(new Color(56,56,112));
		g.draw3DRect(182,0,400,399,true);
		g.draw3DRect(183,1,398,397,true);

		a=image.getHeight(this);										
																		
		if ( maxfac.getState()&&(a<4) )
		{	
			if(!lastscale)
			{	g.setColor(new Color(51,51,102));
				g.fillRect(205,20,360,360);
			}
			g.drawImage(image, 258,73, 254, 254, Color.black ,this);
			lastscale=true;	
		}
		else
		{	g.drawImage(image, 205,20, 360, 360, Color.black ,this); lastscale=false;	}

		if (gotdot)
		{	circsize=360/visize;
			g.setColor(Color.red);
			for (i=0;i<j;i++)
			{	
				g.drawOval(205+(VPx[i]*360)/visize,20+(VPy[i]*360)/visize,circsize,circsize);
				if (i<j-1)	g.drawLine(205+(VPx[i]*360)/visize+circsize/2,20+(VPy[i]*360)/visize+circsize/2,205+(VPx[i+1]*360)/visize+circsize/2,20+(VPy[i+1]*360)/visize+circsize/2);
			}
		}
	}

	public void update(Graphics g)
	{

		paint(g);
	}

	public String getAppletInfo()
	{
		return "Imagen Remote Control Applet (c) 2000 Leander Seige";
 	}

	public void start()
	{
		gifholen();
		//repaint();
	}

	public void init()
	{

		showStatus("STARTING - Please Wait...");

		g=getGraphics();
		
		//try { docurl = new URL (getDocumentBase(),"doc.html"); }
		//catch (Exception e) { }
		//try { lurl = new URL (getDocumentBase(),"links.html"); }
		//catch (Exception e) { }
		try { emailurl = new URL ("mailto:imagen@gmx.de"); }
		catch (Exception e) { }		
		//try { useurl = new URL (getDocumentBase(),"usage.html"); }
		//catch (Exception e) { }		
		try { calcurl = new URL (getDocumentBase(),"calc1.html"); }
		catch (Exception e) { }
		//try { decurl = new URL (getDocumentBase(),"cgi-bin/dimagen.cgi?1+1+1+1+12"); }
		//catch (Exception e) { }	
		//try { hexurl = new URL (getDocumentBase(),"cgi-bin/dimagen.cgi?1+1+1+1+11"); } // "javascript:doimawin(\'usage.html\')"
		//catch (Exception e) {  }	
		//try { chrurl = new URL (getDocumentBase(),"cgi-bin/dimagen.cgi?1+1+1+1+13"); }
		//catch (Exception e) { }					
	
		setBackground(new Color(51,51,102));
		setForeground(Color.black);
	
		font = new Font("SansSerif",Font.PLAIN,12);
		setFont(font);

		setLayout(null);

		panel = new Panel();
		panel.setLayout(null);
		panel.setBounds(0,0,180,500);

			npanel = new Panel();
			npanel.setLayout(null);
			npanel.setBounds(600,0,100,500);

		ubutton = new Button("Update");
		ubutton.setBounds(20,0,140,45);
		ubutton.setBackground(Color.lightGray);
		abutton = new Button("Auto Update");
		abutton.setBounds(20,55,80,40);
		abutton.setBackground(Color.lightGray);
		vrbutton = new Button("Run");
		vrbutton.setBounds(100,335,60,50);
		vrbutton.setBackground(Color.lightGray);	

			dbutton = new Button("Documentation");
			dbutton.setBounds(0,315,100,25);
			dbutton.setBackground(new Color(71,71,142));
			dbutton.setForeground(new Color(127,127,255));
			ebutton = new Button("E-Mail");
			ebutton.setBounds(0,345,100,25);
			ebutton.setBackground(new Color(71,71,142));
			ebutton.setForeground(new Color(127,127,255));										
			sbutton = new Button("How To Use");
			sbutton.setBounds(0,285,100,25);
			sbutton.setBackground(new Color(71,71,142));
			sbutton.setForeground(new Color(127,127,255));	
			lbutton = new Button("Links");
			lbutton.setBounds(0,375,100,25);
			lbutton.setBackground(new Color(71,71,142));
			lbutton.setForeground(new Color(127,127,255));			
				aubutton = new Button("Sound");
				aubutton.setBounds(0,24,100,25);
				aubutton.setBackground(Color.lightGray);
				calcbutton = new Button("Calculator");
				calcbutton.setBounds(0,112,100,25);
				calcbutton.setBackground(Color.lightGray);
					decbut = new Button("Dec");
					decbut.setBounds(0,52,33,25);
					decbut.setBackground(Color.lightGray);
					hexbut = new Button("Hex");
					hexbut.setBounds(33,52,33,25);
					hexbut.setBackground(Color.lightGray);					
					chrbut = new Button("Asc");
					chrbut.setBounds(66,52,34,25);
					chrbut.setBackground(Color.lightGray);				
		

		ubutton.addActionListener(this);
		abutton.addActionListener(this);
		vrbutton.addActionListener(this);

			dbutton.addActionListener(this);
			ebutton.addActionListener(this);
			sbutton.addActionListener(this);
			lbutton.addActionListener(this);
				aubutton.addActionListener(this);
				calcbutton.addActionListener(this);
				decbut.addActionListener(this);
				hexbut.addActionListener(this);
				chrbut.addActionListener(this);

		rlist = new Choice();
		for (i = 2; i<=256; i++)
		{
			rlist.add(i+" x "+i);
		}

		blist = new Choice();
		for (i = 1; i<=8; i++)
		{
			blist.add(i+" Bit");
		}

		rlist.setBounds(20,124+goff,140,24);
		blist.setBounds(20,213+goff*2,140,24);
		rlist.setBackground(Color.white);
		rlist.setForeground(Color.black);		
		blist.setBackground(Color.white);
		blist.setForeground(Color.black);
		rlist.select(14); //rlist.makeVisible(14);
		blist.select(1);  //blist.makeVisible(1);

		rlabel=new Label("Resolution:");
		blabel=new Label("Color Depth:");
		slabel=new Label("Sec.");
		rlabel.setBounds(0,100+goff,100,16);blabel.setBounds(0,189+goff*2,100,16);slabel.setBounds(150,60,30,30);
		rlabel.setForeground(Color.white);blabel.setForeground(Color.white);slabel.setForeground(Color.white);
			altlabel=new Label("Alternative");
			altlabel.setBounds(0,0,100,25);
			altlabel.setForeground(Color.white);
			prgnlabel=new Label("Prognosis");
			prgnlabel.setBounds(0,88,100,25);
			prgnlabel.setForeground(Color.white);
			mflabel=new Label("Troubleshooting");
			mflabel.setBounds(0,150,100,25);
			mflabel.setForeground(Color.white);

		cbg = new CheckboxGroup();
		cbgexp = new CheckboxGroup();
		fcheck = new Checkbox("Color (RGB)",cbg,false);
		gcheck = new Checkbox("Gray / BW",cbg,true);
			cbmix = new Checkbox("Spiral",true);
			cbdiag= new Checkbox("Diagonal",false);
				maxfac = new Checkbox("Safe Scaling",true);
					cbexp = new Checkbox("Experimental",false);
					cbwire = new Checkbox("Wireframe",true,cbgexp);
					cbshad = new Checkbox("Filled",false,cbgexp);

		fcheck.setBounds(10,245+goff*2,88,25);
		gcheck.setBounds(100,245+goff*2,88,25);
			cbdiag.setBounds(10,156+goff,88,25);
			cbmix.setBounds(100,156+goff,88,25);	
				maxfac.setBounds(0,172,100,25);
					cbexp.setBounds(44,310,100,25);
					cbwire.setBounds(10,335,88,25);
					cbshad.setBounds(10,360,88,25);		
		fcheck.setForeground(Color.white);
		gcheck.setForeground(Color.white);
			cbmix.setForeground(Color.white);
			cbdiag.setForeground(Color.white);
				maxfac.setForeground(Color.lightGray);
					cbexp.setForeground(new Color(255,192,192));
					cbwire.setForeground(Color.white);
					cbshad.setForeground(Color.white);

		cbexp.addItemListener(this);

		cbwire.setEnabled(false);
		cbshad.setEnabled(false);
		vrbutton.setEnabled(false);

		autopause = new TextField("3",2);
		autopause.setBounds( 105, 63,40,28);
		autopause.setForeground(Color.black);
		autopause.setBackground(Color.white); 

		panel.add(ubutton);
		panel.add(abutton);
		panel.add(vrbutton);
		panel.add(rlist);
		panel.add(blist);
		panel.add(rlabel);
		panel.add(blabel);
		panel.add(slabel);
		panel.add(fcheck);
		panel.add(gcheck);
		panel.add(cbmix);
		panel.add(cbwire);
		panel.add(cbshad);
		panel.add(cbexp);
		panel.add(cbdiag);
		panel.add(autopause);

			npanel.add(dbutton);
			npanel.add(lbutton);
			npanel.add(ebutton);
			npanel.add(sbutton);
			npanel.add(aubutton);
			npanel.add(decbut);
			npanel.add(hexbut);
			npanel.add(chrbut);
			npanel.add(altlabel);
			npanel.add(prgnlabel);									
			npanel.add(calcbutton);
			npanel.add(maxfac);
			npanel.add(mflabel);


		add(panel);

			add(npanel);
		
	}



	public void actionPerformed (ActionEvent ActEvt)
	{	
		String cmd = ActEvt.getActionCommand();

		if (cmd=="Update")
		{
			ubutton.setLabel("Please Wait...");
			gifholen();
			ubutton.setLabel("Update");
		}

		if (cmd=="Auto Update" || cmd=="Stop")
		{
			if (running==false)
			{
				leothread = new autobild();
				leothread.start();
					ubutton.setEnabled(false);
					aubutton.setEnabled(false);
					calcbutton.setEnabled(false);
					decbut.setEnabled(false);
					hexbut.setEnabled(false);
					chrbut.setEnabled(false);
				abutton.setLabel("Stop");					
				running=true;
			}
			else
			{
				leothread = null;	
					ubutton.setEnabled(true);
					aubutton.setEnabled(true);
					calcbutton.setEnabled(true);
					decbut.setEnabled(true);
					hexbut.setEnabled(true);
					chrbut.setEnabled(true);
				abutton.setLabel("Auto Update");
				running=false;
			}
		}

		if (cmd=="Documentation")
		{
			//getAppletContext().showDocument(docurl,"Information");	
			jsshow("doc.html");
		}
		if (cmd=="E-Mail")
		{
			getAppletContext().showDocument(emailurl);	
		}
		if (cmd=="Links")
		{
			//getAppletContext().showDocument(lurl,"Information");
			jsshow("links.html");	
		}
		if (cmd=="How To Use")
		{
			//getAppletContext().showDocument(useurl,"Information");	
			jsshow("usage.html");
		}
		if (cmd=="Calculator")
		{
			getAppletContext().showDocument(calcurl);	//    ,"Information"
		}
		if (cmd=="Run")
		{	if(running)
			{	
				leothread = null;	
				abutton.setLabel("Auto Update");
				running=false;
			}	
					ubutton.setEnabled(false);
					abutton.setEnabled(false);
					aubutton.setEnabled(false);
					calcbutton.setEnabled(false);
					decbut.setEnabled(false);
					hexbut.setEnabled(false);
					chrbut.setEnabled(false);
				vrbutton.setLabel("Wait");
			imavector();
				vrbutton.setLabel("Run");
					ubutton.setEnabled(true);
					abutton.setEnabled(true);
					aubutton.setEnabled(true);
					calcbutton.setEnabled(true);
					decbut.setEnabled(true);
					hexbut.setEnabled(true);
					chrbut.setEnabled(true);
		}		
		if (cmd=="Sound")
		{
			bitdepth   = new Integer (blist.getSelectedIndex()+1);
			play(getDocumentBase(), "cgi-bin/dimagen.cgi?1+"+bitdepth.toString()+"+1+1+1");
			//ImAu = getAudioClip(getDocumentBase(), "cgi-bin/dimagen.cgi?1+"+bitdepth.toString()+"+1+1+1");
			//	try { Thread.sleep(1000); }
			//	catch (InterruptedException e) {  }
			//ImAu.play();
			//	try { Thread.sleep(1000); }
			//	catch (InterruptedException e) {  }
				
		}
		if (cmd=="Dec"||cmd=="Hex"||cmd=="Asc")
		{
			if (cmd=="Dec")	jsshow("cgi-bin/dimagen.cgi?1+1+1+1+12");	//getAppletContext().showDocument(decurl,"Information");	
			
			if (cmd=="Hex")	jsshow("cgi-bin/dimagen.cgi?1+1+1+1+11");	//getAppletContext().showDocument(hexurl,"Information");

			if (cmd=="Asc")	jsshow("cgi-bin/dimagen.cgi?1+1+1+1+13");	//getAppletContext().showDocument(chrurl,"Information");

		}

	}

	public void itemStateChanged(ItemEvent e)
	{
		if (cbexp.getState())
		{	cbwire.setState(false);
			cbshad.setState(false);
			cbwire.setEnabled(true);
			cbshad.setEnabled(true);
			vrbutton.setEnabled(true);
		}
		else
		{	cbwire.setState(false);
			cbshad.setState(false);
			cbwire.setEnabled(false);
			cbshad.setEnabled(false);
			vrbutton.setEnabled(false);
		}
	}

	public void gifholen()
	{	gotdot=false;	
	
		if (imflu) image.flush();
		tempres = rlist.getSelectedIndex()+2;
		tempbit = blist.getSelectedIndex()+1;

		if (fcheck.getState())	{	colormode = new Integer ( 3 );	}
		else					{	colormode = new Integer ( 1 );	}

		mixermode = new Integer ( 1 );
		if (cbdiag.getState())	{	mixermode = new Integer ( 2 );	};
		if ( cbmix.getState())	{	mixermode = new Integer ( 3 );	};
		if ( cbmix.getState() && cbdiag.getState() )	{	mixermode = new Integer ( 5 );	};

			trunctest=0;
			m=tempres*tempres;
			k=tempres*tempres*tempbit;
			if ( k > 65536)  { tempbit=65536/m; trunctest=1; }
			k=tempres*tempres*tempbit;
			if ( colormode.intValue()==3 && k > 16384 ) { tempres=tempres/2; trunctest=1; }

		resolution = new Integer (tempres);
		bitdepth   = new Integer (tempbit);

		SSString="Resolution: "+resolution.toString()+"x"+resolution.toString()+"    Color Depth: "+bitdepth.toString()+" Bit / Channel";
		if (trunctest==1) { SSString="TRUNCATED:    "+SSString; } else { SSString="COMPLETE:    "+SSString; }
		if (colormode.intValue()==3) { SSString=SSString+"    Mode: RGB"; } else { SSString=SSString+"    Mode: Grayscale/BW"; }
	
		showStatus("LOADING - Please Wait...");

		image = getImage (getDocumentBase(), "cgi-bin/dimagen.cgi?"+resolution.toString()+"+"+bitdepth.toString()+"+"+colormode.toString()+"+"+mixermode.toString() );

		mt = new MediaTracker(this);
		mt.addImage(image,0);
			try{ mt.waitForID(0);}
			catch (InterruptedException e) { showStatus("INTERNAL ERROR!"); }
		mt.removeImage(image,0);

		imflu=true;

		showStatus(SSString);

		repaint();

	}

	public void jsshow(String injsstring)
	{
		try
		{
			JSObject window = JSObject.getWindow(this);
			Object[] args = {"http://imagen.hgb-leipzig.de/"+injsstring};
			window.call("doimawin", args);
		}
		catch (Exception e)
		{			try { docurl = new URL (getDocumentBase(),injsstring); }
					catch (Exception e2) {	showStatus("OH, SOMETHING IS VERY WRONG! PLEASE MAIL ME!");
										 }
					getAppletContext().showDocument(docurl,"Information");
		}

	}

	public void imavector()
	{	
		cbmix.setState(true);
		cbdiag.setState(true);

		System.out.println("IRCA V0.0.1 Vector Unit");

		gifholen();

		visize=image.getHeight(this);
		vint = new int[visize*visize];

		vgrab = new PixelGrabber(image,0,0,visize,visize,vint,0,visize);
		try	{	vgrab.grabPixels();	}
		catch (InterruptedException e) { showStatus("Grabber ERROR!");	}
		if ( (vgrab.getStatus()&ImageObserver.ABORT) !=0)
		{ 
			showStatus("Grabber ABORT ERROR");
		}
		else
		{
			j=0;

			for (i=0;i<(visize*visize);i++)
			{	if ( (vint[i]&0xFFFFFF)!=0 )	j++;	}

			if (j>2)
			{
				if (j< ((visize*visize)/2) )
				{
					VPy=new int[j];VPx=new int[j];
					VPr=new int[j];VPg=new int[j];VPb=new int[j];
					j=0;
					for (i=0;i<(visize*visize);i++)
					{
						if ( (vint[i]&0xFFFFFF)!=0 )
						{	
							VPy[j]=(int)(i/visize);
							VPx[j]=(i-((int)(i/visize)*visize));
							VPr[j]=((vint[i]>>16)&0xFF);
							VPg[j]=((vint[i]>>8)&0xFF);
							VPb[j]=((vint[i])&0xFF);
							j++;
						}
					}
					for (i=0;i<j;i++)
					{
						System.out.println(i+". "+"Y,X: "+VPy[i]+","+VPx[i]+"   R,G,B: "+VPr[i]+","+VPg[i]+","+VPb[i]);
					}
			
					System.out.println("-"+j+"- - - - - - - - - - - - - - -");

					// alles positiv -> hier weiter

					VPnum=j;
					gotdot=true;
					repaint();
					System.out.println("Done.");
					
				}
				else
				{
					System.out.println("More Than 50% Coloured Pixels."); 
				}
				
			}
			else
			{
				System.out.println("Not Enough Points Found: "+j);
			}
		}

	}

	public class autobild extends Thread
	{	        
		public void run ()
		{
			Thread currentThread = Thread.currentThread();

			while (currentThread==leothread)
			{	
				gifholen();
				//repaint();

				try
				{	waitsecs=(new Integer(autopause.getText())).intValue();	}	
				catch (NumberFormatException e) { waitsecs=2; }

				if (waitsecs<=0) { waitsecs=2; }

				autopause.setText((new Integer(waitsecs)).toString());			
				
				try { currentThread.sleep(waitsecs*1000); }
				catch (InterruptedException e) { break; }
			}
			
		}
	}


}