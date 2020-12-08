import java.awt.*;
import java.awt.image.*;
import java.math.*;
import java.awt.event.*;
import java.applet.*;
import java.net.*;
import java.util.*;
import netscape.javascript.*;

public class ImagenApplet
extends java.applet.Applet
implements ActionListener,ItemListener
{

	int  i,j,k,tempres,tempbit, m, n, trunctest, waitsecs,a,b,c,d,e,visize,vecy,vecx,vecr,vecg,vecb,vec2r,vec2g,vec2b,circsize,VPnum,STORAGEWIDTH,VPedgeN;
	int vint[],VPy[],VPx[],VPr[],VPg[],VPb[],VPedgeA[],VPedgeE[],VPres[];

	double distance[];
	float HRfac;
	double Da,Dy,Dx,Wb,fac,Dda,Ddo;

	Color VUcol;

	Integer resolution, bitdepth, colormode, mixermode;

	int goff=0;

	Panel panel, npanel;

	Font font;

	String SSString;

	Button ubutton, abutton, dbutton, ebutton, sbutton, aubutton, calcbutton, lbutton, vrbutton;
	Button decbut,hexbut,chrbut;
	Choice rlist, blist;
	CheckboxGroup cbg,cbgexp,cbgtri;
	Checkbox fcheck,gcheck,maxfac,cbmix,cbdiag,cbexp,cbwire,cbshad,cbstruc,cbhires,cbuk,cbmax,cbdel;
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

		if (gotdot&&cbstruc.getState())
		{	circsize=360/visize;
			g.setColor(Color.black);
			g.setXORMode(Color.white);
			for (i=0;i<VPedgeN;i++)
			{
				g.drawLine(205+(VPx[VPedgeA[i]]*360)/visize+circsize/2,20+(VPy[VPedgeA[i]]*360)/visize+circsize/2,205+(VPx[VPedgeE[i]]*360)/visize+circsize/2,20+(VPy[VPedgeE[i]]*360)/visize+circsize/2);
			}
			g.setPaintMode();
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
		vrbutton.setBounds(105,275,60,25);
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
		for (i = 2; i<256; i++)
		{
			rlist.add(i+" x "+i);
		}

		blist = new Choice();
		for (i = 1; i<=8; i++)
		{
			blist.add(i+" Bit");
		}

		rlist.setBounds(20,124+goff,140,24);
		blist.setBounds(20,205+goff*2,140,24);
		rlist.setBackground(Color.white);
		rlist.setForeground(Color.black);		
		blist.setBackground(Color.white);
		blist.setForeground(Color.black);
		rlist.select(14); //rlist.makeVisible(14);
		blist.select(1);  //blist.makeVisible(1);

		rlabel=new Label("Resolution:");
		blabel=new Label("Color Depth:");
		slabel=new Label("Sec.");
		rlabel.setBounds(0,100+goff,100,16);blabel.setBounds(0,182,100,16);slabel.setBounds(150,60,30,30);
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
		cbgtri = new CheckboxGroup();		
		fcheck = new Checkbox("Color (RGB)",cbg,false);
		gcheck = new Checkbox("Gray / BW",cbg,true);
			cbmix = new Checkbox("Spiral",true);
			cbdiag= new Checkbox("Diagonal",false);
				maxfac = new Checkbox("Safe Scaling",true);
					cbexp = new Checkbox("VectorMode",false);
					cbwire = new Checkbox("Wireframe",false,cbgexp);
					cbshad = new Checkbox("Shaded",true,cbgexp);
						cbstruc = new Checkbox("show Structure",false);
						cbhires = new Checkbox("HiRes",true);
					cbdel = new Checkbox("Delaunay",true,cbgtri);
					cbuk = new Checkbox("ConvHull",false,cbgtri);
					cbmax = new Checkbox("AllComb",false,cbgtri);

		fcheck.setBounds(10,237,88,25);
		gcheck.setBounds(100,237,88,25);
			cbdiag.setBounds(10,156+goff,88,25);
			cbmix.setBounds(100,156+goff,88,25);	
				maxfac.setBounds(0,172,100,25);
					cbexp.setBounds(10,275,90,25);
					cbwire.setBounds(10,350-12,80,25);
					cbshad.setBounds(10,375-12,80,25);		
						cbstruc.setBounds(70,302,100,23);
						cbhires.setBounds(10,302,58,23);
					cbdel.setBounds(95,375,85,25);					
					cbuk.setBounds(95,350,85,25);							
					cbmax.setBounds(95,325,80,25);							
		fcheck.setForeground(Color.white);
		gcheck.setForeground(Color.white);
			cbmix.setForeground(Color.white);
			cbdiag.setForeground(Color.white);
				maxfac.setForeground(Color.lightGray);	VUcol=Color.lightGray;
					cbexp.setForeground(VUcol);											//	new Color(255,192,192)
					cbwire.setForeground(VUcol);
					cbshad.setForeground(VUcol);
						cbstruc.setForeground(VUcol);
						cbhires.setForeground(VUcol);
					cbuk.setForeground(VUcol);	
					cbdel.setForeground(VUcol);										
					cbmax.setForeground(VUcol);										

		cbexp.addItemListener(this);

		cbwire.setEnabled(false);
		cbshad.setEnabled(false);
		cbuk.setEnabled(false);
		cbmax.setEnabled(false);		
		cbdel.setEnabled(false);				
		cbstruc.setEnabled(false);
		cbhires.setEnabled(false);
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
		panel.add(cbstruc);
		panel.add(cbhires);
		panel.add(cbuk);
		panel.add(cbmax);		
		panel.add(cbdel);				
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
		{	
			cbwire.setEnabled(true);
			cbshad.setEnabled(true);
			cbstruc.setEnabled(true);
			cbhires.setEnabled(true);
			cbuk.setEnabled(true);
			cbdel.setEnabled(true);
			cbmax.setEnabled(true);			
			vrbutton.setEnabled(true);
		}
		else
		{	
			cbwire.setEnabled(false);
			cbshad.setEnabled(false);
			cbstruc.setEnabled(false);			
			cbhires.setEnabled(false);			
			cbuk.setEnabled(false);			
			cbdel.setEnabled(false);						
			cbmax.setEnabled(false);						
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

		showStatus("TRIANGULATION - Please Wait...");

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
				if (true)		//	j< ((visize*visize)/2)
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
							VPb[j]=(vint[i]&0xFF);
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

					if (cbhires.getState())
					{
						vint=new int[360*360];
						HRfac=((float)359)/((float)(visize-1));
						for (i=0;i<VPnum;i++) { VPx[i]=Math.round(VPx[i]*HRfac); VPy[i]=Math.round(VPy[i]*HRfac); }
						visize=360;
					}


					if (cbdel.getState())
					{
						STORAGEWIDTH=VPnum+4;
						distance = new double[ VPnum * STORAGEWIDTH];				

						setDelaunay();
						drawDelaunay();
					}
					else
					{	VPedgeN=0; 
						VPedgeA = new int[ VPnum * VPnum ];
						VPedgeE = new int[ VPnum * VPnum ];
						if (cbuk.getState())
						{
							VPres = new int[ VPnum * VPnum ];	// VPres= markier. bereits uerbern. punkte
							i=0; Dx=10;							// Dx=letzter winkel | Dda = dist.akt.zu pruef. Punkt
							do									// Ddo= dist.letzt.uebernomm. Punkt | Wb = winkel letzt uebern. Punkt
							{	b=0;Wb=-10;Ddo=-1;				// c=x-distance d=y-distance 
								for (j=0;j<VPnum;j++)
								{
									if (i!=j && VPres[j]==0)
									{
										c=(VPx[j]-VPx[i]);d=(VPy[j]-VPy[i]);
										Dda=fac=Math.sqrt((c*c)+(d*d));
										fac=d/fac;
										if (c>0 && d>=0) fac=2-fac;
										if (c>0 && d<0) fac=-2-fac;
										if ( fac>=Wb  && fac<=Dx ) { if (!((Wb==fac)&&(Dda>Ddo))) {b=j;Wb=fac;Ddo=Dda;} }
									}
								}
								VPedgeA[VPedgeN]=i; VPedgeE[VPedgeN]=b;
								VPedgeN++; i=b;	VPres[b]=1;	Dx=Wb; 																	
							}
							while(i!=0);
							
							for (i=0;i<VPedgeN;i++)	{	
							System.out.println(VPx[VPedgeA[i]]+"/"+VPy[VPedgeA[i]]+"  "+VPx[VPedgeE[i]]+"/"+VPy[VPedgeE[i]]);	}
							
						}
						else
						{
							for (i=0;i<VPnum;i++)
							{	for (j=i;j<VPnum;j++)
								{
									if (i!=j)
									{
										VPedgeA[VPedgeN]=i;VPedgeE[VPedgeN]=j;
										VPedgeN++;
									}
								}	
							}
						}

					}

					for (i=0;i<VPedgeN;i++)	{	Line2(VPx[VPedgeA[i]],VPy[VPedgeA[i]],VPx[VPedgeE[i]],VPy[VPedgeE[i]]);	}
					
					if(cbshad.getState())	{	MMFill();	}

					image.flush();
					image = createImage(new MemoryImageSource(visize,visize,vint,0,visize));

					repaint();	
					
					showStatus("DONE.  Points: "+VPnum+"  Edges: "+VPedgeN);
					
				}
				else
				{
					showStatus("More Than 50% Coloured Pixels."); 
				}
				
			}
			else
			{
				showStatus("Not Enough Points Found: "+j);
			}
		}

	}

	private void MMFill()
	{
		int x,y,xa,xe,n,m;
		
		for (y=0;y<visize;y++)
		{
			for (x=0;x<(visize-2);x++)
			{
				if ( ((vint[x+(y*visize)]&0xFFFFFF)!=0) && ((vint[x+1+(y*visize)]&0xFFFFFF)==0) )
				{
					xa=x;xe=0;

			loop2:	for (n=x+2;n<visize;n++)
					{	if ((vint[n+(y*visize)]&0xFFFFFF)!=0)	{	xe=n; break loop2;	}	}

					if (xa<xe)
					{	for (m=xa+1;m<xe;m++)
						{	
							vecr= (vint[xa+(y*visize)]>>16)&0xFF;vecg= (vint[xa+(y*visize)]>>8)&0xFF;vecb= (vint[xa+(y*visize)])&0xFF;
							vec2r=(vint[xe+(y*visize)]>>16)&0xFF;vec2g=(vint[xe+(y*visize)]>>8)&0xFF;vec2b=(vint[xe+(y*visize)])&0xFF;
							vecr=vecr-Math.round( ((float)(vecr-vec2r)) * (((float)m-xa)/((float)xe-xa))  );
							vecg=vecg-Math.round( ((float)(vecg-vec2g)) * (((float)m-xa)/((float)xe-xa))  );
							vecb=vecb-Math.round( ((float)(vecb-vec2b)) * (((float)m-xa)/((float)xe-xa))  );
							vint[m+(y*visize)]=(255<<24) | (vecr<<16) | (vecg<<8) | (vecb);
						} 
						x=xe-1;
					}

				}
			}
		}
	}
	
	private void Line2(int x1, int y1, int x2, int y2)
	{
		int x, y, xlength, ylength, dx=0, dy=0;
		float xslope, yslope; 

		xlength = Math.abs(x1-x2);
		if ((x1-x2) < 0) dx = -1;
		if ((x1-x2) == 0) dx = 0;		//	else if ((x1-x2) == 0) dx = 0;
		if ((x1-x2) > 0) dx =  1; 		//	else dx = +1;				

		ylength = Math.abs(y1-y2);
		if ((y1-y2) < 0) dy = -1;
		if ((y1-y2) == 0) dy = 0;		//	else if ((y1-y2) == 0) dy = 0;	
		if ((y1-y2) > 0) dy =  1; 		//	else dy = +1; 						

		if (dy == 0) 
		{
			if (dx < 0)	for (x=x1; x<(x2+1); x++)	{Putpixel (x,y1,  ((float)(x-x1))/((float)(x2-x1)) );}
			if (dx > 0)	for (x=x2; x<(x1+1); x++)	{Putpixel (x,y1,1-((float)(x-x2))/((float)(x1-x2)) );}
    	} 

		if (dx == 0)
		{
			if (dy < 0)	for (y=y1; y<(y2+1); y++)	{Putpixel (x1,y,  ((float)(y-y1))/((float)(y2-y1)) );}
			if (dy > 0)	for (y=y2; y<(y1+1); y++)	{Putpixel (x1,y,1-((float)(y-y2))/((float)(y1-y2)) );}
		} 

		if ((xlength != 0) && (ylength != 0))
		{
			xslope = ((float)xlength)/((float)ylength);
			yslope = ((float)ylength)/((float)xlength);
		}
		else
		{
			xslope = (float)0.0;	yslope = (float)0.0;
		} 

		if ((xslope != 0) && (yslope != 0) &&	((yslope/xslope) < 1)  && ((yslope/xslope) > -1)) 
		{	
			if (dx < 0)	for (x=x1; x<(x2+1); x++) {	y = y1+Math.round( ((float)-dy) * (yslope*((float)x-x1)) );	Putpixel (x,y,  ((float)(x-x1))/((float)(x2-x1)) );	}
			if (dx > 0)	for (x=x2; x<(x1+1); x++) {	y = y2+Math.round( ((float) dy) * (yslope*((float)x-x2)) );	Putpixel (x,y,1-((float)(x-x2))/((float)(x1-x2)) );	}
		}
		else
		{
			if (dy < 0)	for (y=y1; y<(y2+1); y++) {	x = x1+Math.round( ((float)-dx) * (xslope*((float)y-y1)) );	Putpixel (x,y,  ((float)(y-y1))/((float)(y2-y1)) );	}
			if (dy > 0)	for (y=y2; y<(y1+1); y++) {	x = x2+Math.round( ((float) dx) * (xslope*((float)y-y2)) );	Putpixel (x,y,1-((float)(y-y2))/((float)(y1-y2)) );	}
		} 
	}

	private void Putpixel (int Ppx, int Ppy, float perc)
	{
		vecr=(VPr[VPedgeA[i]])+Math.round(perc * (VPr[VPedgeE[i]]-VPr[VPedgeA[i]]) );
		vecg=(VPg[VPedgeA[i]])+Math.round(perc * (VPg[VPedgeE[i]]-VPg[VPedgeA[i]]) );
		vecb=(VPb[VPedgeA[i]])+Math.round(perc * (VPb[VPedgeE[i]]-VPb[VPedgeA[i]]) );

		if ( (vint[Ppx+(Ppy*visize)]&0xFFFFFF)!=0 )
		{
			vec2r=(vint[Ppx+(Ppy*visize)]>>16)&0xFF; vec2g=(vint[Ppx+(Ppy*visize)]>>8)&0xFF; vec2b=(vint[Ppx+(Ppy*visize)])&0xFF;
			vecr=(vecr+vec2r)/2;vecg=(vecg+vec2g)/2;vecb=(vecb+vec2b)/2;
		}

		vint[Ppx+(Ppy*visize)]= (255<<24) | (vecr<<16) | (vecg<<8) | (vecb);

		
	}


	
