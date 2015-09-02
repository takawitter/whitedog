# Example Source Code #

Here are example code copied from jp.whitedog.sample.paint.Paint.
```
public class Paint extends JFrame{
	public Paint(){
		final JPanel panel = new JPanel();
		MouseMotionListener listener = new MouseMotionAdapter(){
			@Share
			public void mouseDragged(MouseEvent e) {
				Graphics g = panel.getGraphics();
				g.fillOval(e.getX(), e.getY(), 3, 3);
				g.dispose();
			}
		};
		panel.addMouseMotionListener(listener);
		add(panel);

		// create session, register shared object and connect.
		Session session = new JGroupsSession("hello");
		session.register(listener);
		try{
			session.connect();
		} catch(WhiteDogException e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception{
		SwingUtilities.invokeAndWait(new Runnable(){
			public void run() {
				Paint p = new Paint();
				p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				p.setSize(600, 400);
				p.setTitle("shared paint");
				p.setVisible(true);
			}
		});
	}
}
```
These code shows how you can use WhiteDog framework.
By annotating a method with @Share annotaion and register the object that has the method to the session, you can share the execution of the annotated method in the session.

In this example, the mouseDragged method of anonymous inner class in the constructor of Paint. That method draws small dot on line of mouse drag. So the dots will be shared in the session.

This example uses JGroupsSession. JGroupsSession finds the session that has same session name (In this case "hello") in local network.

Here is the screen shot.

![http://whitedog.googlecode.com/files/screenshot_paint.png](http://whitedog.googlecode.com/files/screenshot_paint.png)