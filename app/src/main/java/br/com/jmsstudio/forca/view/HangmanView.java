package br.com.jmsstudio.forca.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.jmsstudio.forca.controller.HangmanController;
import br.com.jmsstudio.forca.model.Member;
import br.com.jmsstudio.forca.model.Side;

/**
 * Created by jms on 02/01/17.
 */
public class HangmanView extends CartesianPlanView {

    private Paint hangmanPaint;
    private Path hangmanPath;
    private HangmanController hangmanController;

    public HangmanView(Context context) {
        super(context);

        hangmanPaint = new Paint();
        hangmanPath = new Path();
        this.setShowCartesianPlan(false);
    }

    public HangmanView(Context context, AttributeSet attrs) {
        super(context, attrs);

        hangmanPaint = new Paint();
        hangmanPath = new Path();
        this.setShowCartesianPlan(false);
    }

    private void drawHangmanBase() {
        hangmanPath.moveTo(unitToPixel(1), unitToPixel(10));
        hangmanPath.lineTo(unitToPixel(3), unitToPixel(10));

        hangmanPath.moveTo(unitToPixel(2), unitToPixel(10));
        hangmanPath.lineTo(unitToPixel(2), unitToPixel(1));

        hangmanPath.rLineTo(unitToPixel(4), 0);

        hangmanPath.rLineTo(0, unitToPixel(1));
    }

    private void drawHead() {
        hangmanPath.addCircle(unitToPixel(6), unitToPixel(3), unitToPixel(1), Path.Direction.CW);
    }

    private void drawBody() {
        hangmanPath.moveTo(unitToPixel(6), unitToPixel(4));
        hangmanPath.lineTo(unitToPixel(6), unitToPixel(7));
    }

    private void drawMember(Member member, Side side) {
        final int memberX = unitToPixel(6);
        int memberY;
        int sideX, sideY;

        if (member == Member.ARM) {
            memberY = unitToPixel(5);
        } else {
            memberY = unitToPixel(7);
        }

        if (side == Side.LEFT) {
            sideX = memberX - unitToPixel(1);
        } else {
            sideX = memberX + unitToPixel(1);
        }
        sideY = memberY + unitToPixel(1);

        hangmanPath.moveTo(memberX, memberY);
        hangmanPath.lineTo(sideX, sideY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawHangmanBase();

        if (this.hangmanController != null && this.hangmanController.getErrors() > 0) {

            drawTriedChars(canvas);

            switch (this.hangmanController.getErrors()) {
                case 1:
                    drawHead();
                    break;
                case 2:
                    drawBody();
                    break;
                case 3:
                    drawMember(Member.ARM, Side.LEFT);
                    break;
                case 4:
                    drawMember(Member.ARM, Side.RIGHT);
                    break;
                case 5:
                    drawMember(Member.LEG, Side.LEFT);
                    break;
                case 6:
                    drawMember(Member.LEG, Side.RIGHT);
                    break;
            }
        }

        drawWord(canvas);

        hangmanPaint.setStyle(Paint.Style.STROKE);
        hangmanPaint.setStrokeWidth(10);
        hangmanPaint.setColor(Color.BLACK);

        canvas.drawPath(hangmanPath, hangmanPaint);
    }

    public void drawWord(Canvas canvas) {
        int startingXAxis = unitToPixel(3) + (unitToPixel(1)/3);
        int yAxis = unitToPixel(10) - 20;

        Paint p = new Paint();
        p.setColor(Color.BLACK);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(3);
        p.setAntiAlias(true);
        p.setTextSize(35);
        p.setTypeface(Typeface.SANS_SERIF);

        char[] wordCharacters = {};
        if (this.hangmanController != null) {
            wordCharacters = this.hangmanController.getWordInCurrentState().toCharArray();
        }

        for (int i = 0; i < wordCharacters.length; i++) {
            String ch = Character.valueOf(wordCharacters[i]).toString();
            if (ch.equals(" ")) {
                canvas.drawLine(startingXAxis + (40 * i) - 5, yAxis + 16, startingXAxis + (40 * i) + 25, yAxis + 16, p);
            } else {
                canvas.drawText(ch, startingXAxis + (40 * i), yAxis, p);
            }
        }
    }

    public void drawTriedChars(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.BLACK);
        p.setAntiAlias(true);
        p.setTextSize(35);
        p.setTypeface(Typeface.SANS_SERIF);


        List<Character> characters = new ArrayList<>();
        characters.addAll(this.hangmanController.getUsedCharacters());

        int totalChars = characters.size();
        for (int i = 0; i < totalChars; i++) {
            canvas.drawText(characters.get(i).toString(), unitToPixel(9), unitToPixel(2) + 35 * i, p);
        }
    }

    public void setHangmanController(HangmanController hangmanController) {
        this.hangmanController = hangmanController;
    }
}
