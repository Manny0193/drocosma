package com.parrot.freeflight.activities;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parrot.freeflight.CommandRecognitionTest;
import com.parrot.freeflight.R;
import com.parrot.freeflight.activities.base.ParrotActivity;
import com.parrot.freeflight.receivers.DroneBatteryChangedReceiverDelegate;
import com.parrot.freeflight.receivers.DroneConnectionChangeReceiverDelegate;
import com.parrot.freeflight.receivers.DroneConnectionChangedReceiver;
import com.parrot.freeflight.receivers.DroneFlyingStateReceiver;
import com.parrot.freeflight.receivers.DroneFlyingStateReceiverDelegate;
import com.parrot.freeflight.receivers.DroneReadyReceiver;
import com.parrot.freeflight.receivers.DroneReadyReceiverDelegate;
import com.parrot.freeflight.service.DroneControlService;

public class TestActivity extends ParrotActivity implements ServiceConnection, DroneReadyReceiverDelegate, DroneConnectionChangeReceiverDelegate, DroneFlyingStateReceiverDelegate, DroneBatteryChangedReceiverDelegate
{


    private static final String TAG = TestActivity.class.getSimpleName();
// /   TextView textView = (TextView) findViewById(R.id.commandTextView);
//
    Intent intent;
    private SpeechRecognizer speechRecognizer;
  //  private SpeechRecognizerManager mSpeechManager;

    private static DroneControlService mService;
    private DroneFlyingStateReceiver droneBatteryStateReceiver;

    private BroadcastReceiver droneReadyReceiver;
    private BroadcastReceiver droneConnectionChangeReceiver;
    private DroneFlyingStateReceiver droneFlyingStateReceiver;

    private static boolean isFliying = false;
    private static TestActivity testActivity;

    public static void setResults(int result){
        //((TextView) testActivity.findViewById(R.id.errorTextView)).setText(result);
     //   speechRecognizer.stopListening();
       // int i;
        switch (result){

            case 0:
                if (!isFliying) {
                    mService.triggerTakeOff(); //Despegar el drone
                    ((TextView) testActivity.findViewById(R.id.commandTextView)).setText("Command: Take off");
                }else
                    ((TextView) testActivity.findViewById(R.id.errorTextView)).setText("Error: Drone is fliying");
                    ((TextView) testActivity.findViewById(R.id.commandTextView)).setText("Command: Take off");
                break;
            case 1:
                if (isFliying) {
                    mService.triggerTakeOff();   //Aterrizar el drone
                    ((TextView) testActivity.findViewById(R.id.commandTextView)).setText("Command: Landing");
                    break;
                }else
                    ((TextView) testActivity.findViewById(R.id.commandTextView)).setText("Command: Landing");
                    ((TextView) testActivity.findViewById(R.id.errorTextView)).setText("Error: Drone is not fliying");
                break;
            case 2:
                mService.setYaw(-0.1f); // Girar a la izquierda
                ((TextView) testActivity.findViewById(R.id.commandTextView)).setText("Command: Turn Left");
                //try {
                 //   Thread.sleep(1000);
               // } catch (InterruptedException e) {
                //    e.printStackTrace();
                //}
                //mService.setYaw(0);


                break;
            case 3:
                mService.setYaw(0.1f);   //Girar a la derecha
                ((TextView) testActivity.findViewById(R.id.commandTextView)).setText("Command: Turn right");
                //try {
                 //   Thread.sleep(1000);
                //} catch (InterruptedException e) {
                 //   e.printStackTrace();
                //}
                //mService.setYaw(0);
                break;
            case 4:
                mService.setGaz(0.5f); //Ascender
                ((TextView) testActivity.findViewById(R.id.commandTextView)).setText("Command: Moving up");
                //try {
                  //  Thread.sleep(500);
                //} catch (InterruptedException e) {
                  //  e.printStackTrace();
                //}
                //mService.setGaz(0);

                break;
            case 5:
               mService.setGaz(-0.5f); //Descender
                ((TextView) testActivity.findViewById(R.id.commandTextView)).setText("Command: Moving down");
                //try {
                 //   Thread.sleep(500);
                //} catch (InterruptedException e) {
                //    e.printStackTrace();
                //}
                //mService.setGaz(0);
                break;

            case 6:
                mService.setYaw(0f);
                mService.setProgressiveCommandEnabled(true);
                mService.setPitch(-0.3f);   //Mover hacia delante
                ((TextView) testActivity.findViewById(R.id.commandTextView)).setText("Command: Moving Forward");
               // try {
                //    Thread.sleep(1000);
                //} catch (InterruptedException e) {
                //    e.printStackTrace();
               // }
               // mService.setPitch(0);
                //mService.setProgressiveCommandEnabled(false);
                break;
            case 7:
                mService.setYaw(0f);
                mService.setProgressiveCommandEnabled(true);
                mService.setPitch(0.3f);    //Mover hacia detras
                ((TextView) testActivity.findViewById(R.id.commandTextView)).setText("Command: Moving backward");
                //try {
                 //   Thread.sleep(1000);
                //} catch (InterruptedException e) {
                //    e.printStackTrace();
                //}
                //mService.setPitch(0);
                //mService.setProgressiveCommandEnabled(false);
                break;
            case 8:
                mService.setProgressiveCommandEnabled(true);
                mService.setRoll(-0.5f); //Mover izquierda
                ((TextView) testActivity.findViewById(R.id.commandTextView)).setText("Command: Moving left");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mService.setRoll(0);
                mService.setProgressiveCommandEnabled(false);
                break;
            case 9:
                mService.setProgressiveCommandEnabled(true);
                mService.setRoll(0.5f);  //Mover Derecha
                ((TextView) testActivity.findViewById(R.id.commandTextView)).setText("Command: Moving right");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mService.setRoll(0);
                mService.setProgressiveCommandEnabled(false);
                break;
            case 10:

                mService.setPitch(0);
                mService.setRoll(0);
                mService.setGaz(0);
                mService.setYaw(0);
                mService.setProgressiveCommandEnabled(false);
                mService.setDeviceOrientation(0, 0);
                ((TextView) testActivity.findViewById(R.id.commandTextView)).setText("Command: Stop");
                break;
        }






    }

