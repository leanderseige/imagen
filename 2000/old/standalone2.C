#include <GL/glut.h>
#include <stdlib.h>
#include <stdio.h>
#include <time.h>


#define pixelanz 65536
#define imagey 256
#define imagex 256

static GLubyte image[pixelanz];

static GLuint texName;

long int a,b,c,d;
long int pixcount,maxpix;
signed short int bitcount,over;
time_t tstart,tende;

int main(int)
{	
	b=0;c=0;maxpix=0;

	tstart=time(NULL);	

	do 					{

	over=1;

	for (bitcount=7;bitcount>=0;bitcount--)
	{	

		for (pixcount=0;pixcount<=65535;pixcount++)
		{

			if (over==1)
				{	if (((image[pixcount]>>bitcount)&0x01)==0x01)
					{	image[pixcount]=image[pixcount] & ( !(0x01<<bitcount) );

					}
					else 
					{	image[pixcount]=image[pixcount] | (  (0x01<<bitcount) );
						over=0;break;
					}
				} 
	
		}	

		if (over==0) break;

	}

	if (maxpix<pixcount) maxpix=pixcount;
	
	b++;
	if (b==10000000)
	{	c++;
		printf("%d0 Mio. / %d\n",c,maxpix);
		b=0;
	}

						} while (c<100);

	tende=time(NULL);
	printf("%d Sekunden\n",tende-tstart);

	return 0;

}