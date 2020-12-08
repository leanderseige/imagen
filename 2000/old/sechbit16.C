
#include <stdlib.h>
#include <stdio.h>
/*#include <time.h>*/

unsigned int para[32];

long int a,b,c,d;
long int bitcount,pixcount,over;
bool test;
time_t zstart,zende;

void binaus(unsigned long int vari)
{	
	for (b=0;b<=15;b++)
	{	if ( vari&0x01==1 ) { printf("O"); }
		else { printf(" "); }
		vari=vari>>1;
	}
	printf("\n");
}

void main (void)
{	
	printf("------------------------\n");
	/*zstart=time(NULL);*/

	for (para[0]=0;para[0]<65535;para[0]++)
	{for (para[1]=0;para[1]<65535;para[1]++)
	 {for (para[2]=0;para[2]<65535;para[2]++)
	  {for (para[3]=0;para[3]<65535;para[3]++)
	   {for (para[4]=0;para[4]<65535;para[4]++)
	    {for (para[5]=0;para[5]<65535;para[5]++)
	     {for (para[6]=0;para[6]<65535;para[6]++)
	      {for (para[7]=0;para[7]<65535;para[7]++)
	       {for (para[8]=0;para[8]<65535;para[8]++)
	        {for (para[9]=0;para[9]<65535;para[9]++)
	         {for (para[10]=0;para[10]<65535;para[10]++)
	          {for (para[11]=0;para[11]<65535;para[11]++)
	           {for (para[12]=0;para[12]<65535;para[12]++)
	            {for (para[13]=0;para[13]<65535;para[13]++)
	             {for (para[14]=0;para[14]<65535;para[14]++)
	              {for (para[15]=0;para[15]<65535;para[15]++){}
	              
 											/*	zende=time(NULL);
							       			if (zende-zstart==1)
							       				{*/	for (a=0;a<=15;a++)
							       					{	binaus(para[a]); /*printf("%d\n",para[a]);*/
							       					}
							       					/*zstart=zende;*/
													printf("----------------\n");
							       				//}							     

							       //} /*printf(".");	*/
			      }            
	             }
	            }
	           }
	          }
	         }
	        }
	       } 	      
	      }
	     }
	    }
	   }
	  }
	 }
    }	
}



