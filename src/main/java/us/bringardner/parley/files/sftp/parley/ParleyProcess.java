package us.bringardner.parley.files.sftp.parley;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import us.bringardner.parley.files.sftp.spi.SshExitStatus;
import us.bringardner.parley.ssh.client.SessionChannel;

/** A command running on the server over a parley-ssh session channel (SshConnection.start). */
final class ParleyProcess extends Process {

	private final SessionChannel channel;

	ParleyProcess(SessionChannel channel) {
		this.channel = channel;
	}

	@Override
	public OutputStream getOutputStream() {
		return channel.getOutputStream();
	}

	@Override
	public InputStream getInputStream() {
		return channel.getInputStream();
	}

	@Override
	public InputStream getErrorStream() {
		return channel.getErrorStream();
	}

	@Override
	public int waitFor() throws InterruptedException {
		while( !channel.waitForClose(1, TimeUnit.SECONDS)) {
			// still running
		}
		return exitValue();
	}

	@Override
	public boolean waitFor(long timeout, TimeUnit unit) throws InterruptedException {
		return channel.waitForClose(timeout, unit);
	}

	@Override
	public int exitValue() {
		if( !channel.isClosed()) {
			throw new IllegalThreadStateException("The command is still running");
		}
		return SshExitStatus.of(channel.getExitStatus(), channel.getExitSignal());
	}

	@Override
	public boolean isAlive() {
		return !channel.isClosed();
	}

	@Override
	public void destroy() {
		channel.close();
	}
}
