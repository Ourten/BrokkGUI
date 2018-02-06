package org.yggard.brokkgui.style.adapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.yggard.brokkgui.paint.Texture;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class TextureStyleAdapterTest
{
    private TextureStyleAdapter adapter;

    @Before
    public void init()
    {
        this.adapter = new TextureStyleAdapter();
    }

    @Test
    public void test()
    {
        Texture texture = new Texture("brokkgui:textures/gui/container_background.png");
        assertThat(adapter.decode("url(\"brokkgui:textures/gui/container_background.png\")")).isEqualTo(texture);

        Texture textureUV = new Texture("brokkgui:textures/gui/container_background.png", 100, 100);
        assertThat(adapter.decode("url(\"brokkgui:textures/gui/container_background.png\", 100, 100)")).isEqualTo
                (textureUV);

        Texture textureUVST = new Texture("brokkgui:textures/gui/container_background.png", 16, 16, 32, 32);
        assertThat(adapter.decode("url(\"brokkgui:textures/gui/container_background.png\", 16, 16,32, 32 )"))
                .isEqualTo(textureUVST);
    }

    @Test
    public void error()
    {
        assertThat(adapter.decode("file(brokkgui:textures/gui/container_background.png")).isNull();
    }
}
