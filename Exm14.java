import java.awt.*;                 
import java.applet.*;              
import java.awt.event.*;

public class Exm14 extends Applet implements ActionListener, AdjustmentListener  
	{
    Button button1 = new Button(); // Plot Button
    Button button2 = new Button(); // Init Button
    Image buffer;   // image buffer
    Graphics MyGC;  // graphics for the buffer
    //Label langles = new Label("Number of Angles");
    Label lporcentaje = new Label ("");
  
  
    Label l =  new Label ("Number of Angles");
    Label l2 = new Label ("Quantum Channels");
    Label l3 = new Label ("Channel");
    Label l4 = new Label ("Divisions of Initial Y");
    
    Label ld  = new Label ("");
    Label l2d = new Label ("");
    Label l3d = new Label ("");
    Label l4d = new Label ("");
    
    
    
    
    Scrollbar ScrollNumAngulos;
    
    Scrollbar ScrollTotalChannels;
    
    Scrollbar ScrollChannel;
    
    Scrollbar ScrollDivisionsY;
    
    
    

    int vxmin,vymin,vxmax,vymax;  //screen coordinates
    double xmin,ymin,xmax,ymax;   //imaginary coordinates
    double dx;                    //interval of x
    //double angle;                 //angle
     double d_angle;               //interval of angle
     double cx,cy,cr;              //center and radius of circle
     double D;
     double A;
     double L;
     double M;
	 final static int MAX = 77;
     final static double TOL    = 0.000000000000001;
     final static double  delta = 0.000000000000001; 
     final static double  lista[] = new double [2];
     double numangulos=50;//nch
     double totalquantumchannels=5.0;
     double channel=1.0; // chn
     double ydivisions=20.0;
	 	
    
    
    
    
    /* initializing */
    public void init()
    
    {
        String str;
        
        xmin=0.0;
        xmax=5.55;
        ymin=0.0;
        ymax=Math.PI;
        
        vxmin=0;
        vxmax=700;
        vymin=0;
        vymax=500;
        
        D = 1.0;
        A = 0.305;
        L = 5.55;
        M = 0.0;   
        
       	ScrollNumAngulos = new Scrollbar( Scrollbar.HORIZONTAL,50,100,0,1150);
     	ScrollNumAngulos.setBlockIncrement(100);    
       	
       	ScrollTotalChannels = new Scrollbar( Scrollbar.HORIZONTAL,5,5,0,35 );
       	ScrollTotalChannels.setBlockIncrement(5);    
       	
       	
       	ScrollChannel = new Scrollbar( Scrollbar.HORIZONTAL,1,1,0,6 );
       	ScrollChannel.setBlockIncrement(1);    
       	
       	
       	ScrollDivisionsY = new Scrollbar( Scrollbar.HORIZONTAL,20,20,0,520 );
       	ScrollDivisionsY.setBlockIncrement(20);
	
             
	    setLayout(null);  //free layout
	    setSize(855,500); //size of this applet
		
		
		
	
		
		
		
    				   l.setBounds(710,10,120,20);
					   add(l);
			
			add(ScrollNumAngulos);
    	
		ScrollNumAngulos.setBounds(720,40,70,20 );
    	ScrollNumAngulos.addAdjustmentListener(this);
    
    				   ld.setText(""+(int)numangulos);
    				   ld.setBounds(805,40,45,20);
    				   add(ld);
    	
    
    	
    				  l2.setBounds(710,70,130,20);
					   add(l2);
		
		ScrollTotalChannels.setBounds(720,100,70,20 );
    	ScrollTotalChannels.addAdjustmentListener(this);
    	add(ScrollTotalChannels);
    	
    				   l2d.setText(""+(int)totalquantumchannels);
    				   l2d.setBounds(805,100,45,20);
    				   add(l2d);
    	


					   l3.setBounds(710,130,120,20);
					   add(l3);
		
		ScrollChannel.setBounds(720,160,70,20 );
    	ScrollChannel.addAdjustmentListener(this);
    	add(ScrollChannel);
    	
    			
    				   l3d.setText(""+(int)channel);
    				   l3d.setBounds(805,160,45,20);
    				   add(l3d);
    	
 //--
 
 						l4.setBounds(710,190,120,20);
					   add(l4);
		
		ScrollDivisionsY.setBounds(720,220,70,20 );
    	ScrollDivisionsY.addAdjustmentListener(this);
    	add(ScrollDivisionsY);
    	
    				   l4d.setText(""+(int)ydivisions);
    				   l4d.setBounds(805,220,45,20);
    				   add(l4d);
    	
 
 
//--

		
		add(button1);
		button1.setLabel("Plot");
		button1.setFont(new Font("Dialog", Font.PLAIN, 16));
		button1.setBounds(720,300,100,20);
		button1.addActionListener(this);
	    
		
		
		add(lporcentaje);
		lporcentaje.setBounds(710,280,60,20);
		
		
		
        
	// create off-screen image
		
		buffer = createImage(vxmax-vxmin,vymax-vymin);
	
	// get graphics
		MyGC = buffer.getGraphics();
      
    	setLayout(null);
		
		setSize(855,500);
    }
    
    public void paint(Graphics g)
    
    {
        drawGraph(); //draw graph to the buffer.
        g.drawImage(buffer,0,0,this); //copy buffer to the screen 
    }
    
    
    Punto secante(Particula Q, double p0, double p1)
    {
    	int i;
    	double  q0,q1;
    	double  px;
    	double  py;
    	double  cotaInferior;
    	double  cotaSuperior;
    	double  Epsilon;
    	Punto   punto;
        
        // asegurar el intervalo 
    	
    	cotaInferior = p0;
    	
    	cotaSuperior = p1; 
    	
    	
    	
    	// evalua los puntos iniciales
    	// la resta de las funciones es igual a cero 
    	// en el punto en donde convergen
		    	
    	
    	q0 = funcionF(p0) - funcionG(Q,p0);
    	
    	
    	q1 = funcionF(p1) - funcionG(Q,p1);
    	
    	
    	
    	i = 1;
    	
    	
    	while (i <= MAX)
    	{
    		
    	px= p1 - q1 *((p1-p0)/(q1-q0));
    		
    	Epsilon = px-p1;
    		
    	if ((Math.abs(Epsilon) < TOL)&&(px>=cotaInferior)&&(px<=cotaSuperior))
    		{
    	
    	    py = funcionF(px); 
    	    
    	    punto = new Punto(px,py,true);
    		
    		// las funciones se intersectan en el Intervalo, regresa el punto x y y 

    		return (punto);
    		
    		}
    		
    	//actualiza los puntos	
    	i++;
    	p0= p1;
    	q0= q1;
    	p1= px;
    	q1= funcionF(px) - funcionG(Q,px);
    	}
    	
    	// acabo las iteraciones y nunca convergieron las funciones en en intervalo I
    	
    	Punto nop = new Punto(false);
    	
    	return (nop);
    	

    }//secante



	double funcionF(double x)
	{
	 double y;	
	 
	 y = D + A * ( 1 -  Math.cos( (2*Math.PI*x)/L  ) );	
	 
	
	 return y;			
	}
	
	
	double funcionG(Particula Q, double x)
	{
	double y;

	// eq, de la recta que se genera a partir de la Particula
	// o sea, se genera con un punto y un angulo.
		
	y = Math.tan(Q.angulo)*x - Math.tan(Q.angulo)*Q.x + Q.y;	
	
	
	return y;	
	}


	double derivadafuncionF(double x)
	{
		double yp;
			
	 yp = A*(2*Math.PI/L)*Math.sin(2*Math.PI*x/L);
	 
	 
	return yp;	
		
		
	}
	
	int dminimo(double a, double b, double c)
	{
	   double val1;	
	   double val2;
		
		
	   val1  =  Math.abs(c-a);
	   
	   val2  =  Math.abs(c-b);
	   
	   
	   if (val1 < val2)
	   
	   //si la distancia entre q.x y lista[0].x es mas pequena, es
	   //por que es la misma o casi 0, en ese caso, manda al otro extremo 
	   
	   return 2;
	   
	   
	   else 
	   
	   return 1;
		
	}
	
	double especularidad(double x, double angulo)
	{
		
		
		double fi;
		double beta;
		//aplicar a x la derivada 
		
		fi   = derivadafuncionF(x);
		
		fi   = Math.atan(fi);
		
		beta = 2*fi-angulo;
		
		return (beta);
		
		
	}
	
	



int metodo(Particula Q, Punto lista[]) 
    
    {	
    	boolean b,t,t1;
    	int cont = 0;
    	double Delta = 0.0000000000001;
    	int Divisions=20;
    	Punto punto  = new Punto();
    	Punto punto2 = new Punto();
    	
    	b=search(Q,M,L,punto,Divisions);
    	
    
    
    	if (b==true)
    	
    	{
    		lista[0]=new Punto(punto.x,punto.y);
    	
    	   	t =  search (Q,punto.x+Delta,L,punto2,Divisions);
    	   	
     	   	
     	   	cont++;
    	   	
    	   	
    	   	if  (t == true)
    	   	{
			    				    	   	
    	   		lista[1]=new Punto(punto2.x, punto2.y);
    			cont++;
    				
    		
    		}
    		
    	}

    	return cont;
    	
    }

 
   
   
  
 



	boolean search (Particula Q,double d0,double d1, Punto PP, int Divisions)
	{
		
		Punto P = new Punto();
		//int    Divisions=10;
		double k1;
		double distP;
		double delta=0.00000000000001;
	
	
		
		distP = (Math.abs(d1-d0))/Divisions;
		
		
		for (int k = 0; k < Divisions; k++)
    	
    	{
  			
  			k1=d0+distP;
  			
  			
  			P = secante(Q ,d0 ,k1);
    						
    		if ( (P.existe == true) )
    		{
    			
   			    //System.out.println("Px ="+ P.x);
    			//System.out.println("Py ="+ P.y);
    			PP.x=P.x;
    			PP.y=P.y;
				    			
    			return true;
    		}
    		
    	
    	
    		d0= d0+distP;
    	
		}
		   //System.out.println("doesn't exist in this interval");
		   return false;
	}

	void escribe(double x)
	{
		System.out.println("X="+x);
		
	}

    
    
    
    
    public void drawGraph() 
    
     {             
		double x1,y1,x2,y2;           
	    Billar    B;
       	Punto     P;
    	Punto     PA= new Punto();
    	Punto     PB= new Punto();
    	Punto     PC= new Punto();
    	Particula Q = new Particula();
      	boolean   t,t1,t2;
    	double    angulo1;
    	double    angulo2;
    	int cont;
    	double x;
    	double w;
        double p0,p1;
        Punto lista[]= new Punto[2];
       	Punto Punkte  = new Punto();
    	Punto Punkte1  = new Punto();
    	Punto Punkte2  = new Punto();
    	Particula Q1 = new Particula();
		boolean verdad;
		int num;
		double valorminimo;
		int actualizacion;
		int extremo;
		boolean wahr;
		int total;
		double delta1=  0.00000000000001;
	    boolean nobreak;
		double m=0.0;
		double stepy;
        int dy=1;
        double alphai=0.0;
        double alphaf = Math.PI/2.0;
        double alpha = (alphaf-alphai)/numangulos;
        double ALPHA;
        double angulo;
	    double alfa;
	    double X;
	    double tchne;
	    
	   	
	 	stepy= D/ydivisions;	
	 		
	 		  	
	   	MyGC.setColor(new Color(255, 255, 250)); 
        MyGC.fillRect(vxmin,vymin,vxmax-vxmin,vymax-vymin);
	    MyGC.setColor(Color.black);
        Line(MyGC,0,ymin,0,ymax);
        Line(MyGC,xmin,0,xmax,0);
	    MyGC.setColor(Color.blue);   
        
	    
	  
	  

for (double y =0.0; y <=1.0+stepy; y=y+stepy )
	
{	
		
	lporcentaje.setText(""+(int)(y*100) +" % ");
		    	
    
	for (int k = 1 ; k < (int)numangulos ; k ++)  
	{    
    	
    	
    	ALPHA = alphai + ((float)k-0.5) * alpha;
    	
		tchne = totalquantumchannels*Math.sin(ALPHA);
    
 		if ((tchne < (channel-1.0))||(tchne > channel))
    
 		continue;
    
    
		angulo = ALPHA;
	

		for (int ct = 0; ct < 2; ct ++)
	    {	
	    	Q.y=y;
	    	Q.x=0.0;	
	    	Q.angulo=angulo;	    
	    	
	    
	    	
			verdad=true;	    
			actualizacion = 0;
	    
	    	while (verdad == true)
	   		{
	   			
	   			
	   			
					if ((Q.y==0.0))
		    	{
	    		
		    		X = ((1.0)- Q.y)/Math.tan(Q.angulo)+Q.x;	
	    	
	    	
	    	
	    			if ((X<M)||(X>L))
					{	
						break;	
					}   		
	    		}
	   			
	   			
    			num=metodo(Q,lista);
   				
   				
   				switch  (num)
	   	   		{
	   
	   				case 1:	
	   				{
	   	
	   		  			if ((Math.abs(lista[0].x-Q.x)<delta1))
	   		  	
	   		  			{	    
	   	  		
	   	  		   			x= ((0.0)- Q.y)/Math.tan(Q.angulo)+Q.x;
				
							if (x<L&&x>M)
						
							{
				  				Q.x=x;
				  				Q.y=0.0;
				  				Q.angulo=-Q.angulo;	
						
								Line(MyGC,Q.x,Q.angulo,Q.x,Q.angulo);
								
								
						
							}
				
	   	  					else
	   	  					{
	   	  						verdad=false;	
	   	  					}
	   	  				}
	   	  				else
	   	  				{
	   	  					Q.x=lista[0].x;
		  					Q.y=lista[0].y; 
		   					Q.angulo=especularidad(lista[0].x,Q.angulo);
		 				} 
			   		}		  
					break;
	 	
	 				case 0:
	 				{		
	 					if (actualizacion==0)
	 				
	 					{
	 					
	 						x= ((0.0)- Q.y)/Math.tan(Q.angulo)+Q.x;
				
							if (x<L&&x>M)
							{
								Q.x=x;
				  				Q.y=0.0;
				  				Q.angulo=-Q.angulo;	
				  			
								Line(MyGC,Q.x,Q.angulo,Q.x,Q.angulo);
				  			}
				  			else
				  			{
				  				verdad=false;	
				  			}
					
						}
	 					
	 					else	
	 					
	 					{
	 	
							verdad=false;
	   				
	   					}
	   				
	   				
	   				}
	   				break;
	   	
	   				case 2:
	   	
	   				{
	   					extremo =dminimo(lista[0].x,lista[1].x,Q.x);
		
						if (extremo==2)
						{
							Q.x=lista[1].x;
							Q.y=lista[1].y;
							Q.angulo=especularidad(lista[1].x,Q.angulo);
		  				}
		  				else 
						{
							Q.x=lista[0].x;
							Q.y=lista[0].y;
							Q.angulo=especularidad(lista[0].x,Q.angulo);
		  				}
		  	
		 			}
		 
					break;
			
				}//switch
	   	
			
	   
				actualizacion++;	   
	   
	   
			}//while  	
	  
	  		//cambiar el signo pal siguiente ciclo
	  
	  	angulo= -angulo;
      
    	}//for para negative and positive
      
	}//for para y = 0 a y = 1        


  }
		
		
		
		
				
		
		        
          

        
        
 
   }


    public int mapX(double x)
    {
	int sx;
	sx= vxmin+(int)((x-xmin)/(xmax-xmin)*(double)(vxmax-vxmin)) ;
	return sx;
    }
    public int mapY(double y)
    {
	int sy;
	sy= vymin+(int)((ymax-y)/(ymax-ymin)*(double)(vymax-vymin));
	return sy;
    }
    
    
    public void Line(Graphics g,double x1,double y1,double x2,double y2)
    
    {
	g.drawLine(mapX(x1),mapY(y1),mapX(x2),mapY(y2));
    }
     
    
    
    
    
    
    
    
    
    
    public void Circle(Graphics g,double x,double y,double r)
    {
	g.drawOval(mapX(x-r),mapY(y+r),mapX(2*r)-mapX(0),mapY(-2*r)-mapY(0));
    }
    
    
    public void scale(Graphics g)
    
    {
	double x,y;
	
	g.setColor(new Color(255, 255, 192)); 
	g.fillRect(vxmin,vymin,vxmax-vxmin,vymax-vymin);
	    
	g.setColor(Color.lightGray);
	for (x=Math.PI/2;x<=Math.PI*2;x=x+Math.PI/2){
		Line(g,x,-0.05,x,0.05);
	}
	for (y=-1;y<=1;y=y+1){
		Line(g,-0.05,y,0.05,y);
	}
	    
	
	g.setColor(Color.black);
	Line(g,0,ymin,0,ymax);
	Line(g,xmin,0,xmax,0);     
    }
    
    public void actionPerformed(ActionEvent event)
	{
		String str;
		Object object = event.getSource();
		Graphics g=getGraphics();
		
		
		if (object == button1)
		{
		    button1.setEnabled(false);		    
			paint(g);
			button1.setEnabled(true);		    
		
		}
		if (object == button2)
		{
		
		repaint();
		}
  	}
  	
  	
  	public void adjustmentValueChanged( AdjustmentEvent a)
    {
    	Object obj = a.getSource();
    
    	if (obj==ScrollNumAngulos)
    	{
    	ld.setText(" "+a.getValue());
    	numangulos = (double)a.getValue();
			
		}
    	
    	if (obj==ScrollTotalChannels)
    	{
    	l2d.setText(" "+(int)a.getValue());
    	totalquantumchannels = (double)a.getValue();
    	ScrollChannel.setMaximum((int)totalquantumchannels+1);
		}
    	
    	if (obj==ScrollChannel)
    	{
    	l3d.setText(" "+(int)a.getValue());
    	channel = (double)a.getValue();
    	
		}
    	
    	if (obj==ScrollDivisionsY)
    	{
    	l4d.setText(" "+(int)a.getValue());
    	ydivisions = (double)a.getValue();
    	
		}
    	
    	
    	
    	
    	
    }
    
  	
	
}




















class Punto
{
boolean existe;	
double x;
double y;

	Punto (double a, double b, boolean e)
	{
	x = a;
	y = b;
	existe = e;	
	}
	
	Punto (double a, double b)
	{
	x = a;
	y = b;
	existe = false;	
	}
	
	
	Punto (double a, boolean e)
	{
		
	x=a;
	existe = e;
	 	
	}
	
	Punto (boolean valor)
	{
		existe = valor;
	}
	
	Punto ()
	{
		
	}

}

class Particula
{
	
double x;
double y;
double angulo;	
boolean salio;
	
	Particula (Punto p, double ang)
	{
		
		x     = p.x;
		y     = p.y;
		angulo= ang;
		salio=false;
		
		
	}
	
	Particula ()
	{
		
	}
	
	
	
}


