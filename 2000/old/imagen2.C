#include <GL/glut.h>
#include <stdlib.h>
#include <stdio.h>
#include <math.h>


#define pixelanz 65536
#define imagey 256
#define imagex 256

static GLubyte image[65536];

static GLuint texName;

long int a,b,c,d;
int bitcount,pixcount,over;

void imageproz(void)
{	printf("proz %d\n",c++);

/*	b=b+1;
	if (b==8) b=0;

	for (a=0;a<=65535;a++)
	{	image[a]=0x00;
	
	}*/

	over=1;
	pixcount=0;

/*	for (bitcount=7;bitcount<=7;bitcount++)
	{*/	
		for (pixcount=1;pixcount<=65535;pixcount++);
		{
			/*if (over==1)
				{	if (((image[pixcount]>>bitcount)&0x01)==0x01)
					{	image[pixcount]=image[pixcount] & ( !(0x01<<bitcount) );
						over=1;
					}
					else //if (((image[pixcount]>>bitcount)&0x01)==0x00)
					{	image[pixcount]=image[pixcount] | (  (0x01<<bitcount) );
						over=0;
					}
				} */

				image[pixcount]=0xFF;
					printf ("i[pc]:%d pc:%d\n",image[pixcount],pixcount);
		}	
/*	}*/
}

void idle(void)
{	
	for (a=0;a<10000;a++)
	{	printf(".");
		printf("\b");	}
		
		printf("\n");


	glutPostRedisplay();
}

void init(void)
{	a=0;b=0;c=0;d=0;over=0;

	glClearColor (0.0, 0.0, 0.0, 0.0);
	glShadeModel (GL_FLAT);

	glPixelStorei (GL_UNPACK_ALIGNMENT, 1);

	glGenTextures (1, &texName);
	glBindTexture (GL_TEXTURE_2D, texName);

	glTexParameteri (GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
	glTexParameteri (GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
	glTexParameteri (GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
	glTexParameteri (GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

	glTexImage2D (GL_TEXTURE_2D, 0, GL_LUMINANCE, imagex, imagey, 0, GL_LUMINANCE, GL_UNSIGNED_BYTE, image);
}

void display(void)
{		
	glClear (GL_COLOR_BUFFER_BIT);

	imageproz();

	glTexImage2D (GL_TEXTURE_2D, 0, GL_LUMINANCE, imagex, imagey, 0, GL_LUMINANCE, GL_UNSIGNED_BYTE, image);

	glEnable (GL_TEXTURE_2D);
	glTexEnvf (GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_REPLACE);

	glBindTexture (GL_TEXTURE_2D, texName);

	glBegin (GL_QUADS);
		glTexCoord2f(0.0, 0.0); glVertex2f (-2.0, -2.0);	
		glTexCoord2f(0.0, 1.0); glVertex2f (-2.0,  2.0);	
		glTexCoord2f(1.0, 1.0); glVertex2f ( 2.0,  2.0);	
		glTexCoord2f(1.0, 0.0); glVertex2f ( 2.0, -2.0);	
	glEnd();
	
	glutSwapBuffers();

}

void reshape (int w, int h)
{	glViewport (0,0, (GLsizei)w, (GLsizei)h);
	glMatrixMode (GL_PROJECTION);
	glLoadIdentity();
	gluPerspective (60.0, (GLfloat) w/(GLfloat) h, 1.0, 30.0);
	glMatrixMode (GL_MODELVIEW);
	glLoadIdentity();
	glTranslatef (0.0, 0.0, -5.0);
}

void keyboard (unsigned char key, int x, int y)
{	switch (key) 	{
		case 27:
			exit(0);
			break;
		default:
			break;
					}
}

int main(int argc, char** argv)
{	glutInit (&argc, argv);
	glutInitDisplayMode (GLUT_DOUBLE | GLUT_RGB);
	glutInitWindowSize (1000, 750);
	glutInitWindowPosition (100,100);
	glutCreateWindow (argv[0]);
	init();
	glutDisplayFunc (display);
	glutReshapeFunc (reshape);
	glutKeyboardFunc (keyboard);
	glutIdleFunc (idle);
	glutMainLoop();
	return 0;
}