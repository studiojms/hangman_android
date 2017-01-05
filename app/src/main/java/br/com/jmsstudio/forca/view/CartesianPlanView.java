package br.com.jmsstudio.forca.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jms on 02/01/17.
 */
public class CartesianPlanView extends View {

    //armazena a unidade a ser trabalhada
    private int unit;
    //armazena o menor lado do display (horizontal ou vertical)
    private int minorSide;

    private boolean showCartesianPlan = false;

    public CartesianPlanView(Context context) {
        super(context);
    }

    public CartesianPlanView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (getHeight() > getWidth()) {
            minorSide = getWidth();
        } else {
            minorSide = getHeight();
        }
        unit = minorSide / 10;

        drawCartesianPlan(canvas);
    }

    /**
     * Desenha as linhas de um plano cartesiano, baseado na unidade informada (1 décimo do tamanho da tela)
     * @param canvas
     */
    private void drawCartesianPlan(Canvas canvas) {
        if (showCartesianPlan) {
            Path path = new Path();

            int max = unitToPixel(10);

            for (int n = 0; n <= 10; n++) {
                //desenha linhas na vertical
                path.moveTo(unitToPixel(n), 1);
                path.lineTo(unitToPixel(n), max);

                //desenha linhas na horizontal
                path.moveTo(1, unitToPixel(n));
                path.lineTo(max, unitToPixel(n));
            }

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(1);

            canvas.drawPath(path, paint);
        }
    }

    protected int unitToPixel(int value) {
        return value * unit;
    }

    public boolean isShowCartesianPlan() {
        return showCartesianPlan;
    }

    public void setShowCartesianPlan(boolean showCartesianPlan) {
        this.showCartesianPlan = showCartesianPlan;
    }
}
