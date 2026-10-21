package us.bringardner.parley.files.sftp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import us.bringardner.parley.files.sftp.spi.SshProviders;
import us.bringardner.parley.files.sftp.parley.ParleyProvider;

/**
 * The move to Parley renamed the "bjl" SSH library to "parley"; "bjl" must keep working as a name.
 * (The bjl.sftp.* system properties are no longer read: in 2.0.0 they are parley.files.sftp.*.)
 * No server needed.
 */
public class SftpLegacyNamesTest {

	@AfterEach
	public void clearProperties() {
		System.clearProperty(SshProviders.SYSTEM_PROPERTY);
		System.clearProperty(SftpFileSourceFactory.SYSTEM_PROPERTY_ATTRIBUTE_CACHE_TTL);
	}

	@Test
	public void bjlIsAnotherNameForParley() {
		assertTrue(SshProviders.get("bjl") instanceof ParleyProvider);
		assertTrue(SshProviders.get("BJL") instanceof ParleyProvider);
		assertTrue(SshProviders.get("parley") instanceof ParleyProvider);
		assertEquals(SshProviders.PARLEY, SshProviders.get("bjl").getName());
		assertEquals(SshProviders.PARLEY, SshProviders.canonicalName(" Bjl "));
		assertEquals("mina", SshProviders.canonicalName("MINA"));
	}

	@Test
	public void factoryStoresTheNewName() {
		SftpFileSourceFactory f = new SftpFileSourceFactory();
		f.setImplementation("bjl");
		assertEquals(SshProviders.PARLEY, f.getImplementation());
		assertEquals(SshProviders.PARLEY, f.getEffectiveImplementation());
	}

	@Test
	public void implementationProperty() {
		System.setProperty(SshProviders.SYSTEM_PROPERTY, "bjl");
		assertEquals(SshProviders.PARLEY, SshProviders.defaultName());
		System.setProperty(SshProviders.SYSTEM_PROPERTY, "mina");
		assertEquals("mina", SshProviders.defaultName());
	}

	@Test
	public void attributeCacheTtlProperty() {
		System.setProperty(SftpFileSourceFactory.SYSTEM_PROPERTY_ATTRIBUTE_CACHE_TTL, "456");
		assertEquals(456, SftpFileSourceFactory.defaultAttributeCacheTtl());
	}
}
