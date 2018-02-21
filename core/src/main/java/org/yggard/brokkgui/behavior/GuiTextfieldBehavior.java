package org.yggard.brokkgui.behavior;

import java.util.regex.Pattern;

import org.yggard.brokkgui.BrokkGuiPlatform;
import org.yggard.brokkgui.element.GuiTextfield;
import org.yggard.brokkgui.event.CursorMoveEvent;
import org.yggard.brokkgui.event.KeyEvent;
import org.yggard.brokkgui.event.TextTypedEvent;
import org.yggard.brokkgui.internal.IKeyboardUtil;

/**
 * @author Ourten 2 oct. 2016
 */
public class GuiTextfieldBehavior<T extends GuiTextfield> extends GuiBehaviorBase<T>
{
    
    static final Pattern ALPHA_NUM_REGEX = Pattern.compile("[a-zA-Z0-9]");
    
    private final IKeyboardUtil keyboard = BrokkGuiPlatform.getInstance().getKeyboardUtil();

    public GuiTextfieldBehavior(final T model)
    {
        super(model);

        this.getModel().getEventDispatcher().addHandler(KeyEvent.TYPE, this::onKeyTyped);
    }

    public void onKeyTyped(final KeyEvent event)
    {
        final String oldText = this.getModel().getText();
        boolean contentChanged = false;
        if (event.getKey() == this.keyboard.getKeyCode("DELETE"))
        {
            if (this.getModel().isEditable())
            {
                if(keyboard.isCtrlKeyDown())
                    contentChanged = this.deleteWordAfterCursor();
                else
                    contentChanged = this.deleteAfterCursor();
            }
        }
        else if (event.getKey() == this.keyboard.getKeyCode("BACK"))
        {
            if (this.getModel().isEditable())
            {
                if(keyboard.isCtrlKeyDown())
                    contentChanged = this.deleteWordBeforeCursor();
                else
                    contentChanged = this.deleteFromCursor();
            }
        }
        else if (event.getKey() == this.keyboard.getKeyCode("LEFT"))
            if(keyboard.isCtrlKeyDown())
                this.setCursorPosition(this.previousWordPosition());
            else
                this.setCursorPosition(this.getModel().getCursorPosition() - 1);
        else if (event.getKey() == this.keyboard.getKeyCode("RIGHT"))
            if(keyboard.isCtrlKeyDown())
                this.setCursorPosition(this.nextWordPosition());
            else
                this.setCursorPosition(this.getModel().getCursorPosition() + 1);
        else if (event.getKey() == this.keyboard.getKeyCode("V")
                && BrokkGuiPlatform.getInstance().getKeyboardUtil().isCtrlKeyDown())
        {
            if (this.getModel().isEditable())
            {
                this.appendTextToCursor(BrokkGuiPlatform.getInstance().getKeyboardUtil().getClipboardString());
                contentChanged = true;
            }
        }
        else if (this.getModel().isEditable()
                && BrokkGuiPlatform.getInstance().getKeyboardUtil().isKeyValidChar(event.getKey()))
        {
            this.appendTextToCursor(String.valueOf(event.getCharacter()));
            contentChanged = true;
        }
        if (this.getModel().getOnTextTyped() != null && contentChanged)
            this.getModel().getEventDispatcher().dispatchEvent(TextTypedEvent.TYPE,
                    new TextTypedEvent(this.getModel(), oldText, this.getModel().getText()));
    }

    /**
     * Delete a character from the current cursor position
     *
     * @return if a change to the contained text happened.
     */
    protected boolean deleteFromCursor()
    {
        if (this.getModel().getCursorPosition() == 0 || this.getModel().getText().length() == 0)
            return false;
        String result = this.getModel().getText();
        final String temp = this.getModel().getText().length() > this.getModel().getCursorPosition() ? this.getModel()
                .getText().substring(this.getModel().getCursorPosition(), this.getModel().getText().length()) : "";

        result = result.substring(0, this.getModel().getCursorPosition() - 1);
        result += temp;
        this.getModel().setText(result);
        this.setCursorPosition(this.getModel().getCursorPosition() - 1);
        return true;
    }

