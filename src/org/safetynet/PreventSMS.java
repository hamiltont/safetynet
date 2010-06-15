package org.safetynet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Prevents incoming and outgoing SMS messages.
 * 
 * Potential modifiers: - HIDE messages. Rather than actually throwing messages
 * away, simply store them internally and hide them from showing immediately. If
 * the device is recovered then the application can be opened and all of the SMS
 * message can be released <br />
 * <br />
 * <br />
 * ALLOW UNKNOWN CONTACTS - If someone sends or receives a message to/from a
 * contact whose number is not stored in the phone, then allow that message to
 * come thru. This could be useful if a thief was communicating with an
 * accomplice
 * 
 * 
 * 
 * @author Hamilton Turner
 * 
 */
public class PreventSMS extends BroadcastReceiver {

	private static final boolean STOP_INCOMING = true;

	static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";

	// TODO implement these features
	private static final boolean HIDE = false;
	private static final boolean ALLOW_UNKNOWN = false;
	// see
	// http://stackoverflow.com/questions/990558/android-broadcast-receiver-for-sent-sms-messages
	private static final boolean STOP_OUTGOING = true;

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(ACTION)) {

			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				Object o = bundle.get("pdus");

				// This could happen if some program other than the default one
				// sent the SMS and didn't properly create the Intent (note:
				// look into android source to see if this is in fact possible)
				if (o == null)
					Log.i("sn", "Null pointer exception about to occur");

				Object[] pdus = (Object[]) o;
				final SmsMessage[] messages = new SmsMessage[pdus.length];
				for (int i = 0; i < pdus.length; i++)
					messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);

				if (pdus.length != 1)
					Log.i("sn", "Received " + pdus.length + " messages");

				for (SmsMessage s : messages)
					Log.i("sn", "Message: " + s.getDisplayMessageBody());

			}

		}
	}
}