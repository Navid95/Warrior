package noc;

import com.jcraft.jsch.*;
import java.io.ByteArrayOutputStream;


public class WarriorUcip {

    private Session session;
    private ByteArrayOutputStream response;

    public WarriorUcip(String host, String username, String password) throws JSchException {
        this.response = new ByteArrayOutputStream();
        this.session = new JSch().getSession(username, host);
        this.session.setPassword(password);
        this.session.setConfig("StrictHostKeyChecking", "no");
        this.session.connect();
    }
//    public WarriorUcip() throws JSchException {
//        this.response = new ByteArrayOutputStream();
//        this.session = new JSch().getSession("mongo", "192.168.1.104");
//        this.session.setPassword("mongo");
//        this.session.setConfig("StrictHostKeyChecking", "no");
//        this.session.connect();
//    }

//  **************************************************************************

    public String get_sftp(String src , String dst) throws SftpException, JSchException, InterruptedException {
        ChannelSftp channelSftp = (ChannelSftp) this.session.openChannel("sftp");
        channelSftp.setOutputStream(response);
        channelSftp.connect();
        channelSftp.get(src , dst);
        channelSftp.disconnect();
        if (channelSftp.getExitStatus() <=0)
            return returnResponse();
        else return "Exit code : "+String.valueOf(channelSftp.getExitStatus());
    }

    public String put_sftp( String src , String dst) throws SftpException, JSchException, InterruptedException {
        ChannelSftp channelSftp = (ChannelSftp) this.session.openChannel("sftp");
        channelSftp.setOutputStream(response);
        channelSftp.connect();
        channelSftp.put(src , dst);
        channelSftp.disconnect();
        if (channelSftp.getExitStatus() <=0)
            return returnResponse();
        else return "Exit code : "+String.valueOf(channelSftp.getExitStatus());
    }

    public String exec_cmd( String cmd) throws JSchException, InterruptedException {
        ChannelExec channel = (ChannelExec) this.session.openChannel("exec");
        channel.setCommand(cmd);
        channel.setOutputStream(response);
        channel.setErrStream(response);
        channel.connect();
        while (channel.isConnected()) {
            Thread.sleep(100);
        }
        channel.disconnect();
        if (channel.getExitStatus() ==0)
            return returnResponse();
        else return "Exit code : "+String.valueOf(channel.getExitStatus());
    }

    private String returnResponse(){
        String responseStr = new String(response.toByteArray());
        response.reset();
        return responseStr;
    }

    public void closeSession(){
        session.disconnect();
    }
}
