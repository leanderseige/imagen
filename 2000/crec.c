#include <stdlib.h>
#include <stdio.h>
#include <time.h>

unsigned int para[16];
unsigned int a,b,c,d;
unsigned long int pic;

time_t ztotal,zstart,zende;

void binaus(unsigned long int vari)
{	printf("|");
	for (b=0;b<=15;b++)
	{	if ( vari&0x01==1 ) { printf("#"); }
		else { printf(" "); }
		vari=vari>>1;
	}
	printf("|\n");
}

void recurse(unsigned int rec_d)
{	
	unsigned int r;

	rec_d=rec_d-1;
	
	for (r=0;r<=65535;r++) 
	{
		para[rec_d]=r;
		
		if (rec_d!=0)
		{	recurse(rec_d);
		}
		else
		{	pic++;
			if(pic>=1000000000) 
			{ 	zende=time(NULL);
				pic=0; c++;	
				printf ("\n\n ----------------   %3d Mrd  ...%ds / %ds total\n",c,zende-zstart,zende-ztotal); zstart=zende;
				for (a=0;a<=15;a++)
				{	binaus(para[15-a]);	}
				printf (" ---------------- \n");
			}
		}
	}

}

int main (void)
{	
	for(a=0;a<=15;a++) para[a]=0;
	
	d=16;pic=0;a=0;b=0;c=0;
	ztotal=zstart=time(NULL);
	recurse(d);
	return 0;
}