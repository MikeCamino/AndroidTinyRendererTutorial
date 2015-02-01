package ru.camino.tinyrenderer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import ru.camino.tinyrenderer.utils.TargaImageReader;
import ru.camino.tinyrenderer.utils.Timing;

public class MainActivity extends Activity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getSimpleName();

    // Change these values to match desired canvas size
    private static final int CANVAS_WIDTH = 100;
    private static final int CANVAS_HEIGHT = 100;

    private ImageView mImageView;
    private TextView mInfoPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.activity_main_image);
        mInfoPanel = (TextView) findViewById(R.id.activity_main_info);
        findViewById(R.id.activity_main_btn_fit).setOnClickListener(this);
        findViewById(R.id.activity_main_btn_1x).setOnClickListener(this);

        new DrawAsyncTask().execute((Void[]) null);

        /*
        Bitmap b = null;
        try {
            b = TargaImageReader.getImage(this, "african_head_diffuse.tga.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
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

    private class DrawAsyncTask extends AsyncTask<Void, Integer, Void> {
        private Bitmap mBitmap;
        private Canvas mCanvas;
        private Timing mDrawTiming;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mDrawTiming = new Timing("Draw time");
            mDrawTiming.start();

            // Bitmap to draw into
            mBitmap = Bitmap.createBitmap(CANVAS_WIDTH, CANVAS_HEIGHT, Bitmap.Config.ARGB_8888);
            // Canvas to draw on
            mCanvas = new Canvas(mBitmap);

            // Paint it black to follow the tutorial
            final Paint p = new Paint();
            p.setColor(Color.BLACK);
            mCanvas.drawPaint(p);
        }

        @Override
        protected Void doInBackground(Void... params) {
            draw(mCanvas);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mImageView.setImageBitmap(mBitmap);

            mDrawTiming.stop();
            mInfoPanel.setText(mDrawTiming.toString());
        }
    }
}
