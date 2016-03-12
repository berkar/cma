package cma;

/** Basic Swing Console: Connection Handler
 *  @author John Chamberlain
 */

import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;

public class ConsoleConnection extends Thread
{
    private String msConnectionName = null;
    private Socket msocketClient;
    private BufferedReader breader = null;
    private BufferedWriter bwriter = null;
    private Object moEventSink;
    private boolean mbBreakConnection = false; // term flag
    private int mLineCount = 0;
    private final int THREAD_RELIEF_INTERVAL = 100;

    public ConsoleConnection(Socket theClientSocket,
                             Object oEventSink)
    {
        super();
        this.setDaemon(true);
        this.msConnectionName =
            theClientSocket.getInetAddress().getHostName();
        this.msocketClient = theClientSocket;
        this.moEventSink = oEventSink;
        try {
            breader = new BufferedReader(new InputStreamReader(theClientSocket.getInputStream()));
            bwriter = new BufferedWriter(new OutputStreamWriter(theClientSocket.getOutputStream()));
            this.start();
        }
        catch (Exception ex) {
            System.err.println("Unable to set up streams: " + ex);
        }
    }

    public void run()
    {
        int iNextThreadRelief = THREAD_RELIEF_INTERVAL;
        while (true) {
            try {
                if (mbBreakConnection) break;
                String sIncomingMessage = breader.readLine();

                // the following line is protection against
                // keepalive connections which send a steady stream
                // of nulls
                if (sIncomingMessage == null) continue;

                mLineCount++;
                MessageEvent theMessageEvent = new MessageEvent(this.moEventSink, sIncomingMessage);
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(theMessageEvent);
                this.yield();
                if (mLineCount == iNextThreadRelief) {
                    this.sleep(500);
                    iNextThreadRelief += THREAD_RELIEF_INTERVAL;
                }
            }
            catch (SocketException ex) {
                MessageEvent theMessageEvent = new MessageEvent(this.moEventSink, "connection closed");
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(theMessageEvent);
                break;
            }
            catch (Exception ex) {
                System.err.println("Failed to post message: " + ex);
            }
        }
    }

    String getConnectionName()
    {
        return this.msConnectionName;
    }

    void vWriteLine(String sLine)
    {
        if (bwriter != null) {
            try {
                bwriter.write(sLine);
                bwriter.newLine();
                bwriter.flush();
            }
            catch (Exception ex) {
                System.err.println("Failed to write line: " + ex);
            }
        }
    }

    String getStatus()
    {
        if (msocketClient == null) {
            return "no connection";
        }
        else {
            return "listening to: " +
                msocketClient.getInetAddress();
        }
    }

    boolean zTerminate(StringBuffer sbError)
    {
        try {
            msocketClient.close();
            mbBreakConnection = true;
            return true;
        }
        catch (Exception ex) {
            sbError.append("Error closing socket: " + ex);
            return false;
        }
    }
}

class MessageEvent extends AWTEvent
{
    static final int EVENT_ID_MessageEvent = AWTEvent.RESERVED_ID_MAX + 1;
    private String msMessage;

    MessageEvent(Object source, String sMessage)
    {
        super(source, EVENT_ID_MessageEvent);
        this.msMessage = sMessage;
        consumed = true;
    }

    String getMessage()
    {
        return msMessage;
    }
}