//*****************************************************************MaedaMameo**start***********
	
	private void setDelaunay(){
		int i, j, k, l, m, n;
		double a, b, c, x0, y0, x1, y1, x2, y2, x3, y3;

		for( i = 0; i < VPnum - 1; i++){
			x0 = VPx[i];
			y0 = VPy[i];
			n = i * STORAGEWIDTH + i + 1;
			for( j = i + 1; j < VPnum; j++){
				x1 = VPx[j];
				y1 = VPy[j];
				a = b = Math.sqrt( ( x1 - x0) * ( x1 - x0) + ( y1 - y0) * ( y1 - y0));

				loop:
				for( k = 0; k < i; k++){
					x2 = VPx[k];
					y2 = VPy[k];
					m = k * STORAGEWIDTH + k + 1;
					for( l = k + 1; l < VPnum; l++){
						c = distance[m];
						if( l != i && l != j && c >= 0){
							x3 = VPx[l];
							y3 = VPy[l];
							if( isCross( x0, y0, x1, y1, x2, y2, x3, y3)){
								if( b > c){
									a = -1;
									break loop;
								}
							}
						}
						m++;
					}
				}
				distance[n] = a;
				if( a >= 0){
					for( k = 0; k < i; k++){
						x2 = VPx[k];
						y2 = VPy[k];
						m = k * STORAGEWIDTH + k + 1;
						for( l = k + 1; l < VPnum; l++){
							x3 = VPx[l];
							y3 = VPy[l];
							if( l != i && l != j && distance[m] >= 0 && isCross( x0, y0, x1, y1, x2, y2, x3, y3)) distance[m] = -1;
							m++;
						}
					}
				}
				n++;
			}
		}
	}



	private void drawDelaunay( ){
		int i, j, n;

		VPedgeN=0;
		VPedgeA = new int[ VPnum * STORAGEWIDTH];
		VPedgeE = new int[ VPnum * STORAGEWIDTH];	

		for( i = 0; i < VPnum - 1; i++){
			n = i * STORAGEWIDTH + i + 1;
			for( j = i + 1; j < VPnum; j++){
				if( distance[n] >= 0){
					//g.drawLine( (int)VPx[i], (int)VPy[i], (int)VPx[j], (int)VPy[j]);
					//g.drawLine(205+(VPx[i]*360)/visize+circsize/2,20+(VPy[i]*360)/visize+circsize/2,205+(VPx[j]*360)/visize+circsize/2,20+(VPy[j]*360)/visize+circsize/2);
					VPedgeA[VPedgeN]=i;
					VPedgeE[VPedgeN]=j;
					VPedgeN++;					
				}
				n++;
			}
		}

		System.out.println("Found "+VPedgeN+" Edges");
	}



	private boolean isCross( double x0, double y0, double x1, double y1, double x2, double y2, double x3, double y3){
		double a, b;

		if( x1 == x0) a = 10000;
		else a = ( y1 - y0) / ( x1 - x0);
		b = y0 - a * x0;
		if( ( y2 - ( a * x2 + b)) * ( y3 - ( a * x3 + b)) < 0){
			if( x3 == x2) a = 10000;
			else a = ( y3 - y2) / ( x3 - x2);
			b = y2 - a * x2;
			if( ( y0 - ( a * x0 + b)) * ( y1 - ( a * x1 + b)) < 0) return true;
		}
		return false;
	}


//*****************************************************************MaedaMameo**stop************
	
	
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