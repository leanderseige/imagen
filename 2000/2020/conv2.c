#include <stdlib.h>
#include <stdio.h>
#include <time.h>

unsigned int para[4096];		/* Datenfeld			*/
unsigned int paraout[65536];	/* Datenfeld output     */
unsigned int paramix[65536];	/* Hilfsfeld pixelmixer	*/
unsigned long int a,b,c,d,shift,x,y,zx,zy;	/* div Hilfsvariablen 	*/
unsigned long int pic,pici,maxpix;		/* Bild Index Zaehler 	*/
unsigned int farbe[9][9];
unsigned int inpix,inbit,inmod,incol;


time_t zstart,zende;			/* Zeitstrukturen		*/

FILE *dateiptr;					/* zum Filehandling		*/
char *dateiname="./data/genima.raw";
char *seedname ="./data/seed";



void readseed(void)
{	
	dateiptr=fopen(seedname,"r");

	if(dateiptr==NULL)
		{	fprintf(stderr,"\nKein Seed gefunden.\n");
		}
	else
		{	
			for(b=0;b<=4095;b++)
			{	fscanf(dateiptr," %d ",&para[b]);
			}
			fclose(dateiptr);
			fprintf(stderr,"\nSeed geladen.\n");
		}	
}


void pixeldiager(unsigned int mixpix)
{
	for (a=0;a<(mixpix*mixpix);a++)
	{
		paramix[a]=paraout[a];		/* 	wegretten	 */
		paraout[a]=0;
	}

		if (incol==3) {	mixpix=mixpix/2;	}

	for (a=0;a<mixpix;a++)
	{
		for (b=0;b<mixpix;b++)
		{
			c=a*incol;
			paraout[c+ ( ((b+a)%(mixpix*incol))*mixpix*incol ) ]=paramix[(b*mixpix*incol)+c];
			if (incol==3)
			{
				paraout[c+1+ ( ((b+a)%(mixpix*incol))*mixpix*incol ) ]=paramix[(b*mixpix*incol)+c+1];
				paraout[c+2+ ( ((b+a)%(mixpix*incol))*mixpix*incol ) ]=paramix[(b*mixpix*incol)+c+2];							
			}
		}
	}
}


