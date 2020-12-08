//********************************************************************************
// FrogCells.java
//
// "FROG CELLS" 「カエルーム」
//
// (c) 1998 Maeda Mameo     http://www.alles.or.jp/~mameo/
//********************************************************************************

import java.awt.*;

public class FrogCells extends java.applet.Applet implements Runnable{
	private Image imageOff, imageStatics;
	private Graphics gOff, gStatics;
	private FontMetrics theFontMetrics;
	private int appW, appH;
	private Thread theThread;
	private boolean paintRequest;
	private Frame browserFrame;

	static final double PI = Math.PI;
	static final double PI2 = 2 * PI;

	static final double BORDERWIDTH = .5;
	static final double MAXFROGSPEED = 5;

	static final int MAXFROGNUM = 30;
	static final int STORAGEWIDTH = MAXFROGNUM + 4;
	private int frogNum;
	private double frogX[] = new double[MAXFROGNUM];
	private double frogY[] = new double[MAXFROGNUM];
	private double frogAngle[] = new double[MAXFROGNUM];
	private double frogSpeed[] = new double[MAXFROGNUM];

	private double distance[] = new double[ MAXFROGNUM * STORAGEWIDTH];
	private double sx[] = new double[ MAXFROGNUM * STORAGEWIDTH];
	private double sy[] = new double[ MAXFROGNUM * STORAGEWIDTH];
	private double ex[] = new double[ MAXFROGNUM * STORAGEWIDTH];
	private double ey[] = new double[ MAXFROGNUM * STORAGEWIDTH];

	private int markedCell, capturedFrog;

	private int mouseX, mouseY;
	private int clickedX, clickedY;
	private boolean isMouseShoved;

	private double magnification;

	private boolean fpsRequest;
	private String fpsString;
	static final int MAXFPS = 24;

//********************************************************************************
//********************************************************************************

	public void init(){
		Dimension d;
		Component c;

		mouseX = -1;
		isMouseShoved = false;

		fpsString = "";
		paintRequest = fpsRequest = false;

		setBackground(Color.white);

		d = size();
		appW = d.width;
		appH = d.height;
		imageOff = createImage( appW, appH); gOff = imageOff.getGraphics();
		imageStatics = createImage( appW, appH); gStatics = imageStatics.getGraphics();

		for( c = getParent(); c != null; c = c.getParent()){
			if( c instanceof Frame){
				browserFrame = (Frame)c;
				break;
			}
		}

		gStatics.setFont( new Font( "TimesRoman", java.awt.Font.BOLD, 100));
		theFontMetrics = gStatics.getFontMetrics();
	}

	public void update( Graphics g){ paint(g);}

	public void paint( Graphics g){
		if(fpsRequest) showStatus(fpsString);

		if(paintRequest){
			g.drawImage( imageOff, 0, 0, this);
			paintRequest = false;
		}
	}

	public void start(){ theThread = new Thread(this); theThread.start();}

	public void stop(){ theThread.stop(); theThread = null;}

	public boolean mouseDown( Event e, int x, int y){
		isMouseShoved = true;
		if( clickedY < 0 && x >= 10 && x < appW - 10 && y >= 10 && y < appH - 10){
			clickedX = x;
			clickedY = y;
		}
		return true;
	}

	public boolean mouseUp( Event e, int x, int y){
		isMouseShoved = false;
		return true;
	}

	public boolean mouseMove( Event e, int x, int y){
		mouseX = x;
		mouseY = y;
		return true;
	}

	public boolean mouseDrag( Event e, int x, int y){
		mouseX = x;
		mouseY = y;
		return true;
	}

	public boolean mouseEnter( Event e, int x, int y){
		mouseX = x;
		mouseY = y;

		fpsRequest = true;
		return true;
	}

	public boolean mouseExit( Event e, int x, int y){
		mouseX = -1;

		fpsRequest = false;
		showStatus(null);
		return true;
	}

//********************************************************************************
//********************************************************************************

