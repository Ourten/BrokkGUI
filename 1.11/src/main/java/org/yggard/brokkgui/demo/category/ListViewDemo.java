package org.yggard.brokkgui.demo.category;

import net.minecraft.util.text.TextFormatting;
import org.yggard.brokkgui.control.GuiTooltip;
import org.yggard.brokkgui.demo.GuiDemo;
import org.yggard.brokkgui.element.GuiButton;
import org.yggard.brokkgui.element.GuiLabel;
import org.yggard.brokkgui.element.GuiListView;
import org.yggard.brokkgui.panel.GuiRelativePane;
import org.yggard.brokkgui.wrapper.elements.MCTooltip;

import java.util.Arrays;

public class ListViewDemo extends GuiRelativePane
{
    public ListViewDemo(GuiDemo gui)
    {
        GuiListView<String> labelList = new GuiListView<>();

        labelList.setWidth(75);
        labelList.setHeight(30);

        labelList.setCellHeight(20);
        labelList.setCellWidth(75);
        labelList.setStyle("-border-color: gray; -border-thin: 1;");

        labelList.setPlaceholder(new GuiLabel("I'm a placeholder"));

        labelList.setElements(Arrays.asList("One", "Two", "Three"));

        this.addChild(labelList, 0.25f, 0.5f);

        final GuiListView<GuiButton> buttonList = new GuiListView<>();

        buttonList.setSize(75, 30);

        buttonList.setCellHeight(20);
        buttonList.setCellWidth(75);

        GuiButton button1 = new GuiButton("HEY 1");
        GuiButton button2 = new GuiButton("HELLO");
        GuiButton button3 = new GuiButton("LALALA");
        buttonList.setElements(Arrays.asList(button1, button2, button3));
        buttonList.setCellYPadding(1);

        button1.setTooltip(new GuiTooltip("This is a button"));
        button2.setTooltip(MCTooltip.build().line(TextFormatting.RED + "Another button").create());

        this.addChild(buttonList, 0.75f, 0.5f);

        GuiLabel toastLabel = new GuiLabel("You clicked on the button!");
        toastLabel.addStyleClass("toast-label");
        toastLabel.setWidth(150);
        toastLabel.setHeight(20);
        button1.setOnActionEvent(e -> gui.toastManager.addToast(toastLabel, 3000L));
    }
}
