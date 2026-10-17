# Changelog

## parley-files-sftp-parley-ssh 1.0.0 (2026-10-17)

The first release. The parley-ssh provider for parley-files-sftp. It was part of parley-files-sftp (package
`us.bringardner.parley.files.sftp.client.parley`, before that BjlFileSystemSftp's
`us.bringardner.io.filesource.sftp.client.bjl`) and is now a project of its own, found on the class path
through `ServiceLoader`.

- Package: `us.bringardner.parley.files.sftp.parley`.
- Module name (`Automatic-Module-Name`): `us.bringardner.parley.files.sftp.parley`.
- Logs in with an ssh-agent (`auth=Agent`) too (needs Java 16 on macOS and Linux).
- Tested with parley-files-sftp's provider test suite and its own tests.
