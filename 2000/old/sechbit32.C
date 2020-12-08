
#include <stdlib.h>
#include <stdio.h>
#include <time.h>

unsigned long int para[32];

long int a,b,c,d;
long int bitcount,pixcount,over;
bool test;
time_t zstart,zende;

void main (void)
{	
	printf("------------------------\n");
	zstart=time(NULL);

	for (para[0]=0;para[0]<4294967295;para[0]++)
	{for (para[1]=0;para[1]<4294967295;para[1]++)
	 {for (para[2]=0;para[2]<4294967295;para[2]++)
	  {for (para[3]=0;para[3]<4294967295;para[3]++)
	   {for (para[4]=0;para[4]<4294967295;para[4]++)
	    {for (para[5]=0;para[5]<4294967295;para[5]++)
	     {for (para[6]=0;para[6]<4294967295;para[6]++)
	      {for (para[7]=0;para[7]<4294967295;para[7]++)
	       {for (para[8]=0;para[8]<4294967295;para[8]++)
	        {for (para[9]=0;para[9]<4294967295;para[9]++)
	         {for (para[10]=0;para[10]<4294967295;para[10]++)
	          {for (para[11]=0;para[11]<4294967295;para[11]++)
	           {for (para[12]=0;para[12]<4294967295;para[12]++)
	            {for (para[13]=0;para[13]<4294967295;para[13]++)
	             {for (para[14]=0;para[14]<4294967295;para[14]++)
	              {for (para[15]=0;para[15]<4294967295;para[15]++)
	               {for (para[16]=0;para[16]<4294967295;para[16]++)
	                {for (para[17]=0;para[17]<4294967295;para[17]++)
	                 {for (para[18]=0;para[18]<4294967295;para[18]++)
	                  {for (para[19]=0;para[19]<4294967295;para[19]++)
	                   {for (para[20]=0;para[20]<4294967295;para[20]++)
	                   	{for (para[21]=0;para[21]<4294967295;para[21]++)
						 {for (para[22]=0;para[22]<4294967295;para[22]++)
						  {for (para[23]=0;para[23]<4294967295;para[23]++)
	  					   {for (para[24]=0;para[24]<4294967295;para[24]++)
							{for (para[25]=0;para[25]<4294967295;para[25]++)
						     {for (para[26]=0;para[26]<4294967295;para[26]++)
						      {for (para[27]=0;para[27]<4294967295;para[27]++)
	      					   {for (para[28]=0;para[28]<4294967295;para[28]++)
							    {for (para[29]=0;para[29]<4294967295;para[29]++)
							     {for (para[30]=0;para[30]<4294967295;para[30]++)
							      {for (para[31]=0;para[31]<4294967295;para[31]++) 
							       { 	zende=time(NULL);
							       			if (zende-zstart==60)
							       				{	/*for (a=0;a<=31;a++)*/
							       					{	printf("%d\n",para[31]);
							       					}
							       					zstart=zende;
							       				}							     

							       } /*printf(".");	*/
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
}

