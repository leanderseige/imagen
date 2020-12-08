#include <stdio.h>
#include <stdlib.h>

unsigned long int cl,x,a,b,c,t;

unsigned char instring[1000000];
unsigned char *rhenv;
FILE *dateiptr;					
char *jpgname="../temp.jpg";
char *hstname="../temp.hst";


void httpfehler()
{
		printf("Content-Type: text/html\n\n");
		printf("<html>\n");	
		printf("   <head>\n");
		printf("      <title>Imagen</title>\n");
		printf("      <body bgcolor=#333366 text=#FF8080>\n");
		printf("   </head>\n");
		printf("   <body>\n");			
		printf("   <center><p>\n");
		printf("Sorry, Please Don't Submit Pictures Larger Than 100.000 Bytes.<br>\n");
		printf("Press The BACK Button Of Your Browser Or Close This Window.<br>\n");
		printf("   </p></center>\n");
		printf("   </body>\n");
		printf("</html>\n");		
}

void httpfehlerB()
{
		printf("Content-Type: text/html\n\n");
		printf("<html>\n");	
		printf("   <head>\n");
		printf("      <title>Imagen</title>\n");
		printf("      <body bgcolor=#333366 text=#FF8080>\n");
		printf("   </head>\n");
		printf("   <body>\n");			
		printf("   <center><p>\n");
		printf("Sorry, Please Submit JPEG (.jpg/.jpe/.jpeg) Pictures Only.<br>\n");
		printf("Press The BACK Button Of Your Browser Or Close This Window.<br>\n");
		printf("   </p></center>\n");
		printf("   </body>\n");
		printf("</html>\n");		
}

int main ( int argc, char *argv[] )
{
	cl=atoi(getenv("CONTENT_LENGTH"));t=0;

	if (cl<=100000)
	{
		if ( !fread(instring,1,cl,stdin)) { fprintf(stderr,"String ERROR!\n"); exit;	}

		dateiptr=fopen(jpgname,"w");

		if(dateiptr==NULL)
		{	fprintf(stderr,"\nKonnte JPG nicht speichern.\n");
		}
		else
		{	
			for (x=0;x<cl;x++)
			{
				if (instring[x]==0xFF)
				{	
					if ((instring[x+1]==0xD8)&&(instring[x+2]==0xFF)&&(instring[x+3]==0xE0))
					{
						fwrite(&instring[x],1,cl-x,dateiptr);
						x=cl;t=1;
					}	
				}
			}
		}

		 

		if (t==0)	{ httpfehlerB();	}
		else
		{
			dateiptr=fopen(hstname,"w");
/*			rhenv=getenv("REMOTE_ADDR"); 	*/
			fprintf(dateiptr,getenv("REMOTE_ADDR")); 		/* getenv("REMOTE_HOST") */
			fclose(dateiptr);
						
			printf("Content-Type: text/html\n\n");
			printf("<html>\n");	
			printf("   <head>\n");
			printf("      <title>Imagen</title>\n");
			printf("      <body bgcolor=#333366 text=#FFFFFF>\n");
			printf("   </head>\n");
			printf("   <body>\n");			
			printf("   <center>\n");
			printf("   <applet code=\"ImagenCalculator\" codebase=\"../\" width=700 height=400>\n");
			printf("   </applet>\n");
			printf("   </center>\n");
			printf("   </body>\n");
			printf("</html>\n");		
		}
	}
	else
	{
		httpfehler();
	}
}