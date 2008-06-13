/* $Id$
 * 
 * Copyright 2008 Takao Nakaguchi.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.whitedog.sample.paint;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import jp.whitedog.Session;
import jp.whitedog.Share;
import jp.whitedog.WhiteDogException;
import jp.whitedog.jgroups.JGroupsSession;

/**
 * Sample paint application.
 * @author Takao Nakaguchi
 */
public class Paint extends JFrame{
	/**
	 * main method.
	 * @param args
	 * @throws Exception
	 */
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

	/**
	 * constructor.
	 */
	public Paint(){
		final JPanel panel = new JPanel();
		MouseMotionListener l = new MouseMotionAdapter(){
			@Share
			public void mouseDragged(MouseEvent e) {
				Graphics g = panel.getGraphics();
				g.fillOval(e.getX(), e.getY(), 3, 3);
				g.dispose();
			}
		};
		panel.addMouseMotionListener(l);
		add(panel);

		// create session, register shared object and connect.
		Session session = new JGroupsSession("hello");
		session.register(l);
		try{
			session.connect();
		} catch(WhiteDogException e){
			e.printStackTrace();
		}
	}

	private static final long serialVersionUID = 2438937147716338917L;
}
