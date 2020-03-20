package net.voxelindustry.brokkgui.immediate.element;

import net.voxelindustry.brokkgui.immediate.style.StyleType;
import net.voxelindustry.brokkgui.immediate.style.TextStyle;
import net.voxelindustry.brokkgui.paint.Color;

import static net.voxelindustry.brokkgui.immediate.style.StyleType.NORMAL;

public interface TextElement extends ImmediateElement
{
    default void setTextStyle(TextStyle style)
    {
        setTextStyle(style, NORMAL);
    }

    default void setTextStyle(TextStyle style, StyleType type)
    {
        setStyleObject(style, type);
    }

    default boolean text(String text, float x, float y)
    {
        return text(text, x, y, NORMAL);
    }

    default boolean text(String text, float x, float y, StyleType type)
    {
        return text(text, x, y, getStyleObject(type, TextStyle.class));
    }

    default boolean text(String text, float x, float y, TextStyle style)
    {
        return text(text, x, y, style.textColor, style.shadowColor, style.hoverTextColor, style.hoverShadowColor);
    }

    default boolean text(String text, float x, float y, Color color, Color shadowColor, Color hoverColor, Color hoverShadowColor)
    {
        boolean isHovered = getMouseX() > x && getMouseY() > y && getMouseY() < y + getRenderer().getHelper().getStringHeightMultiLine(text) && getMouseX() < x + getRenderer().getHelper().getStringWidthMultiLine(text);

        if (!isHovered)
            getRenderer().getHelper().drawStringMultiline(text, x, y, 1, color, shadowColor);
        else
            getRenderer().getHelper().drawStringMultiline(text, x, y, 1, hoverColor, hoverShadowColor);

        return isHovered;
    }
}
