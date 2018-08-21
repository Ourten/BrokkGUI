package org.yggard.brokkgui.element;

import fr.ourten.teabeans.binding.BaseBinding;
import fr.ourten.teabeans.value.BaseListProperty;
import fr.ourten.teabeans.value.BaseProperty;
import org.yggard.brokkgui.BrokkGuiPlatform;
import org.yggard.brokkgui.behavior.GuiTextfieldBehavior;
import org.yggard.brokkgui.behavior.ITextInput;
import org.yggard.brokkgui.control.GuiControl;
import org.yggard.brokkgui.data.RectOffset;
import org.yggard.brokkgui.event.CursorMoveEvent;
import org.yggard.brokkgui.event.TextTypedEvent;
import org.yggard.brokkgui.skin.GuiSkinBase;
import org.yggard.brokkgui.skin.GuiTextfieldSkin;
import org.yggard.brokkgui.validation.BaseTextValidator;
import org.yggard.hermod.EventHandler;

import java.util.List;

/**
 * @author Ourten 2 oct. 2016
 */
public class GuiTextfield extends GuiControl implements ITextInput
{
    private final BaseProperty<String> textProperty, promptTextProperty, promptEllipsisProperty;

    private final BaseProperty<Boolean> promptTextAlwaysDisplayedProperty, editableProperty,
            validatedProperty;
    private final BaseProperty<Boolean> expandToTextProperty;

    private final BaseProperty<Integer>    maxTextLengthProperty;
    private final BaseProperty<Integer>    cursorPosProperty;
    private final BaseProperty<RectOffset> textPaddingProperty;

    private final BaseListProperty<BaseTextValidator> validatorsProperty;

    private EventHandler<TextTypedEvent>  onTextTyped;
    private EventHandler<CursorMoveEvent> onCursorMoveEvent;

    public GuiTextfield(final String text)
    {
        super("textfield");

        this.textProperty = new BaseProperty<>(text, "textProperty");
        this.promptTextProperty = new BaseProperty<>("", "promptTextProperty");
        this.promptEllipsisProperty = new BaseProperty<>("...", "promptEllipsisProperty");

        this.maxTextLengthProperty = new BaseProperty<>(-1, "maxTextLength");
        this.cursorPosProperty = new BaseProperty<>(0, "cursorPosProperty");
        this.textPaddingProperty = new BaseProperty<>(new RectOffset(0, 1, 0, 1), "textPaddingProperty");

        this.promptTextAlwaysDisplayedProperty = new BaseProperty<>(false, "promptTextAlwaysDisplayedProperty");
        this.editableProperty = new BaseProperty<>(true, "editableProperty");
        this.validatedProperty = new BaseProperty<>(true, "validatedProperty");

        this.expandToTextProperty = new BaseProperty<>(false, "expandToTextProperty");

        this.validatorsProperty = new BaseListProperty<>(null, "validatorsProperty");

        this.setFocusable(true);
    }

    public GuiTextfield()
    {
        this("");
    }

    @Override
    protected GuiSkinBase<?> makeDefaultSkin()
    {
        return new GuiTextfieldSkin<>(this, new GuiTextfieldBehavior<>(this));
    }

    public BaseProperty<String> getTextProperty()
    {
        return this.textProperty;
    }

    public BaseProperty<String> getPrompTextProperty()
    {
        return this.promptTextProperty;
    }

    public BaseProperty<Boolean> getEditableProperty()
    {
        return this.editableProperty;
    }

    public BaseListProperty<BaseTextValidator> getValidatorsProperty()
    {
        return this.validatorsProperty;
    }

    public BaseProperty<Boolean> getValidatedProperty()
    {
        return this.validatedProperty;
    }

    public BaseProperty<Boolean> getPromptTextAlwaysDisplayedProperty()
    {
        return this.promptTextAlwaysDisplayedProperty;
    }

    public BaseProperty<Integer> getMaxTextLengthProperty()
    {
        return this.maxTextLengthProperty;
    }

    public BaseProperty<Integer> getCursorPosProperty()
    {
        return this.cursorPosProperty;
    }

    public BaseProperty<RectOffset> getTextPaddingProperty()
    {
        return this.textPaddingProperty;
    }

    public BaseProperty<String> getPromptEllipsisProperty()
    {
        return this.promptEllipsisProperty;
    }

    public BaseProperty<Boolean> getExpandToTextProperty()
    {
        return expandToTextProperty;
    }

