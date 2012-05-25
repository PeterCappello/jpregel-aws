/**
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package aws.datameer.awstasks.ssh;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.Session;

import aws.datameer.awstasks.util.IoUtil;
import aws.datameer.awstasks.util.SshUtil;

public class ScpUploadCommand extends JschCommand {

    private static final String SCP_UPLOAD_FOLDER_COMMAND = "scp -r -d -t ";
    private static final String SCP_UPLOAD_FILE_COMMAND = "scp -t ";

    private final File _localFile;
    private final String _targetPath;

    public ScpUploadCommand(File localFile, String targetPath) {
        _localFile = localFile;
        _targetPath = targetPath;
    }

    @Override
    public void execute(Session session) throws IOException {
        String command = constructScpUploadCommand(_localFile.isDirectory(), _targetPath);
        Channel channel = SshUtil.openExecChannel(session, command);
        try {
            OutputStream out = channel.getOutputStream();
            InputStream in = channel.getInputStream();

            SshUtil.checkAcknowledgement(in);
            if (_localFile.isDirectory()) {
                uploadFolder(_localFile, in, out);
            } else {
                uploadFile(_localFile, in, out);
            }
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
        }
    }

    protected final static String constructScpUploadCommand(boolean isFolder, String remotePath) {
        if (isFolder) {
            return SCP_UPLOAD_FOLDER_COMMAND + remotePath;
        }
        return SCP_UPLOAD_FILE_COMMAND + remotePath;
    }

    private static void uploadFolder(File folder, InputStream in, OutputStream out) throws IOException {
        SshUtil.writeAcknowledgedMessage("D0755 0 " + folder.getName() + "\n", in, out);

        uploadFolderChildren(folder, in, out);
        SshUtil.writeAcknowledgedMessage("E\n", in, out);
    }

    private static void uploadFolderChildren(File localFile, InputStream in, OutputStream out) throws IOException {
        File[] files = localFile.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                uploadFolder(file, in, out);
            } else {
                uploadFile(file, in, out);
            }
        }
    }

    private static void uploadFile(File localFile, InputStream in, OutputStream out) throws IOException {
        SshUtil.writeAcknowledgedMessage("C0644 " + localFile.length() + " " + localFile.getName() + "\n", in, out);
        FileInputStream fileInputStream = new FileInputStream(localFile);
        long startTime = System.currentTimeMillis();
        long totalLength = 0;

        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Sending: " + localFile.getName() + " : " + localFile.length() + " bytes");
            }
            totalLength = IoUtil.copyBytes(fileInputStream, out);
            out.flush();
            SshUtil.sendAckOk(out);
            SshUtil.checkAcknowledgement(in);
        } finally {
            if (LOG.isDebugEnabled()) {
                long endTime = System.currentTimeMillis();
                logStats(startTime, endTime, totalLength);
            }
            fileInputStream.close();
        }
    }

}
