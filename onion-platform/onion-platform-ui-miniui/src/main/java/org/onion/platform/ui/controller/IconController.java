package org.onion.platform.ui.controller;

import org.onion.commons.file.FileUtils;
import org.onion.web.core.authorize.annotation.Authorize;
import org.onion.web.core.exception.BusinessException;
import org.onion.web.core.message.ResponseMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/icon-list")
public class IconController {

    @RequestMapping
    @Authorize
    public ResponseMessage iconList() throws IOException {
        Reader reader = FileUtils.getResourceAsReader("static/ui/plugins/miniui/themes/icons.css");
        BufferedReader br = new BufferedReader(reader);
        List<String> icons = new ArrayList<>();
        while (br.ready()) {
            String line = br.readLine();
            if (line.startsWith(".icon-")) {
                icons.add(line.substring(1));
            }
        }
        return ResponseMessage.ok(icons).onlyData();
    }
}
