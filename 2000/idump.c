#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include <ctype.h>

unsigned int para[4096];		/* Datenfeld			*/
unsigned long int a,b,c,d,shift,x,y,zx,zy;	/* div Hilfsvariablen 	*/
unsigned long int pic,pici,maxpix;		/* Bild Index Zaehler 	*/
unsigned int mode;
unsigned char CH;

FILE *dateiptr;					/* zum Filehandling		*/
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

void druckhex(void)
{

	a=(a/8)+1;
	for (b=0;b<a;b++)
	{
		printf("%8d : %4x %4x %4x %4x %4x %4x %4x %4x<br>\n",8*b,para[8*b],para[8*b+1],para[8*b+2],para[8*b+3],para[8*b+4],para[8*b+5],para[8*b+6],para[8*b+7]);  
	}
}

void druckint(void)
{

	a=(a/8)+1;
	for (b=0;b<a;b++)
	{
		printf("%8d : %5d %5d %5d %5d %5d %5d %5d %5d<br>\n",8*b,para[8*b],para[8*b+1],para[8*b+2],para[8*b+3],para[8*b+4],para[8*b+5],para[8*b+6],para[8*b+7]);  
	}
}

void druckchr(void)
{
	for (b=0;b<=a;b++)
	{
				CH=(para[b]>>8)&0x7F;
			if (isalnum(CH)==0){CH='.';}
			fputc (CH,stdout);
				CH=(para[b])&0x7F;
			if (isalnum(CH)==0){CH='.';}
			fputc (CH,stdout);

			if ((b%8)==7) printf("<br>\n");
	}
}

int main ( int argc, char *argv[] )
{	

	mode=atoi(argv[1]);

	readseed();

	for(a=4095;a>=0;a--)
	{	if (para[a]!=0)	{	break;	}
	}
			printf("<html>\n");	
			printf("   <head>\n");
			printf("      <title>Imagen</title>\n");
			printf("      <body bgcolor=#333366 text=#FFFFFF>\n");
			printf("   </head>\n");
			printf("   <body>\n");			
			printf("   <center>\n");
			printf("Actual Data Set:<br><br><pre>\n");

	if (mode==11)	druckhex();
	if (mode==12)	druckint();
	if (mode==13)	druckchr();	

	if (a<4095)		printf("</pre>Only Zeros Following...<br>\n");

			printf("   </center>\n");
			printf("   </body>\n");
			printf("</html>\n");
	
	return (0);
}