package com.tiantan.handle.waterhandle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by Administrator on 2016/4/3.
 */
public class MyWaterRingView extends View{

    private float strokeWidth;
    private int xPoint;
    private int yPoint;
    private float radius;
    private Paint paint;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MyWaterRingView.this.flushState();
            //开始绘制 执行onDraw
            MyWaterRingView.this.invalidate();
            if (paint.getAlpha() != 0){
                handler.sendEmptyMessageDelayed(0, 50);
            }

        }
    };

    public MyWaterRingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initView();
    }


    /**
     * 大小测量按系统默认的规则
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN://点击获取圆环的中心位置
                xPoint = (int) event.getX();
                yPoint = (int) event.getY();
                //初始化画笔和重置画笔
                this.initView();
                this.handler.sendEmptyMessage(0);
                break;
            default:
                break;
        }

        return true;
    }

    private void initView() {
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.paint.setColor(Color.RED);
        this.paint.setStyle(Paint.Style.STROKE);//刻画，画线条
        this.paint.setStrokeWidth(strokeWidth);//设置线条的厚度
        this.paint.setAlpha(255); //0-255, 完全透明到不透明

        this.radius = 0;
        this.strokeWidth = 0;
    }

    /**
     * 刷新半径，画笔
     */
    private void flushState(){
        this.radius += 15;
        this.strokeWidth = this.radius/3;
        this.paint.setStrokeWidth(this.strokeWidth);
        int nextAlpha = paint.getAlpha()-20;
        if (nextAlpha <= 20){
            nextAlpha = 0;
        }
        this.paint.setAlpha(nextAlpha);
    }
    /**
     * 绘制内容,需要知道中心点的坐标，所以要重写onTouchEvent
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //处理变化的
        canvas.drawCircle(xPoint,yPoint, radius, paint);
    }


}
