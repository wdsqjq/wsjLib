package per.wangsj.myview.titanic;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import per.wangsj.myview.R;


public class TitanticActivity extends AppCompatActivity {

    private TitanicTextView tvTitantic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_titantic);
        tvTitantic = (TitanicTextView) findViewById(R.id.tvTitantic);
        tvTitantic.setTypeface(Typeface.createFromAsset(getAssets(),"Satisfy-Regular.ttf"));
        new Titanic().start(tvTitantic);
    }
}
