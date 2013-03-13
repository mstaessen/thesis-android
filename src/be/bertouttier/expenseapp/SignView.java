package be.bertouttier.expenseapp;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class SignView extends View {
	
	private Bitmap _bitmap;
	private Canvas _canvas;
	private Path _path;
	private Paint _bitmapPaint;
	private Paint _paint;
	
	public SignView(Context c, AttributeSet attrs)
	{
		super(c, attrs);
		_path = new Path();
		_bitmapPaint = new Paint();
		_bitmapPaint.setDither(true);
		_bitmapPaint.setColor(Color.BLACK);

		_paint = new Paint();
		_paint.setAntiAlias(true);
		_paint.setDither(true);
		_paint.setColor(Color.BLACK);
		_paint.setStyle(Paint.Style.STROKE);
		_paint.setStrokeJoin(Paint.Join.ROUND);
		_paint.setStrokeCap(Paint.Cap.ROUND);
		_paint.setStrokeWidth(5f);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		super.onSizeChanged(w, h, oldw, oldh);
		_bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		_canvas = new Canvas(_bitmap);
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		canvas.drawColor(Color.WHITE);			
		canvas.drawBitmap(_bitmap, 0, 0, _bitmapPaint);
		canvas.drawPath(_path, _paint);
	}
	
	private float _mX, _mY;
	private float TouchTolerance = 4;
	
	private void TouchStart(float x, float y)
	{
		_path.reset();
		_path.moveTo(x, y);
		_mX = x;
		_mY = y;
	}
	
	private void TouchMove(float x, float y)
	{
		float dx = Math.abs(x - _mX);
		float dy = Math.abs(y - _mY);
		
		if (dx >= TouchTolerance || dy >= TouchTolerance)
		{
			_path.quadTo(_mX, _mY, (x + _mX) / 2, (y + _mY) / 2);
			_mX = x;
			_mY = y;
		}
	}
	
	private void TouchUp()
	{
		_path.lineTo(_mX, _mY);
		_canvas.drawPath(_path, _paint);
		
		_path.reset();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		float x = e.getX();
		float y = e.getY();
		
		switch (e.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			TouchStart(x, y);
			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
			TouchMove(x, y);
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			TouchUp();
			invalidate();
			break;
		}
		return true;
	}
	
	public void clearCanvas()
	{
		_canvas.drawColor(Color.WHITE);
		invalidate();
	}
	
	public Bitmap canvasBitmap()
	{
		return _bitmap;
	}

}
