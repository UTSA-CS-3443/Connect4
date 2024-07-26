package edu.utsa.cs3443.connect4;

import android.graphics.Paint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreditsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        TextView projectManager = (TextView) findViewById(R.id.project_manager);
        projectManager.setPaintFlags(projectManager.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        TextView developer = (TextView) findViewById(R.id.developer);
        developer.setPaintFlags(developer.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        TextView tester = (TextView) findViewById(R.id.tester);
        tester.setPaintFlags(tester.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        TextView designer = (TextView) findViewById(R.id.designer);
        designer.setPaintFlags(designer.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }
}