/**
 * encode: utf-8
 *
 * @edited by Assis Henrique Oliveira Pacheco
 * @version: 1.0
 *
 * @docs: https://stackoverflow.com/questions/42020170/how-to-properly-unit-test-a-class-that-gets-file-from-an-ftp-server
 * @docs: https://github.com/apache/mina-ftpserver/
 * @docs: https://mina.apache.org/ftpserver-project/
 *
 * # PortuguesBR
 * Classe que deve definir e gerenciar um servidor de FTP
 *
 * # English
 * Class to manage an FTP server
 */
package util;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.*;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PasswordEncryptor;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.file.MyFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UtilFTPServer {
    private static final Logger logger = LoggerFactory.getLogger(UtilFTPServer.class);

    private PropertiesUserManagerFactory userManagerFactory;
    private FtpServerFactory serverFactory;
    private FtpServer server;

    public UtilFTPServer(int port){
        try{
            createUsersProperties();
        }catch (Exception e){
            logger.error("Exception");
            logger.error(e.toString());
            e.printStackTrace();
        }
        serverFactory = new FtpServerFactory();
        ListenerFactory factory = new ListenerFactory();
        factory.setPort(port);
        serverFactory.addListener("default", factory.createListener());
        userManagerFactory = new PropertiesUserManagerFactory();
        userManagerFactory.setFile(new File("myusers.properties"));//choose any. We're telling the FTP-server where to read its user list
        userManagerFactory.setPasswordEncryptor(new PasswordEncryptor()
        {//We store clear-text passwords in this example
            @Override
            public String encrypt(String password) {
                return password;
            }

            @Override
            public boolean matches(String passwordToCheck, String storedPassword) {
                return passwordToCheck.equals(storedPassword);
            }
        });
    }

    public void start(){

        Map<String, Ftplet> m = new HashMap<String, Ftplet>();
        m.put("miaFtplet", new Ftplet()
        {
            @Override
            public void init(FtpletContext ftpletContext) throws FtpException {
                //System.out.println("init");
                //System.out.println("Thread #" + Thread.currentThread().getId());
            }

            @Override
            public void destroy() {
                //System.out.println("destroy");
                //System.out.println("Thread #" + Thread.currentThread().getId());
            }

            @Override
            public FtpletResult beforeCommand(FtpSession session, FtpRequest request) throws FtpException, IOException
            {
                //System.out.println("beforeCommand " + session.getUserArgument() + " : " + session.toString() + " | " + request.getArgument() + " : " + request.getCommand() + " : " + request.getRequestLine());
                //System.out.println("Thread #" + Thread.currentThread().getId());

                //do something
                return FtpletResult.DEFAULT;//...or return accordingly
            }

            @Override
            public FtpletResult afterCommand(FtpSession session, FtpRequest request, FtpReply reply) throws FtpException, IOException
            {
                //System.out.println("afterCommand " + session.getUserArgument() + " : " + session.toString() + " | " + request.getArgument() + " : " + request.getCommand() + " : " + request.getRequestLine() + " | " + reply.getMessage() + " : " + reply.toString());
                //System.out.println("Thread #" + Thread.currentThread().getId());

                //do something
                return FtpletResult.DEFAULT;//...or return accordingly
            }

            @Override
            public FtpletResult onConnect(FtpSession session) throws FtpException, IOException
            {
                //System.out.println("onConnect " + session.getUserArgument() + " : " + session.toString());
                //System.out.println("Thread #" + Thread.currentThread().getId());

                //do something
                return FtpletResult.DEFAULT;//...or return accordingly
            }

            @Override
            public FtpletResult onDisconnect(FtpSession session) throws FtpException, IOException
            {
                //System.out.println("onDisconnect " + session.getUserArgument() + " : " + session.toString());
                //System.out.println("Thread #" + Thread.currentThread().getId());

                //do something
                return FtpletResult.DEFAULT;//...or return accordingly
            }
        });
        serverFactory.setFtplets(m);
        //Map<String, Ftplet> mappa = serverFactory.getFtplets();
        //System.out.println(mappa.size());
        //System.out.println("Thread #" + Thread.currentThread().getId());
        //System.out.println(mappa.toString());
        server = serverFactory.createServer();
        try
        {
            server.start();//Your FTP server starts listening for incoming FTP-connections, using the configuration options previously set
        }
        catch (FtpException e)
        {
            logger.error("FTPException");
            logger.error(e.toString());
            //Deal with exception as you need
        }
    }

    public void addUser(String userTest, String passTest) {
        //Let's add a user, since our myusers.properties file is empty on our first test run
        BaseUser user = new BaseUser();
        user.setName(userTest);
        user.setPassword(passTest);
        List<Authority> authorities = new ArrayList<Authority>();
        authorities.add(new WritePermission());
        user.setAuthorities(authorities);
        UserManager um = userManagerFactory.createUserManager();
        try
        {
            um.save(user);//Save the user to the user list on the filesystem
        }
        catch (FtpException e)
        {
            logger.error("FTPException");
            logger.error(e.toString());
            //Deal with exception as you need
        }
        serverFactory.setUserManager(um);
    }

    public void stop() {
        server.stop();
    }

    private void createUsersProperties() throws Exception {
        MyFile file = new MyFile("");
        file.setFile("myusers.properties");
        file.createFile();
        file.newLine("# Password is \"admin\"");
        file.newLine("ftpserver.user.admin.userpassword=21232F297A57A5A743894A0E4A801FC3");
        file.newLine("ftpserver.user.admin.homedirectory=./res/home");
        file.newLine("ftpserver.user.admin.enableflag=true");
        file.newLine("ftpserver.user.admin.writepermission=true");
        file.newLine("ftpserver.user.admin.maxloginnumber=0");
        file.newLine("ftpserver.user.admin.maxloginperip=0");
        file.newLine("ftpserver.user.admin.idletime=0");
        file.newLine("ftpserver.user.admin.uploadrate=0");
        file.newLine("ftpserver.user.admin.downloadrate=0");
        file.newLine("");
        file.newLine("ftpserver.user.anonymous.userpassword=");
        file.newLine("ftpserver.user.anonymous.homedirectory=./res/home");
        file.newLine("ftpserver.user.anonymous.enableflag=true");
        file.newLine("ftpserver.user.anonymous.writepermission=false");
        file.newLine("ftpserver.user.anonymous.maxloginnumber=20");
        file.newLine("ftpserver.user.anonymous.maxloginperip=2");
        file.newLine("ftpserver.user.anonymous.idletime=300");
        file.newLine("ftpserver.user.anonymous.uploadrate=4800");
        file.newLine("ftpserver.user.anonymous.downloadrate=4800");
        boolean fileSave = file.saveFile(); // https://github.com/apache/mina-ftpserver/blob/master/distribution/res/conf/users.properties
        if(fileSave){
            //OK
        }else{
            throw new Exception("Error creating myusers.properties: " + file.toString());
        }
    }
}
