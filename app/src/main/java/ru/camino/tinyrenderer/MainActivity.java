package ru.camino.tinyrenderer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;

import ru.camino.tinyrenderer.utils.TargaImageReader;

public class MainActivity extends Activity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getSimpleName();

    // Change these values to match desired canvas size
    private static final int CANVAS_WIDTH = 100;
    private static final int CANVAS_HEIGHT = 100;

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.activity_main_image);
        findViewById(R.id.activity_main_btn_fit).setOnClickListener(this);
        findViewById(R.id.activity_main_btn_1x).setOnClickListener(this);

        // Bitmap to draw into
        final Bitmap b = Bitmap.createBitmap(CANVAS_WIDTH, CANVAS_HEIGHT, Bitmap.Config.ARGB_8888);
        // Canvas to draw on
        final Canvas c = new Canvas(b);

        // Paint it black to follow the tutorial
        final Paint p = new Paint();
        p.setColor(Color.BLACK);
        c.drawPaint(p);

        draw(c);

        /*
        Bitmap b = null;
        try {
            b = TargaImageReader.getImage(this, "african_head_diffuse.tga.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        mImageView.setImageBitmap(b);
    }

    @Override
    public void onClick(View v) {
        ImageView.ScaleType scaleType = ImageView.ScaleType.FIT_CENTER;
        switch (v.getId()) {
            case R.id.activity_main_btn_fit:
                scaleType = ImageView.ScaleType.FIT_CENTER;
                break;
            case R.id.activity_main_btn_1x:
                scaleType = ImageView.ScaleType.CENTER;
                break;
        }
        mImageView.setScaleType(scaleType);
    }

    /**
     * Perform all your drawings here
     * @param c {@link android.graphics.Canvas} to draw on
     */
    private void draw(Canvas c) {
        line(13, 20, 80, 40, c, Color.WHITE);
        line(20, 13, 40, 80, c, Color.RED);
        line(80, 40, 13, 20, c, Color.RED);
    }

    private void line(int x0, int y0, int x1, int y1, Canvas c, int color) {
        final Paint p = new Paint();
        p.setColor(color);

        for (float t = 0f; t < 1f; t += .01) {
            int x = (int) (x0 * (1f - t) + x1 * t);
            int y = (int) (y0 * (1f - t) + y1 * t);
            c.drawPoint(x, y, p);
        }
    }
}
