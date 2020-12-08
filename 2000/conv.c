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
			printf("Seed geladen.\n");
		}	
}



int psave(unsigned int ps_pixs, unsigned int ps_bits)					
{		
	dateiptr=fopen(dateiname,"w");
	
	if(dateiptr==NULL)
		{	printf("Kein Dateihandle.\n");
		}
	else
		{	printf (" %dx%d - %d\n", ps_pixs, ps_pixs, ps_bits);	
		
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



int main (void)
{	
	readseed();
	
	psave(0,0);

	return (0);
}