    /**
     * Delete a character after the current cursor position
     *
     * @return if a change to the contained text happened.
     */
    protected boolean deleteAfterCursor()
    {
        if (this.getModel().getCursorPosition() == this.getModel().getText().length()
                || this.getModel().getText().length() == 0)
            return false;
        String result = this.getModel().getText();
        final String temp = this.getModel().getText().substring(this.getModel().getCursorPosition() + 1,
                this.getModel().getText().length());

        result = result.substring(0, this.getModel().getCursorPosition());
        result += temp;
        this.getModel().setText(result);
        return true;
    }

    protected void appendTextToCursor(final String toAppend)
    {
        if (this.getModel().getMaxTextLength() >= 0
                && this.getModel().getText().length() + toAppend.length() <= this.getModel().getMaxTextLength())
            toAppend.substring(0, this.getModel().getText().length() - this.getModel().getMaxTextLength() < 0 ? 0
                    : this.getModel().getText().length() - this.getModel().getMaxTextLength());
        if (toAppend != "")
        {
            String result = this.getModel().getText();
            final String temp = this.getModel().getText().substring(this.getModel().getCursorPosition(),
                    this.getModel().getText().length());

            result = result.substring(0, this.getModel().getCursorPosition());
            result += toAppend;
            result += temp;
            this.getModel().setText(result);
            this.setCursorPosition(this.getModel().getCursorPosition() + toAppend.length());
        }
    }

    public void setCursorPosition(final int cursorPosition)
    {
        if (cursorPosition >= 0 && cursorPosition <= this.getModel().getText().length())
        {
            if (this.getModel().getOnCursorMoveEvent() != null && this.getModel().getCursorPosition() != cursorPosition)
                this.getModel().getEventDispatcher().dispatchEvent(CursorMoveEvent.TYPE,
                        new CursorMoveEvent(this.getModel(), this.getModel().getCursorPosition(), cursorPosition));
            this.getModel().setCursorPosition(cursorPosition);
        }
    }
    
    protected int previousWordPosition() 
    {
        String textBeforeCursor = getModel().getText().substring(0, getModel().getCursorPosition());
        if(textBeforeCursor.isEmpty())
            return 0;
        int pos = getModel().getCursorPosition();
        
        boolean foundCharacter = false;
        while(pos > 0) 
        {
            String charAtPos = textBeforeCursor.charAt(pos - 1) + "";
            if(ALPHA_NUM_REGEX.matcher(charAtPos).matches()) 
            {
                foundCharacter = true;
                pos--;
            }
            else if(charAtPos.equals(" ")) 
            {
                if(foundCharacter)
                    break;
                else
                    pos--;
            }
            else if(pos == getModel().getCursorPosition()) 
            {
                pos--;
                break;
            } 
            else 
            {
                pos--;
            }
        }
        
        return pos;
    }
    
    protected int nextWordPosition() 
    {
        if(getModel().getCursorPosition() == getModel().getText().length())
            return getModel().getCursorPosition();
        String text = getModel().getText();
        int pos = getModel().getCursorPosition();
        boolean foundSpace = false;
        while(pos < getModel().getText().length())
        {
            String charAtPos = text.charAt(pos) + "";
            if(charAtPos.equals(" ")) 
            {
                foundSpace = true;
                pos++;
            }
            else if(foundSpace) 
            {
                break;
            } 
            else if(!ALPHA_NUM_REGEX.matcher(charAtPos).matches() && pos != getModel().getCursorPosition())
            {
                break;
            }
            else
            {
                pos++;
            }
        }
        return pos;
    }
    
    protected boolean deleteWordBeforeCursor() 
    {
        if(this.getModel().getCursorPosition() == 0)
            return false;
        int pos = previousWordPosition();
        String currentText = this.getModel().getText();
        this.getModel().setText(currentText.substring(0, pos) + currentText.substring(this.getModel().getCursorPosition()));
        this.setCursorPosition(pos);
        return true;
    }
    
    protected boolean deleteWordAfterCursor()
    {
        if(this.getModel().getCursorPosition() == this.getModel().getText().length())
            return false;
        int pos = nextWordPosition();
        String currentText = this.getModel().getText();
        this.getModel().setText(currentText.substring(0, getModel().getCursorPosition()) + currentText.substring(pos));
        return true;
    }
    
}