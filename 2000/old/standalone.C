#include <GL/glut.h>
#include <stdlib.h>
#include <stdio.h>
#include <math.h>


#define pixelanz 65536
#define imagey 256
#define imagex 256

static GLubyte image[pixelanz];

static GLuint texName;

long int a,b,c,d;
signed long int bitcount,pixcount,over,controlmsb,last_controlmsb;

void imageproz(void)
{	
	over=1;

	controlmsb=0;

	for (bitcount=7;bitcount>=0;bitcount--)
	{	

		for (pixcount=0;pixcount<=65535;pixcount++)
		{

			if (over==1)
				{	if (((image[pixcount]>>bitcount)&0x01)==0x01)
					{	image[pixcount]=image[pixcount] & ( !(0x01<<bitcount) );
						over=1; controlmsb=pixcount*(8-bitcount);
					}
					else 
					{	image[pixcount]=image[pixcount] | (  (0x01<<bitcount) );
						over=0;
					}
				} 
	
			if (controlmsb>=last_controlmsb) break;
		}	

		if (controlmsb>=last_controlmsb) break;

	}

	last_controlmsb=controlmsb;

	b++;
	if (b==1000)
	{	c++;
		printf("%d Tausend\n",c);
		b=0;
	}
}


int main(int argc, char** argv)
{	a=0;b=0;c=0;d=0;
	for (a=0;a<1;a)
	{	imageproz();
	}
	return 0;
}