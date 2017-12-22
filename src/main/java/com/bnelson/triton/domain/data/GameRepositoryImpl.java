package com.bnelson.triton.domain.data;

import com.bnelson.triton.domain.config.DirectoryConfig;
import com.bnelson.triton.domain.model.GameModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GameRepositoryImpl implements GameRepository {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper(new YAMLFactory());

    private final FileRepository<GameModel, GameModel> fileRepository;

    @Autowired
    public GameRepositoryImpl(@Qualifier(DirectoryConfig.GAME_DIR) String configDir) {
        this.fileRepository = new FileRepository<>(
                configDir,
                OBJECT_MAPPER,
                GameModel.class,
                gameModel -> gameModel,
                GameRepositoryImpl::buildNameFromGameModel);
    }

    @Override
    public GameModel getOne(String gameName, String serverName) {
        return fileRepository.getOneLike(new GameModel.Builder(gameName, serverName).build());
    }

    @Override
    public List<GameModel> getAll() {
        return fileRepository.getAll();
    }

    @Override
    public boolean create(GameModel gameModel) {
        return fileRepository.create(gameModel);
    }

    @Override
    public boolean update(GameModel gameModel) {
        return fileRepository.update(gameModel);
    }

    @Override
    public boolean delete(GameModel gameModel) {
        return fileRepository.delete(gameModel);
    }

    private static String buildNameFromGameModel(GameModel gameModel) {
        return (gameModel.getGameName() + "_" + gameModel.getServerName() + ".yml")
                .replace(" ", "_");
    }

    public static void main(String[] args) {
        GameRepositoryImpl dao = new GameRepositoryImpl(System.getProperty("user.home") + "\\Documents\\triton\\config");
        GameModel gameModel = new GameModel.Builder("7 Days to die", "Server 1").build();
        dao.create(gameModel);
    }
}
