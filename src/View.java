/**
 * 
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import org.junit.Test;

/**
 * BackUp class for Java GUI
 * 
 * @author Yndal
 *
 */
public class View {
	JFrame frame;
	Canvas canvas;
	JViewport viewport;
	Road[] roads;
	final int viewportMove;
	
	
	public View(){
		canvas = new Canvas();
		viewport = new JViewport();
		viewportMove = 50;
	}
	
	public void createFrame(){
		frame = new JFrame("Krax BETA");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(600, 300));
		frame.setPreferredSize(new Dimension(900, 600));
	
		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		JScrollPane scrollPaneCanvas = new JScrollPane(canvas, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		JScrollPane scrollPaneCanvas = new JScrollPane(canvas, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		viewport = scrollPaneCanvas.getViewport();
		
		
		//Not sure how much help the two next lines does
		viewport.setDoubleBuffered(true);
		canvas.setDoubleBuffered(true);

		
//		scrollPaneCanvas.setViewport(viewport);

		//TODO Set position for viewport
		
		
		contentPane.add(scrollPaneCanvas, BorderLayout.CENTER);
		
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(4,1));
		contentPane.add(rightPanel, BorderLayout.EAST);
		
		
		JPanel directionPanel = new JPanel();
		directionPanel.setLayout(new GridLayout(3,3));
		
		
		
//		directionPanel.setLayout(new BorderLayout());
		JButton northButton = new JButton("N");
		northButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent a) {
				System.out.println("Going North");
				
				Point viewablePartOfCanvas = viewport.getViewPosition();
				viewablePartOfCanvas.y -= viewportMove;
				if (viewablePartOfCanvas.y < 0) viewablePartOfCanvas.y = 0;
				viewport.setViewPosition(viewablePartOfCanvas);
			}
		});
		
		
		JButton southButton = new JButton("S");
		southButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent a) {
				System.out.println("Going South");
				Point viewablePartOfCanvas = viewport.getViewPosition();
				viewablePartOfCanvas.y += viewportMove;
//				if (viewablePartOfCanvas.y > ?) viewablePartOfCanvas.y = ?;
				viewport.setViewPosition(viewablePartOfCanvas);
			}
		});
		
		JButton eastButton = new JButton("E");
		eastButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent a) {
				System.out.println("Going East");
				Point viewablePartOfCanvas = viewport.getViewPosition();
				viewablePartOfCanvas.x += viewportMove;
//				if (viewablePartOfCanvas.x > ?) viewablePartOfCanvas.x = ?;
				viewport.setViewPosition(viewablePartOfCanvas);
				viewport.toViewCoordinates(viewablePartOfCanvas);
			}
		});
		
		JButton westButton = new JButton("W");
		westButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent a) {
				System.out.println("Going West");
				
				Point viewablePartOfCanvas = viewport.getViewPosition();
				viewablePartOfCanvas.x -= viewportMove;
				if (viewablePartOfCanvas.x < 0) viewablePartOfCanvas.x = 0;
				viewport.setViewPosition(viewablePartOfCanvas);
			}
		});
		
		//Creating the direction panel with the N, E, S, W and the empty spaces
		directionPanel.add(Box.createRigidArea(new Dimension(40, 40)));
		directionPanel.add(northButton);
		directionPanel.add(Box.createRigidArea(new Dimension(40, 40)));
		directionPanel.add(westButton);
		directionPanel.add(Box.createRigidArea(new Dimension(40, 40)));
		directionPanel.add(eastButton);
		directionPanel.add(Box.createRigidArea(new Dimension(40, 40)));
		directionPanel.add(southButton);
		directionPanel.add(Box.createRigidArea(new Dimension(40, 40)));
		

		rightPanel.add(Box.createRigidArea(new Dimension(40, 40)));
		rightPanel.add(directionPanel);
		
		JPanel zoomPanel = new JPanel();
		zoomPanel.setLayout(new FlowLayout());
		rightPanel.add(zoomPanel);
		
		JButton zoomInButton = new JButton("+");
		zoomInButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent a) {
				int x;
				int y;
				
				Rectangle visibleView = viewport.getViewRect();
				
				x = (visibleView.x + visibleView.width) / 2;
				y = (visibleView.y + visibleView.height) / 2;
				
				Point centerOfView = new Point(x, y);
				Controller.newZoomLevel(centerOfView, true);
				
				System.out.println("Zoomed in");
				
			}
		});
		
		JButton zoomOutButton = new JButton("-");
		zoomOutButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent a) {
				int x;
				int y;
				
				Rectangle visibleView = viewport.getViewRect();
				
				x = (visibleView.x + visibleView.width) / 2;
				y = (visibleView.y + visibleView.height) / 2;
				
				Point centerOfView = new Point(x, y);
				Controller.newZoomLevel(centerOfView, false);
				
				System.out.println("Zoomed out");
			}
		});
		
		
		
		zoomPanel.add(zoomOutButton);
		zoomPanel.add(Box.createRigidArea(new Dimension(40,40)));
		zoomPanel.add(zoomInButton);
		
		
		
		
		frame.pack();
		frame.setVisible(true);
	}
	
	public void drawRoads(Road[] roads){
		this.roads = roads;
		//TODO Set coordinates of the viewport
		canvas.setSize(new Dimension((int) Controller.getMaxXCurrent(), (int) Controller.getMaxYCurrent()));
		canvas.updateUI();//
	}
	
	
	
	
	
	
	
	
	
	public class Canvas extends JPanel{
		private int mouseX;
		private int mouseY;
		//private int OFFSET = 1;
		
		
		public Canvas(){
			setBackground(Color.white);
			setBorder(BorderFactory.createLineBorder(Color.black));
			
//			addMouseListener(new MouseAdapter(){
//				public void mousePressed(MouseEvent e){
//					//currentShape.drawPressed(g, c, x, y)
//					moveSquare(e.getX(), e.getY());
//					
//				}
//			});
//			
//			addMouseMotionListener(new MouseAdapter(){
//				public void mouseDragged(MouseEvent e){
//					moveSquare(e.getX(), e.getY());
//				}
//			});
		}
//		
//		private void moveSquare(int x, int y){
//			if ((x != mouseX) || (y != mouseY)){
//				repaint(mouseX, mouseY, squareH+OFFSET, squareW+OFFSET);
//				mouseX = x;
//				mouseY = y;
//				repaint(mouseX, mouseY, shapeH, shapeW);
//				
//			}
//		}
		
		public void paintComponent(Graphics g){
			Graphics2D g2 = (Graphics2D) g;
			super.paintComponent(g2);
			
			if(roads != null){
				for(Road road : roads){
					g2.setColor(Controller.getRoadColor(road.type));
					g2.setStroke(new BasicStroke(Controller.getRoadWidth(road.type)));
					g2.drawLine((int) road.x1, (int) road.y1, (int) road.x2, (int) road.y2);
				}
			}
//			g2.drawLine(150, 150, 150, 150);
//			g2.drawLine(151, 150, 151, 150);
//			g2.drawLine(150, 151, 150, 151);
//			g2.drawLine(151, 151, 151, 151);
			
			
	
//			g2.setColor(Color.green);
//			g2.drawLine(150, 150, 250, 250);
//			g2.drawString("Testing testing testing2...", 100, 20);
//			g2.setColor(Color.red);
//			g2.fillRect(0, 0, 100, 100);
//			g2.setColor(Color.black);
//			g2.drawRect(100, 100, 100, 100);
			
		}
		
		
		//TODO May throw a nullPointerException if DataHelper have not calculated the max values of x and y
		@Override
		public Dimension getPreferredSize(){
			return new Dimension(8000, 6000);
			//return new Dimension((int) dataHelper.getMaxXCurrent(), (int) dataHelper.getMaxYCurrent());
		}
	}	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
