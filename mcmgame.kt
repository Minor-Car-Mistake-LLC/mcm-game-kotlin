import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final String FILE_NAME = "minorcarmistake/save.txt";

    int carnum = 1;
    int hits = 15;
    int funamm = 1;
    int points = 0;
    int rep = 0;
    int key = 0;
    String car = "\uD83D\uDE97";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.e("MainActivity", "Storage not available or read only");
            return;
        }

        File file = new File(getExternalFilesDir(null), FILE_NAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
                writeToFile('{"carnum":' + carnum + ', "hits":' + hits + ', "funamm":' + funamm + ', "points":' +  points + '}');
            } catch (IOException e) {
                Log.e("MainActivity", "Error creating file", e);
            }
        } else {
            try {
                String content = readFromFile();
                JSONObject json = new JSONObject(content);
                carnum = json.getInt("carnum");
                hits = json.getInt("hits");
                funamm = json.getInt("funamm");
                points = json.getInt("points");
            } catch (JSONException | IOException e) {
                Log.e("MainActivity", "Error reading file", e);
            }
        }

        Thread saveThread = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    try {
                        TimeUnit.SECONDS.sleep(240);
                        writeToFile('{"carnum":' + carnum + ', "hits":' + hits + ', "funamm":' + funamm + ', "points":' +  points + '}');
                    } catch (InterruptedException | IOException e) {
                        Log.e("MainActivity", "Error saving file", e);
                    }
                }
            }
        };
        saveThread.start();
    }

    private void clearScreen() {
        TextView textView = findViewById(R.id.text_view);
        textView.setText("");
    }

    public void openShop(View view) {
        // TODO: Implement shop functionality
    }

   