    public static void setErrorText(String Result){
        ((TextView)testActivity.findViewById(R.id.errorTextView)).setText(Result + " " );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        droneReadyReceiver = new DroneReadyReceiver(this);
        droneConnectionChangeReceiver = new DroneConnectionChangedReceiver(this);
        droneFlyingStateReceiver = new DroneFlyingStateReceiver(this);

        bindService(new Intent(this, DroneControlService.class), this, Context.BIND_AUTO_CREATE);

        LocalBroadcastManager localBroadcastMgr = LocalBroadcastManager.getInstance(getApplicationContext());
        localBroadcastMgr.registerReceiver(droneFlyingStateReceiver, new IntentFilter(DroneControlService.DRONE_FLYING_STATE_CHANGED_ACTION));

        CommandRecognitionTest commandListener = new CommandRecognitionTest();

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(commandListener);




        testActivity = this;

        findViewById(R.id.listenButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
                intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "voice.recognition.test");
                intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, Long.valueOf(100));
                intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, Long.valueOf(100));
                intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, Long.valueOf(100));


                TextView textView = (TextView) findViewById(R.id.commandTextView);

                speechRecognizer.cancel();
                speechRecognizer.startListening(intent);

                textView.setText("Command: Started Listening");

                /*if(mSpeechManager==null)
                {
                    SetSpeechListener();
                }
                else if(!mSpeechManager.ismIsListening())
                {
                    mSpeechManager.destroy();
                    SetSpeechListener();
                }
                textView.setText("Vamo' a hablarno");


                ((TextView) testActivity.findViewById(R.id.errorTextView)).setText("Error: MMG ENTRE");*/

            }
        });




    }







    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        unbindService(this);
        Log.d(TAG, "Connect activity destroyed");
    }


    @Override
    protected void onPause()
    {
        super.onPause();

        if (mService != null) {
            mService.pause();
        }

        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getApplicationContext());
        manager.unregisterReceiver(droneReadyReceiver);
        manager.unregisterReceiver(droneConnectionChangeReceiver);
    }


    @Override
    protected void onResume()
    {
        super.onResume();

        if (mService != null)
            mService.resume();

        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getApplicationContext());
        manager.registerReceiver(droneReadyReceiver, new IntentFilter(DroneControlService.DRONE_STATE_READY_ACTION));
        manager.registerReceiver(droneConnectionChangeReceiver, new IntentFilter(
                DroneControlService.DRONE_CONNECTION_CHANGED_ACTION));


    }


    public void onServiceConnected(ComponentName name, IBinder service)
    {
        mService = ((DroneControlService.LocalBinder) service).getService();

        mService.resume();
        mService.requestDroneStatus();
    }

    public void onDroneConnected()
    {
        // We still waiting for onDroneReady event
        mService.requestConfigUpdate();
        System.out.println("Stablishing connection with the drone");

        mService.setPitch(0);
        mService.setRoll(0);
        mService.setGaz(0);
        mService.setYaw(0);
        mService.setDeviceOrientation(0, 0);


        Button takeOffLandButton= (Button) findViewById(R.id.buttonTakeoffLand);
        Button moveDownButton = (Button) findViewById(R.id.buttonMoveDownTest);
        Button moveUpButton = (Button) findViewById(R.id.buttonMoveUpTest);
        Button moveLeftButton = (Button) findViewById(R.id.buttonMoveLeftTest);
        Button moveRightButton = (Button) findViewById(R.id.buttonMoveRightTest);
        Button moveBackwardButton = (Button) findViewById(R.id.buttonMoveBackwardTest);
        Button moveForwardButton = (Button) findViewById(R.id.buttonMoveForwardTest);
        Button turnRightButton = (Button) findViewById(R.id.buttonTurnRightTest);
        Button turnLeftButton = (Button) findViewById(R.id.buttonTurnLeftTest);
      //  TextView batteryText = (TextView) findViewById(R.id.textViewBattery);

        // final EditText valorTexto = (EditText) findViewById(R.id.valorText);

        moveDownButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //when pressing the button
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mService.setGaz(-0.5f);
                }
                //When releasing the btton
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    mService.setGaz(0);
                }
                return true;
            }
        });

        moveUpButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //when pressing the button
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mService.setGaz(0.5f);
                    mService.setPitch(0.5f);
                }
                //When releasing the btton
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    mService.setGaz(0);
                    mService.setPitch(0);
                }
                return true;
            }
        });

        moveLeftButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //when pressing the button
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mService.setProgressiveCommandEnabled(true);
                    mService.setRoll(-1f);

                }
                //When releasing the btton
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    mService.setProgressiveCommandEnabled(false);
                    mService.setRoll(0);
                }

                return true;
            }
        });
        moveRightButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //when pressing the button
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mService.setProgressiveCommandEnabled(true);
                    mService.setRoll(1f);
                }
                //When releasing the btton
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    mService.setProgressiveCommandEnabled(false);
                    mService.setRoll(0);
                }
                return true;
            }
        });
        moveForwardButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //when pressing the button
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mService.setProgressiveCommandEnabled(true);

                    mService.setPitch(-1f);
                }
                //When releasing the btton
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    mService.setProgressiveCommandEnabled(false);
                    mService.setPitch(0f);
                }
                return true;
            }
        });
        moveBackwardButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //when pressing the button
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mService.setProgressiveCommandEnabled(true);
                    mService.setPitch(1f);

                }
                //When releasing the btton
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    mService.setProgressiveCommandEnabled(false);
                    mService.setPitch(0);

                }
                return true;
            }
        });
        turnLeftButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //when pressing the button
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    mService.setYaw(1f);
                    //mService.setYaw(0.5f);
                }
                //When releasing the btton
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    mService.setYaw(0);
                }
                return true;
            }
        });

                turnRightButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //when pressing the button
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    mService.setYaw(-1f);
                    //mService.setYaw(0.5f);
                }
                //When releasing the btton
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    mService.setYaw(0);
                }
                return true;
            }
        });

        takeOffLandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mService.triggerTakeOff();
            }
        });





    }



    public void onDroneReady()
    {
        System.out.println("DRONE CONNECTION STABLISHED MODAFOKA");
     //   mService.triggerTakeOff();
    }


    public void onDroneDisconnected()
    {
        // Left unimplemented
        System.out.println("DISCONECTED, DO I KNOW, SOMETHING HAPPENED?");
    }


    public void onServiceDisconnected(ComponentName name)
    {
        // Left unimplemented
    }


    @Override
    public void onDroneFlyingStateChanged(boolean flying) {

        ((Button) findViewById(R.id.buttonTakeoffLand)).setText(flying ? "Land":"Take Off");
        isFliying = !isFliying;
    }

    @Override
    public void onDroneBatteryChanged(int value) {
        //((TextView)findViewById(R.id.textViewBattery)).setText(" " + value );
    }

   /* private void SetSpeechListener() {
        mSpeechManager = new SpeechRecognizerManager(this, new SpeechRecognizerManager.onResultsReady() {
            @Override
            public void onResults(ArrayList<String> results) {


                if (results != null && results.size() > 0) {

                    if (results.size() == 1) {
                        mSpeechManager.destroy();
                        mSpeechManager = null;
                        textView.setText(results.get(0));
                    } else {
                        StringBuilder sb = new StringBuilder();
                        if (results.size() > 5) {
                            results = (ArrayList<String>) results.subList(0, 5);
                        }
                        for (String result : results) {
                            sb.append(result).append("\n");
                        }
                        textView.setText(sb.toString());
                    }
                } else
                    textView.setText("No Matching Results");
            }
        });
    }*/
}
