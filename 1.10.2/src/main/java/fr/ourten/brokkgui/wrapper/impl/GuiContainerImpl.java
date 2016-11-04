package fr.ourten.brokkgui.wrapper.impl;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import fr.ourten.brokkgui.internal.IBrokkGuiImpl;
import fr.ourten.brokkgui.internal.IGuiRenderer;
import fr.ourten.brokkgui.wrapper.GuiRenderer;
import fr.ourten.brokkgui.wrapper.container.BrokkGuiContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class GuiContainerImpl extends GuiContainer implements IBrokkGuiImpl
{
    private final BrokkGuiContainer<? extends Container> brokkgui;

    private final GuiRenderer                            renderer;

    public GuiContainerImpl(final BrokkGuiContainer<? extends Container> brokkGui)
    {
        super(brokkGui.getContainer());
        this.brokkgui = brokkGui;
        this.renderer = new GuiRenderer(Tessellator.getInstance());
        this.brokkgui.setWrapper(this);
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
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY)
    {
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
        if (this.mc.thePlayer != null)
            this.inventorySlots.onContainerClosed(this.mc.thePlayer);
        this.mc.displayGuiScreen(null);
        this.mc.setIngameFocus();

        this.brokkgui.onClose();
    }

    @Override
    public void askOpen()
    {
        // TODO : Container opening sync

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
    protected void handleMouseClick(final Slot slot, final int slotID, final int button, final ClickType flag)
    {
        super.handleMouseClick(slot, slotID, button, flag);

        if (slot != null)
            this.brokkgui.slotClick(slot, button);
    }
}