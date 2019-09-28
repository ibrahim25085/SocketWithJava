
package clientserverdemo;
import java.io.*;
import java.net.*;
import java.util.*;

public class ServerFrame extends javax.swing.JFrame {
    ArrayList clientOutputStreams;
    ArrayList<String> users;
    int port = 1201;
    public ServerFrame() {
        initComponents();
    }
    public class ClientHandler implements Runnable	
   {
       BufferedReader reader;
       Socket server;
       PrintWriter client;

       public ClientHandler(Socket clientSocket, PrintWriter user) 
       {
            client = user;
            try 
            {
                server = clientSocket;
                InputStreamReader isReader = new InputStreamReader(server.getInputStream());
                reader = new BufferedReader(isReader);
            }
            catch (Exception ex) 
            {
                msgArea.append("Unexpected error... \n");
            }

       }

       @Override
       public void run() 
       {
            String message, connect = "Connect", disconnect = "Disconnect", chat = "Chat" ;
            String[] data;

            try 
            {
                while ((message = reader.readLine()) != null) 
                {
                    msgArea.append("Received: " + message + "\n");
                    data = message.split(":");
                    
                    for (String token:data) 
                    {
                        msgArea.append(token + "\n");
                    }

                    if (data[2].equals(connect)) 
                    {
                        tellEveryone((data[0] + ":" + data[1] + ":" + chat));
                        userAdd(data[0]);
                    } 
                    else if (data[2].equals(disconnect)) 
                    {
                        tellEveryone((data[0] + ":has disconnected." + ":" + chat));
                        userRemove(data[0]);
                    } 
                    else if (data[2].equals(chat)) 
                    {
                        tellEveryone(message);
                    } 
                    else 
                    {
                        msgArea.append("No Conditions were met. \n");
                    }
                } 
             } 
             catch (Exception ex) 
             {
                msgArea.append("Lost a connection. \n");
                ex.printStackTrace();
                clientOutputStreams.remove(client);
             } 
	} 
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        serverLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        msgArea = new javax.swing.JTextArea();
        conBtn = new javax.swing.JButton();
        disconBtn = new javax.swing.JButton();
        allUsersBtn = new javax.swing.JButton();
        clearBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        serverLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        serverLabel.setText("Server");

        msgArea.setEditable(false);
        msgArea.setColumns(20);
        msgArea.setRows(5);
        jScrollPane1.setViewportView(msgArea);

        conBtn.setText("Connect");
        conBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                conBtnActionPerformed(evt);
            }
        });

        disconBtn.setText("Disconnect");
        disconBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconBtnActionPerformed(evt);
            }
        });

        allUsersBtn.setText("Online Users");
        allUsersBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allUsersBtnActionPerformed(evt);
            }
        });

        clearBtn.setText("Clear");
        clearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(serverLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(conBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(disconBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(allUsersBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearBtn)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(serverLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(conBtn)
                    .addComponent(disconBtn)
                    .addComponent(allUsersBtn)
                    .addComponent(clearBtn))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void conBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conBtnActionPerformed
        Thread starter = new Thread(new ServerStart());
        starter.start();
        
        msgArea.append("Server started...\n");
    }//GEN-LAST:event_conBtnActionPerformed

    private void clearBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearBtnActionPerformed
       msgArea.setText("");
    }//GEN-LAST:event_clearBtnActionPerformed

    private void disconBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disconBtnActionPerformed
        try 
        {
            Thread.sleep(5000);                 //5000 milliseconds is five second.
        } 
        catch(InterruptedException ex) {Thread.currentThread().interrupt();}
        
        tellEveryone("Server:is stopping and all users will be disconnected.\n:Chat");
        msgArea.append("Server stopping... \n");
        
        msgArea.setText("");
    }//GEN-LAST:event_disconBtnActionPerformed

    private void allUsersBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allUsersBtnActionPerformed
        msgArea.append("\n Online users : \n");
        for (String current_user : users)
        {
            msgArea.append(current_user);
            msgArea.append("\n");
        }
    }//GEN-LAST:event_allUsersBtnActionPerformed
    public class ServerStart implements Runnable 
    {
        @Override
        public void run() 
        {
            clientOutputStreams = new ArrayList();
            users = new ArrayList();  

            try 
            {
                ServerSocket serverSock = new ServerSocket(port);

                while (true) 
                {
				Socket clientSock = serverSock.accept();
				PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
				clientOutputStreams.add(writer);

				Thread listener = new Thread(new ClientHandler(clientSock, writer));
				listener.start();
				msgArea.append("Got a connection. \n");
                }
            }
            catch (Exception ex)
            {
                msgArea.append("Error making a connection. \n");
            }
        }
    }
    public void userAdd (String data) 
    {
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        msgArea.append("Before " + name + " added. \n");
        users.add(name);
        msgArea.append("After " + name + " added. \n");
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        for (String token:tempList) 
        {
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
    }
    
    public void userRemove (String data) 
    {
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        users.remove(name);
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        for (String token:tempList) 
        {
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
    }
    
    public void tellEveryone(String message) 
    {
	Iterator it = clientOutputStreams.iterator();

        while (it.hasNext()) 
        {
            try 
            {
                PrintWriter writer = (PrintWriter) it.next();
		writer.println(message);
		msgArea.append("Sending: " + message + "\n");
                writer.flush();
                msgArea.setCaretPosition(msgArea.getDocument().getLength());

            } 
            catch (Exception ex) 
            {
		msgArea.append("Error telling everyone. \n");
            }
        } 
    }
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ServerFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton allUsersBtn;
    private javax.swing.JButton clearBtn;
    private javax.swing.JButton conBtn;
    private javax.swing.JButton disconBtn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea msgArea;
    private javax.swing.JLabel serverLabel;
    // End of variables declaration//GEN-END:variables
}
