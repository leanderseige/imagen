#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include <unistd.h>
#include <sys/types.h>
#include <signal.h>

pid_t syncpid;					/* eigene PID			*/ 
pid_t imagenpid;				/* Imagen PID			*/
 
FILE *dateiptr;					/* zum Filehandling		*/
char *imagename="./data/impid";
char *syncname ="./data/syncpid";

volatile sig_atomic_t sschubs;


void sschubs_mich (int x)
{	fprintf (stderr," ... exit.\n");

	exit(0);
}


void writepid()
{
	syncpid=getpid();
	
	dateiptr=fopen(syncname,"w");
	
	if(dateiptr==NULL)
		{	fprintf(stderr,"\nKonnte PID nicht speichern.\n");
		}
	else
		{	
			fprintf(dateiptr," %d \n",syncpid);
			fclose(dateiptr); 
		}	


}


void readpid()
{	
	dateiptr=fopen(imagename,"r");
	
	if(dateiptr==NULL)
		{	fprintf(stderr,"\nKonnte PID nicht oeffnen.\n");
		}
	else
		{	
			fscanf(dateiptr," %d \n",&imagenpid);
			fclose(dateiptr); 
		}	
}


int main (void)
{
	signal (SIGUSR1, sschubs_mich);

	writepid();
	readpid();

	kill(imagenpid, SIGUSR1);

	fprintf (stderr,"PID:%d,Imagen PID: %d   ",syncpid,imagenpid);

	pause();
	
	return (0);
}