	public void run(){
		int i;
		int timeMillisCount;
		long delay, averageTime, latestTimeMillis, t;
		boolean f;

		markedCell = capturedFrog = -1;

		timeMillisCount = 0;
		delay = 20;
		latestTimeMillis = System.currentTimeMillis();

		frogNum = MAXFROGNUM; clickedY = 0;

		while(true){
			if( clickedY >= 0){
				if( capturedFrog < 0){
					if( frogNum < MAXFROGNUM){
						frogX[frogNum] = clickedX;
						frogY[frogNum] = clickedY;
						frogAngle[frogNum] = PI2 * Math.random();
						frogSpeed[frogNum] = MAXFROGSPEED;

						frogNum++;

					} else resetFrog();

					markedCell = capturedFrog = -1;

					gStatics.setColor(Color.white);
					gStatics.fillRect( 0, 0, appW, appH);

					gStatics.setColor(Color.lightGray);
					for( i = 0; i < 20; i++){
						gStatics.drawLine( (int)( ( i + .5) / 20 * appW), 0, (int)( ( i + .5) / 20 * appW), appH - 1);
						gStatics.drawLine( 0, (int)( ( i + .5) / 20 * appH), appW - 1, (int)( ( i + .5) / 20 * appH));
					}
					gStatics.drawString( Integer.toString(frogNum), appW / 20, appH - appH / 20);
				}
				clickedY = -1;
			} 

			f = ( mouseX >= 10 && mouseX < appW - 10 && mouseY >= 10 && mouseY < appH - 10);
			if(f){
				if( isMouseShoved && capturedFrog >= 0){
					frogX[capturedFrog] = mouseX;
					frogY[capturedFrog] = mouseY;
				}
			} else moveFrog();

			setDelaunay();
			setVoronoi();

			if(f){
				if( isMouseShoved == false){
					markedCell = getMarkedCell( mouseX, mouseY);
					capturedFrog = -1;
					if( markedCell >= 0){
						if( Math.pow( frogX[markedCell] - mouseX, 2) + Math.pow( frogY[markedCell] - mouseY, 2) < Math.pow( 10, 2)){
							capturedFrog = markedCell;
						}
					}
				}
			} else markedCell = capturedFrog = -1;

			if( capturedFrog >= 0) browserFrame.setCursor(Frame.HAND_CURSOR);
			else browserFrame.setCursor(Frame.DEFAULT_CURSOR);

			sleeper(delay);
			while(paintRequest) sleeper(1);

			gOff.drawImage( imageStatics, 0, 0, this);
			drawMarkedCell( markedCell, Color.pink);
			drawDelaunay(Color.magenta);
			drawVoronoi(Color.darkGray);
			drawCores();
			paintRequest = true; repaint();

			if( ++timeMillisCount == MAXFPS){
				t = System.currentTimeMillis();
				averageTime = ( t - latestTimeMillis) / MAXFPS;
				fpsString = "" + 1000 / averageTime + "." + 10000 / averageTime % 10 + " frames a second.";
				delay = Math.max( 20, delay + 1000 / MAXFPS - averageTime);
				latestTimeMillis = t;
				timeMillisCount = 0;
			}
		}
	}

	private void resetFrog(){
		int i;
		double a;

		frogNum = 7;

		for( i = 0; i < frogNum; i++){
			a = PI2 * i / frogNum + .2 * Math.random();
			frogX[i] = ( .5 + ( .2 + .2 * Math.random()) * Math.cos(a)) * appW;
			frogY[i] = ( .5 + ( .2 + .2 * Math.random()) * Math.sin(a)) * appH;
			frogAngle[i] = a + .7 * PI;
			frogSpeed[i] =  ( 2 + i) * MAXFROGSPEED / ( frogNum + 1);
		}
	}

