
#include <stdlib.h>
#include <stdio.h>
#include <time.h>

unsigned int para[32];

long int a,b,c,d;
long int bitcount,pixcount,over;
bool test;
time_t zstart,zende;

void binaus(unsigned long int vari)
{	
	for (b=0;b<=7;b++)
	{	if ( vari&0x01==1 ) { printf("."); }
		else { printf(" "); }
		vari=vari>>1;
	}
	printf("\n");
}

void main (void)
{	
	printf("------------------------\n");
	zstart=time(NULL);

	for (para[0]=0;para[0]<=255;para[0]++)
	{for (para[1]=0;para[1]<=255;para[1]++)
	 {for (para[2]=0;para[2]<=255;para[2]++)
	  {for (para[3]=0;para[3]<=255;para[3]++)
	   {for (para[4]=0;para[4]<=255;para[4]++)
	    {for (para[5]=0;para[5]<=255;para[5]++)
	     {for (para[6]=0;para[6]<=255;para[6]++)
	      {for (para[7]=0;para[7]<=255;para[7]++)

			  {

		zende=time(NULL);
		if (zende-zstart==10){
		for (a=0;a<=7;a++)
					      					{	binaus(para[a]); /*printf("%d\n",para[a]);*/
					       					}

					zstart=zende;
					printf("--------\n");}
		      }

		   }

 	      }
 	      
	     }
	    }
	   }
	  }
	 }
 }



