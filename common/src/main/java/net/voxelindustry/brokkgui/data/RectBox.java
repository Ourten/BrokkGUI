package net.voxelindustry.brokkgui.data;

import java.util.Objects;

public class RectBox
{
    public static RectBox EMPTY = new RectBox(0);
    public static RectBox ONE   = new RectBox(1);
    public static RectBox TWO   = new RectBox(2);

    private final float top;
    private final float left;
    private final float bottom;
    private final float right;

    public RectBox(float top, float left, float bottom, float right)
    {
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
    }

    public RectBox(float all)
    {
        this(all, all, all, all);
    }

    public float getTop()
    {
        return top;
    }

    public float getLeft()
    {
        return left;
    }

    public float getBottom()
    {
        return bottom;
    }

    public float getRight()
    {
        return right;
    }

    public float getHorizontal()
    {
        return left + right;
    }

    public float getVertical()
    {
        return top + bottom;
    }

    public boolean isInside(float x, float y)
    {
        return x > getLeft() && x < getRight() && y > getTop() && y < getBottom();
    }

    public float getSide(RectSide side)
    {
        switch (side)
        {
            case UP:
                return getTop();
            case DOWN:
                return getBottom();
            case LEFT:
                return getLeft();
            case RIGHT:
                return getRight();
        }
        return 0;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RectBox that = (RectBox) o;
        return Float.compare(that.top, top) == 0 &&
                Float.compare(that.left, left) == 0 &&
                Float.compare(that.bottom, bottom) == 0 &&
                Float.compare(that.right, right) == 0;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(top, left, bottom, right);
    }

    @Override
    public String toString()
    {
        return "RectBox{" +
                "top=" + top +
                ", left=" + left +
                ", bottom=" + bottom +
                ", right=" + right +
                '}';
    }

    public static RectBox fromSide(RectSide side, float size)
    {
        switch (side)
        {
            case UP:
                return new RectBox(size, 0, 0, 0);
            case DOWN:
                return new RectBox(0, 0, size, 0);
            case LEFT:
                return new RectBox(0, size, 0, 0);
            case RIGHT:
                return new RectBox(0, 0, 0, size);
        }
        return EMPTY;
    }

    public static RectBox sum(RectBox first, RectBox second)
    {
        return new RectBox(first.top + second.top, first.left + second.left, first.bottom + second.bottom, first.right + second.right);
    }

    public static Builder build()
    {
        return new Builder();
    }

    public static class Builder
    {
        private float top    = -1;
        private float left   = -1;
        private float bottom = -1;
        private float right  = -1;

        public Builder top(float top)
        {
            this.top = top;
            return this;
        }

        public Builder left(float left)
        {
            this.left = left;
            return this;
        }

        public Builder bottom(float bottom)
        {
            this.bottom = bottom;
            return this;
        }

        public Builder right(float right)
        {
            this.right = right;
            return this;
        }

        public Builder all(float all)
        {
            if (top == -1)
                top = all;
            if (left == -1)
                left = all;
            if (bottom == -1)
                bottom = all;
            if (right == -1)
                right = all;
            return this;
        }

        public RectBox create()
        {
            return new RectBox(top == -1 ? 0 : top,
                    left == -1 ? 0 : left,
                    bottom == -1 ? 0 : bottom,
                    right == -1 ? 0 : right);
        }
    }
}
