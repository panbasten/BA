package com.flywet.platform.bi.cust.p001.services;

import java.io.IOException;
import java.text.ParseException;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPFile;

import org.apache.log4j.Logger;

public class BIFTPClient extends FTPClient {

	/**
	 * MDTM supported flag
	 */
	private boolean mdtmSupported = true;

	/**
	 * SIZE supported flag
	 */
	private boolean sizeSupported = true;

	private Logger log;

	public BIFTPClient(Logger log) {
		super();
		this.log = log;
	}

	public boolean exists(String remoteFile) throws IOException, FTPException {
		checkConnection(true);

		// first try the SIZE command
		if (sizeSupported) {
			lastReply = control.sendCommand("SIZE " + remoteFile); //$NON-NLS-1$
			char ch = lastReply.getReplyCode().charAt(0);
			if (ch == '2')
				return true;
			if (ch == '5'
					&& fileNotFoundStrings.matches(lastReply.getReplyText()))
				return false;

			sizeSupported = false;
			log.error("SIZE not supported - trying MDTM"); //$NON-NLS-1$
		}

		// then try the MDTM command
		if (mdtmSupported) {
			lastReply = control.sendCommand("MDTM " + remoteFile); //$NON-NLS-1$
			char ch = lastReply.getReplyCode().charAt(0);
			if (ch == '2')
				return true;
			if (ch == '5'
					&& fileNotFoundStrings.matches(lastReply.getReplyText()))
				return false;

			mdtmSupported = false;
			log.error("MDTM not supported - trying LIST"); //$NON-NLS-1$
		}

		try {
			FTPFile[] files = dirDetails(null); // My fix - replace "." with
												// null in this call for MVS
												// support
			for (int i = 0; i < files.length; i++) {
				if (files[i].getName().equals(remoteFile)) {
					return files[i].isFile();
				}
			}
			return false;
		} catch (ParseException ex) {
			log.error(ex.getMessage());
			return false;
		}
	}

}
