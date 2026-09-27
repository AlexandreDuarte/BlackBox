package com.azkh.blackbox.ui.leaderboardscreen;

import com.azkh.blackbox.GameType;
import com.azkh.blackbox.Leaderboards;
import com.azkh.blackbox.Resources;
import com.azkh.blackbox.ScreenSelectionInterface;
import com.azkh.blackbox.data.Leaderboard;
import com.azkh.blackbox.effects.CRTBoardRenderer;
import com.azkh.blackbox.ui.element.BBText;
import com.azkh.blackbox.ui.element.Element;
import com.azkh.blackbox.ui.element.button.BBButton;
import com.azkh.blackbox.ui.element.button.BBButtonLeftArrow;
import com.azkh.blackbox.ui.element.button.BBButtonRightArrow;
import com.azkh.blackbox.ui.element.button.BBListener;
import com.azkh.blackbox.ui.element.button.BBListenerButton;
import com.azkh.blackbox.utils.ListCarousel;
import com.azkhgameservices.IGameServiceClient;
import com.azkhgameservices.IGameServiceClient.OnlineScoreEntry;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

public class LeaderboardUI implements Element, BBListener, InputProcessor {

    public enum LeaderboardViews {
        fourfour("4x4"), fivefive("5x5"), sixsix("6x6");

        final String title;

        LeaderboardViews(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }

    public enum ScoreSource {
        LOCAL("LOCAL"),
        ONLINE("ONLINE");

        final String title;

        ScoreSource(String title) {
            this.title = title;
        }
    }

    ScreenSelectionInterface screenSelectionInterface;
    List<BBButton> buttons;

    public static final int SIZE_LEFT_BUTTON = 0;
    public static final int SIZE_RIGHT_BUTTON = 1;
    public static final int MODE_LEFT_BUTTON = 2;
    public static final int MODE_RIGHT_BUTTON = 3;
    public static final int SOURCE_BUTTON = 4;
    public static final int BACK_BUTTON = 5;

    LeaderboardViews currentView = LeaderboardViews.fourfour;
    GameType.Mode currentGameMode = GameType.Mode.DEFAULT;
    ScoreSource scoreSource = ScoreSource.LOCAL;

    BBText mainTitleText;
    BBText modeTitleText;
    BBText categoryTitleText;
    BBListenerButton sourceButton;

    private final GlyphLayout tableHeaderNameLayout;
    private final GlyphLayout timeHeaderLayout;
    private final GlyphLayout loadingLayout;
    private final GlyphLayout noScoresLayout;
    private final GlyphLayout errLayout;
    private final GlyphLayout rankLayout;
    private final GlyphLayout nameLayout;
    private final GlyphLayout timeLayout;
    private final GlyphLayout userLayout;

    ListIterator<LeaderboardViews> sizeCarousel = (new ListCarousel<>(currentView)).iterator();
    ListIterator<GameType.Mode> modeCarousel = (new ListCarousel<>(currentGameMode)).iterator();

    List<OnlineScoreEntry> displayScores = new ArrayList<>();
    OnlineScoreEntry userBestScore = null;
    boolean isLoading = false;
    String errorMessage = null;

    float scoreds_display = 0.0f;

