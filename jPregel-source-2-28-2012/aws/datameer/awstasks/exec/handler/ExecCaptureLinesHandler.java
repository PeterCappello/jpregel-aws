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
package aws.datameer.awstasks.exec.handler;

import java.util.ArrayList;
import java.util.List;

import aws.datameer.awstasks.exec.ExecOutputHandler;

public class ExecCaptureLinesHandler implements ExecOutputHandler<List<String>> {

    private final List<String> _readLines = new ArrayList<String>();

    @Override
    public void handleLine(String line) {
        _readLines.add(line);
    }

    public List<String> getReadLines() {
        return _readLines;
    }

    @Override
    public List<String> getResult(int exitCode) {
        return _readLines;
    }

}
