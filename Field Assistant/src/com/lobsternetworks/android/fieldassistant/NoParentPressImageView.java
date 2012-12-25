/**
 * Created on Aug 27, 2011 by Dave Smith
 * Wireless Designs, LLC
 *
 * NoParentPressImageView.java
 * ImageView that ignores press state changes from the parent
 * http://wiresareobsolete.com/2011/08/clickable-zones-in-listview-items/
 */
package com.lobsternetworks.android.fieldassistant;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import com.lobsternetworks.android.fieldassistant.R;

public class NoParentPressImageView extends ImageView {
    
    public NoParentPressImageView(Context context) {
        this(context, null);
    }
    
    public NoParentPressImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    @Override
    public void setPressed(boolean pressed) {
        // If the parent is pressed, do not set to pressed.
        if (pressed && ((View) getParent()).isPressed()) {
            return;
        }
        super.setPressed(pressed);
    }
}