    CRTBoardRenderer crtRenderer;
    public LeaderboardUI(ScreenSelectionInterface screenSelectionInterface) {
        this.screenSelectionInterface = screenSelectionInterface;

        crtRenderer = new CRTBoardRenderer();

        tableHeaderNameLayout = new GlyphLayout(Resources.textFont, "# NAME / DATE");
        timeHeaderLayout = new GlyphLayout(Resources.textFont, "TIME");
        loadingLayout = new GlyphLayout(Resources.textFont, "LOADING SCORES...");
        noScoresLayout = new GlyphLayout(Resources.textFont, "NO RECORDED SCORES");
        errLayout = new GlyphLayout();
        rankLayout = new GlyphLayout();
        nameLayout = new GlyphLayout();
        timeLayout = new GlyphLayout();
        userLayout = new GlyphLayout();

        float topInset = Gdx.graphics.getSafeInsetTop();
        float H = Gdx.graphics.getHeight();
        float W = Gdx.graphics.getWidth();

        float headerY = H - topInset;
        float sourceY = 210;

        BBButton backButton = new BBListenerButton(BACK_BUTTON, 50, headerY, "BACK", Resources.textFont, this, Align.left, Align.top);

        headerY = headerY - backButton.height() - backButton.height()/2;

        mainTitleText = new BBText("LEADERBOARD", Resources.menuFont, W / 2.0f, headerY, Resources.titleColor, Align.center, Align.top);

        float modeY = headerY - mainTitleText.getHeight() - 150;

        modeTitleText = new BBText(currentGameMode.getDisplayName(), Resources.textFont, W / 2.0f, modeY, Resources.titleColor, Align.center);

        buttons = new ArrayList<>();
        BBButtonLeftArrow modeLeft = new BBButtonLeftArrow(MODE_LEFT_BUTTON, W / 6f, modeY, this);
        BBButtonRightArrow modeRight = new BBButtonRightArrow(MODE_RIGHT_BUTTON, 5 * W / 6f, modeY, this);

        float sizeY = modeY - modeLeft.height() - 110;

        BBButtonLeftArrow sizeLeft = new BBButtonLeftArrow(SIZE_LEFT_BUTTON, W / 6f, sizeY, this);
        BBButtonRightArrow sizeRight = new BBButtonRightArrow(SIZE_RIGHT_BUTTON, 5 * W / 6f, sizeY, this);

        scoreds_display = sizeY - sizeLeft.height() - 110;

        categoryTitleText = new BBText(currentView.getTitle(), Resources.textFont, W / 2.0f, sizeY, Resources.titleColor, Align.center);

        sourceButton = new BBListenerButton(SOURCE_BUTTON, W / 2.0f, sourceY, "SOURCE: " + scoreSource.title, Resources.textFont, this, Align.center, Align.center);

        buttons.add(modeLeft);
        buttons.add(modeRight);
        buttons.add(sizeLeft);
        buttons.add(sizeRight);
        buttons.add(sourceButton);
        buttons.add(backButton);

        loadScores();
    }

    private Leaderboard getLeaderboardForViewAndMode(LeaderboardViews view, GameType.Mode mode) {
        if (mode == GameType.Mode.DYNAMIC) {
            switch (view) {
                case fourfour:
                    return Leaderboards.leaderboard4_dynamic;
                case fivefive:
                    return Leaderboards.leaderboard5_dynamic;
                case sixsix:
                    return Leaderboards.leaderboard6_dynamic;
                default:
                    return Leaderboards.leaderboard4_dynamic;
            }
        } else {
            switch (view) {
                case fourfour:
                    return Leaderboards.leaderboard4;
                case fivefive:
                    return Leaderboards.leaderboard5;
                case sixsix:
                    return Leaderboards.leaderboard6;
                default:
                    return Leaderboards.leaderboard4;
            }
        }
    }

    private void loadScores() {
        displayScores.clear();
        userBestScore = null;
        errorMessage = null;

        if (scoreSource == ScoreSource.LOCAL) {
            loadLocalScores();
        } else {
            loadOnlineScores();
        }
    }

    private void loadLocalScores() {
        Leaderboard target = getLeaderboardForViewAndMode(currentView, currentGameMode);
        FileHandle file = Gdx.files.local("local_leaderboard_" + target.getName() + ".dat");
        List<OnlineScoreEntry> entries = new ArrayList<>();
        if (file.exists()) {
            String text = file.readString();
            String[] lines = text.split("\n");
            for (String rawLine : lines) {
                String line = rawLine.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    try {
                        String date = parts[0].trim();
                        long time = Long.parseLong(parts[1].trim());
                        entries.add(new OnlineScoreEntry("", date, time));
                    } catch (Exception ignored) {
                    }
                }
            }
            Collections.sort(entries, (a, b) -> Long.compare(a.time, b.time));
        }

        for (int i = 0; i < entries.size(); i++) {
            OnlineScoreEntry raw = entries.get(i);
            OnlineScoreEntry formatted = new OnlineScoreEntry("#" + (i + 1), raw.holderName, raw.time);
            if (i < 5) {
                displayScores.add(formatted);
            }
            if (i == 0) {
                userBestScore = formatted;
            }
        }
    }

