package com.example.arabadireksiyon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class hakkimda extends AppCompatActivity {
    ListView liste ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hakkimda);
        liste=(ListView) findViewById(R.id.lisst);
        String[] komand={"          <<ARACA GÖNDERİLEN KARAKTERLER>> ","İleri    >>    F","Geri    >>     B","Sol   >>    L","Sağ    >>    R","Dur    >>    S","Ön far aç    >>    W","Ön far kapat    >>     w",
                "Arka  far aç    >>    E","Arka  far kapat    >>    e","Korna aç    >>    Z","Korna kapat    >>    z","Park aç    >>    H","Park kapat   >>    z",
                "Sağ sinyal aç    >>    U","Sağ sinyal kapat    >>    u","Sol sinyal aç   >>   V","Sol sinyal kapat   >>   v","Full ac    >>    P", "Full kapat    >>    p",
                "Kilit ac    >>    K","Kilit kapat    >>     k"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,android.R.id.text1,komand);
        liste.setAdapter(adapter);
    }
}