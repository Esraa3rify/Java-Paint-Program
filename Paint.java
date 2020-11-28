//threads example: print time and date as a digital clock
//draws colorful lines but I have to press the color first: else>> exception
import java.awt.Color;
import java.applet.Applet;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.*;
import java.awt.Stroke; 
import java.awt.BasicStroke;
public class Paint extends Applet{
	int x1;
	int y1;
	int x2;
	int y2; 
	int x;
	int y;
	int width=0;
	int height=0;
	int tempX1;
	int tempY1;
	int eraserWidth=10;
	int eraserHeight=10;
	boolean keepX2=false;
	boolean keepY2=false;
	int rad;
	Vector <GeoShape> shapesVector = new Vector <GeoShape>();
	int j=0;
	boolean clearPressed = false;
	boolean dottedPressed = false;
	boolean filledPressed = false;
	boolean undoPressed = false;
	//POSSIBLE MODIFICATIONS: ARRAY OF BOOLEANS
	Button btnLine;
	Button btnRec;
	Button btnCircle;
	Button btnRed;
	Button btnGreen;
	Button btnBlue;
	Button btnSketch;
	Button btnEraser;
	Button btnClear;
	Button btnDotted;
	Button btnFill;
	Button btnUndo;
	Color currentColor = Color.black; //default color
	char currentShape;
	int counter;
	Color c;
	