    private void loadOnlineScores() {
        if (!Resources.gsClient.isAuthenticated()) {
            errorMessage = "NOT SIGNED IN - TAP SOURCE TO SIGN IN";
            errLayout.setText(Resources.textFont, errorMessage);
            return;
        }

        isLoading = true;
        Leaderboard targetLeaderboard = getLeaderboardForViewAndMode(currentView, currentGameMode);

        Resources.gsClient.fetchLeaderboardScores(targetLeaderboard, new IGameServiceClient.LeaderboardCallback() {
            @Override
            public void onScoresLoaded(List<OnlineScoreEntry> topScores, OnlineScoreEntry userScore) {
                Gdx.app.postRunnable(() -> {
                    isLoading = false;
                    displayScores.clear();
                    if (topScores != null) {
                        displayScores.addAll(topScores);
                    }
                    userBestScore = userScore;
                    if (userBestScore != null) {
                        String userStr = "YOUR BEST: " + userBestScore.rank + "  -  " + formatTime(userBestScore.time);
                        userLayout.setText(Resources.scoreFont, userStr);
                    }
                });
            }

            @Override
            public void onFailed() {
                Gdx.app.postRunnable(() -> {
                    isLoading = false;
                    errorMessage = "FAILED TO LOAD ONLINE SCORES";
                    errLayout.setText(Resources.textFont, errorMessage);
                });
            }
        });
    }

    @Override
    public void onClick(BBButton flb) {
        switch (flb.getId()) {
            case SIZE_LEFT_BUTTON:
                currentView = sizeCarousel.previous();
                categoryTitleText.setText(currentView.getTitle());
                loadScores();
                break;
            case SIZE_RIGHT_BUTTON:
                currentView = sizeCarousel.next();
                categoryTitleText.setText(currentView.getTitle());
                loadScores();
                break;
            case MODE_LEFT_BUTTON:
                currentGameMode = modeCarousel.previous();
                modeTitleText.setText(currentGameMode.getDisplayName());
                loadScores();
                break;
            case MODE_RIGHT_BUTTON:
                currentGameMode = modeCarousel.next();
                modeTitleText.setText(currentGameMode.getDisplayName());
                loadScores();
                break;
            case SOURCE_BUTTON:
                if (scoreSource == ScoreSource.LOCAL) {
                    scoreSource = ScoreSource.ONLINE;
                    if (!Resources.gsClient.isAuthenticated()) {
                        Resources.gsClient.auth();
                    }
                } else {
                    scoreSource = ScoreSource.LOCAL;
                }
                sourceButton.setText("SOURCE: " + scoreSource.title);
                loadScores();
                break;
            case BACK_BUTTON:
                screenSelectionInterface.setNextScreen(Resources.game.getMainMenuScreen());
                break;
        }
    }

    @Override
    public void render() {
        float W = Gdx.graphics.getWidth();

        float panelTop = scoreds_display;
        float panelWidth = W * 0.88f;
        float panelHeight = scoreds_display - 300;
        float panelLeft = (W - panelWidth) / 2f;

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        ShapeRenderer shapeRenderer = Resources.game.getShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Draw buttons shape layer (arrow buttons)
        for (BBButton b : buttons) {
            if (!b.isHidden()) {
                b.draw(shapeRenderer);
            }
        }
        shapeRenderer.end();

        SpriteBatch batch = Resources.game.getBatch();
        batch.begin();
        batch.setColor(1.0f, 1.0f, 1.0f, Resources.screenAlpha);
        mainTitleText.draw(batch);
        modeTitleText.draw(batch);
        categoryTitleText.draw(batch);
        for (BBButton b : buttons) {
            if (!b.isHidden()) {
                b.draw(batch);
            }
        }
        batch.end();

        if (crtRenderer != null) {
            crtRenderer.begin();
        }

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // 1. Draw Table Panel Background Card
        shapeRenderer.setColor(Resources.boardInactive.r, Resources.boardInactive.g, Resources.boardInactive.b, 0.45f * Resources.screenAlpha);
        shapeRenderer.rect(panelLeft, panelTop - panelHeight, panelWidth, panelHeight);

        shapeRenderer.end();

        // 2. Draw Table Border & Divider Lines
        Gdx.gl.glLineWidth(2f);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Resources.pastelBlueOutline.r, Resources.pastelBlueOutline.g, Resources.pastelBlueOutline.b, 0.35f * Resources.screenAlpha);
        shapeRenderer.rect(panelLeft, panelTop - panelHeight, panelWidth, panelHeight);

        // Divider line under table header
        float headerLineY = panelTop - Resources.textFont.getLineHeight();
        shapeRenderer.line(panelLeft + 15, headerLineY, panelLeft + panelWidth - 15, headerLineY);
        shapeRenderer.end();

        // 3. Render SpriteBatch Text Content


        // Screen Main Title Header & Carousels (BBText)
        batch.begin();

