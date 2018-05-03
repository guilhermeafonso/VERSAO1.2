package staysafepi.staysafepi.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import staysafepi.staysafepi.R;

public class PrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
    }
    public void proximaTela(View view){

        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}