	private void drawCores(){
		int i, r, g, b;
		double a, x, y;

		for( i = 0; i < frogNum; i++){
			x = frogX[i];
			y = frogY[i];
			a = frogAngle[i];

			if( i == capturedFrog){
				magnification = .25;
				drawFrog( x, y, Color.green, a);
			} else{
				magnification = .05;
				drawTadpole( x, y, Color.black, a);
			}
		}
	}

	private void drawTadpole( double x, double y, Color c, double r){
		double vx, vy;

		r += -.3 + .6 * Math.random();
		vx = magnification * Math.cos(r);
		vy = magnification * Math.sin(r);

		gOff.setColor(c);
		drawFillCircle( x, y, 40);
		gOff.drawLine( (int)( x - 35 * vx), (int)( y - 35 * vy), (int)( x - 120 * vx), (int)( y - 120 * vy));
	}

	private void drawFrog( double x, double y, Color c, double r){
		double xL, xR, vx, yL, yR, vy;

		vx = magnification * Math.cos(r); vy = magnification * Math.sin(r);
		xL = x + 28 * vx + 32 * vy      ; yL = y + 28 * vy - 32 * vx;
		xR = x + 28 * vx - 32 * vy      ; yR = y + 28 * vy + 32 * vx;

		gOff.setColor(Color.black);
		drawFillCircle( x, y, 40);
		drawFillCircle( xL, yL, 24);
		drawFillCircle( xR, yR, 24);

		gOff.setColor(c);
		drawFillCircle( x, y, 32);
		drawFillCircle( xL, yL, 16);
		drawFillCircle( xR, yR, 16);

		gOff.setColor(Color.white);
		drawFillCircle( xL, yL, 12);
		drawFillCircle( xR, yR, 12);

		gOff.setColor(Color.black);
		drawFillCircle( xL + 8 * vx, yL + 8 * vy, 7);
		drawFillCircle( xR + 8 * vx, yR + 8 * vy, 7);

		drawFillCircle( x - 5 * vx + 8 * vy, y - 5 * vy - 8 * vx, 3);
		drawFillCircle( x - 5 * vx - 8 * vy, y - 5 * vy + 8 * vx, 3);
	}

	private void drawFillCircle( double x, double y, int r){
		gOff.fillOval(
			(int)( x - magnification * r),
			(int)( y - magnification * r),
			(int)( magnification * 2 * r) + 1,
			(int)( magnification * 2 * r) + 1
		);
	}

	private void moveFrog(){
		int i, t;
		double a, s, x, y, cx, cy, vx, vy;

		for( i = 0; i < frogNum; i++){
			x = frogX[i];
			y = frogY[i];
			a = frogAngle[i];
			s = frogSpeed[i];

			vx = Math.cos(a) * s;
			vy = Math.sin(a) * s;

			x += vx;
			y += vy;

			if( x < 10 || x >= appW - 10 || y < 10 || y >= appH - 10){
				if( x < 10){ x = 10; vx *= -1;}
				if( x > appW - 11){ x = appW - 11; vx *= -1;}
				if( y < 10){ y = 10; vy *= -1;}
				if( y > appH - 11){ y = appH - 11; vy *= -1;}
				frogAngle[i] = getAngle( vx, vy);
			}

			frogX[i] = x;
			frogY[i] = y;
		}
	}

