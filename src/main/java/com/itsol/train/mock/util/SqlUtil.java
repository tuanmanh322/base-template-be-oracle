package com.itsol.train.mock.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class SqlUtil {
    private static final Logger log = LoggerFactory.getLogger(SqlUtil.class);

    public String getSqlQueryById(String module,
                                         String queryId) {
        File folder;
        try {
            folder = new ClassPathResource(
                    "sql" + File.separator + module + File.separator + queryId + ".sql").getFile();
            // Read file
            if (folder.isFile()) {
                String sql = new String(Files.readAllBytes(Paths.get(folder.getAbsolutePath())));
                return sql;
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return StringUtils.EMPTY;
        }
        return StringUtils.EMPTY;
    }
}
