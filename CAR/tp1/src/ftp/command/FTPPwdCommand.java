package ftp.command;

import ftp.FTPMessageSender;
import ftp.configuration.FTPClientConfiguration;

public class FTPPwdCommand extends FTPMessageSender implements FTPCommand {

    @Override
    public boolean accept(String command) {
	return command.equals("PWD");
    }

    @Override
    public void execute(String argument, FTPClientConfiguration clientConfiguration) {
	// TODO Auto-generated method stub

    }

}
