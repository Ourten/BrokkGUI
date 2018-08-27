package org.yggard.brokkgui.wrapper.impl;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.input.Keyboard;
import org.yggard.brokkgui.BrokkGuiPlatform;
import org.yggard.brokkgui.GuiFocusManager;
import org.yggard.brokkgui.gui.BrokkGuiScreen;
import org.yggard.brokkgui.internal.IBrokkGuiImpl;
import org.yggard.brokkgui.internal.IGuiRenderer;
import org.yggard.brokkgui.internal.PopupHandler;
import org.yggard.brokkgui.paint.RenderPass;
import org.yggard.brokkgui.wrapper.GuiHelper;
import org.yggard.brokkgui.wrapper.GuiRenderer;

import java.io.IOException;

public class GuiScreenImpl extends GuiScreen implements IBrokkGuiImpl
{
    private final BrokkGuiScreen brokkgui;
    private final String         modID;

    private final GuiRenderer renderer;

    GuiScreenImpl(String modID, BrokkGuiScreen brokkgui)
    {
        this.brokkgui = brokkgui;
        this.modID = modID;
        this.renderer = new GuiRenderer(Tessellator.getInstance());
        this.brokkgui.setWrapper(this);
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    @Override
    public void initGui()
    {
        super.initGui();

        this.brokkgui.getScreenWidthProperty().setValue(this.width);
        this.brokkgui.getScreenHeightProperty().setValue(this.height);

        Keyboard.enableRepeatEvents(true);
        this.brokkgui.initGui();
    }

    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);

        this.brokkgui.onClose();
    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);

        this.brokkgui.render(mouseX, mouseY, RenderPass.BACKGROUND);
        this.brokkgui.render(mouseX, mouseY, RenderPass.MAIN);
        this.brokkgui.render(mouseX, mouseY, RenderPass.FOREGROUND);
        this.brokkgui.render(mouseX, mouseY, RenderPass.HOVER);

        this.brokkgui.render(mouseX, mouseY, GuiHelper.ITEM_MAIN);
        this.brokkgui.render(mouseX, mouseY, GuiHelper.ITEM_HOVER);

        PopupHandler.getInstance().renderPopupInPass(this.renderer, RenderPass.BACKGROUND, mouseX, mouseY);
        PopupHandler.getInstance().renderPopupInPass(this.renderer, RenderPass.MAIN, mouseX, mouseY);
        PopupHandler.getInstance().renderPopupInPass(this.renderer, RenderPass.FOREGROUND, mouseX, mouseY);
        PopupHandler.getInstance().renderPopupInPass(this.renderer, RenderPass.HOVER, mouseX, mouseY);

        PopupHandler.getInstance().renderPopupInPass(this.renderer, GuiHelper.ITEM_MAIN, mouseX, mouseY);
        PopupHandler.getInstance().renderPopupInPass(this.renderer, GuiHelper.ITEM_HOVER, mouseX, mouseY);
    }

    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int key) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, key);

        this.brokkgui.onClick(mouseX, mouseY, key);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
    {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);

        this.brokkgui.onClickDrag(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        super.mouseReleased(mouseX, mouseY, state);

        this.brokkgui.onClickStop(mouseX, mouseY, state);
    }

    @Override
    public void handleMouseInput() throws IOException
    {
        super.handleMouseInput();
        this.brokkgui.handleMouseInput();
    }

    @Override
    public void keyTyped(final char c, final int key) throws IOException
    {
        if (key == BrokkGuiPlatform.getInstance().getKeyboardUtil().getKeyCode("ESCAPE") ||
                GuiFocusManager.getInstance().getFocusedNode() == null)
            super.keyTyped(c, key);
        this.brokkgui.onKeyTyped(c, key);
    }

    @Override
    public void askClose()
    {
        this.mc.displayGuiScreen(null);
        this.mc.setIngameFocus();

        this.brokkgui.onClose();
    }

    @Override
    public void askOpen()
    {
        this.mc.displayGuiScreen(this);

        this.brokkgui.onOpen();
    }

    @Override
    public int getScreenWidth()
    {
        return this.width;
    }

    @Override
    public int getScreenHeight()
    {
        return this.height;
    }

    @Override
    public IGuiRenderer getRenderer()
    {
        return this.renderer;
    }

    @Override
    public String getThemeID()
    {
        return this.modID;
    }

    public BrokkGuiScreen getGui()
    {
        return brokkgui;
    }
}