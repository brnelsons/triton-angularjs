package com.bnelson.triton.domain.data;

import com.bnelson.triton.domain.model.GameModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GameRepositoryImpl implements GameRepository {
    private static final String BASE_PATH = "C:/Users/brnel/Documents/triton/configs";//TODO inject this directory

    private final FileRepository<GameModel, GameModel> fileRepository;

    @Autowired
    public GameRepositoryImpl() {
        this.fileRepository = new FileRepository<>(
                BASE_PATH,
                new ObjectMapper(new YAMLFactory()),
                GameModel.class,
                new FileRepository.FileNameConverter<GameModel, GameModel>() {
                    @Override
                    public GameModel convert(GameModel gameModel) {
                        return gameModel;
                    }
                },
                new FileRepository.FileNameBuilder<GameModel>() {
                    @Override
                    public String Build(GameModel gameModel) {
                        return (gameModel.getGameName() + "_" + gameModel.getServerName() + ".yml")
                                .replace(" ", "_");
                    }
                });
    }

    @Override
    public List<GameModel> getAll() {
        return fileRepository.getAll();
    }

    @Override
    public boolean create(GameModel gameModel) {
        return fileRepository.save(gameModel, false);
    }

    @Override
    public boolean update(GameModel gameModel) {
        return fileRepository.save(gameModel, true);
    }

    @Override
    public GameModel getOne(String gameName, String serverName) {
        return fileRepository.getOneLike(new GameModel.Builder(gameName, serverName).build());
    }

    @Override
    public boolean delete(GameModel gameModel) {
        return fileRepository.delete(gameModel);
    }


    public static void main(String[] args) {
        GameRepositoryImpl dao = new GameRepositoryImpl();
        GameModel gameModel = new GameModel.Builder("7 Days to die", "Server 1").build();
        dao.create(gameModel);
    }
}
