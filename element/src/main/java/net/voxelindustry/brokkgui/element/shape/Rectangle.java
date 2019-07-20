package net.voxelindustry.brokkgui.element.shape;

import net.voxelindustry.brokkgui.shape.RectangleShape;
import net.voxelindustry.brokkgui.shape.ShapeDefinition;

public class Rectangle extends GuiNode
{
    public static final RectangleShape SHAPE = new RectangleShape();

    public Rectangle(float xLeft, float yLeft, float width, float height)
    {
        transform().xTranslate(xLeft);
        transform().yTranslate(yLeft);

        transform().width(width);
        transform().height(height);
    }

    public Rectangle(float width, float height)
    {
        this(0, 0, width, height);
    }

    public Rectangle()
    {
        this(0, 0);
    }

    @Override
    public String type()
    {
        return "rectangle";
    }

    @Override
    public ShapeDefinition shape()
    {
        return SHAPE;
    }
}
