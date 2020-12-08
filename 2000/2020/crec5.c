#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include <signal.h>
#include <unistd.h>
#include <sys/types.h>

unsigned int para[4096];		/* Datenfeld			*/
unsigned int a,b,c,d,shift; 	/* div Hilfsvariablen 	*/
unsigned long int pic,pici;		/* Bild Index Zaehler 	*/

time_t zstart,zende;			/* Zeitstrukturen		*/

pid_t imagenpid;				/* eigene PID			*/ 
pid_t syncpid;					/* Synctool PID			*/

FILE *dateiptr;					/* zum Filehandling		*/
char *dateiname="./data/genima.raw";
char *seedname ="./data/seed";
char *imagename="./data/impid";
char *syncname ="./data/syncpid";

volatile sig_atomic_t ischubs;



void writepid()
{
	imagenpid=getpid();
	
	dateiptr=fopen(imagename,"w");
	
	if(dateiptr==NULL)
		{	printf("\nKonnte PID nicht speichern.\n");
		}
	else
		{	
			fprintf(dateiptr," %d \n",imagenpid);
			fclose(dateiptr); 
		}	
}



void readpid()
{	
	dateiptr=fopen(syncname,"r");
	
	if(dateiptr==NULL)
		{	printf("\nKonnte PID nicht oeffnen.\n");
		}
	else
		{	
			fscanf(dateiptr," %d \n",&syncpid);
			fclose(dateiptr); 
		}	
}



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
			{	binaus(para[a]);
			}
			printf (" +-------><-------+\n"); 

			if (para[0]==65535) { para[0]=0; }				/* weil dieser eine Wert 				*/
			else				{ para[0]=para[0]+1; }		/* sonst doppelt prozessiert wird (???) */
		}	
}



void ischubs_mich (int x)
{
	ischubs = 1;
	signal (SIGUSR1, ischubs_mich);
}


void leostat(void)				 
{
				zende=time(NULL);	
				printf ("\n +-------><-------+ %d Mio / %d s\n",pic,zende-zstart);		
				pic=0;zstart=zende;
				for (a=0;a<=15;a++)
					{
						binaus(para[a]);
					}
				printf (" +-------><-------+\n");
				writeseed();
				readpid();
				kill (syncpid,SIGUSR1);
}



void rekursiv(unsigned int rec_d)
{	
	unsigned int r;		/* 16 Bit Zahl dieser Rekursionsstufe */

	rec_d=rec_d-1;		/* Kontrolle der Rekursionstiefe */
	
	for (r=para[rec_d];r<=65535;r++) 
	{
		para[rec_d]=r;
		
			if (rec_d!=0)
				{	rekursiv(rec_d);
				}
			else
				{
					if (ischubs)	/* (pic>=2000000000)  */
					{	leostat();
						ischubs=0;
					}
				}
	}
	
	para[rec_d]=0;		/* Überlauf -> Null */
}



int main (void)
{	
	for(a=0;a<=4095;a++) para[a]=0;
	d=4096;		/* 4096 statt 4095 weil in rekursiver Fnkt. Subtraktion VOR Wert->Speicher */
	pic=0;a=0;b=0;c=0;
	pic=0;pici=0;

	writepid();

	readseed();

	signal (SIGUSR1, ischubs_mich);

	zstart=time(NULL);
	rekursiv(d);

	return (0);
}