void pixelspira(unsigned int mixpix)
{
	for (a=0;a<65536;a++)
	{
		paramix[a]=paraout[a];		/* 	wegretten	 */
		paraout[a]=0;
	}

		if (incol==3) {	mixpix=mixpix/2;	}
		maxpix=(mixpix*mixpix*incol)-1;
		d=mixpix-1;x=0;y=0;

		while(d>2)
		{
			for (a=0;a<d;a++)	{
									if (incol==1)
									{	paraout[(x     )*incol+((x+a)  *mixpix*incol)]			=paramix[maxpix-y]; y++;	}
									else
									{	paraout[(x     )*incol+((x+a)  *mixpix*incol)]			=paramix[maxpix-y-2];
										paraout[(x     )*incol+((x+a)  *mixpix*incol)+1]		=paramix[maxpix-y-1];
										paraout[(x     )*incol+((x+a)  *mixpix*incol)+2]		=paramix[maxpix-y];
										y=y+3;
									}
								} 
			for (a=0;a<d;a++)	{	if (incol==1)
									{	paraout[(x+a   )*incol+((x+d)  *mixpix*incol)]			=paramix[maxpix-y]; y++;	}	
									else
									{	paraout[(x+a   )*incol+((x+d)  *mixpix*incol)]			=paramix[maxpix-y-2];
										paraout[(x+a   )*incol+((x+d)  *mixpix*incol)+1]		=paramix[maxpix-y-1];
										paraout[(x+a   )*incol+((x+d)  *mixpix*incol)+2]		=paramix[maxpix-y];
										y=y+3;
									}
				
								}			
			for (a=0;a<d;a++)	{	if (incol==1)
									{	paraout[(x+d   )*incol+((x+d-a)*mixpix*incol)]			=paramix[maxpix-y]; y++;	}			
									else
									{	paraout[(x+d   )*incol+((x+d-a)*mixpix*incol)]			=paramix[maxpix-y-2];
										paraout[(x+d   )*incol+((x+d-a)*mixpix*incol)+1]		=paramix[maxpix-y-1];
										paraout[(x+d   )*incol+((x+d-a)*mixpix*incol)+2]		=paramix[maxpix-y];
										y=y+3;
									}
								}		
			for (a=0;a<d;a++)	{	if (incol==1)
									{	paraout[(x+d-a )*incol+((x)    *mixpix*incol)]			=paramix[maxpix-y]; y++;	}
									else
									{	paraout[(x+d-a )*incol+((x)    *mixpix*incol)]			=paramix[maxpix-y-2];	
										paraout[(x+d-a )*incol+((x)    *mixpix*incol)+1]		=paramix[maxpix-y-1];	
										paraout[(x+d-a )*incol+((x)    *mixpix*incol)+2]		=paramix[maxpix-y];	
										y=y+3;
									}

								}		
			d=d-2;x++;
		}
		if (d==1)	
		{	
			zx=((mixpix>>1)-1)*incol+(((mixpix>>1)-1)*mixpix*incol);

			if (incol==1)
			{	paraout[zx]						=paramix[maxpix-y];y++;	}
			else
			{	paraout[zx  ]					=paramix[maxpix-y-2];
				paraout[zx+1]					=paramix[maxpix-y-1];			
				paraout[zx+2]					=paramix[maxpix-y];
				y=y+3;			
			}
			if (incol==1)
			{	paraout[zx  +mixpix*incol]		=paramix[maxpix-y];y++; }
			else
			{	paraout[zx  +mixpix*incol]		=paramix[maxpix-y-2];
				paraout[zx+1+mixpix*incol]		=paramix[maxpix-y-1];			
				paraout[zx+2+mixpix*incol]		=paramix[maxpix-y];			
				y=y+3;
			}	
			if (incol==1)
			{	paraout[zx  +mixpix*incol+incol]=paramix[maxpix-y];y++; }
			else
			{	paraout[zx  +mixpix*incol+incol]=paramix[maxpix-y-2];
				paraout[zx+1+mixpix*incol+incol]=paramix[maxpix-y-1];	
				paraout[zx+2+mixpix*incol+incol]=paramix[maxpix-y];
				y=y+3;			
			}	
			if (incol==1)
			{	paraout[zx  +incol]				=paramix[maxpix-y];y++; }
			else
			{	paraout[zx  +incol]				=paramix[maxpix-y-2];
				paraout[zx+1+incol]				=paramix[maxpix-y-1];			
				paraout[zx+2+incol]				=paramix[maxpix-y];			
				y=y+3;
			}		
		}
		if (d==2)	
		{
			zx=((mixpix>>1)-1)*incol+(((mixpix>>1)-1)*mixpix*incol);

			if (incol==1)
			{	paraout[zx]							=paramix[maxpix-y];y++;	}
			else
			{	paraout[zx  ]						=paramix[maxpix-y-2];
				paraout[zx+1]						=paramix[maxpix-y-1];			
				paraout[zx+2]						=paramix[maxpix-y];
				y=y+3;			
			}
			if (incol==1)
			{	paraout[zx  +mixpix*incol]			=paramix[maxpix-y];y++; }
			else	
			{	paraout[zx  +mixpix*incol]			=paramix[maxpix-y-2];
				paraout[zx+1+mixpix*incol]			=paramix[maxpix-y-1];			
				paraout[zx+2+mixpix*incol]			=paramix[maxpix-y];			
				y=y+3;
			}	
			if (incol==1)
			{	paraout[zx  +mixpix*incol*2]		=paramix[maxpix-y];y++; }
			else
			{	paraout[zx  +mixpix*incol*2]		=paramix[maxpix-y-2];
				paraout[zx+1+mixpix*incol*2]		=paramix[maxpix-y-1];			
				paraout[zx+2+mixpix*incol*2]		=paramix[maxpix-y];			
				y=y+3;
			}	
			if (incol==1)
			{	paraout[zx  +mixpix*incol*2+incol]	=paramix[maxpix-y];y++; }
			else
			{	paraout[zx  +mixpix*incol*2+incol]	=paramix[maxpix-y-2];
				paraout[zx+1+mixpix*incol*2+incol]	=paramix[maxpix-y-1];	
				paraout[zx+2+mixpix*incol*2+incol]	=paramix[maxpix-y];
				y=y+3;			
			}				
			if (incol==1)
			{	paraout[zx  +mixpix*incol*2+incol*2]=paramix[maxpix-y];y++; }
			else
			{	paraout[zx  +mixpix*incol*2+incol*2]=paramix[maxpix-y-2];
				paraout[zx+1+mixpix*incol*2+incol*2]=paramix[maxpix-y-1];	
				paraout[zx+2+mixpix*incol*2+incol*2]=paramix[maxpix-y];
				y=y+3;			
			}							
			if (incol==1)
			{	paraout[zx  +mixpix*incol+incol*2]=paramix[maxpix-y];y++; }
			else
			{	paraout[zx  +mixpix*incol+incol*2]=paramix[maxpix-y-2];
				paraout[zx+1+mixpix*incol+incol*2]=paramix[maxpix-y-1];	
				paraout[zx+2+mixpix*incol+incol*2]=paramix[maxpix-y];
				y=y+3;			
			}							
			if (incol==1)
			{	paraout[zx  +incol*2]=paramix[maxpix-y];y++; }
			else
			{	paraout[zx  +incol*2]=paramix[maxpix-y-2];
				paraout[zx+1+incol*2]=paramix[maxpix-y-1];	
				paraout[zx+2+incol*2]=paramix[maxpix-y];
				y=y+3;			
			}					
			if (incol==1)
			{	paraout[zx  +incol]=paramix[maxpix-y];y++; }
			else
			{	paraout[zx  +incol]=paramix[maxpix-y-2];
				paraout[zx+1+incol]=paramix[maxpix-y-1];	
				paraout[zx+2+incol]=paramix[maxpix-y];
				y=y+3;			
			}
			if (incol==1)
			{	paraout[zx  +mixpix*incol+incol]	=paramix[maxpix-y];y++; }
			else
			{	paraout[zx  +mixpix*incol+incol]	=paramix[maxpix-y-2];
				paraout[zx+1+mixpix*incol+incol]	=paramix[maxpix-y-1];	
				paraout[zx+2+mixpix*incol+incol]	=paramix[maxpix-y];
				y=y+3;			
			}													
		
		}

}


