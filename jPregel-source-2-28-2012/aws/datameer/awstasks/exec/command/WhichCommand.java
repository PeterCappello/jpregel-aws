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
package aws.datameer.awstasks.exec.command;

import aws.datameer.awstasks.exec.ShellCommand;
import aws.datameer.awstasks.exec.handler.ExecCaptureLineHandler;

/**
 * Executes the 'which' command and so determine the location of a given executable.
 * 
 */
public class WhichCommand extends ShellCommand<String> {

    public WhichCommand(String command) {
        super(new String[] { "which", command }, true);
        setDefaultHandler(new ExecCaptureLineHandler());
    }

}
