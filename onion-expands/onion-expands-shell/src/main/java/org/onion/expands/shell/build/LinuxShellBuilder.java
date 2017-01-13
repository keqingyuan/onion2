package org.onion.expands.shell.build;

import org.onion.expands.shell.Shell;

/**
 * Created by zhouhao on 16-6-28.
 */
public class LinuxShellBuilder extends AbstractShellBuilder {

    @Override
    public Shell buildTextShell(String text) throws Exception {
        if (!text.startsWith("#!")) {
            text = "#!/usr/bin/env bash\n" + text;
        }
        String file = createFile(text);

        return Shell.build("bash", file);
    }

}
