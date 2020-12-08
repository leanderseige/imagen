#include <GL/glut.h>
#include <stdlib.h>
#include <stdio.h>
#include <math.h>


#define pixelanz 65536
#define imagey 256
#define imagex 256

static GLubyte image[pixelanz];

static GLuint texName;

unsigned long int para[32];

long int a,b,c,d;
long int bitcount,pixcount,over;
bool test;

void imageproz(void)
{	
	for (para[0];para[0]<65536;para[0]++)
	{for (para[1];para[1]<65536;para[1]++)
	 {for (para[2];para[2]<65536;para[2]++)
	  {for (para[3];para[3]<65536;para[3]++)
	   {for (para[4];para[4]<65536;para[4]++)
	    {for (para[5];para[5]<65536;para[5]++)
	     {for (para[6];para[6]<65536;para[6]++)
	      {for (para[7];para[7]<65536;para[7]++)
	       {for (para[8];para[8]<65536;para[8]++)
	        {for (para[9];para[9]<65536;para[9]++)
	         {for (para[10];para[10]<65536;para[10]++)
	          {for (para[11];para[11]<65536;para[11]++)
	           {for (para[12];para[12]<65536;para[12]++)
	            {for (para[13];para[13]<65536;para[13]++)
	             {for (para[14];para[14]<65536;para[14]++)
	              {for (para[15];para[15]<65536;para[15]++)
	               {for (para[16];para[16]<65536;para[16]++)
	                {for (para[17];para[17]<65536;para[17]++)
	                 {for (para[18];para[18]<65536;para[18]++)
	                  {for (para[19];para[19]<65536;para[19]++)
	                   {for (para[20];para[20]<65536;para[20]++)
	                   	{for (para[21];para[21]<65536;para[21]++)
						 {for (para[22];para[22]<65536;para[22]++)
						  {for (para[23];para[23]<65536;para[23]++)
	  					   {for (para[24];para[24]<65536;para[24]++)
							{for (para[25];para[25]<65536;para[25]++)
						     {for (para[26];para[26]<65536;para[26]++)
						      {for (para[27];para[27]<65536;para[27]++)
	      					   {for (para[28];para[28]<65536;para[28]++)
							    {for (para[29];para[29]<65536;para[29]++)
							     {for (para[30];para[30]<65536;para[30]++)
							      {for (para[31];para[31]<65536;para[31]++) 
							       { 								     
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

	 	for (a=0;a<=31;a++)
	{printf("%d %d\n",para[a],a);}
	
    }
	

}

void idle(void)
{	
	for (a=0;a<=31;a++)
	{ para[a]=0; }
	
	imageproz();


}


void init(void)
{	
	printf("Size: %d\n",sizeof(unsigned long int));
 

	
	a=0;b=0;c=0;d=0;over=0;

	glClearColor (0.0, 0.0, 0.0, 0.0);
	glShadeModel (GL_FLAT);

}

void display(void)
{		
	glClear (GL_COLOR_BUFFER_BIT);

	glColor3f (1.0,1.0,1.0);
	glEnable(GL_POLYGON_STIPPLE);
/*	glPolygonStipple( &para[0] );*/
	glRectf(2.0,2.0,-2.0,-2.0);
	
	glutSwapBuffers();
/* 	glFlush();*/

}

void reshape (int w, int h)
{	glViewport (0,0, (GLsizei)w, (GLsizei)h);
	glMatrixMode (GL_PROJECTION);
	glLoadIdentity();
	gluPerspective (60.0, (GLfloat) w/(GLfloat) h, 1.0, 30.0);
	glMatrixMode (GL_MODELVIEW);
	glLoadIdentity();
	glTranslatef (0.0, 0.0, -4.0);
}

void keyboard (unsigned char key, int x, int y)
{	switch (key) 	{
		case 27:
			exit(0);
			break;
		case ' ':
			glutPostRedisplay();
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