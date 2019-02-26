
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;




public class Plotter extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final int MAX_TRKS = 1000;
	static final int MAX_HT = 100000;
	static final double MAX_RANGE = 5;
	static final double MIN_RANGE = 1;
	static final long MAX_VEL = 10000;
	static final long MIN_VEL = 0;
	static final double LONG_RANGE = MAX_RANGE;
	static final double MEDIUM_RANGE = MAX_RANGE/2.0;
	static final double SHORT_RANGE = MAX_RANGE/4.0;
	static final int HISTORY = 6;
	static int newest = -1;
	static double scale = LONG_RANGE;
	static long deltatms = 35000;
	static Point mousep = null;
	static Integer stickyid = null;

	private Plotter() {
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));

	}

	JLabel scaleL= new JLabel("Scale Vertical");
	JTextField scaleT= new JTextField("35000",10);
    @SuppressWarnings("rawtypes")
	DefaultComboBoxModel fruitsName = new DefaultComboBoxModel();
    String data = "Track 1";

 
    @SuppressWarnings("unchecked")
	JComboBox fruitCombo = new JComboBox(fruitsName);    

    JButton showButton = new JButton("Select");


	JButton go = new JButton("go");
	JButton longRange = new JButton("Long");
	JButton mediumRange = new JButton("Medium");
	JButton shortRange = new JButton("Short");
	JFrame jfrm = new JFrame("Merging of tracks from two RADAR");
	static Plotter pe;
	int height;
	int width;
	double a=0;
	double b=0;
	double e = 0;
	double f = 0;
	double r1x =0;
	double r1y = 0;
	double r2x =0;
	double r2y = 0;
	double g1 = 0;
	double h = 0;
	double k = 0;
	double l = 0;
    boolean buttonPressed = false;


	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		height = getHeight();
		width = getWidth();
		if (mousep == null) {
			a = -5.0;
			b = 10.0;
		}else if (data.equals("Track 1")&& !buttonPressed){
			double[] pt = screenToMap(mousep.x, mousep.y);
			a = pt[0];
			b = pt[1];		
		}
		double c = 5.0;
		double d = 20.0;
		if (mousep == null) {
			r1x = 60;r1y = -100;	
		}else if (data.equals("r1")&& !buttonPressed){
			double[] pt = screenToMap(mousep.x, mousep.y);
			r1x = pt[0];r1y = pt[1];	
		}
		e = a-r1x;
		f = b-r1y;		
		if (mousep == null) {
			g1 = 5.0;
			h = 10.0;
		}else if (data.equals("Track 2")&& !buttonPressed){
			double[] pt = screenToMap(mousep.x, mousep.y);
			g1 = pt[0];
			h = pt[1];		
		}
		double i = 5.0;
		double j = 25.0;
		if (mousep == null) {
			r2x = -50.0;
			r2y = -100.0;
		}else if (data.equals("r2")&& !buttonPressed){
			double[] pt = screenToMap(mousep.x, mousep.y);
			r2x = pt[0];r2y = pt[1];	
		}
		k = g1-r2x;
		l = h-r2y;		
		double x1 = 0;
		double y1 = 0;
		double x1new = 0;
		double y1new = 0;
		double x2 = 0;
		double y2 = 0;
		double x2new = 0;
		double y2new = 0;
		double x3 = 0;
		double y3 = 0;
		double x3new = 0;
		double y3new = 0;
		Point track1 = new Point(), track2 = new Point(), merged = new Point();
		int z1ht=0, z2ht=0, z3ht=0;
		for(int y = -100; y < 100; y++) {
			boolean newLine = true;
			for (int x= -100; x < 100; x++) {
				double isopt[]=	cartesianToIsometric(x, y);
				if (newLine) {
					x1 = isopt[0];
					y1 = isopt[1];
					x2 = isopt[0]+2;
					y2 = isopt[1];
					x3 = isopt[0]+4;
					y3 = isopt[1];
					newLine = false;
				}
				g.setColor(Color.GREEN);
				int z1 = (int)//(scale *deltatms*1000*formula6(a,b,c,d,e,f,g1,h,i,j,k,l,Math.PI*2-Math.atan2(f, e),Math.PI*2-Math.atan2(l, k),1.0*x,1.0*y));
						(scale *deltatms* formula1(a,b,c,d,e,f,x,y,Math.PI*2-Math.atan2(f, e)));
				if (z1 > z1ht) {
					track1.x=x;
					track1.y = y;
					z1ht = z1;
				}

				y1new = -z1+isopt[1];
				x1new = isopt[0];
				g.drawLine((int)x1,(int)y1,(int)x1new,(int)(y1new));
				x1 = x1new; y1 = y1new;
				g.setColor(Color.BLUE);
				int z2 = (int) //(scale *deltatms*1000*formula7(a,b,c,d,e,f,g1,h,i,j,k,l,Math.PI*2-Math.atan2(f, e),Math.PI*2-Math.atan2(l, k),1.0*x,1.0*y));
						(scale *deltatms* formula2(g1,h,i,j,k,l,x,y,Math.PI*2-Math.atan2(l, k)));
				if (z2 > z2ht) {
					track2.x=x;
					track2.y = y;
					z2ht = z2;
				}
				y2new = -z2+isopt[1]+2;
				x2new = isopt[0];
				g.drawLine((int)x2,(int)y2,(int)x2new,(int)(y2new));
				x2 = x2new; y2 = y2new;
				g.setColor(Color.RED);
				int z3 = (int) (scale *deltatms*1000*formula3(a,b,c,d,e,f,g1,h,i,j,k,l,Math.PI*2-Math.atan2(f, e),Math.PI*2-Math.atan2(l, k),x,y));					
				if (z3 > z3ht) {
					merged.x=x;
					merged.y = y;
					z3ht = z3;
				}
				y3new = -z3+isopt[1]+4;
				x3new = isopt[0];

				g.drawLine((int)x3,(int)y3,(int)x3new,(int)(y3new));
				x3 = x3new; y3 = y3new;
			}
		}
		System.out.println("merged(red)"+"["+formula4(a,b,c,d,e,f,g1,h,i,j,k,l,Math.PI*2-Math.atan2(f, e),Math.PI*2-Math.atan2(l, k))+","+
				formula5(a,b,c,d,e,f,g1,h,i,j,k,l,Math.PI*2-Math.atan2(f, e),Math.PI*2-Math.atan2(l, k))+"]");
		track1.x=(int) a;track1.y=(int) b;track2.x=(int) g1;track2.y=(int) h;
		System.out.println("track1(green)"+track1.toString());
		System.out.println("track2(blue)"+track2.toString());
		double isopt1[]=	cartesianToIsometric(-100,-100);
		double isopt2[]=	cartesianToIsometric(100,-100);
		double isopt3[]=	cartesianToIsometric(100, 100);
		double isopt4[]=	cartesianToIsometric(-100, 100);
		//System.out.println("{"+isopt1[0]+","+isopt1[1]+"} {"+isopt2[0]+","+isopt2[1]+"} {"+isopt3[0]+","+isopt3[1]+"} {"+isopt4[0]+","+isopt4[1]+"}");
		g.setColor(Color.BLACK);
		double isopt5[]=	cartesianToIsometric(100,0);
		double isopt6[]=	cartesianToIsometric(-100, 0);
		double isopt7[]=	cartesianToIsometric(0,100);
		double isopt8[]=	cartesianToIsometric(0,-100);
		Point p1 = new Point(100,0);
		Point p2 = new Point(-100,0);
		Point p3 = new Point(0,100);
		Point p4 = new Point(0,-100);
		//System.out.println("{"+isopt5[0]+","+isopt5[1]+"} {"+isopt6[0]+","+isopt6[1]+"} {"+isopt7[0]+","+isopt7[1]+"} {"+isopt8[0]+","+isopt8[1]+"}");
		g.drawLine((int)(isopt5[0]),(int)(isopt5[1]),(int)(isopt6[0]),(int)(isopt6[1]));
		g.drawLine((int)(isopt7[0]),(int)(isopt7[1]),(int)(isopt8[0]),(int)(isopt8[1]));
		g.drawString(p1.toString(),(int)(isopt5[0]),(int)(isopt5[1]));
		g.drawString(p2.toString(),(int)(isopt6[0])-140,(int)(isopt6[1]));
		g.drawString(p3.toString(),(int)(isopt7[0]),(int)(isopt7[1]));
		g.drawString(p4.toString(),(int)(isopt8[0])-140,(int)(isopt8[1]));
		double[] r1 = cartesianToIsometric((int)(r1x),(int)(r1y));
		double[] r2 = cartesianToIsometric((int)(r2x),(int)(r2y));
		g.drawString("r1",(int)(r1[0]),(int)(r1[1]));
		g.drawString("r2",(int)(r2[0]),(int)(r2[1]));
		buttonPressed = false;
	}
	private double[] screenToMap(int x, int y) {
		int screenX = x-width/2; int screenY = height/2-y;
		double mapx = ((screenX /Math.cos(Math.PI*30/180)  - screenY/Math.sin(Math.PI*30/180)) / scale) * 0.5;
	    double mapy = ((screenX /Math.cos(Math.PI*30/180)  + screenY/Math.sin(Math.PI*30/180)) / scale) * 0.5;        
	    double[] result = {mapx, mapy} ; // -.5/+.5 because the drawing isn't aligned to the tile, it's aligned to the image
	    return result;
	}

	private double[] cartesianToIsometric(int x, int y)  {
		int cartesianX = x; int cartesianY = y;
		double isometricX = (cartesianX + cartesianY) *Math.cos(Math.PI*30/180)*scale;
		double isometricY = (-cartesianX + cartesianY) * Math.sin(Math.PI*30/180)*scale;        
	    double[] result = {(isometricX + width/2), (height / 2 -isometricY)};
	    return result;
	}
	private double formula5(double x0, double y0, double c, double d, double e, double f, double x1, double y1, double i,
			double j, double k, double l, double atan2, double atan22) {
		double	a1 = sqr(Cos(atan2))/(2*sqr(c)) + sqr(Sin(atan2))/(2*sqr(d));
		double  b1 = -Sin(2*atan2)/(4*sqr(c)) + Sin(2*atan2)/(4*sqr(d));
		double  c1 = sqr(Sin(atan2))/(2*sqr(c)) + sqr(Cos(atan2))/(2*sqr(d));
		double	a2 = sqr(Cos(atan22))/(2*sqr(i)) + sqr(Sin(atan22))/(2*sqr(j));
		double  b2 = -Sin(2*atan22)/(4*sqr(i)) + Sin(2*atan22)/(4*sqr(j));
		double  c2 = sqr(Sin(atan22))/(2*sqr(i)) + sqr(Cos(atan22))/(2*sqr(j));
		double 	yvalue=(-((a1*x0)/(a1+a2))+(b1*x0)/(b1+b2)-(a2*x1)/(a1+a2)+(b2*x1)/(b1+b2)-(b1*y0)/(a1+a2)+(c1*y0)/(b1+b2)
				-(b2*y1)/(a1+a2)+(c2*y1)/(b1+b2))/(-(b1/(a1+a2))-b2/(a1+a2)+c1/(b1+b2)+c2/(b1+b2));


		return yvalue;
	}

	
	private double formula4(double x0, double y0, double c, double d, double e, double f, double x1, double y1, double i,
			double j, double k, double l, double atan2, double atan22) {
		double	a1 = sqr(Cos(atan2))/(2*sqr(c)) + sqr(Sin(atan2))/(2*sqr(d));
		double  b1 = -Sin(2*atan2)/(4*sqr(c)) + Sin(2*atan2)/(4*sqr(d));
		double  c1 = sqr(Sin(atan2))/(2*sqr(c)) + sqr(Cos(atan2))/(2*sqr(d));
		double	a2 = sqr(Cos(atan22))/(2*sqr(i)) + sqr(Sin(atan22))/(2*sqr(j));
		double  b2 = -Sin(2*atan22)/(4*sqr(i)) + Sin(2*atan22)/(4*sqr(j));
		double  c2 = sqr(Sin(atan22))/(2*sqr(i)) + sqr(Cos(atan22))/(2*sqr(j));
		double	xvalue=(-((a1*x0)/(b1+b2))+(b1*x0)/(c1+c2)-(a2*x1)/(b1+b2)+(b2*x1)/(c1+c2)-(b1*y0)/(b1+b2)
				+(c1*y0)/(c1+c2)-(b2*y1)/(b1+b2)+(c2*y1)/(c1+c2))/(-(a1/(b1+b2))-a2/(b1+b2)+b1/(c1+c2)+b2/(c1+c2));	
		return xvalue;
	}
	//0=(Cos(α)*((x-a)*cos(α)+(y-b)*sin(α)-e*cos(α)-f*sin(α))/(c*c)+((x-a)*sin(α)-(y-b)*cos(α)-e*sin(α)+f*cos(α))*sin(α)/(d*d)+cos(β)*((x-g)*cos(β)+(y-h)*sin(β)-k*cos(β)-l*sin(β))/(i*i)+((x-g)*sin(β)-(y-h)*cos(β)-k*sin(β)+l*cos(β))*sin(β)/(j*j))
	//0=(Sin(α)*((x-a)*cos(α)+(y-b)*sin(α)-e*cos(α)-f*sin(α))/(c*c)-((x-a)*sin(α)-(y-b)*cos(α)-e*sin(α)+f*cos(α))*cos(α)/(d*d)+sin(β)*((x-g)*cos(β)+(y-h)*sin(β)-k*cos(β)-l*sin(β))/(i*i)-((x-g)*sin(β)-(y-h)*cos(β)-k*sin(β)+l*cos(β))*cos(β)/(j*j))


	//	{{y->((g sqr(i))/(sqr(i)-sqr(j))-(g sqr(j))/(sqr(i)-sqr(j))+(sqr(i) k)/(sqr(i)-sqr(j))-(sqr(j) k)/(sqr(i)-sqr(j))-(h sqr(i) Cot[\[Beta]])/(sqr(i)-sqr(j))-(sqr(i) l Cot[\[Beta]])/(sqr(i)-sqr(j))-(sqr(i) sqr(j) Cos[\[Alpha]] Csc[\[Beta]] Sec[\[Beta]])/(sqr(i)-sqr(j))-(sqr(i) sqr(j) Csc[\[Beta]] Sec[\[Beta]] Sin[\[Alpha]])/(sqr(i)-sqr(j))-(sqr(i) sqr(j) Cos[\[Alpha]])/(sqr(j) Cos[\[Beta]]^2+sqr(i) Sin[\[Beta]]^2)-(g sqr(j) Cos[\[Beta]]^2)/(sqr(j) Cos[\[Beta]]^2+sqr(i) Sin[\[Beta]]^2)-(sqr(j) k Cos[\[Beta]]^2)/(sqr(j) Cos[\[Beta]]^2+sqr(i) Sin[\[Beta]]^2)+(sqr(i) sqr(j) Sin[\[Alpha]])/(sqr(j) Cos[\[Beta]]^2+sqr(i) Sin[\[Beta]]^2)+(h sqr(i) Cos[\[Beta]] Sin[\[Beta]])/(sqr(j) Cos[\[Beta]]^2+sqr(i) Sin[\[Beta]]^2)-(h sqr(j) Cos[\[Beta]] Sin[\[Beta]])/(sqr(j) Cos[\[Beta]]^2+sqr(i) Sin[\[Beta]]^2)+(sqr(i) l Cos[\[Beta]] Sin[\[Beta]])/(sqr(j) Cos[\[Beta]]^2+sqr(i) Sin[\[Beta]]^2)-(sqr(j) l Cos[\[Beta]] Sin[\[Beta]])/(sqr(j) Cos[\[Beta]]^2+sqr(i) Sin[\[Beta]]^2)-(g sqr(i) Sin[\[Beta]]^2)/(sqr(j) Cos[\[Beta]]^2+sqr(i) Sin[\[Beta]]^2)-(sqr(i) k Sin[\[Beta]]^2)/(sqr(j) Cos[\[Beta]]^2+sqr(i) Sin[\[Beta]]^2)-(h sqr(j) Tan[\[Beta]])/(sqr(i)-sqr(j))-(sqr(j) l Tan[\[Beta]])/(sqr(i)-sqr(j)))/(-((sqr(i) Cot[\[Beta]])/(sqr(i)-sqr(j)))+(sqr(i) Cos[\[Beta]] Sin[\[Beta]])/(sqr(j) Cos[\[Beta]]^2+sqr(i) Sin[\[Beta]]^2)-(sqr(j) Cos[\[Beta]] Sin[\[Beta]])/(sqr(j) Cos[\[Beta]]^2+sqr(i) Sin[\[Beta]]^2)-(sqr(j) Tan[\[Beta]])/(sqr(i)-sqr(j)))}}
	//	{{x->(-((h sqr(i))/(sqr(i)-sqr(j)))+(h sqr(j))/(sqr(i)-sqr(j))-(sqr(i) l)/(sqr(i)-sqr(j))+(sqr(j) l)/(sqr(i)-sqr(j))+(g sqr(j) Cot[\[Beta]])/(sqr(i)-sqr(j))+(sqr(j) k Cot[\[Beta]])/(sqr(i)-sqr(j))+(sqr(i) sqr(j) Cos[\[Alpha]] Csc[\[Beta]] Sec[\[Beta]])/(sqr(i)-sqr(j))-(sqr(i) sqr(j) Csc[\[Beta]] Sec[\[Beta]] Sin[\[Alpha]])/(sqr(i)-sqr(j))+(sqr(i) sqr(j) Cos[\[Alpha]])/(sqr(i) Cos[\[Beta]]^2+sqr(j) Sin[\[Beta]]^2)+(h sqr(i) Cos[\[Beta]]^2)/(sqr(i) Cos[\[Beta]]^2+sqr(j) Sin[\[Beta]]^2)+(sqr(i) l Cos[\[Beta]]^2)/(sqr(i) Cos[\[Beta]]^2+sqr(j) Sin[\[Beta]]^2)+(sqr(i) sqr(j) Sin[\[Alpha]])/(sqr(i) Cos[\[Beta]]^2+sqr(j) Sin[\[Beta]]^2)-(g sqr(i) Cos[\[Beta]] Sin[\[Beta]])/(sqr(i) Cos[\[Beta]]^2+sqr(j) Sin[\[Beta]]^2)+(g sqr(j) Cos[\[Beta]] Sin[\[Beta]])/(sqr(i) Cos[\[Beta]]^2+sqr(j) Sin[\[Beta]]^2)-(sqr(i) k Cos[\[Beta]] Sin[\[Beta]])/(sqr(i) Cos[\[Beta]]^2+sqr(j) Sin[\[Beta]]^2)+(sqr(j) k Cos[\[Beta]] Sin[\[Beta]])/(sqr(i) Cos[\[Beta]]^2+sqr(j) Sin[\[Beta]]^2)+(h sqr(j) Sin[\[Beta]]^2)/(sqr(i) Cos[\[Beta]]^2+sqr(j) Sin[\[Beta]]^2)+(sqr(j) l Sin[\[Beta]]^2)/(sqr(i) Cos[\[Beta]]^2+sqr(j) Sin[\[Beta]]^2)+(g sqr(i) Tan[\[Beta]])/(sqr(i)-sqr(j))+(sqr(i) k Tan[\[Beta]])/(sqr(i)-sqr(j)))/((sqr(j) Cot[\[Beta]])/(sqr(i)-sqr(j))-(sqr(i) Cos[\[Beta]] Sin[\[Beta]])/(sqr(i) Cos[\[Beta]]^2+sqr(j) Sin[\[Beta]]^2)+(sqr(j) Cos[\[Beta]] Sin[\[Beta]])/(sqr(i) Cos[\[Beta]]^2+sqr(j) Sin[\[Beta]]^2)+(sqr(i) Tan[\[Beta]])/(sqr(i)-sqr(j)))}}	

	//	D f(x,y)/Dx={(1/(4 c d i j Pisqr()))exp(e Cos[\[Alpha]]-(-a+x) Cos[\[Alpha]]-(-b+y) Cos[\[Alpha]]+(f Cos[\[Alpha]]^2)/(2 sqr(d))-e Sin[\[Alpha]]-(a-x) Sin[\[Alpha]]-(-b+y) Sin[\[Alpha]]+(f Sin[\[Alpha]]^2)/(2 sqr(c))+(-l Cos[\[Beta]]+(-h+y) Cos[\[Beta]]+k Sin[\[Beta]]+(g-x) Sin[\[Beta]])^2/(2 sqr(j))+(-k Cos[\[Beta]]+(-g+x) Cos[\[Beta]]-l Sin[\[Beta]]+(-h+y) Sin[\[Beta]])^2/(2 sqr(i))) Loge() (-Cos[\[Alpha]]+Sin[\[Alpha]]-(1/(sqr(j)))Sin[\[Beta]] (-l Cos[\[Beta]]+(-h+y) Cos[\[Beta]]+k Sin[\[Beta]]+(g-x) Sin[\[Beta]])+(1/(sqr(i)))Cos[\[Beta]] (-k Cos[\[Beta]]+(-g+x) Cos[\[Beta]]-l Sin[\[Beta]]+(-h+y) Sin[\[Beta]]))}
	//	D f(x,y)/Dy={(1/(4 c d i j Pisqr()))exp(e Cos[\[Alpha]]-(-a+x) Cos[\[Alpha]]-(-b+y) Cos[\[Alpha]]+(f Cos[\[Alpha]]^2)/(2 sqr(d))-e Sin[\[Alpha]]-(a-x) Sin[\[Alpha]]-(-b+y) Sin[\[Alpha]]+(f Sin[\[Alpha]]^2)/(2 sqr(c))+(-l Cos[\[Beta]]+(-h+y) Cos[\[Beta]]+k Sin[\[Beta]]+(g-x) Sin[\[Beta]])^2/(2 sqr(j))+(-k Cos[\[Beta]]+(-g+x) Cos[\[Beta]]-l Sin[\[Beta]]+(-h+y) Sin[\[Beta]])^2/(2 sqr(i))) Loge() (-Cos[\[Alpha]]-Sin[\[Alpha]]+(1/(sqr(j)))Cos[\[Beta]] (-l Cos[\[Beta]]+(-h+y) Cos[\[Beta]]+k Sin[\[Beta]]+(g-x) Sin[\[Beta]])+(1/(sqr(i)))Sin[\[Beta]] (-k Cos[\[Beta]]+(-g+x) Cos[\[Beta]]-l Sin[\[Beta]]+(-h+y) Sin[\[Beta]]))}

	//	{(1/(4*c*d*i*j*Pisqr()))exp(e*Cos(atan2)-(-a+x)*Cos(atan2)-(-b+y)*Cos(atan2)+(f*Cos(sqr(atan2)))/(2*sqr(d))-e*Sin(atan2)-(a-x)*Sin(atan2)-(-b+y)*Sin(atan2)+(f*Sin(sqr(atan2)))/(2*sqr(c))-(-l*Cos(atan22)+(-h+y)*Cos(atan22)+k*Sin(atan22)+(g-x)*Sin(atan22))^2/(2*sqr(j))-sqr(-k*Cos(atan22)+(-g+x)*Cos(atan22)-l*Sin(atan22)+(-h+y)*Sin(atan22))/(2*sqr(i)))*Loge()*(-Cos(atan2)+Sin(atan2)+(1/(sqr(j)))Sin(atan22)*(-l*Cos(atan22)+(-h+y)*Cos(atan22)+k*Sin(atan22)+(g-x)*Sin(atan22))-(1/(sqr(i)))Cos(atan22)*(-k*Cos(atan22)+(-g+x)*Cos(atan22)-l*Sin(atan22)+(-h+y)*Sin(atan22)))}
	//	{(1/(4*c*d*i*j*Pisqr()))exp(e*Cos(atan2)-(-a+x)*Cos(atan2)-(-b+y)*Cos(atan2)+(f*Cos(sqr(atan2)))/(2*sqr(d))-e*Sin(atan2)-(a-x)*Sin(atan2)-(-b+y)*Sin(atan2)+(f*Sin(sqr(atan2)))/(2*sqr(c))-(-l*Cos(atan22)+(-h+y)*Cos(atan22)+k*Sin(atan22)+(g-x)*Sin(atan22))^2/(2*sqr(j))-sqr(-k*Cos(atan22)+(-g+x)*Cos(atan22)-l*Sin(atan22)+(-h+y)*Sin(atan22))/(2*sqr(i)))*Loge()*(-Cos(atan2)-Sin(atan2)-(1/(sqr(j)))Cos(atan22)*(-l*Cos(atan22)+(-h+y)*Cos(atan22)+k*Sin(atan22)+(g-x)*Sin(atan22))-(1/(sqr(i)))Sin(atan22)*(-k*Cos(atan22)+(-g+x)*Cos(atan22)-l*Sin(atan22)+(-h+y)*Sin(atan22)))}

	//	A*exp(-(a1*sqr(x-x0)+2*b1*(x-x0)*(y-y0)+c1*sqr(y-y0))))
	//	B*exp(-(a2*sqr(x-x1)+2*b2*(x-x1)*(y-y1)+c2*sqr(y-y1)))
	//	D[A*B*exp(-(a1*sqr(x-x0)+2*b1*(x-x0)*(y-y0)+c1*sqr(y-y0))-(a2*sqr(x-x1)+2*b2*(x-x1)*(y-y1)+c2*sqr(y-y1))),x]
	//	A B exp(-a1 sqr(x-x0)-a2 sqr(x-x1)-2 b1 (x-x0) (y-y0)-c1 sqr(y-y0)-2 b2 (x-x1) (y-y1)-c2 sqr(y-y1)) (-2 a1 (x-x0)-2 a2 (x-x1)-2 b1 (y-y0)-2 b2 (y-y1))
	//	A B exp(-a1 sqr(x-x0)-a2 sqr(x-x1)-2 b1 (x-x0) (y-y0)-c1 sqr(y-y0)-2 b2 (x-x1) (y-y1)-c2 sqr(y-y1)) (-2 b1 (x-x0)-2 b2 (x-x1)-2 c1 (y-y0)-2 c2 (y-y1))
	//	{{y->(-((a1 x0)/(a1+a2))+(b1 x0)/(b1+b2)-(a2 x1)/(a1+a2)+(b2 x1)/(b1+b2)-(b1 y0)/(a1+a2)+(c1 y0)/(b1+b2)-(b2 y1)/(a1+a2)+(c2 y1)/(b1+b2))/(-(b1/(a1+a2))-b2/(a1+a2)+c1/(b1+b2)+c2/(b1+b2))}}
	//	{{x->(-((a1 x0)/(b1+b2))+(b1 x0)/(c1+c2)-(a2 x1)/(b1+b2)+(b2 x1)/(c1+c2)-(b1 y0)/(b1+b2)+(c1 y0)/(c1+c2)-(b2 y1)/(b1+b2)+(c2 y1)/(c1+c2))/(-(a1/(b1+b2))-a2/(b1+b2)+b1/(c1+c2)+b2/(c1+c2))}}	
	//	a1 = sqr(Cos(atan2))/(2*sqr(c)) + sqr(Sin(atan2))/(2*sqr(d));
	//  b1 = -Sin(2*atan2)/(4*sqr(c)) + Sin(2*atan2)/(4*sqr(d));
	//  c1 = sqr(Sin(atan2))/(2*sqr(c)) + sqr(Cos(atan2))/(2*sqr(d));
	//	a2 = sqr(Cos(atan22))/(2*sqr(i)) + sqr(Sin(atan22))/(2*sqr(j));
	//  b2 = -Sin(2*atan22)/(4*sqr(i)) + Sin(2*atan22)/(4*sqr(j));
	//  c2 = sqr(Sin(atan22))/(2*sqr(i)) + sqr(Cos(atan22))/(2*sqr(j));
	private static double formula6(double x0, double y0, double c, double d, double e, double f, //df/dx
			double x1, double y1, double i, double j, double k, double l,double atan2, double atan22,double x, double y) {
		double  a1 = sqr(Cos(atan2))/(2*sqr(c)) + sqr(Sin(atan2))/(2*sqr(d));
		double  b1 = -Sin(2*atan2)/(4*sqr(c)) + Sin(2*atan2)/(4*sqr(d));
		double  c1 = sqr(Sin(atan2))/(2*sqr(c)) + sqr(Cos(atan2))/(2*sqr(d));
		double  a2 = sqr(Cos(atan22))/(2*sqr(i)) + sqr(Sin(atan22))/(2*sqr(j));
		double  b2 = -Sin(2*atan22)/(4*sqr(i)) + Sin(2*atan22)/(4*sqr(j));
		double  c2 = sqr(Sin(atan22))/(2*sqr(i)) + sqr(Cos(atan22))/(2*sqr(j));
		return (1/(4*c*d*i*j*Pisqr()))*Exp(-a1*sqr(x-x0)-a2*sqr(x-x1)-2*b1*(x-x0)*(y-y0)-c1*sqr(y-y0)-2*b2*(x-x1)*(y-y1)-c2*sqr(y-y1))
				*(-2*a1*(x-x0)-2*a2*(x-x1)-2*b1*(y-y0)-2*b2*(y-y1));
	}
	private static double formula7(double x0, double y0, double c, double d, double e, double f, //df/dy
			double x1, double y1, double i, double j, double k, double l,double atan2, double atan22,double x, double y) {
		double	a1 = sqr(Cos(atan2))/(2*sqr(c)) + sqr(Sin(atan2))/(2*sqr(d));
		double  b1 = -Sin(2*atan2)/(4*sqr(c)) + Sin(2*atan2)/(4*sqr(d));
		double  c1 = sqr(Sin(atan2))/(2*sqr(c)) + sqr(Cos(atan2))/(2*sqr(d));
		double	a2 = sqr(Cos(atan22))/(2*sqr(i)) + sqr(Sin(atan22))/(2*sqr(j));
		double  b2 = -Sin(2*atan22)/(4*sqr(i)) + Sin(2*atan22)/(4*sqr(j));
		double  c2 = sqr(Sin(atan22))/(2*sqr(i)) + sqr(Cos(atan22))/(2*sqr(j));
		return (1/(4*c*d*i*j*Pisqr()))*Exp(-a1*sqr(x-x0)-a2*sqr(x-x1)-2*b1*(x-x0)*(y-y0)-c1*sqr(y-y0)-2*b2*(x-x1)*(y-y1)-c2*sqr(y-y1))
				*(-2*b1*(x-x0)-2*b2*(x-x1)-2*c1*(y-y0)-2*c2*(y-y1));
	}

	private static double Pisqr () {
		return sqr(Math.PI);
	}
	private static double Loge () {
		return Math.log(Math.E);
	}
	private static double sqr (double value) {
		return value * value;
	}
	private static double Exp (double value) {
		return Math.pow(Math.E, value);
	}
	private static double Tan (double value) {
		return Math.tan(value);
	}
	private static double Cot (double value) {
		return 1.0/Math.tan(value);
	}
	private static double Csc (double value) {
		return 1.0/Math.sin(value);
	}
	private static double Sec (double value) {
		return 1.0/Math.cos(value);
	}
	private static double Cos(double value) {
		return Math.cos(value);
	}
	private static double Sin(double value) {
		return Math.sin(value);
	}
	//	private static double Cos2(double value) {
	//		return Math.cos(value)*Math.cos(value);
	//	}
	//	private static double Sin2(double value) {
	//		return Math.sin(value)*Math.sin(value);
	//	}

	//g1*g2
	private double formula3(double x0, double y0, double c, double d, double e, double f, double x1, double y1, double i, double j, double k, double l, double atan2, double atan22, double x, double y) {
		double	a1 = sqr(Cos(atan2))/(2*sqr(c)) + sqr(Sin(atan2))/(2*sqr(d));
		double  b1 = -Sin(2*atan2)/(4*sqr(c)) + Sin(2*atan2)/(4*sqr(d));
		double  c1 = sqr(Sin(atan2))/(2*sqr(c)) + sqr(Cos(atan2))/(2*sqr(d));
		double	a2 = sqr(Cos(atan22))/(2*sqr(i)) + sqr(Sin(atan22))/(2*sqr(j));
		double  b2 = -Sin(2*atan22)/(4*sqr(i)) + Sin(2*atan22)/(4*sqr(j));
		double  c2 = sqr(Sin(atan22))/(2*sqr(i)) + sqr(Cos(atan22))/(2*sqr(j));
		return	(1/(2*Math.PI*c*d))*(1/(2*Math.PI*i*j))*Exp(-(a1*sqr(x-x0)+2*b1*(x-x0)*(y-y0)+c1*sqr(y-y0))-(a2*sqr(x-x1)+2*b2*(x-x1)*(y-y1)+c2*sqr(y-y1)));
	}

	//g2
	private double formula2(double x1, double y1, double i, double j, double k, double l, double x, double y,double atan22) {
		double	a2 = sqr(Cos(atan22))/(2*sqr(i)) + sqr(Sin(atan22))/(2*sqr(j));
		double  b2 = -Sin(2*atan22)/(4*sqr(i)) + Sin(2*atan22)/(4*sqr(j));
		double  c2 = sqr(Sin(atan22))/(2*sqr(i)) + sqr(Cos(atan22))/(2*sqr(j));
		return (1/(2*Math.PI*i*j))*Exp(-(a2*sqr(x-x1)+2*b2*(x-x1)*(y-y1)+c2*sqr(y-y1)));
	}

	//g1
	private double formula1(double x0, double y0, double c, double d, double e, double f, double x, double y,double atan2) {
		double	a1 = sqr(Cos(atan2))/(2*sqr(c)) + sqr(Sin(atan2))/(2*sqr(d));
		double  b1 = -Sin(2*atan2)/(4*sqr(c)) + Sin(2*atan2)/(4*sqr(d));
		double  c1 = sqr(Sin(atan2))/(2*sqr(c)) + sqr(Cos(atan2))/(2*sqr(d));
		return(1/(2*Math.PI*c*d))*Exp(-(a1*sqr(x-x0)+2*b1*(x-x0)*(y-y0)+c1*sqr(y-y0)));
	}


	class PaintDemo implements MouseListener{

		@SuppressWarnings("unchecked")
		PaintDemo(){
			jfrm.setSize(1800, 1000);
			jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    fruitsName.addElement("r1");
		    fruitsName.addElement("r2");
		    fruitsName.addElement("Track 1");
		    fruitsName.addElement("Track 2");
		    fruitCombo.setSelectedIndex(2);
		    showButton.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent e) { 
		           if (fruitCombo.getSelectedIndex() != -1) {                     
		              data = fruitCombo.getItemAt
		                 (fruitCombo.getSelectedIndex()).toString(); 
		              buttonPressed = true;
		              pe.repaint();
		           }              
		        }
		     }); 

			jfrm.setLayout(new BorderLayout());

			JPanel temp = new JPanel();
			temp.add(fruitCombo);
			temp.add(showButton);
			
			temp.add(scaleL);		
			temp.add(scaleT);
			temp.add(go);
			go.addActionListener(new ActionListener() {


				@Override
				public void actionPerformed(ActionEvent e) {
					deltatms = Long.parseLong(scaleT.getText());
					pe.repaint();
				}

			});
			temp.add(longRange);
			longRange.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					scale = LONG_RANGE;
					pe.repaint();

				}

			});
			temp.add(mediumRange);
			mediumRange.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					scale = MEDIUM_RANGE;
					pe.repaint();

				}

			});
			temp.add(shortRange);
			shortRange.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					scale = SHORT_RANGE;
					pe.repaint();

				}

			});
			jfrm.add(temp, BorderLayout.NORTH);
			jfrm.add(pe, BorderLayout.CENTER);
			pe.addMouseListener(this);

			jfrm.setVisible(true);
		}




		@Override
		public void mouseClicked(MouseEvent e) {
			Plotter.mousep = e.getPoint();
			pe.repaint();
		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}
	public static void main(String args[]) throws IOException {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (args.length != 0) {
					System.out.println("usage: java Plotter");
					System.exit(1);
				}
				pe = new Plotter();
				pe.new PaintDemo();
			}
		});
	}

}

