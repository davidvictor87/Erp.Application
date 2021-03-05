package erp.application.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import erp.application.entities.ApplicationStaticInfo;
import erp.application.entities.LOG;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.management.RuntimeErrorException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import java.io.File;
import java.io.FileInputStream;

@Service(value="ftpSender")
public class FtpSender {

	@Value("${host.server.url}")
	private String serverURL;
	@Value("${connection.server.port}")
	private int port;
	@Value("${connection.server.user}")
	private String user;
	@Value("${connection.server.pass}")
	private String password;
	private static final int TIMEOUT = 1;
	private CheckConnection checkConnection = new CheckConnection();

	public FtpSender() {
		super();
	}

	public void establishFtpConnection() {
		FTPClient ftpClient = null;
		try {
			ftpClient = new FTPClient();
			ftpClient.connect(java.net.InetAddress.getByName(serverURL), port);
			boolean isConnected = FTPReply.isPositiveCompletion(ftpClient.getReplyCode());
			if (isConnected && checkConnection.checkIfAddressIsReachable(serverURL, port, TIMEOUT)) {
				ftpClient.login(user, password);
				ftpClient.enterLocalPassiveMode();
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				File fileLocation = new File(ApplicationStaticInfo.EMPLOYEE_PROCCESSED_DOCUMENTS);
				for (File files : fileLocation.listFiles()) {
					if (files.isDirectory()) {
						InputStream inputStream = new FileInputStream(files);
						boolean isDone = ftpClient.storeFile("", inputStream);
						if (isDone) {
							LOG.appLogger().info("UPLOADED");
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}

class CheckConnection{
	
	protected boolean checkIfAddressIsReachable(String address, int port, int timeout) {
		Socket socket = new Socket();
		try {
			socket.connect(new InetSocketAddress(address, port), timeout);
			return true;
		}catch (IOException e) {
			throw new RuntimeErrorException((Error) e.fillInStackTrace(), e.getLocalizedMessage());
		}finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