	public void init(){
		//initialize GUI
		//Line button
		btnLine =new Button("Line");
		btnLine.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				currentShape = 'l';
	}}
		);
		add(btnLine);
		//rec button
		btnRec =new Button("Rectangle");
		btnRec.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentShape = 'r';
	}});
		add(btnRec);
		btnCircle =new Button("Circle");
		btnCircle.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentShape= 'c';
	}});
		add(btnCircle);
		//red button
		btnRed =new Button("Red");
		btnRed.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentColor= Color.red;
				repaint();
	}});
		add(btnRed);
		//blue btn
		btnBlue =new Button("Blue");
		btnBlue.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentColor= Color.blue;
				repaint();
				
	}});
		add(btnBlue);
		//green btn
		btnGreen =new Button("Green");
		btnGreen.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentColor= Color.green;
				repaint();
				
	}});
		add(btnGreen);
		//sketch
		btnSketch =new Button("Sketch");
		btnSketch.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentShape= 's';
				repaint();
				
				
	}});
		add(btnSketch);
		//eraser btn
		btnEraser =new Button("Eraser");
		btnEraser.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentShape='e';
				}});
		add(btnEraser);
		//clear all btn
		btnClear =new Button("CLEAR ALL");
		btnClear.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				clearPressed=true;
				repaint();}
			
		});
		add(btnClear);
		//dotted
		btnDotted =new Button("Dotted");
		btnDotted.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(dottedPressed== false){
					dottedPressed=true;
					btnDotted.setBackground(Color.green);
				}
				else{
					dottedPressed=false;
					btnDotted.setBackground(Color.gray);
				}
				repaint();}
			
		});
		add(btnDotted);
		btnFill =new Button("Fill");
		btnFill.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(filledPressed== false){
					filledPressed=true;
					btnFill.setBackground(Color.green);
				}
				else{
					filledPressed=false;
					btnFill.setBackground(Color.gray);
				}
				repaint();}
			
		});
		add(btnFill);
		//Undo
		btnUndo =new Button("Undo");
		btnUndo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				undoPressed = true;
				if(shapesVector.size()>0)
					shapesVector.removeElementAt(shapesVector.size()-1);
				else
					System.out.println("There is nothing to Undo");
				repaint();
	}}
		);
		add(btnUndo);
		
		MouseListener_ m = new MouseListener_();
		MotionListener ml = new MotionListener();
		this.addMouseListener(m);
		this.addMouseMotionListener(ml);
		
	}
	public void paint(Graphics g){
		float[] dash = {10,10,10};
		Graphics2D g2d = (Graphics2D) g.create();
			if(clearPressed){
				shapesVector= new Vector <GeoShape>();
				clearPressed = false;
			}
			
			
			//draw old
			System.out.println(shapesVector.size());
			for (j=0; j< shapesVector.size(); j++){
					System.out.println("hi");
					g2d.setColor(shapesVector.get(j).getColor());
					if(shapesVector.get(j).getDottedState())
							g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,10,dash,10 ));
						else
							g2d.setStroke(new BasicStroke(0));
						
					switch(shapesVector.get(j).getShapeName()){
						case 'l':
							g2d.drawLine(shapesVector.get(j).getX1(), shapesVector.get(j).getY1(), 
									shapesVector.get(j).getX2(), shapesVector.get(j).getY2());
							break;
						case 'r':
						   
							//here x2 is width and y2 is height
							g2d.drawRect(shapesVector.get(j).getX1(), shapesVector.get(j).getY1(), 
									shapesVector.get(j).getX2(), shapesVector.get(j).getY2());	
							if(shapesVector.get(j).isFilled())
								g2d.fillRect(shapesVector.get(j).getX1(), shapesVector.get(j).getY1(), 
									shapesVector.get(j).getX2(), shapesVector.get(j).getY2());
							break;
						case 'c':
							//here x2 is width and y2 is height
							g2d.drawOval(shapesVector.get(j).getX1(), shapesVector.get(j).getY1(), 
									shapesVector.get(j).getX2(), shapesVector.get(j).getY2());	
							if(shapesVector.get(j).isFilled())
								g2d.fillOval(shapesVector.get(j).getX1(), shapesVector.get(j).getY1(), 
									shapesVector.get(j).getX2(), shapesVector.get(j).getY2());	
							break;
						case 'e':
							g2d.fillRect(shapesVector.get(j).getX1(), shapesVector.get(j).getY1(), eraserWidth, eraserHeight);
							
}
			
		}
				if(dottedPressed){
					g2d.setStroke(new BasicStroke(0, BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,10,dash,10 ));
				}
			g.setColor(currentColor);
			
			
			switch(currentShape){
				case 'e':
						g.setColor(Color.black);
						g.drawRect(x2, y2, eraserWidth, eraserHeight);
						g.setColor(getBackground());
						g.fillRect(x2, y2, eraserWidth, eraserHeight);
						break;
				case 'l':
					if(!undoPressed)
						g2d.drawLine( x1, y1, x2, y2);
					break;
				case 'r':
					x= (x1-x2) <0? x1: x2;
					y= (y1-y2) <0 ? y1 : y2;
					g2d.drawRect(x, y, width, height);
					if(filledPressed){
						g.setColor(currentColor);
						g.fillRect(x, y, width, height);
					}
					width =height =0;
					break;
				case 'c':
					x= (x1-x2) <0 ? x1 : x2;
					y= (y1-y2) <0 ? y1 : y2;
					g2d.drawOval(x, y,width, height);
					if(filledPressed){
						g.setColor(currentColor);
						g.fillOval(x, y, width, height);
					}
					width =height =0;
					break;
				case 's':
					g.drawLine( x1, y1, x2, y2);
					break;
			}
			if(undoPressed){
				undoPressed = false;
			}
			}
	//Possible modification: Adapter class
	class MouseListener_ implements MouseListener{
		public void mousePressed(MouseEvent e){
			
				x1= x2 =e.getX();
				y1= y2 =e.getY();
				repaint();
		}
		@Override
		public void mouseExited(MouseEvent e) {
			
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			
		}
		@Override
		public void mouseEntered(MouseEvent e)  {
			
		}
		
		public void mouseReleased(MouseEvent e)  {
			x2=e.getX();
			y2=e.getY();
			
			width= Math.abs(x2-x1);
			height = Math.abs(y2-y1);
			switch(currentShape){
				case 'l':
					shapesVector.add(new Line(x1, y1, x2, y2, 'l',dottedPressed));
					break;
				case 'r':
					x= (x1-x2) <0 ? x1 : x2;
					y= (y1-y2) <0 ? y1 : y2;
					shapesVector.add(new Rect(x, y, width, height, 'r', dottedPressed, filledPressed));
					break;
				case 'c':
					x= (x1-x2) <0 ? x1 : x2;
					y= (y1-y2) <0 ? y1 : y2;
					shapesVector.add(new Circle(x, y, width, height, 'c', dottedPressed, filledPressed));
					break;

			}
			
	repaint();
}}
	
	class MotionListener implements MouseMotionListener{
		
		public void mouseMoved(MouseEvent e) {
			}
		
		public void mouseDragged(MouseEvent e) {
			x2=e.getX();
			y2=e.getY();
			
			width= Math.abs(x2-x1);
			height = Math.abs(y2-y1);
							
			switch(currentShape){
				
				case 'e':
					shapesVector.add(new Rect( x2, y2,eraserWidth, eraserHeight, 'e',  dottedPressed, filledPressed));
					break;
				case 's':
					shapesVector.add(new Line(x1, y1, x2, y2, 'l',  dottedPressed));
					counter+=1;
					if(counter % 2 ==0){
						x1=x2;
						y1=y2;
					}
					
					break;
				
			}
			repaint();
			}}
	
	class GeoShape{
	protected int dim1;
	protected int x1; //starting point
	protected int y1;
	protected int x2; 
	protected int y2;
	private char whoAmI;
	protected boolean dottedPressed;
	protected boolean filledPressed;
	protected Color color;
	
	public GeoShape(){
		
	}
	public GeoShape(int x1, int y1, int x2, int y2, char whoAmI, boolean dottedPressed, boolean filledPressed){
		
			this.x1=x1;
			this.y1=y1;
			this.x2=x2;
			this.y2=y2;
			this.whoAmI=whoAmI;
			this.dottedPressed=dottedPressed;
			this.filledPressed=filledPressed;

			if(whoAmI == 'e')
				this.color =getBackground();
			else
				this.color= currentColor;
}
	public GeoShape(int x1, int y1, int x2, int y2, char whoAmI, boolean dottedPressed){
		
			this.x1=x1;
			this.y1=y1;
			this.x2=x2;
			this.y2=y2;
			this.whoAmI=whoAmI;
			this.dottedPressed=dottedPressed;
			this.color= currentColor;
}
	boolean isFilled(){
		return filledPressed;
	}
	void setShape(char s){
		whoAmI=s;
}
	boolean getDottedState(){
		return this.dottedPressed;
	}
	char getShape(){
		return whoAmI;
}

	void setX1(int x1){
		
			x1=x1;
		}
	void setY1(int y1){
			y1=y1;
		}
	public void setX2(int x2){
			x2=x2;
		}
	public void setY2(int Y2){
			y2=y2;
		}
	
	public void setDim(int d){
		if (d <1)
			System.out.print("SORRY NO NEGATIVE NUMBERS!");
		else
			dim1=d;
		
	}
	public int getDim(){
		return this.dim1;
	}
	int getX1(){
			return x1;
		}
	int getY1(){
			return y1;
		}
	int getX2(){
			return x2;
		}
	int getY2(){
			return y2;
		}
	public Color getColor(){
		return color;
	}
	public char getShapeName(){
		return whoAmI;
}	
}
	
	class Line extends GeoShape{
		
		
		public Line(){
		
		}
		
		public Line(int x1,int y1, int x2,int y2, char whoAmI,  boolean dottedPressed){
			super(x1, y1, x2, y2, whoAmI, dottedPressed);
		}}

	class Rect extends GeoShape{
		
		public Rect(){
		
		}
		
		public Rect(int x1,int y1, int x2,int y2, char whoAmI,  boolean dottedPressed, boolean filledPressed){
			super(x1, y1, x2, y2, whoAmI, dottedPressed,  filledPressed);
		}}

	class Circle extends GeoShape{
		
		public Circle(){
		
		}
		
		public Circle(int x1,int y1, int x2,int y2, char whoAmI,  boolean dottedPressed, boolean filledPressed){
			super(x1, y1, x2, y2, whoAmI, dottedPressed, filledPressed);
		}
}
	
		}

