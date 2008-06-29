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
package jp.whitedog.sample.chat;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import jp.whitedog.Peer;
import jp.whitedog.PeerFactory;
import jp.whitedog.PeerListener;
import jp.whitedog.Session;
import jp.whitedog.Share;
import jp.whitedog.WhiteDogException;
import jp.whitedog.jgroups.JGroupsSession;

/**
 * Sample chat.
 * @author Takao Nakaguchi
 */
public class Chat extends JFrame{
	/**
	 * main method.
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception{
		SwingUtilities.invokeAndWait(new Runnable(){
			public void run()  {
				Chat p = new Chat();
				p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				p.setSize(600, 400);
				p.setTitle("chat");
				p.setVisible(true);
			}
		});
	}

	/**
	 * constructor.
	 */
	public Chat(){
		JSplitPane pane = new JSplitPane();
		pane.setLeftComponent(createChatPane());
		pane.setRightComponent(createUserListPane());
		pane.setDividerLocation(400);
		pane.setContinuousLayout(true);
		this.add(pane);

		// create session, register shared object and connect.
		session = new JGroupsSession("chat", new PeerFactory(){
			public Peer createPeer(String peerId) {
				Peer p = new ChatPeer(peerId){
					@Override
					protected void displayNameChanged() {
						userListModel.set(
								userListModel.indexOf(this)
								, this);
					}
					@Override
					public String toString() {
						String name = getDisplayName();
						if(name == null){
							return getPeerId();
						} else{
							return name;
						}
					}
					private static final long serialVersionUID = 0L;
				};
				return p;
			}
		});
		session.register(chatLogModel);
		session.addPeerListener(new PeerListener(){
			public void peerEntered(Peer peer){
				userListModel.addElement(peer);
				session.register(peer, peer.getPeerId());
			}
			public void peerLeaved(Peer peer) {
				session.unregister(peer);
				userListModel.removeElement(peer);
			}
		});
		try{
			session.connect();
		} catch(WhiteDogException e){
			e.printStackTrace();
		}
	}

	private DefaultListModel chatLogModel = new DefaultListModel(){
		@Override
		@Share
		public void addElement(Object obj) {
			super.addElement(obj);
		}
		private static final long serialVersionUID = 4939763289511399950L;
	};
	private DefaultListModel userListModel = new DefaultListModel();

	private JPanel createChatPane(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JScrollPane chatPane = new JScrollPane();
		chatPane.setViewportView(new JList(chatLogModel));
		panel.add(chatPane, BorderLayout.CENTER);

		JPanel editPane = new JPanel();
		editPane.setLayout(new BorderLayout());
		final JTextField textField = new JTextField();
		final JButton button = new JButton("send");
		textField.addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					button.doClick();
				}
				super.keyPressed(e);
			}
		});
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				chatLogModel.addElement(
						session.getSelfPeer() + ": " + textField.getText()
						);
				textField.setText("");
			}
		});
		editPane.add(textField, BorderLayout.CENTER);
		editPane.add(button, BorderLayout.EAST);

		panel.add(editPane, BorderLayout.SOUTH);

		return panel;
	}

	private JComponent createUserListPane(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		final JTextField nameField = new JTextField();
		nameField.addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					((ChatPeer)session.getSelfPeer()).setDisplayName(
							nameField.getText()
							);
				}
				super.keyPressed(e);
			}
		});
		JPanel namePanel = new JPanel(new BorderLayout());
		namePanel.add(new JLabel("name:"), BorderLayout.WEST);
		namePanel.add(nameField, BorderLayout.CENTER);
		panel.add(namePanel, BorderLayout.NORTH);
		panel.add(new JScrollPane(new JList(userListModel)), BorderLayout.CENTER);
		return panel;
	}
	private Session session;

	private static final long serialVersionUID = 951304335129876757L;
}