        // Table Column Headers
        Resources.textFont.setColor(Resources.pastelBlueOutline.r, Resources.pastelBlueOutline.g, Resources.pastelBlueOutline.b, Resources.pastelBlueOutline.a * Resources.screenAlpha);
        float tableHeaderY = panelTop - 12;
        tableHeaderNameLayout.setText(Resources.textFont, "# NAME / DATE");
        timeHeaderLayout.setText(Resources.textFont, "TIME");
        Resources.textFont.draw(batch, tableHeaderNameLayout, panelLeft + 20, tableHeaderY);
        Resources.textFont.draw(batch, timeHeaderLayout, panelLeft + panelWidth - 20 - timeHeaderLayout.width, tableHeaderY);

        // Table Rows Content
        float startY = panelTop - Resources.textFont.getLineHeight() - Resources.scoreFont.getLineHeight();

        if (isLoading) {
            Resources.textFont.setColor(Resources.titleColor.r, Resources.titleColor.g, Resources.titleColor.b, Resources.titleColor.a * Resources.screenAlpha);
            Resources.textFont.draw(batch, loadingLayout, W / 2.0f - loadingLayout.width / 2f, startY - 30);
        } else if (errorMessage != null) {
            Resources.textFont.setColor(Resources.beamColor1.r, Resources.beamColor1.g, Resources.beamColor1.b, Resources.beamColor1.a * Resources.screenAlpha);
            Resources.textFont.draw(batch, errLayout, W / 2.0f - errLayout.width / 2f, startY - 30);
        } else if (displayScores.isEmpty()) {
            Resources.textFont.setColor(Resources.titleColor.r, Resources.titleColor.g, Resources.titleColor.b, Resources.titleColor.a * Resources.screenAlpha);
            Resources.textFont.draw(batch, noScoresLayout, W / 2.0f - noScoresLayout.width / 2f, startY - 30);
        } else {
            int displayCount = Math.min(5, displayScores.size());
            for (int i = 0; i < displayCount; i++) {
                OnlineScoreEntry entry = displayScores.get(i);
                String rankStr = entry.rank;
                String timeStr = formatTime(entry.time);
                String nameStr = entry.holderName;

                float y = startY - (i * Resources.scoreFont.getLineHeight());

                // Highlight top 1 rank
                if (i == 0) {
                    Resources.scoreFont.setColor(Resources.beamColor1.r, Resources.beamColor1.g, Resources.beamColor1.b, Resources.beamColor1.a * Resources.screenAlpha);
                } else {
                    Resources.scoreFont.setColor(Resources.titleColor.r, Resources.titleColor.g, Resources.titleColor.b, Resources.titleColor.a * Resources.screenAlpha);
                }

                rankLayout.setText(Resources.scoreFont, rankStr);
                nameLayout.setText(Resources.scoreFont, nameStr);
                timeLayout.setText(Resources.scoreFont, timeStr);

                Resources.scoreFont.draw(batch, rankLayout, panelLeft + 20, y);
                Resources.scoreFont.draw(batch, nameLayout, panelLeft + 80, y);
                Resources.scoreFont.draw(batch, timeLayout, panelLeft + panelWidth - 20 - timeLayout.width, y);
            }
        }

        // Render User Best / User Only Score at bottom
        if (userBestScore != null) {
            float userY = panelTop - panelHeight + Resources.scoreFont.getLineHeight();
            Resources.scoreFont.setColor(Resources.pastelBlueOutline.r, Resources.pastelBlueOutline.g, Resources.pastelBlueOutline.b, Resources.pastelBlueOutline.a * Resources.screenAlpha);
            String userStr = "YOUR BEST: " + userBestScore.rank + "  -  " + formatTime(userBestScore.time);
            userLayout.setText(Resources.scoreFont, userStr);
            Resources.scoreFont.draw(batch, userLayout, W / 2.0f - userLayout.width / 2.0f, userY);
        }

        batch.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        if (crtRenderer != null) {
            crtRenderer.endAndDraw();
        }
    }

    public static String formatTime(long time) {
        long millis = time % 100;
        long seconds = (time / 100) % 60;
        long minutes = (time / 6000) % 60;
        return String.format(Locale.UK, "%02d:%02d.%02d", minutes, seconds, millis);
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        float pointerX = screenX;
        float pointerY = Gdx.graphics.getHeight() - screenY;
        for (BBButton b : buttons) {
            if (b.contains(pointerX, pointerY) && !b.isHidden()) {
                b.setSelected(true);
            }
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        float pointerX = screenX;
        float pointerY = Gdx.graphics.getHeight() - screenY;
        for (BBButton b : buttons) {
            if (b.contains(pointerX, pointerY) && b.getSelected()) {
                b.execute();
            }
            b.setSelected(false);
        }
        return true;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
