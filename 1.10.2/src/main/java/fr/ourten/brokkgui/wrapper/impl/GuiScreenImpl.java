package fr.ourten.brokkgui.wrapper.impl;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import fr.ourten.brokkgui.gui.BrokkGuiScreen;
import fr.ourten.brokkgui.internal.IBrokkGuiImpl;
import fr.ourten.brokkgui.internal.IGuiRenderer;
import fr.ourten.brokkgui.wrapper.GuiRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;

public class GuiScreenImpl extends GuiScreen implements IBrokkGuiImpl
{
    private final BrokkGuiScreen brokkgui;

    private final GuiRenderer    renderer;

    public GuiScreenImpl(final BrokkGuiScreen brokkgui)
    {
        this.brokkgui = brokkgui;
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

        this.brokkgui.render(mouseX, mouseY, partialTicks);
    }

    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int key) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, key);

        this.brokkgui.onClick(mouseX, mouseY, key);
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
}