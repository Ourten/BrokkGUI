package fr.ourten.brokkgui.skin;

import org.apache.commons.lang3.StringUtils;

import fr.ourten.brokkgui.behavior.GuiTextfieldBehavior;
import fr.ourten.brokkgui.element.GuiTextfield;
import fr.ourten.brokkgui.internal.IGuiRenderer;
import fr.ourten.brokkgui.paint.Color;
import fr.ourten.brokkgui.paint.EGuiRenderPass;
import fr.ourten.brokkgui.validation.BaseTextValidator;
import fr.ourten.teabeans.value.BaseProperty;

/**
 * @author Ourten 2 oct. 2016
 */
public class GuiTextfieldSkin<T extends GuiTextfield> extends GuiBehaviorSkinBase<T, GuiTextfieldBehavior<T>>
{
    private final BaseProperty<Color> textColorProperty;

    public GuiTextfieldSkin(final T model, final GuiTextfieldBehavior<T> behaviour)
    {
        super(model, behaviour);

        this.textColorProperty = new BaseProperty<>(Color.WHITE, "textColorProperty");
    }

    @Override
    public void render(final EGuiRenderPass pass, final IGuiRenderer renderer, final int mouseX, final int mouseY)
    {
        super.render(pass, renderer, mouseX, mouseY);

        if (pass == EGuiRenderPass.MAIN)
        {
            final Color color = !this.getModel().isValid() ? Color.RED : this.getTextColor();

            if (color != null)
            {
                final float x = this.getModel().getxPos() + this.getModel().getxTranslate();
                final float y = this.getModel().getyPos() + this.getModel().getyTranslate();
                if (!StringUtils.isEmpty(this.getModel().getPromptText())
                        && (this.getModel().isPromptTextAlwaysDisplayed()
                                || StringUtils.isEmpty(this.getModel().getText())))
                    renderer.getHelper().drawString(this.getModel().getPromptText(), x + 3, y + 3,
                            this.getModel().getzLevel(), color.shade(0.5f));
                if (this.getModel().getCursorPosition() == this.getModel().getText().length())
                    renderer.getHelper().drawColoredRect(renderer,
                            x + 4 + renderer.getHelper().getStringWidth(
                                    this.getModel().getText().substring(0, this.getModel().getCursorPosition())),
                            y + renderer.getHelper().getStringHeight() + 2, 5, 1, this.getModel().getzLevel(),
                            color.shade(0.7f));
                renderer.getHelper().drawString(this.getModel().getText(), x + 3 + 1, y + 3 + 1,
                        this.getModel().getzLevel(), color.shade(0.7f));

                if (this.getModel().getCursorPosition() != this.getModel().getText().length())
                    renderer.getHelper().drawColoredRect(renderer,
                            x + 3 + renderer.getHelper().getStringWidth(
                                    this.getModel().getText().substring(0, this.getModel().getCursorPosition())),
                            y + 2, 1, renderer.getHelper().getStringHeight() + 1, this.getModel().getzLevel(),
                            color.shade(0.3f));
                else
                    renderer.getHelper().drawColoredRect(renderer,
                            x + 3 + renderer.getHelper().getStringWidth(
                                    this.getModel().getText().substring(0, this.getModel().getCursorPosition())),
                            y + renderer.getHelper().getStringHeight() + 1, 5, 1, this.getModel().getzLevel(),
                            color.shade(0.3f));
                renderer.getHelper().drawString(this.getModel().getText(), x + 3, y + 3, this.getModel().getzLevel(),
                        color);
            }
            else if (pass == EGuiRenderPass.SPECIAL)
                if (!this.getModel().isValid())
                {
                    int i = 0;
                    for (final BaseTextValidator validator : this.getModel().getValidators())
                        if (validator.isErrored())
                        {
                            renderer.getHelper().drawString(validator.getMessage(),
                                    this.getModel().getxPos() + this.getModel().getxTranslate(),
                                    this.getModel().getyPos() + this.getModel().getyTranslate()
                                            + this.getModel().getHeight()
                                            + i * (renderer.getHelper().getStringHeight() + 1),
                                    this.getModel().getzLevel(), Color.RED);
                            i++;
                        }
                }
        }
    }

    public BaseProperty<Color> getTextColorProperty()
    {
        return this.textColorProperty;
    }

    public Color getTextColor()
    {
        return this.getTextColorProperty().getValue();
    }

    public void setTextColor(final Color textColor)
    {
        this.getTextColorProperty().setValue(textColor);
    }
}