	private void setDelaunay(){
		int i, j, k, l, m, n;
		double a, b, c, x0, y0, x1, y1, x2, y2, x3, y3;

		for( i = 0; i < frogNum - 1; i++){
			x0 = frogX[i];
			y0 = frogY[i];
			n = i * STORAGEWIDTH + i + 1;
			for( j = i + 1; j < frogNum; j++){
				x1 = frogX[j];
				y1 = frogY[j];
				a = b = Math.sqrt( ( x1 - x0) * ( x1 - x0) + ( y1 - y0) * ( y1 - y0));

				loop:
				for( k = 0; k < i; k++){
					x2 = frogX[k];
					y2 = frogY[k];
					m = k * STORAGEWIDTH + k + 1;
					for( l = k + 1; l < frogNum; l++){
						c = distance[m];
						if( l != i && l != j && c >= 0){
							x3 = frogX[l];
							y3 = frogY[l];
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
						x2 = frogX[k];
						y2 = frogY[k];
						m = k * STORAGEWIDTH + k + 1;
						for( l = k + 1; l < frogNum; l++){
							x3 = frogX[l];
							y3 = frogY[l];
							if( l != i && l != j && distance[m] >= 0 && isCross( x0, y0, x1, y1, x2, y2, x3, y3)) distance[m] = -1;
							m++;
						}
					}
				}
				n++;
			}
		}
	}

	private void drawDelaunay( Color c){
		int i, j, n;

		gOff.setColor(c);

		for( i = 0; i < frogNum - 1; i++){
			n = i * STORAGEWIDTH + i + 1;
			for( j = i + 1; j < frogNum; j++){
				if( distance[n] >= 0){
					gOff.drawLine( (int)frogX[i], (int)frogY[i], (int)frogX[j], (int)frogY[j]);
				}
				n++;
			}
		}
	}

	private void setVoronoi(){
		int i, j, k, m, n;
		double a, b, a0, b0, a1, b1, x, y, x0, y0, x1, y1;

		for( i = 0; i < frogNum; i++){
			x0 = frogX[i];
			y0 = frogY[i];
			n = i * STORAGEWIDTH + i + 1;

			for( j = i + 1; j < frogNum; j++){
				x1 = frogX[j];
				y1 = frogY[j];

				if( x1 == x0) a = 0;
				else if( y1 == y0) a = 10000;
				else a = -1 / ( ( y1 - y0) / ( x1 - x0));
				b = ( y0 + y1) / 2 - a * ( x0 + x1) / 2;

				if( a > -1 && a <= 1){
					sx[n] = 0;
					sy[n] = a * sx[n] + b;
					ex[n] = appW - 1;
					ey[n] = a * ex[n] + b;
				} else{
					sy[n] = 0;
					sx[n] = ( sy[n] - b) / a;
					ey[n] = appH - 1;
					ex[n] = ( ey[n] - b) / a;
				}
				n++;
			}
			sx[n] = BORDERWIDTH; sy[n] = BORDERWIDTH; ex[n] = appW - BORDERWIDTH; ey[n] = BORDERWIDTH;
			n++;
			sx[n] = BORDERWIDTH + .1; sy[n] = BORDERWIDTH; ex[n] = BORDERWIDTH; ey[n] = appH - BORDERWIDTH;
			n++;
			sx[n] = appW - BORDERWIDTH; sy[n] = BORDERWIDTH; ex[n] = appW - BORDERWIDTH - .1; ey[n] = appH - BORDERWIDTH;
			n++;
			sx[n] = BORDERWIDTH; sy[n] = appH - BORDERWIDTH; ex[n] = appW - BORDERWIDTH; ey[n] = appH - BORDERWIDTH;
		}

		for( i = 0; i < frogNum; i++){
			x0 = frogX[i];
			y0 = frogY[i];

			for ( j = 0; j < frogNum + 4; j++) if( j != i){
				if( j > i) n = i * STORAGEWIDTH + j; else n = j * STORAGEWIDTH + i;
				if( sx[n] > -Double.MAX_VALUE){
					a0 = ( ey[n] - sy[n]) / ( ex[n] - sx[n]);
					b0 = sy[n] - a0 * sx[n];

					for( k = i + 1; k < frogNum + 4; k++) if( k != j){
						m = i * STORAGEWIDTH + k;
						if( sx[m] > -Double.MAX_VALUE){
							a1 = ( ey[m] - sy[m]) / ( ex[m] - sx[m]);
							b1 = sy[m] - a1 * sx[m];

							x = -( b1 - b0) / ( a1 - a0);
							y = a0 * x + b0;

							if( ( a0 * x0 + b0 - y0) * ( a0 * sx[m] + b0 - sy[m]) < 0){
								sx[m] = x;
								sy[m] = y;
							}
							if( ( a0 * x0 + b0 - y0) * ( a0 * ex[m] + b0 - ey[m]) < 0){
								if( sx[m] == x){
									sx[m] = -Double.MAX_VALUE;
								} else{
									ex[m] = x;
									ey[m] = y;
								}
							}
						}
					}
				}
			}
		}
	}

	private void drawVoronoi( Color c){
		int i, j, n;

		gOff.setColor(c);

		for( i = 0; i < frogNum; i++){
			n = i * STORAGEWIDTH + i + 1;
			for( j = i + 1; j < frogNum + 4; j++){
				if( sx[n] > -Double.MAX_VALUE) gOff.drawLine( (int)sx[n], (int)sy[n], (int)ex[n], (int)ey[n]);
				n++;
			}
		}
	}

	private int getMarkedCell( int x, int y){
		int i, j, n;
		double a, b;
		boolean f;

		if( x < 0) return -1;

		for( i = 0; i < frogNum; i++){
			f = false;
			for( j = 0; j < frogNum; j++) if( j != i){
				if( j > i) n = i * STORAGEWIDTH + j; else n = j * STORAGEWIDTH + i;
				if( sx[n] > -Double.MAX_VALUE){
					if( ex[n] == sx[n]) a = Double.MAX_VALUE;
					else a = ( ey[n] - sy[n]) / ( ex[n] - sx[n]);
					b = sy[n] - a * sx[n];
					if( ( a * frogX[i] + b - frogY[i]) * ( a * x + b - y) < 0){
						f = false;
						break;
					} else{
						f = true;
					}
				}
			}
			if(f) return i;
		}
		return -1;
	}

	private void drawMarkedCell( int m, Color c){
		int i, j, k, n;
		double a, x, y;
		int indices[] = new int[STORAGEWIDTH];
		double angles[] = new double[STORAGEWIDTH];
		Polygon p;

		if( m < 0) return;

		p = new Polygon();
		x = frogX[m];
		y = frogY[m];

		angles[0] = 100;
		for( i = 0; i < frogNum + 4; i++) if( i != m){
			if( i > m) n = m * STORAGEWIDTH + i; else n = i * STORAGEWIDTH + m;
			if( sx[n] > -Double.MAX_VALUE){
				a = getAngle( ( sx[n] + ex[n]) / 2 - x, ( sy[n] + ey[n]) / 2 - y);
				for( j = 0; j < STORAGEWIDTH; j++){
					if( angles[j] > a){
						for( k = frogNum + 4 - 1; k > j; k--){
							angles[k] = angles[ k - 1];
							indices[k] = indices[ k - 1];
						}
						angles[j] = a;
						indices[j] = i;
						break;
					}
				}
			}
			n++;
		}

		for( i = 0; i < STORAGEWIDTH; i++){
			if( angles[i] == 100) break;

			j = indices[i];
			if( m < j) n = m * STORAGEWIDTH + j; else n = j * STORAGEWIDTH + m;
			if( angleDifference( getAngle( sx[n] - x, sy[n] - y), getAngle( ex[n] - x, ey[n] - y)) < 0)
				p.addPoint( (int)ex[n], (int)ey[n]);
			else p.addPoint( (int)sx[n], (int)sy[n]);
		}

		gOff.setColor(c);
		gOff.fillPolygon(p);
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

	private double getAngle( double vx, double vy){
		double a;

		if( vy == 0) if( vx < 0) return PI; else return 0;
		a = Math.acos( vx / Math.sqrt( vx * vx + vy * vy));
		if( vy > 0) return a; else return ( PI2 - a);
	}

	private double angleDifference( double t, double s){
		double a;

		a = t - s;
		if( a > PI) return( a - PI2);
		if( a < -PI) return( a + PI2);
		return a;
	}

	private void sleeper( long t){ try theThread.sleep(t); catch( InterruptedException e);}
}
