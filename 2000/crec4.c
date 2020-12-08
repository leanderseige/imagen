#include <stdlib.h>
#include <stdio.h>
#include <time.h>

unsigned int para[4096];		/* Datenfeld			*/
unsigned int a,b,c,d,shift; 	/* div Hilfsvariablen 	*/
unsigned long int pic,pici;		/* Bild Index Zaehler 	*/

time_t zstart,zende;			/* Zeitstrukturen		*/

FILE *dateiptr;					/* zum Filehandling		*/
char *dateiname="./data/genima.raw";
char *seedname ="./data/seed";



void binaus(unsigned int vari)	 
{	printf(" |");
	for (b=0;b<=15;b++)
	{	if ( vari&0x01==0x01 ) { printf("*"); }
		else { printf(" "); }
		vari=vari>>1;
	}
	printf("|\n");
}



void writeseed(void)
{	
	dateiptr=fopen(seedname,"w");
	
	if(dateiptr==NULL)
		{	printf("\nKonnte Seed nicht speichern.\n");
		}
	else
		{	
			for(b=0;b<=4095;b++)
			{	fprintf(dateiptr," %d \n",para[b]);
			}
			fclose(dateiptr); 
		}	
}



void readseed(void)
{	
	dateiptr=fopen(seedname,"r");

	if(dateiptr==NULL)
		{	printf("\nKein Seed gefunden.\n");
		}
	else
		{	
			for(b=0;b<=4095;b++)
			{	fscanf(dateiptr," %d ",&para[b]);
			}

			fclose(dateiptr);
			printf ("\n +-------><-------+ : eingelesen \n");		

			for (a=0;a<=15;a++)
			{	binaus(para[15-a]);
			}
			printf (" +-------><-------+\n"); 

			if (para[0]==65535) { para[0]=0; }				/* weil dieser eine Wert */
			else				{ para[0]=para[0]+1; }		/* sonst doppelt prozessiert wird (???) */
		}	
}



int psave(unsigned int ps_pixs, unsigned int ps_bits)					
{		
	dateiptr=fopen(dateiname,"w");
	
	if(dateiptr==NULL)
		{	printf("Kein Dateihandle.\n");
		}
	else
		{	
			for(b=0;b<=15;b++)
			{	shift=para[15-b];
				for (a=0;a<=15;a++)
				{	if ( shift&0x01==1 ) {  fputc(0xFF, dateiptr);  }
					else {  fputc(0x00, dateiptr);  }
					shift=shift>>1;
				}
			}

			fclose(dateiptr);
			return(0); 
		}	

}



void leostat(void)				 
{				zende=time(NULL);
				pic=0; pici++;	
				printf ("\n +-------><-------+ %3d Mrd / %d s\n",pici*2,zende-zstart);		
				for (a=0;a<=15;a++)
				{	binaus(para[15-a]);
/*					 printf(" %d\n",para[15-a]);	*/
				}
				printf (" +-------><-------+\n");
				psave(0,0); writeseed();
}



void rekursiv(unsigned int rec_d)
{	
	unsigned int r;

	rec_d=rec_d-1;
	
	for (r=para[rec_d];r<=65535;r++) 
	{
		para[rec_d]=r;
		
			if (rec_d!=0)
				{	rekursiv(rec_d);
				}
			else
				{	pic++;
					if(pic>=2000000000) /* 2000000000 */
					{	leostat();
					}
				}
	}
	
	para[rec_d]=0;
}



int main (void)
{	
	for(a=0;a<=4095;a++) para[a]=0;
	d=4096;	/* 4096 statt 4095 weil in rekursiver Fnkt. Subtraktion VOR Wert->Speicher */
	pic=0;a=0;b=0;c=0;
	pic=0;pici=0;

	readseed();

	zstart=time(NULL);
	rekursiv(d);

	return (0);
}