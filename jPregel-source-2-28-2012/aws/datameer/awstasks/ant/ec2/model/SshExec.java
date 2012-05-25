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
package aws.datameer.awstasks.ant.ec2.model;

import java.io.File;

public class SshExec extends SshCommand {

    private String _command;
    private File _commandFile;
    private String _outputProperty;

    public String getCommand() {
        return _command;
    }

    public void setCommand(String command) {
        _command = command;
    }

    public File getCommandFile() {
        return _commandFile;
    }

    public void setCommandFile(File commandFile) {
        _commandFile = commandFile;
    }

    public void setOutputProperty(String outputProperty) {
        _outputProperty = outputProperty;
    }

    public String getOutputProperty() {
        return _outputProperty;
    }

    @Override
    public String toString() {
        return _command;
    }

}
