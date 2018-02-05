package org.yggard.brokkgui.style;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.yggard.brokkgui.style.tree.StyleList;
import org.yggard.brokkgui.style.tree.StyleSelector;
import org.yggard.brokkgui.style.tree.StyleSelectorHierarchic;
import org.yggard.brokkgui.style.tree.StyleSelectorType;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class StylesheetManagerTest
{
    @Test
    public void parseSimpleCSS()
    {
        StyleList list = null;
        try
        {
            list = StylesheetManager.getInstance().loadStylesheets("/assets/brokkgui/css/test.css");
        } catch (ExecutionException e)
        {
            e.printStackTrace();
        }

        assertThat(list).isNotNull();
        assertThat(list.getInternalStyleList()).hasSize(5);
        assertThat(list.getInternalStyleList().get(0).getSelector()).isInstanceOf(StyleSelector.class);

        StyleSelector wildcard = (StyleSelector) list.getInternalStyleList().get(0).getSelector();
        assertThat(wildcard.getSelectors().get(0).getKey()).isEqualTo(StyleSelectorType.WILDCARD);

        assertThat(list.getInternalStyleList().get(2).getSelector()).isInstanceOf(StyleSelectorHierarchic.class);
        StyleSelectorHierarchic hierarchic = (StyleSelectorHierarchic) list.getInternalStyleList().get(2).getSelector();

        assertThat(hierarchic.isDirectChild()).isTrue();
        assertThat(hierarchic.getChildSelector()).isInstanceOf(StyleSelector.class);
        assertThat(hierarchic.getParentSelector()).isInstanceOf(StyleSelector.class);

        StyleSelector childSelector = (StyleSelector) hierarchic.getChildSelector();
        StyleSelector parentSelector = (StyleSelector) hierarchic.getParentSelector();

        assertThat(childSelector.getSelectors().get(0).getKey()).isEqualTo(StyleSelectorType.CLASS);
        assertThat(childSelector.getSelectors().get(0).getValue()).isEqualTo("snowflakes");

        assertThat(parentSelector.getSelectors().get(0).getKey()).isEqualTo(StyleSelectorType.TYPE);
        assertThat(parentSelector.getSelectors().get(0).getValue()).isEqualTo("pane");
    }
}
