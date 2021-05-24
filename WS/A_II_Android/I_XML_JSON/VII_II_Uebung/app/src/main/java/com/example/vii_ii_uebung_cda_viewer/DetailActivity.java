package com.example.vii_ii_uebung_cda_viewer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String fileName = bundle.getString("fileName");
        fillActivity(fileName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }

    private void fillActivity(String fileName){
        Person pat = new Person();
        Person arzt = new Person();
        ArrayList<String> list = new ArrayList<>();
        try {
            InputStream is  = getAssets().open("xml_Dateien/" + fileName);
            DocumentBuilderFactory dbFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuild = dbFact.newDocumentBuilder();
            Document doc = dBuild.parse(is);

            Element element1 = (Element) doc.getElementsByTagName("patientRole").item(0);
            Element element2 = (Element) element1.getElementsByTagName("addr").item(0);

            pat.setStr(getValu("streetName", element2));
            pat.setNr(getValu("houseNumber", element2));
            pat.setOrt(getValu("city", element2));
            pat.setPlz(getValu("postalCode", element2));

            element2 = (Element) element1.getElementsByTagName("name").item(0);
            pat.setVor(getValu("given", element2));
            pat.setNach(getValu("family", element2));

            element1 = (Element) doc.getElementsByTagName("intendedRecipient").item(0);
            element2 = (Element) element1.getElementsByTagName("addr").item(0);

            arzt.setStr(getValu("streetName", element2));
            arzt.setNr(getValu("houseNumber", element2));
            arzt.setOrt(getValu("city", element2));
            arzt.setPlz(getValu("postalCode", element2));

            element2 = (Element) element1.getElementsByTagName("name").item(0);
            arzt.setVor(getValu("given", element2));
            arzt.setNach(getValu("family", element2));

            element1 = (Element) doc.getElementsByTagName("structuredBody").item(0);
            NodeList nl = element1.getElementsByTagName("component");

            for(int i = 0; i < nl.getLength(); i++){
                element2 = (Element) nl.item(i);
                list.add("" + getValu("title", element2) + " --- " + getValu("text", element2));
            }



        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        TextView tv = findViewById(R.id.patVor);
        tv.setText(pat.getVor());
        tv = findViewById(R.id.patNach);
        tv.setText(pat.getNach());
        tv = findViewById(R.id.patOrt);
        tv.setText(pat.getOrt());
        tv = findViewById(R.id.patPZL);
        tv.setText(pat.getPlz());
        tv = findViewById(R.id.patNr);
        tv.setText(pat.getNr());
        tv = findViewById(R.id.patStr);
        tv.setText(pat.getStr());

        tv = findViewById(R.id.arztVor);
        tv.setText(arzt.getVor());
        tv = findViewById(R.id.arztNach);
        tv.setText(arzt.getNach());
        tv = findViewById(R.id.arztPZL);
        tv.setText(arzt.getPlz());
        tv = findViewById(R.id.arztOrt);
        tv.setText(arzt.getOrt());
        tv = findViewById(R.id.arztNr);
        tv.setText(arzt.getNr());
        tv = findViewById(R.id.arztStr);
        tv.setText(arzt.getStr());

        ListView lv = findViewById(R.id.listView);
        ArrayAdapter<String> aa = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(aa);
    }

    private String getValu(String tag, Element element){
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }
}