    @Override
    public String getText()
    {
        return this.getTextProperty().getValue();
    }

    @Override
    public void setText(final String text)
    {
        this.getTextProperty().setValue(text);
    }

    public String getPromptText()
    {
        return this.getPrompTextProperty().getValue();
    }

    public void setPromptText(final String text)
    {
        this.getPrompTextProperty().setValue(text);
    }

    @Override
    public boolean isEditable()
    {
        return this.getEditableProperty().getValue();
    }

    @Override
    public void setEditable(final boolean editable)
    {
        this.getEditableProperty().setValue(editable);
    }

    /**
     * @return an immutable list
     */
    public List<BaseTextValidator> getValidators()
    {
        return this.getValidatorsProperty().getValue();
    }

    public void addValidator(final BaseTextValidator validator)
    {
        this.getValidatorsProperty().add(validator);
    }

    public void removeValidator(final BaseTextValidator validator)
    {
        this.getValidatorsProperty().remove(validator);
    }

    @Override
    public void validate()
    {
        this.setValid(true);
        if (!this.getValidators().isEmpty())
            this.getValidators().forEach(validator ->
            {
                validator.setErrored(false);
                validator.validate(this.getText());
                if (validator.isErrored())
                    this.setValid(false);
            });
    }

    public boolean isValid()
    {
        return this.getValidatedProperty().getValue();
    }

    public void setValid(final boolean valid)
    {
        this.getValidatedProperty().setValue(valid);
    }

    public int getMaxTextLength()
    {
        return this.getMaxTextLengthProperty().getValue();
    }

    /**
     * Set the maximum text length this Textfield will be able to contain. Setting
     * this value to -1 will allow infinite.
     *
     * @param length
     */
    public void setMaxTextLength(final int length)
    {
        this.getMaxTextLengthProperty().setValue(length);
    }

    public int getCursorPos()
    {
        return this.getCursorPosProperty().getValue();
    }

    public void setCursorPos(final int cursorPos)
    {
        this.getCursorPosProperty().setValue(cursorPos);
    }

    public boolean isPromptTextAlwaysDisplayed()
    {
        return this.getPromptTextAlwaysDisplayedProperty().getValue();
    }

    public void setPromptTextAlwaysDisplayed(final boolean always)
    {
        this.getPromptTextAlwaysDisplayedProperty().setValue(always);
    }

    public void setPromptEllipsis(String ellipsis)
    {
        this.getPromptEllipsisProperty().setValue(ellipsis);
    }

    public String getPromptEllipsis()
    {
        return this.getPromptEllipsisProperty().getValue();
    }

    public void setTextPadding(RectOffset padding)
    {
        this.getTextPaddingProperty().setValue(padding);
    }

    public RectOffset getTextPadding()
    {
        return this.getTextPaddingProperty().getValue();
    }

    public boolean expandToText()
    {
        return this.expandToTextProperty.getValue();
    }

    public void setExpandToText(boolean expandToText)
    {
        if (expandToText && !this.expandToText())
            this.bindSizeToText();
        else if (!expandToText && this.expandToText())
            this.getWidthProperty().unbind();
        this.expandToTextProperty.setValue(expandToText);
    }

    private void bindSizeToText()
    {
        this.getWidthProperty().bind(new BaseBinding<Float>()
        {
            {
                super.bind(getTextProperty(),
                        getTextPaddingProperty(), getHeightProperty());
            }

            @Override
            public Float computeValue()
            {
                return Math.max(getHeight(),
                        Math.max(BrokkGuiPlatform.getInstance().getGuiHelper().getStringWidth(getPromptText()),
                        BrokkGuiPlatform.getInstance().getGuiHelper().getStringWidth(getText())) +
                        getTextPadding().getLeft() + getTextPadding().getRight());
            }
        });
    }

    ////////////
    // EVENTS //
    ////////////

    public EventHandler<TextTypedEvent> getOnTextTyped()
    {
        return this.onTextTyped;
    }

    public void setOnTextTyped(final EventHandler<TextTypedEvent> onTextTyped)
    {
        this.onTextTyped = onTextTyped;
    }

    public EventHandler<CursorMoveEvent> getOnCursorMoveEvent()
    {
        return this.onCursorMoveEvent;
    }

    public void setOnCursorMoveEvent(final EventHandler<CursorMoveEvent> onCursorMoveEvent)
    {
        this.onCursorMoveEvent = onCursorMoveEvent;
    }
}