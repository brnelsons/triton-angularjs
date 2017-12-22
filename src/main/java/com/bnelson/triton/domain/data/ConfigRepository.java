package com.bnelson.triton.domain.data;

import com.bnelson.triton.domain.model.BasicConfig;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.bnelson.triton.domain.config.DirectoryConfig.CONFIG_DIR;

@Repository
public class ConfigRepository implements BasicRepository<BasicConfig> {

    private final SingleFileRepository<BasicConfigs> baseRepository;

    @Autowired
    public ConfigRepository(@Qualifier(CONFIG_DIR) String configDir) {
        this.baseRepository = new SingleFileRepository<>(
                configDir,
                "basicConfig.yml",
                BasicConfigs.class
        );
    }

    @Nonnull
    @Override
    public List<BasicConfig> getAll() {
        BasicConfigs basicConfigs = baseRepository.get();
        if (basicConfigs != null) {
            return basicConfigs.getConfigs().entrySet().stream()
                    .map(entry -> new BasicConfig(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    @Nonnull
    public BasicConfig getByName(String configName) {
        BasicConfigs basicConfigs = baseRepository.get();
        if (basicConfigs != null) {
            return new BasicConfig(
                    configName,
                    basicConfigs.getConfigs().get(configName)
            );
        }
        return new BasicConfig(configName, null);
    }

    @Override
    public boolean update(BasicConfig basicConfig) {
        return push(basicConfig);
    }

    @Override
    public boolean create(BasicConfig basicConfig) {
        return push(basicConfig);
    }

    private boolean push(BasicConfig basicConfig) {
        BasicConfigs basicConfigs = baseRepository.get();
        if (basicConfigs == null) {
            basicConfigs = new BasicConfigs();
            basicConfigs.getConfigs().put(basicConfig.getName(), basicConfig.getValue());
            baseRepository.create(basicConfigs);
        }else {
            basicConfigs.getConfigs().put(basicConfig.getName(), basicConfig.getValue());
            baseRepository.update(basicConfigs);
        }
        return true;
    }

    @Override
    public boolean delete(BasicConfig basicConfig) {
        BasicConfigs basicConfigs = baseRepository.get();
        if (basicConfigs == null) {
            return false;
        }
        basicConfigs.getConfigs().remove(basicConfig.getName());
        baseRepository.update(basicConfigs);
        return true;
    }

    public boolean save(@Nonnull List<BasicConfig> basicConfigs) {
        BasicConfigs configs = new BasicConfigs();
        for(BasicConfig basicConfig : basicConfigs) {
            configs.getConfigs().put(basicConfig.getName(), basicConfig.getValue());
        }
        return baseRepository.update(configs);
    }

    private static class BasicConfigs {
        private Map<String, String> configs = new HashMap<>();

        public Map<String, String> getConfigs() {
            return configs;
        }

        public void setConfigs(Map<String, String> configs) {
            this.configs = configs;
        }
    }
}
