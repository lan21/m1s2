package ftp.command;

import java.net.Socket;

import ftp.FTPDatabase;
import ftp.FTPMessageSender;
import ftp.FailedCwdException;
import ftp.configuration.FTPClientConfiguration;

/**
 * Class representing the CWD command 
 */
public class FTPCwdCommand extends FTPMessageSender implements FTPCommand {

	/**
	 * constructs a CWD-command
	 * @param database the database
	 */
	public FTPCwdCommand(FTPDatabase database) {
		super(database);
	}

	@Override
	public boolean accept(String command) {
		return command.equals("CWD");
	}

	@Override
	public void execute(String argument,
			FTPClientConfiguration clientConfiguration) {
		final Socket connection = clientConfiguration.getConnection();
		if (".".equals(argument)) {
			sendCommandWithDefaultMessage(connection, 250);
			return;
		}
		if ("..".equals(argument)) {
			try {
				clientConfiguration.goUp();
			} catch (FailedCwdException e) {
				sendCommandWithDefaultMessage(connection, 530);
			}
			sendCommandWithDefaultMessage(connection, 250);
			return;
		}
		if (!clientConfiguration.isConnected()) {
			sendCommandWithDefaultMessage(connection, 530);
			return;
		}
		//clientConfiguration.setWorkingDirectory(argument);
		sendCommandWithDefaultMessage(connection, 250);
	}

}
