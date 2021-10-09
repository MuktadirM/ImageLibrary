package com.muktadir.photoeditor.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
/**
 * Draw shape on object
 * */
public interface Shape {
    void draw(Canvas canvas, Paint paint);
    void startShape(float x, float y);
    void moveShape(float x, float y);
    void stopShape();
}
