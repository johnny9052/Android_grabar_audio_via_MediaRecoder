package com.example.johnny.android_grabar_audio_via_mediarecoder;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

/*La implementacion permite que cuando se termine de reproducir un archivo, se pueda llamar
* otra funcion*/
public class MainActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

    /*******
     * RECORDAR LOS 2 PERMISOS DEL MANIFIEST E IMPLEMENTAR LA INTERFACE DE LA CLASE
     *
     * RECORDAR EN EL CELULAR, ACTIVARLE LOS PERMISOS QUE SE ESTAN SOLICITANDO PARA QUE FUNCIONE
     ***********/



    /*Objeto para grabar audio*/
    MediaRecorder recorder;
    /*Objeto para reproducir el audio*/
    MediaPlayer player;
    /*Archivo donde se almacenara el audio*/
    File archivo;

    Button btnGrabar, btnDetener, btnReproducir;
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Referenciamos los objetos graficos*/
        tv1 = (TextView) this.findViewById(R.id.lblEstado);
        btnGrabar = (Button) findViewById(R.id.btnGrabar);
        btnDetener = (Button) findViewById(R.id.btnDetener);
        btnReproducir = (Button) findViewById(R.id.btnReproducir);
    }


    public void grabar(View v) {
        /*Instanciamos el objeto que permite grabar audio*/
        recorder = new MediaRecorder();
        /*Definimos el microfono como fuente del audio*/
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        /*Definimos el formato de salida en .3GP*/
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        /*Definimos el codec para el .3GP*/
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        /*Obtenemos la ruta absoluta de la SD y se crea un archivo temporal con esa extension*/
        File path = new File(Environment.getExternalStorageDirectory()
                .getPath());
        try {
            /*Creamos el archivo donde se almacenara el audio*/
            archivo = File.createTempFile("temporal", ".3gp", path);
        } catch (IOException e) {

        }

        /*Definimos que el archivo creado es  donde se almacenara el audio*/
        recorder.setOutputFile(archivo.getAbsolutePath());

        try {
            /*Preparamos el archivo para iniciar la grabacion*/
            recorder.prepare();
        } catch (IOException e) {

        }

        /*Iniciamos la grabacion*/
        recorder.start();

        /*Cambiamos el estado del lbl*/
        tv1.setText("Grabando");
        /*Bloqueamos el boton de grabar*/
        btnGrabar.setEnabled(false);
        /*Habilitamos el boton de deneter grabacion*/
        btnDetener.setEnabled(true);
        /*Bloqueamos el boton de grabar*/
        btnReproducir.setEnabled(false);
    }


    public void detener(View v) {
        /*Detenemos la grabacion*/
        recorder.stop();
        /*Liberamos recursos*/
        recorder.release();
        /*Instanciamos el objeto para reproducir el archivo que ha sido grabado*/
        /*Ojo, SOLO SE DEJA LISTO PARA REPRODUCIR, NUNCA SE REPRODUCE*/
        player = new MediaPlayer();

        /*Se referencia la funcion onCompletion definida a lo ultimo, para que cuando termine
        * la reproduccion, se ejecute luego esa funcion*/
        player.setOnCompletionListener(this);

        try {
            /*Definimos el archivo que sera reproducido*/
            player.setDataSource(archivo.getAbsolutePath());
        } catch (IOException e) {

        }
        try {
            /*Lo preparamos para reproducir*/
            player.prepare();
        } catch (IOException e) {

        }

        /*Habilitamos el boton de grabar*/
        btnGrabar.setEnabled(true);
        /*Bloqueamos el boton de detener*/
        btnDetener.setEnabled(false);
        /*Habilitamos el boton de reproducir*/
        btnReproducir.setEnabled(true);
        /*Cambiamos el estado*/
        tv1.setText("Listo para reproducir");
    }

    public void reproducir(View v) {
        /*Se reproduce e archivo*/
        player.start();
        /*Bloquea todos los botones*/
        btnGrabar.setEnabled(false);
        btnDetener.setEnabled(false);
        btnReproducir.setEnabled(false);
        /*Cambia el estado*/
        tv1.setText("Reproduciendo");
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        /*Habilita todos los botones*/
        btnGrabar.setEnabled(true);
        btnDetener.setEnabled(true);
        btnReproducir.setEnabled(true);
        /*Cambia el estado*/
        tv1.setText("Listo");
    }
}