int psave(unsigned int ps_pixs, unsigned int ps_bits)		
{		
	dateiptr=fopen(dateiname,"w");
	
	if(dateiptr==NULL)
		{	fprintf(stderr,"Kein Dateihandle.\n");
		}
	else
		{	x=0;y=0;
			while (x<(ps_pixs*ps_pixs))
			{	for (a=1;a<=ps_bits;a++)
				{	paraout[x]=paraout[x]+( ( (para[y>>4]>>((y%16))) &0x0001) * farbe[ps_bits][a] );
					y++;
				}
				x++;
			}

			if (inmod==2||inmod==5) 
			{ 
				pixeldiager( ps_pixs );
			}

			if (inmod==3||inmod==5) 
			{ 
				pixelspira( ps_pixs );
			}

			for (a=0;a<(ps_pixs*ps_pixs);a++)
			{	fputc(paraout[a], dateiptr);	/* alter paraout[x] bei x=(ps_pixs*ps_pixs)-a-1 */
			}

			fclose(dateiptr);
			return(0); 
		}	
}



int main ( int argc, char *argv[] )
{	

farbe[1][1]=0xFF;
farbe[2][1]=0x55;farbe[2][2]=0xAA;
farbe[3][1]=0x25;farbe[3][2]=0x49;farbe[3][3]=0x91;
farbe[4][1]=0x11;farbe[4][2]=0x22;farbe[4][3]=0x44;farbe[4][4]=0x88;
farbe[5][1]=0x08;farbe[5][2]=0x10;farbe[5][3]=0x20;farbe[5][4]=0x41;farbe[5][5]=0x83;
farbe[6][1]=0x04;farbe[6][2]=0x08;farbe[6][3]=0x10;farbe[6][4]=0x20;farbe[6][5]=0x41;farbe[6][6]=0x82;
farbe[7][1]=0x02;farbe[7][2]=0x04;farbe[7][3]=0x08;farbe[7][4]=0x10;farbe[7][5]=0x20;farbe[7][6]=0x40;farbe[7][7]=0x81;
farbe[8][1]=0x01;farbe[8][2]=0x02;farbe[8][3]=0x04;farbe[8][4]=0x08;farbe[8][5]=0x10;farbe[8][6]=0x20;farbe[8][7]=0x40;farbe[8][8]=0x80;
	
	for (a=0;a<=65535;a++)
	{	paraout[a]=0;
		paramix[a]=0;
	}

	incol=atoi(argv[4]);
	inmod=atoi(argv[3]);
	inbit=atoi(argv[2]);
	inpix=atoi(argv[1]);

	readseed();
	
	psave(inpix,inbit);

	return (0);
}