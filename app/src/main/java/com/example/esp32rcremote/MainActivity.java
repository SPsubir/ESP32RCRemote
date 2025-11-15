package com.example.esp32rcremote;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    ImageView green1, blue1, white1, green2, blue2, white2, mainL, mainR;
    TextView tl1, tl2, tr1, tr2, ip, port;
    LinearLayout LiniarL, LiniarR;
    //ConstraintLayout Main;
    float x = 0, y = 0, dx = 0, dy = 0, Lx, dLy, Ly, setX, setY, pX, TLpY, TLpX, pY, LlastX, LstartX,
            LlastY, LstartY, ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////---------------------------------------------------------Proto beta 1.0111111.1.1.1.11.
    xr = 0, yr = 0, dxR = 0, dyR = 0, LxR, dLyR, LyR, setXr, setYr, pXr, TLpYr, TLpXr, pYr,
            RlastY, RstartY, RlastX, RstartX;
    int TMone, TMtwo, AMone, AMtwo, Iport;//
    String Sip;
    boolean IsStartFirstL = true, IsStartFirstR = true;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_cons), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);///set landscape
        {
            green1 = findViewById(R.id.green1);
            blue1 = findViewById(R.id.blue1);
            white1 = findViewById(R.id.white1);
            green2 = findViewById(R.id.green2);
            blue2 = findViewById(R.id.blue2);
            white2 = findViewById(R.id.white2);
            mainL = findViewById(R.id.mainImageL);
            mainR = findViewById(R.id.mainImageR);//end
            /////////////////Layout
            LiniarL = findViewById(R.id.L1);
            LiniarR = findViewById(R.id.R1);
            tl1 = findViewById(R.id.LText1);
            tl2 = findViewById(R.id.LText2);
            tr1 = findViewById(R.id.RText1);
            tr2 = findViewById(R.id.RText2);
            ip = findViewById(R.id.ip);
            port = findViewById(R.id.port);//end

        }
        //Left
        Runnable Left = new Runnable() {
            @Override
            public void run() {
                LiniarL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        Iport=Integer.parseInt(port.getText().toString());
                        Sip=ip.getText().toString();
                        pX = white1.getX();
                        pY = white1.getY();
                        TLpX = event.getX();//geting x
                        TLpY = event.getY();//geting y
                        if (IsStartFirstL) {
                            InitiateL();
                        }
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                x = event.getX();
                                y = event.getY();
                                break;
                            case MotionEvent.ACTION_MOVE:
                                //dx = event.getX() - x;///try 1
                                //dy = event.getY() - y;
                                stopXYl();
                                ////////////Get quardinate
                                x = event.getX();
                                y = event.getY();
                                break;
                            case MotionEvent.ACTION_UP:
                                x = setX;
                                y = setY;
                                white1.animate().translationX(setX);//X-axis
                                green1.animate().translationX(setX);
                                white1.animate().translationY(setY);//Y-axis
                                blue1.animate().translationY(-setY + dLy * 2f);
                                white1.setX(setX);
                                white1.setY(setY);
                                //setXYl();
                                TMone=scale(white1.getX(),LstartX,LlastX,0,180)+14;
                                TMtwo=scale(white1.getY(),LstartY,LlastY,360,0)+11;//changes the value according to your screen
                                tl2.setText("TM(ch1):" + Float.toString(TMone)
                                        +"\tTM(ch2):" + Float.toString(TMtwo));
                                send();

                                //setXYl();
                                break;
                        }
                        return true;
                    }
                });
            }
        };
        Thread threadL = new Thread(Left);
        threadL.start();
        //Right
        Runnable Right = new Runnable() {
            @Override
            public void run() {
                LiniarR.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        Iport=Integer.parseInt(port.getText().toString());
                        Sip=ip.getText().toString();
                        pXr = white2.getX();
                        pYr = white2.getY();
                        TLpXr = event.getX();
                        TLpYr = event.getY();
                        if (IsStartFirstR) {
                            InitiateR();
                        }
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                xr = event.getX();
                                yr = event.getY();
                                break;
                            case MotionEvent.ACTION_MOVE:
                                //dxR = event.getX() - xr;///try 1
                                //dyR = event.getY() - yr;
                                stopXYr();
                                /*********Debug start********/
                                /*********Debug end*********/
                                xr = event.getX();
                                yr = event.getY();
                                break;
                            case MotionEvent.ACTION_UP:
                                xr = setXr;
                                yr = setYr;
                                white2.animate().translationX(-setXr);//X-axis
                                green2.animate().translationX(-setXr);
                                white2.animate().translationY(setYr);//Y-axis
                                blue2.animate().translationY(-setYr + dLyR * 2f);
                                white2.setX(setXr);
                                white2.setY(setYr);
                                AMone=Math.abs(scale(white2.getX(),RstartX,RlastX,180,0))-13;
                                AMtwo=scale(white2.getY(),RstartY,RlastY,0,180)-5;//changes the value according to your screen
                                tr2.setText("AM(ch3):"+Float.toString(AMone)
                                        +"\tAM(ch4):"+Float.toString(AMtwo));
                                send();
                                //setXYr();
                                break;
                        }
                        return true;
                    }
                });
            }
        };
        Thread threadR = new Thread(Right);
        threadR.start();
    }
    private void InitiateL() {
        //BluetoothAdapter BlueAdap = BluetoothAdapter.getDefaultAdapter();
        dLy = white1.getHeight() / 2f; //needful
        Lx = LiniarL.getWidth();
        Ly = mainL.getHeight();
        /*********Debug---Dont deleter untill finish**********/
        tl1.setText("Thoratal manuver");
        /////////////////////////////Left
        setX = -dLy + Lx / 2f;
        setY = +dLy + Ly / 2f;//to fetch value of cursor position
        LlastX = (2f * setX) + dLy;
        LstartX = (setX / 2f) - (2.5f * dLy);//set final value for ploter
        LlastY = (2f * setY) - dLy;
        LstartY = (setY / 6f) - dLy;//set final value for ploter

        //left white
        white1.animate().translationX(setX);
        white1.animate().translationY(setY);//end
        //left green
        green1.animate().translationX(setX);
        green1.animate().translationY(setY);//end
        //left blue
        blue1.animate().translationX(setX);
        blue1.animate().translationY(setY);//end
        IsStartFirstL = false;
        //bluetooth();
    }//For one time initiation
    private void setXYl() {
        white1.setX(pX + dx);
        green1.setX(pX + dx); //vertical as green
        /*********Debug start********/
        TMone=scale(white1.getX(),LstartX,LlastX,0,180);//changes the value according to your screen
        TMtwo=scale(white1.getY(),LstartY,LlastY,360,0)+11+38;
        tl2.setText("TM(ch1):" + Float.toString(TMone)
                +"\tTM(ch2):" + Float.toString(TMtwo));
        send();
        //send(TMone,TMtwo,true);
        //tl1.setText("x lim:"+Float.toString(2f*setX-dLy)+" seY:"+Float.toString(setY));
        /*********Debug end*********/
        /////Y-axis
        white1.setY(pY + dy);
        blue1.setY(pY + dy); //horizontal as blue
        //onActivityResult();
    }//Only used by stopXY
    private void stopXYl() {
        /*********Debug start********//*
        tl2.setText("x:"+Float.toString(x)+" and Y:"+Float.toString(y));
        tl1.setText("x lim:"+Float.toString(2f*setX-dLy)+" seY:"+Float.toString(setY));
        /*********Debug end*********/
        if (((x <= LlastX && x >= LstartX)) || x == setX) {
            dx = TLpX - x;
        }//x-axis start
        else {
            dx -= (dx >= 0) ? dx : -1;
            x += dx;
        }//x-axis --end--
        if ((y <= LlastY && y >= LstartY) || x == setY) {////y axis start
            dy = TLpY - y;
        } else {
            dy -= (dy >= 0) ? 1 : dy;
            y += dy;
        }//y-axis --end--
        setXYl();
    }//Left
    /*****************************-end of throatlw manuver*****************************/
    private void InitiateR() {
        tr1.setText("Aviation Control");
        dLyR = white2.getHeight() / 2f; //needful
        LxR = LiniarR.getWidth();
        LyR = mainR.getHeight();
        /*********Debug---Dont deleter untill finish**********/
        tr2.setText("I am alive");
        /////////////////////////////Left
        setXr = -dLyR + LxR / 2f;
        setYr = +dLyR + LyR / 2f;//to fetch value of cursor position
        RlastX = (2f * setXr) + dLyR;
        RstartX = (setXr / 2f) - (2.5f * dLyR);//set final value for ploter
        RlastY = (2f * setYr) - dLyR;
        RstartY = (setYr / 6f) - dLyR;//set final value for ploter
        //right white
        white2.animate().translationX(-setXr);
        white2.animate().translationY(setYr);//end
        //right green
        green2.animate().translationX(-setXr);
        green2.animate().translationY(setYr);//end
        //right blue
        blue2.animate().translationX(-setXr);
        blue2.animate().translationY(-setYr+2f*dLyR);//end
        IsStartFirstR = false;
    }//For one time initiation_--------------------------------------------------off limit dont open
    private void setXYr() {
        white2.setX(pXr + dxR);
        green2.setX(pXr + dxR); //vertical as green
        /*********Debug start********/
        AMone=Math.abs(scale(white2.getX(),RstartX,RlastX,180,0))-13-14;//changes the value according to your screen
        AMtwo=scale(white2.getY(),RstartY,RlastY,0,180)-5-19;
        tr2.setText("AM(ch3):"+Float.toString(AMone)
                +"\tAM(ch4):"+Float.toString(AMtwo));
        send();
        //send(AMone,AMtwo,false);
        /*********Debug end*********/
        /////Y-axis
        white2.setY(pYr + dyR);
        blue2.setY(pYr + dyR); //horizontal as blue
        //onActivityResult();
    }//Only used by stopXY
    private void stopXYr() {
        /*********Debug start********/
        //tl2.setText("x:"+Float.toString(x)+" and Y:"+Float.toString(y));
        //tr1.setText("RlastX:"+Float.toString(RlastX)+" RstartX:"+Float.toString(RstartX));
        /*********Debug end*********/
        if ((xr <= RlastX && xr >= RstartX) || xr == setXr) {
            dxR = TLpXr - xr;
        }//x-axis start
        else {
            dxR -= (dxR >= 0) ? 1 : dxR;
            xr += dxR;
        }//x-axis --end--
        if ((yr <= RlastY && yr >= RstartY) || yr == setYr) {////y axis start
            dyR = TLpYr - yr;
        } else {
            dyR -= (dyR >= 0) ? 1 : dyR;
            yr += dyR;
        }//y-axis --end--
        setXYr();
    }//Right

    /*****************************-scale-******************************/
    private int scale(float scl, float Omin, float Omax, float Nmin, float Nmax){
        float OldPerc = (scl-Omin)/(Omax-Omin);
        float NewX = ((Nmax-Nmin)*OldPerc)+Nmin;
        return (int)NewX;
    }
    /*****************************-sending-data-to-Esp**********************************/
    private void send(/*float c1, float c2, boolean chSelect*/){
        MessageSender massageSender = new MessageSender(Sip,Iport);
        //MessageSender massageSender = new MessageSender();
        massageSender.execute("ch1-"+Integer.toString(TMone)+":ch2-"+Integer.toString(TMtwo)+
                "!ch3-"+Integer.toString(AMone)+"*ch4-"+Integer.toString(AMtwo)+"@");
        /*if(chSelect){
            massageSender.execute("ch1-"+Integer.toString((int)c1)+":ch2-"+Integer.toString((int)c2)+"@");
        }else{
            massageSender.execute("ch3-"+Integer.toString((int)c1)+":ch4-"+Integer.toString((int)c2)+"#");
        }
         */
    }
}

/////////////////////////********************Prototype-Beta-1.12.6*****************/////////////////////*
//threading
//wifi connection with sarver
//fix changes register on clicking any where in the screen
// adjust the chanel value according to the screen
