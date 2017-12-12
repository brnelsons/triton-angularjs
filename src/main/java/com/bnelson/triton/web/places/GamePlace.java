package com.bnelson.triton.web.places;

import com.bnelson.triton.api.model.GameLinkMetadata;
import com.bnelson.triton.api.model.GameMetadata;
import com.bnelson.triton.api.model.LinkMetadata;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GamePlace {
    private static final String TITLE = "title";
    private static final String ACTIVE = "active";
    private static final String LINKS = "links";
    private static final String GAME = "game";

    private static final LinkMetadata LINK_METADATA = new LinkMetadata();

    private boolean hasLinks = false;

    private ModelAndView modelAndView;

    public GamePlace(@Nonnull String viewName,
                     @Nonnull String title,
                     @Nonnull String activeAttribute) {
        this(viewName, title, activeAttribute, null);
    }

    public GamePlace(@Nonnull String viewName,
                     @Nonnull String title,
                     @Nonnull String activeAttribute,
                     @Nullable GameMetadata gameMetadata) {
        this.modelAndView = new ModelAndView(viewName);
        this.modelAndView.addObject(TITLE, title);
        this.modelAndView.addObject(activeAttribute, ACTIVE);
        addLinks(gameMetadata);
    }

    private void addLinks(@Nullable GameMetadata gameMetadata) {
        if (hasLinks) {
            throw new IllegalStateException("Already has links set.");
        }
        hasLinks = true;
        if (gameMetadata != null) {
            modelAndView.addObject(LINKS, new GameLinkMetadata(gameMetadata.getGameName(), gameMetadata.getServerName()));
            modelAndView.addObject(GAME, gameMetadata);
            modelAndView.addObject(GameLinkMetadata.GAME_NAME, gameMetadata.getGameName());
            modelAndView.addObject(GameLinkMetadata.SERVER_NAME, gameMetadata.getServerName());
        } else {
            modelAndView.addObject(LINKS, LINK_METADATA);
        }
    }

    public ModelAndView getModelAndView() {
        return modelAndView;
    }
}
