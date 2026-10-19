# parley-files-sftp-parley-ssh

The parley-ssh provider for [parley-files-sftp](https://github.com/parley-project/parley-files-sftp): SFTP through
[parley-ssh](https://github.com/parley-project/parley-ssh), Parley's own SSH library, with no third party
code.

```xml
<dependency>
    <groupId>us.bringardner.parley</groupId>
    <artifactId>parley-files-sftp-parley-ssh</artifactId>
    <version>1.0.0</version>
</dependency>
```

It brings parley-files-sftp with it.

It is the provider parley-files-sftp prefers: with it on the class path, a factory that names no provider
uses it, even when other providers are there too. Choose another with the `implementation` connection
property or `-Dparley.sftp.implementation`.

## What it supports

Everything parley-files-sftp asks of a provider: passwords, private keys (OpenSSH, PEM and PKCS#8, with
or without a passphrase, and Java key stores), ssh-agent login (`auth=Agent`), known_hosts checking,
writing at any position in one request, links and permissions.

**Java versions.** parley-ssh uses the JDK's Ed25519, which arrived in Java 15, and reaches an ssh-agent on
macOS and Linux through Unix domain sockets, which arrived in Java 16. On Java 11 to 14 those fail with an
`IOException` that says so; RSA and ECDSA keys, passwords and the Windows agents work on Java 11.


## Locks and FileChannel

As parley-files-sftp describes: `PROCESS` locks, atomic `createNewFile()` (an exclusive open), `map()` of a local
copy in `READ_ONLY` and `PRIVATE` modes, and `force()` that sends what is held, then asks the server to sync the
file with `fsync@openssh.com` when it offers that.

## Testing

`mvn verify` runs parley-files-sftp's provider test suite against this provider (the tests jar, through
surefire's `dependenciesToScan`), and this project's own tests: the parley-specific ones (`SftpBatch23Test`) and that the older name `bjl` still selects it (`SftpLegacyNamesTest`). See "Testing a provider" in parley-files-sftp's README; the
suite's server is chosen with `-Dparley.sftp.test.server` as described there.
