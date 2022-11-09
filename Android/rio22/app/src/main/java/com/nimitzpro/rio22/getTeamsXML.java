package com.nimitzpro.rio22;

import android.content.Context;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class getTeamsXML {

    private Context context;

    public ArrayList<Team> getTeams() {
        return teams;
    }

    private ArrayList<Team> teams = new ArrayList<>();

    public getTeamsXML(Context context) {
        this.context = context;

        InputStream stream = context.getResources().openRawResource(R.raw.teams);
        DocumentBuilder docBuilder;
        Document xmlDoc = null;

        try {
            docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            xmlDoc = docBuilder.parse(stream);
        }catch(Exception e){
            Log.d("parseException", e.toString());
        }

//        Log.d("nimitztest", xmlDoc.getElementsByTagName("name").item(0).getFirstChild().getTextContent());

        NodeList nameList = xmlDoc.getElementsByTagName("name");
        NodeList regionList = xmlDoc.getElementsByTagName("region");
        NodeList descList = xmlDoc.getElementsByTagName("desc");
        NodeList winningsList = xmlDoc.getElementsByTagName("winnings");
        NodeList createdList = xmlDoc.getElementsByTagName("created");
        NodeList imageList = xmlDoc.getElementsByTagName("image");
        NodeList urlList = xmlDoc.getElementsByTagName("url");

        for(int i = 0; i < nameList.getLength(); i++){
            String name = nameList.item(i).getFirstChild().getNodeValue();
            String region = regionList.item(i).getFirstChild().getNodeValue();
            String desc = descList.item(i).getFirstChild().getNodeValue();
            String winnings = winningsList.item(i).getFirstChild().getNodeValue();
            String created = createdList.item(i).getFirstChild().getNodeValue();
            String image = imageList.item(i).getFirstChild().getNodeValue();
            String url = urlList.item(i).getFirstChild().getNodeValue();

            teams.add(new Team(name, region, desc, winnings, created, image, url));
//            Log.d("nimitztest", String.valueOf(i));
        }
//
//        Log.d("nimitztest", "before");
//        Log.d("nimitztest", this.teams.get(6).getName());

    }

    public int getLength(){return teams.size();}
    public Team getTeam(int i){return teams.get(i);}
    public String [] getNames(){
        String names [] = new String[teams.size()];
        for(int i=0;i<getLength();i++){
            names[i] = getTeam(i).getName();
        }
        return names;
    }
}
