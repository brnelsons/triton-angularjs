package com.bnelson.triton.domain.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class DaoUtil {
    public static final ObjectMapper YAML_FILE_MAPPER = new ObjectMapper(new YAMLFactory());
}
