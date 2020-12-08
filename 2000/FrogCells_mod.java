
	static final int MAXFROGNUM = 30;
	static final int STORAGEWIDTH = MAXFROGNUM + 4;
	private double distance[] = new double[ MAXFROGNUM * STORAGEWIDTH];
	

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
