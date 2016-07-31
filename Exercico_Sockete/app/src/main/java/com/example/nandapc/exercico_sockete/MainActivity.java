package com.example.nandapc.exercico_sockete;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    public void iniciaThread(final TextView tv) throws IOException{
        Log.i("Fernanda","iniciando thread");
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket("192.168.140.2", 10998);
                    InputStream input = socket.getInputStream();
                    final byte[] b = new byte[1024];
                    input.read(b);
                    socket.close();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(new String(b));
                        }
                    });

                } catch (IOException e) {
                    Log.e("Fernanda", "Erro na Thread", e);
                }
            }
        };
        thread.start();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button)findViewById(R.id.button);
        final TextView textView = (TextView) findViewById(R.id.texto);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    iniciaThread(textView);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
