package app.app.rapidlite.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.app.rapidlite.attributes.Constant;
import app.app.rapidlite.attributes.UL;

public class SMSReceiver  extends BroadcastReceiver
{
    public void onReceive(Context context, Intent intent)
    {
        Bundle myBundle = intent.getExtras();
        SmsMessage[] messages = null;
        String strMessage = "";
        if (UL.getPrfStr(context, Constant.SERVICE_STATUS).equalsIgnoreCase("true")){
            if (myBundle != null)
            {
                Object [] pdus = (Object[]) myBundle.get("pdus");
                messages = new SmsMessage[pdus.length];

                for (int i = 0; i < messages.length; i++)
                {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    strMessage += "SMS From: " + messages[i].getOriginatingAddress();
                    strMessage += " : ";
                    strMessage += messages[i].getMessageBody();
                    strMessage += "\n";
                    Log.e("TEST",messages[i].getMessageBody());
                    if (messages[i].getMessageBody().contains("Acc ON!!!")){
                        Log.e("TES","TRUE");
                        Intent newIntent = new Intent(context, BgService.class);
                        newIntent.putExtra("from", messages[i].getOriginatingAddress());
                        ArrayList<String> links = pullLinks(messages[i].getMessageBody());
                        if (links!=null && links.size()>0){
                            newIntent.putExtra("body", links.get(links.size()-1));
                        }
                        context.startService(newIntent);
                    } else {
                        Log.e("TES","FALSE");
                    }
                }

//            Toast.makeText(context, strMessage, Toast.LENGTH_SHORT).show();
            }
        }

    }

    private ArrayList pullLinks(String text) {
        ArrayList<String> links = new ArrayList<String>();

        String regex = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&amp;@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&amp;@#/%=~_()|]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);
        while(m.find()) {
            String urlStr = m.group();
            if (urlStr.startsWith("(") && urlStr.endsWith(")"))
            {
                urlStr = urlStr.substring(1, urlStr.length() - 1);
            }
            links.add(urlStr);
        }
        return links;
    }

}